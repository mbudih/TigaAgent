package com.tiga.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tiga.agent.R;
import com.tiga.firebase.model.product.ProductBuy;
import com.tiga.utils.AppUtils;

public class ProductNonHolder extends RecyclerView.ViewHolder{
    TextView tvName, tvPrice, tvQty, tvTotal;

    public ProductNonHolder(View itemView) {
        super(itemView);
        tvName = (TextView) itemView.findViewById(R.id.p_name);
        tvPrice = (TextView) itemView.findViewById(R.id.p_price);
        tvQty = (TextView) itemView.findViewById(R.id.p_qty);
        tvTotal = (TextView) itemView.findViewById(R.id.p_total);

    }

    public void setValue(ProductBuy product){
        tvName.setText(product.getProduct().getName());
        tvPrice.setText(AppUtils.getIDR(product.getProduct().getPrice()));
        tvQty.setText(String.valueOf(product.getQty()));
        tvTotal.setText(AppUtils.getIDR(product.getTotal()));
    }
}
