package com.tiga.firebase;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.squareup.picasso.Picasso;
import com.tiga.agent.R;
import com.tiga.firebase.model.product.Product;


public class FirebaseAdapterProduct extends
        FirebaseRecyclerAdapter<Product,FirebaseAdapterProduct.ViewHolder>{

    private Activity activity;
    private FirebaseAdapterInvoiceListener listener;

    public FirebaseAdapterProduct(FirebaseRecyclerOptions options, Activity activity,
                                  FirebaseAdapterInvoiceListener listener) {
        super(options);
        this.activity = activity;
        this.listener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        private ImageView ivProduct;
        private Button btBuy;

        public ViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tv_buy);
            ivProduct = (ImageView) itemView.findViewById(R.id.iv_buy);
            btBuy = (Button) itemView.findViewById(R.id.bt_buy);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product, parent, false);

        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(ViewHolder viewHolder, int position, Product product) {
        viewHolder.tvName.setText(product.getName());
        Picasso.with(activity)
                .load(product.getImg_url())
                .placeholder(R.drawable.ic_home_32dp)
                .error(R.drawable.ic_home_32dp)
                .into(viewHolder.ivProduct);
        // apply click events
        applyClickEvents(viewHolder, position, product);
    }

    private void applyClickEvents(ViewHolder holder, final int position, final Product product) {
        if (product.isIs_subsidi()){
            holder.btBuy.setVisibility(View.VISIBLE);
        }else {
            holder.btBuy.setVisibility(View.GONE);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onProductNonClicked(position, product);
            }
        });
        holder.btBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onProductClicked(position, product);
            }
        });
    }

    public interface FirebaseAdapterInvoiceListener {
        void onProductClicked(int position, Product product);
        void onProductNonClicked(int position, Product product);
    }
}
