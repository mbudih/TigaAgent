package com.tiga.beli.subsidi;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.tiga.adapter.ProductAdapter;
import com.tiga.agent.LoginActivity;
import com.tiga.agent.R;
import com.tiga.firebase.FirebaseDB;
import com.tiga.firebase.model.KKSData;
import com.tiga.firebase.model.penjualan.Penjualan;
import com.tiga.firebase.model.penjualan.TransactionItem;
import com.tiga.firebase.model.product.Product;
import com.tiga.firebase.model.product.ProductBuy;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SubsidiKKSActivity extends AppCompatActivity {

    private TextView tvName, tvAddress, tvNoKTP;
    private Button btnPay;
    private RecyclerView recyclerView;
    ProgressDialog dialog;
    List<ProductBuy> productBuyList = new LinkedList<>();

    KKSData kksData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subsidi_kks);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tvName = (TextView) findViewById(R.id.kks_name);
        tvAddress = (TextView) findViewById(R.id.kks_address);
        tvNoKTP = (TextView) findViewById(R.id.kks_ktp);

        btnPay= (Button) findViewById(R.id.btn_pay);
        recyclerView = (RecyclerView) findViewById(R.id.rv_p_item);


        tvName.setText("Supardi");
        tvAddress.setText("Jalan Lodan Timur, Kota Tua, Ancol, Pademangan, Kota Jkt Utara");
        tvNoKTP.setText("3329102909920002");

        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);

        Product product = new Product();
        product.setName("Elpiji Gas 3 KG");
        product.setCode("EPJ3");
        product.setImg_url("http://cdn2.tstatic.net/bali/foto/bank/images/elpiji-3-kg_20150924_144235.jpg");
        product.setPrice(15000);

        ProductBuy productBuy = new ProductBuy();
        productBuy.setProduct(product);
        productBuy.setQty(1);

        productBuyList.add(productBuy);

        ProductAdapter productAdapter = new ProductAdapter(productBuyList);

        recyclerView.swapAdapter(productAdapter,false);

        dialog = new ProgressDialog(this);

        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.setMessage("Memproses...");
                dialog.show();

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        Intent intent = new Intent();
                        Penjualan penjualan = setPenjualan(productBuyList.get(0));
                        FirebaseDB.init().addPenjualan(penjualan);
                        intent.putExtra("PENJUALAN", penjualan);
                        intent.setClass(getApplicationContext(), SubsidiTrResultActivity.class);
                        startActivity(intent);
                        dialog.dismiss();
                    }
                }, 2000);

            }
        });
    }

    public Penjualan setPenjualan(ProductBuy productBuy){
        Penjualan penjualan = new Penjualan();
        penjualan.setAgentId("Agen1");
        penjualan.setKKSOwner("Supardi");
        penjualan.setKKSNo(Long.valueOf("7890120122220092"));
        penjualan.setTransactionDate(new Date().getTime());

        TransactionItem transactionItem = new TransactionItem();
        transactionItem.setQuantity(1);
        transactionItem.setPrice(productBuy.getProduct().getPrice());
        transactionItem.setProduct(productBuy.getProduct().getName());
        transactionItem.setImageURL(productBuy.getProduct().getImg_url());
        List<TransactionItem> transactionItemList = new LinkedList<>();
        transactionItemList.add(transactionItem);
        penjualan.setItems(transactionItemList);

        return penjualan;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
