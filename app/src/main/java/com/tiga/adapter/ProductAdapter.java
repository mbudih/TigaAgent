package com.tiga.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tiga.agent.R;
import com.tiga.firebase.model.product.Product;
import com.tiga.firebase.model.product.ProductBuy;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductHolder> {

    List<ProductBuy> productList;


    public ProductAdapter(List<ProductBuy> productList) {
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product_beli, parent, false);

        return new ProductHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductHolder holder, int position) {
        holder.setValue(productList.get(position));
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }
}
