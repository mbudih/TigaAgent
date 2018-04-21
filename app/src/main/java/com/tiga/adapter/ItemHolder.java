package com.tiga.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tiga.agent.R;
import com.tiga.firebase.model.penjualan.TransactionItem;
import com.tiga.firebase.model.product.ProductBuy;

public class ItemHolder extends RecyclerView.ViewHolder {

    ImageView imageView;
    TextView tvName, tvPrice, tvQty, tvTotal;

    public ItemHolder(View itemView) {
        super(itemView);
        imageView = (ImageView) itemView.findViewById(R.id.p_img);
        tvName = (TextView) itemView.findViewById(R.id.p_name);
        tvPrice = (TextView) itemView.findViewById(R.id.p_price);
        tvQty = (TextView) itemView.findViewById(R.id.p_qty);
        tvTotal = (TextView) itemView.findViewById(R.id.p_total);

    }

    public void setValue(TransactionItem transactionItem){
        tvName.setText(transactionItem.getProduct());
        tvPrice.setText(String.valueOf(transactionItem.getPrice()));
        tvQty.setText(String.valueOf(transactionItem.getQuantity()));
        double total = transactionItem.getPrice()*transactionItem.getQuantity();
        tvTotal.setText(String.valueOf(total));

        Picasso.with(imageView.getContext())
                .load(transactionItem.getImageURL())
                .placeholder(R.drawable.ic_home_32dp)
                .error(R.drawable.ic_home_32dp)
                .into(imageView);
    }
}
