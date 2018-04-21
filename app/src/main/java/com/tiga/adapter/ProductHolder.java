package com.tiga.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tiga.agent.R;
import com.tiga.firebase.model.product.Product;
import com.tiga.firebase.model.product.ProductBuy;

public class ProductHolder extends RecyclerView.ViewHolder {

    ImageView imageView;
    TextView tvName, tvPrice, tvQty, tvTotal;

    public ProductHolder(View itemView) {
        super(itemView);
        imageView = (ImageView) itemView.findViewById(R.id.p_img);
        tvName = (TextView) itemView.findViewById(R.id.p_name);
        tvPrice = (TextView) itemView.findViewById(R.id.p_price);
        tvQty = (TextView) itemView.findViewById(R.id.p_qty);
        tvTotal = (TextView) itemView.findViewById(R.id.p_total);

    }

    public void setValue(ProductBuy product){
        tvName.setText(product.getProduct().getName());
        tvPrice.setText(String.valueOf(product.getProduct().getPrice()));
        tvQty.setText(String.valueOf(product.getQty()));
        tvTotal.setText(String.valueOf(product.getTotal()));

        Picasso.with(imageView.getContext())
                .load(product.getProduct().getImg_url())
                .placeholder(R.drawable.ic_home_32dp)
                .error(R.drawable.ic_home_32dp)
                .into(imageView);
    }
}
