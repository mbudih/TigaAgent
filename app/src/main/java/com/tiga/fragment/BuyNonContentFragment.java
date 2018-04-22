package com.tiga.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.Query;
import com.tiga.adapter.ProductAdapter;
import com.tiga.adapter.ProductNonAdapter;
import com.tiga.agent.R;
import com.tiga.beli.nonsubsidi.NonSubsidiActivity;
import com.tiga.firebase.FirebaseAdapterProduct;
import com.tiga.firebase.FirebaseDB;
import com.tiga.firebase.model.penjualan.Penjualan;
import com.tiga.firebase.model.penjualan.TransactionItem;
import com.tiga.firebase.model.product.Product;
import com.tiga.firebase.model.product.ProductBuy;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class BuyNonContentFragment  extends Fragment
        implements FirebaseAdapterProduct.FirebaseAdapterInvoiceListener{

    ProgressDialog dialog;
    private boolean subsidiStatus;
    private RecyclerView rvProduct, rvProductSelected;
    private Button btBayar;
    private FirebaseAdapterProduct adapter;
    ProductNonAdapter productAdapter;

    List<ProductBuy> productBuyList = new LinkedList<>();

    public List<ProductBuy> getProductBuyList() {
        return productBuyList;
    }

    public void setProductBuyList(List<ProductBuy> productBuyList) {
        this.productBuyList = productBuyList;
    }

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
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_non_buy_content, container, false);
        rvProduct = (RecyclerView) v.findViewById(R.id.rv_buy_item);
        rvProductSelected = (RecyclerView) v.findViewById(R.id.rv_buy_item_selected);
        btBayar = (Button) v.findViewById(R.id.btn_buy);

        Query query = FirebaseDB.init().getDBReference()
                .child(FirebaseDB.REF_PRODUCT).orderByChild("is_subsidi").equalTo(getSubsidiStatus());

        FirebaseRecyclerOptions<Product> options = new FirebaseRecyclerOptions.Builder<Product>()
                .setQuery(query, Product.class)
                .build();

        adapter = new FirebaseAdapterProduct(options, getActivity(), this);

        // Attach the adapter to the recyclerview to populate items
        rvProduct.swapAdapter(adapter,false);

        // Set layout manager to position the items
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        rvProduct.setLayoutManager(staggeredGridLayoutManager);

        StaggeredGridLayoutManager sg = new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL);
        rvProductSelected.setLayoutManager(sg);

        dialog = new ProgressDialog(getActivity());

        btBayar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.setMessage("Memproses...");
                dialog.show();

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        Intent intent = new Intent();
                        Penjualan penjualan = setPenjualan(getProductBuyList());
                        intent.putExtra("PENJUALAN", penjualan);
                        intent.setClass(getActivity().getApplicationContext(), NonSubsidiActivity.class);
                        startActivity(intent);
                        dialog.dismiss();
                    }
                }, 2000);
            }
        });
        return v;
    }

    public Penjualan setPenjualan(List<ProductBuy> productBuyList){
        List<TransactionItem> transactionItemList = new LinkedList<>();
        Penjualan penjualan = new Penjualan();
        penjualan.setAgentId("Agen1");
        penjualan.setKKSOwner("Supardi");
        penjualan.setKKSNo(Long.valueOf("000"));
        penjualan.setTransactionDate(new Date().getTime());

        for (ProductBuy productBuy : productBuyList) {
            TransactionItem transactionItem = new TransactionItem();
            transactionItem.setQuantity(1);
            transactionItem.setPrice(productBuy.getProduct().getPrice());
            transactionItem.setProduct(productBuy.getProduct().getName());
            transactionItem.setImageURL(productBuy.getProduct().getImg_url());

            transactionItemList.add(transactionItem);
        }
        penjualan.setItems(transactionItemList);

        return penjualan;
    }

    @Override
    public void onProductClicked(int position, Product product) {

    }

    @Override
    public void onProductNonClicked(int position, Product product) {
        if (getProductBuyList().size() > 0) {
            int pos = -1;
            for (int i = 0; i < getProductBuyList().size(); i++) {
                if (getProductBuyList().get(i).getProduct().getCode().equals(product.getCode())){
                    pos = i;
                }
            }
            if (pos==-1){
                ProductBuy productBuy = new ProductBuy();
                productBuy.setQty(1);
                productBuy.setProduct(product);
                getProductBuyList().add(productBuy);
            }else{
                int newQty = getProductBuyList().get(pos).getQty()+1;
                getProductBuyList().get(pos).setQty(newQty);
            }

        }else{
            ProductBuy productBuy = new ProductBuy();
            productBuy.setQty(1);
            productBuy.setProduct(product);
            getProductBuyList().add(productBuy);
        }
        productAdapter = new ProductNonAdapter(getProductBuyList());
        refresh();
    }

    public void refresh(){
        rvProductSelected.swapAdapter(productAdapter,false);
    }
}
