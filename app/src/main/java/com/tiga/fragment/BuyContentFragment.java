package com.tiga.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.Query;
import com.tiga.agent.R;
import com.tiga.firebase.FirebaseAdapterProduct;
import com.tiga.firebase.FirebaseDB;
import com.tiga.firebase.model.product.Product;

public class BuyContentFragment extends Fragment
        implements FirebaseAdapterProduct.FirebaseAdapterInvoiceListener{

    private boolean subsidiStatus;
    private RecyclerView rvProduct;
    private FirebaseAdapterProduct adapter;

    public boolean getSubsidiStatus() {
        return subsidiStatus;
    }

    public void setSubsidiStatus(boolean subsidiStatus) {
        this.subsidiStatus = subsidiStatus;
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
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_buy_content, container, false);
        rvProduct = (RecyclerView) v.findViewById(R.id.rv_buy_item);

        Query query = FirebaseDB.init().getDBReference()
                .child(FirebaseDB.REF_PRODUCT).orderByChild("is_subsidi").equalTo(getSubsidiStatus());

        FirebaseRecyclerOptions<Product> options = new FirebaseRecyclerOptions.Builder<Product>()
                .setQuery(query, Product.class)
                .build();

        adapter = new FirebaseAdapterProduct(options, getActivity(), this);

        // Attach the adapter to the recyclerview to populate items
        rvProduct.swapAdapter(adapter,false);

        // Set layout manager to position the items
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
        rvProduct.setLayoutManager(staggeredGridLayoutManager);

        return v;
    }

    @Override
    public void onProductClicked(int position, Product product) {
        Toast.makeText(getActivity().getApplicationContext(), product.getName(), Toast.LENGTH_SHORT).show();
    }
}
