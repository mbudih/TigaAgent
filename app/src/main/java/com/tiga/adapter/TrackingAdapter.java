package com.tiga.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tiga.agent.R;
import com.tiga.firebase.model.InvoiceTracking;

import java.util.List;

public class TrackingAdapter extends RecyclerView.Adapter<TrackingHolder>{

    List<InvoiceTracking> invoiceTrackingList;

    public TrackingAdapter(List<InvoiceTracking> invoiceTrackingList) {
        this.invoiceTrackingList = invoiceTrackingList;
    }

    @Override
    public void onBindViewHolder(@NonNull TrackingHolder holder, int position) {
        holder.setValue(invoiceTrackingList.get(position));
    }

    @NonNull
    @Override
    public TrackingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_detail_tracking, parent, false);

        return new TrackingHolder(itemView);
    }

    @Override
    public int getItemCount() {
        return invoiceTrackingList.size();
    }
}
