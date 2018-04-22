package com.tiga.fragment;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;
import com.tiga.agent.LoginActivity;
import com.tiga.agent.R;
import com.tiga.firebase.FirebaseDB;
import com.tiga.firebase.model.penjualan.Penjualan;
import com.tiga.firebase.model.penjualan.TransactionItem;
import com.tiga.riwayat.DetailRiwayatActivity;
import com.tiga.utils.AppUtils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by adikwidiasmono on 15/11/17.
 */

public class AccountFragment extends Fragment {

    private FirebaseRecyclerAdapter fbAdapter;
    private RecyclerView recyclerView;

    private String strAgentId;
    private int year, month, day;
    private DatePicker datePicker;
    private Calendar calendar;
    LinearLayout linearLayout;


    public static AccountFragment createFor(String text) {
        AccountFragment fragment = new AccountFragment();
        Bundle args = new Bundle();
        args.putString("text", text);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        fbAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        fbAdapter.stopListening();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_account, container, false);

        recyclerView = v.findViewById(R.id.rv_history);
        Intent intent = getActivity().getIntent();
        strAgentId = intent.getStringExtra("AgentId");

        return v;
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        Query query = FirebaseDatabase.getInstance()
                .getReference().child(FirebaseDB.REF_PENJUALAN).orderByChild("transactionDate");

        FirebaseRecyclerOptions options = new FirebaseRecyclerOptions
                .Builder<Penjualan>()
                .setQuery(query, Penjualan.class)
                .build();

        fbAdapter = new FirebaseRecyclerAdapter<Penjualan, ViewHolder>(options) {
            @Override
            public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_history, parent, false);

                return new ViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(ViewHolder holder, int position, final Penjualan penjualan) {
                StringBuilder sb = new StringBuilder();
                for (TransactionItem ti : penjualan.getItems()) {
                    Picasso.with(getActivity())
                            .load(ti.getImageURL())
                            .placeholder(R.drawable.history)
                            .error(R.drawable.ic_error)
                            .into(holder.ivItem);

                    sb.append(ti.getQuantity() + " unit " + ti.getProduct() + " Rp " + AppUtils.getIDR(ti.getPrice())+"\n");
                }
                holder.tvTransDetail.setText(sb.toString());

                Timestamp stamp = new Timestamp(penjualan.getTransactionDate());
                holder.tvTransDate.setText(new SimpleDateFormat("dd MMMM yyyy")
                        .format(new Date(stamp.getTime())));
                holder.tvTransDetail.setText(sb.toString());

                if (penjualan.getKKSOwner()!=null) {
                    holder.tvItemType.setText(getResources().getString(R.string.title_subsidi));
                } else holder.tvItemType.setText(getResources().getString(R.string.title_non_subsidi));

                if (!penjualan.getKKSNo().toString().equals("0")) {
                    holder.tvItemType.setText(getResources().getString(R.string.title_subsidi));
                } else holder.tvItemType.setText(getResources().getString(R.string.title_non_subsidi));

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.putExtra("PENJUALAN", penjualan);
                        intent.setClass(getActivity().getApplicationContext(), DetailRiwayatActivity.class);
                        startActivity(intent);
                    }
                });
            }
        };

        recyclerView.setAdapter(fbAdapter);

        // Set layout manager to position the items
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        // Reverse adding new data
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);

        recyclerView.setLayoutManager(mLayoutManager);


    }


    private class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvItemType, tvTransDetail, tvTransDate;
        private ImageView ivItem;
        private EditText etInputDate;

        public ViewHolder(View itemView) {
            super(itemView);

            tvItemType = itemView.findViewById(R.id.tv_item_type);
            tvTransDetail = itemView.findViewById(R.id.tv_trans_detail);
            tvTransDate = itemView.findViewById(R.id.tv_trans_date);

            ivItem = itemView.findViewById(R.id.iv_item);

            etInputDate = itemView.findViewById(R.id.et_input);
        }
    }

    private Dialog showCalendarPicker() {
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), dateSetListener, year, month, day);
    }

    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {

        }
    };
}