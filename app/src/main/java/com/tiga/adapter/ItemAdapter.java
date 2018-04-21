package com.tiga.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tiga.agent.R;
import com.tiga.firebase.model.penjualan.TransactionItem;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemHolder> {

        List<TransactionItem> transactionItemList;


        public ItemAdapter(List<TransactionItem> transactionItemList) {
            this.transactionItemList = transactionItemList;
        }

        @NonNull
        @Override
        public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.item_product_beli, parent, false);

            return new ItemHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
            holder.setValue(transactionItemList.get(position));
        }

        @Override
        public int getItemCount() {
            return transactionItemList.size();
        }
}