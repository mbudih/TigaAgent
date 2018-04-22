package com.tiga.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.Query;
import com.tiga.agent.LoginActivity;
import com.tiga.agent.R;
import com.tiga.firebase.FirebaseAdapterInvoice;
import com.tiga.firebase.FirebaseDB;
import com.tiga.firebase.model.Invoice;
import com.tiga.stok.StokActivity;

import java.util.Date;

/**
 * Created by adikwidiasmono on 15/11/17.
 */

public class StockFragment extends Fragment
        implements FirebaseAdapterInvoice.FirebaseAdapterInvoiceListener {

    private static final String EXTRA_TEXT = "text";

    private FirebaseAdapterInvoice adapter;
    private RecyclerView recyclerView;

    public static StockFragment createFor(String text) {
        StockFragment fragment = new StockFragment();
        Bundle args = new Bundle();
        args.putString(EXTRA_TEXT, text);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.startListening();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_stock, container, false);

        recyclerView = v.findViewById(R.id.rv_invoice);

        return v;
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        Query query = FirebaseDB.init().getDBReference()
                .child(FirebaseDB.REF_INVOICES)
                .orderByChild("CreateDate").startAt(-1 * new Date().getTime());

        FirebaseRecyclerOptions<Invoice> options = new FirebaseRecyclerOptions.Builder<Invoice>()
                .setQuery(query, Invoice.class)
                .build();

        adapter = new FirebaseAdapterInvoice(options, getActivity(), this);

        // Attach the adapter to the recyclerview to populate items
        recyclerView.setAdapter(adapter);

        // Set layout manager to position the items
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);

        recyclerView.setLayoutManager(mLayoutManager);
    }

    @Override
    public void onInvoiceClicked(int position, Invoice invoice) {
        Intent intent = new Intent();
        intent.setClass(getActivity().getApplicationContext(), StokActivity.class);
        intent.putExtra("INVOICE", invoice);
        startActivity(intent);
    }

}