package com.tiga.firebase;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.squareup.picasso.Picasso;
import com.tiga.agent.R;
import com.tiga.firebase.model.Invoice;
import com.tiga.firebase.model.InvoiceItem;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by adikwidiasmono on 21/11/17.
 */

public class FirebaseAdapterInvoice extends
        FirebaseRecyclerAdapter<Invoice, FirebaseAdapterInvoice.ViewHolder> {

    private Activity activity;
    private FirebaseAdapterInvoiceListener listener;

    public FirebaseAdapterInvoice(FirebaseRecyclerOptions options, Activity activity,
                                  FirebaseAdapterInvoiceListener listener) {
        super(options);
        this.activity = activity;
        this.listener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvInvoiceNo, tvInvoiceDate, tvInvoiceItems, tvLastStatus;
        private ImageView ivInvoice;

        public ViewHolder(View itemView) {
            super(itemView);

            tvInvoiceNo = itemView.findViewById(R.id.tv_invoice_no);
            tvInvoiceDate = itemView.findViewById(R.id.tv_invoice_date);
            tvInvoiceItems = itemView.findViewById(R.id.tv_invoice_items);
            tvLastStatus = itemView.findViewById(R.id.tv_last_status);

            ivInvoice = itemView.findViewById(R.id.iv_invoice);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_invoice, parent, false);

        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(ViewHolder viewHolder, int position, Invoice invoice) {
        viewHolder.tvInvoiceNo.setText(invoice.getInvoiceId());
        viewHolder.tvLastStatus.setText(invoice.getLastStatus());

        Picasso.with(activity)
                .load(invoice.getItems().get(0).getImageURL())
                .placeholder(R.drawable.ic_home_32dp)
                .error(R.drawable.ic_home_32dp)
                .into(viewHolder.ivInvoice);

        Timestamp stamp = new Timestamp(invoice.getCreateDate());
        viewHolder.tvInvoiceDate.setText(new SimpleDateFormat("dd MMMM yyyy").format(new Date(stamp.getTime())));

        StringBuilder sbIt = new StringBuilder();
        int i = 0;
        for (InvoiceItem it : invoice.getItems()) {
            i++;
            sbIt.append(i + ". " + it.getProduct() + " : " + it.getQuantity() + " unit" + "\n");
        }
        viewHolder.tvInvoiceItems.setText(sbIt.toString());

        // apply click events
        applyClickEvents(viewHolder, position, invoice);
    }

    private void applyClickEvents(ViewHolder holder, final int position, final Invoice invoice) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onInvoiceClicked(position, invoice);
            }
        });
    }

    public interface FirebaseAdapterInvoiceListener {
        void onInvoiceClicked(int position, Invoice invoice);
    }

}
