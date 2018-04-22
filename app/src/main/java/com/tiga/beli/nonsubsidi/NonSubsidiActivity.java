package com.tiga.beli.nonsubsidi;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tiga.adapter.ItemAdapter;
import com.tiga.adapter.ProductAdapter;
import com.tiga.agent.R;
import com.tiga.beli.subsidi.SubsidiTrResultActivity;
import com.tiga.firebase.FirebaseDB;
import com.tiga.firebase.model.penjualan.Penjualan;
import com.tiga.firebase.model.penjualan.TransactionItem;

public class NonSubsidiActivity extends AppCompatActivity {

    Button btnPay;
    RecyclerView recyclerView;
    Penjualan penjualan;
    ProgressDialog dialog;
    TextView tvTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_non_subsidi);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        btnPay= (Button) findViewById(R.id.btn_pay);
        recyclerView = (RecyclerView) findViewById(R.id.rv_p_item);
        tvTotal = (TextView) findViewById(R.id.total_detail);

        penjualan = (Penjualan) getIntent().getSerializableExtra("PENJUALAN");

        ItemAdapter itemAdapter = new ItemAdapter(penjualan.getItems());
        recyclerView.swapAdapter(itemAdapter,false);

        // Set layout manager to position the items
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        // Reverse adding new data
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);

        recyclerView.setLayoutManager(mLayoutManager);

        dialog = new ProgressDialog(this);

        double total = 0;
        for (TransactionItem transactionItem: penjualan.getItems()){
            double sub = transactionItem.getPrice()*transactionItem.getQuantity();
            total = total + sub;
        }

        tvTotal.setText(String.valueOf(total));

        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.setMessage("Memproses...");
                dialog.show();

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        Intent intent = new Intent();
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
