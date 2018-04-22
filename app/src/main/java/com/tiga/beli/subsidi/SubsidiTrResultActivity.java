package com.tiga.beli.subsidi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tiga.adapter.ItemAdapter;
import com.tiga.agent.MainActivity;
import com.tiga.agent.R;
import com.tiga.firebase.model.penjualan.Penjualan;
import com.tiga.firebase.model.penjualan.TransactionItem;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SubsidiTrResultActivity extends AppCompatActivity {

    Button button;
    Penjualan penjualan;
    TextView tvTanggal, tvName, tvKKS, tvTotal;
    LinearLayout lyNo, lyName;

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subsidi_tr_result);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        button = (Button) findViewById(R.id.bt_back);
        recyclerView = (RecyclerView) findViewById(R.id.rv_detail);
        tvTanggal = (TextView) findViewById(R.id.trx_date);
        tvName = (TextView) findViewById(R.id.trx_name);
        tvKKS = (TextView) findViewById(R.id.trx_kksno);
        tvTotal = (TextView) findViewById(R.id.total_detail);
        lyName = (LinearLayout) findViewById(R.id.ly_kksname);
        lyNo = (LinearLayout) findViewById(R.id.ly_kksno);

        penjualan = new Penjualan();
        penjualan = (Penjualan) getIntent().getSerializableExtra("PENJUALAN");

        ItemAdapter itemAdapter = new ItemAdapter(penjualan.getItems());
        recyclerView.swapAdapter(itemAdapter,false);

        // Set layout manager to position the items
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        // Reverse adding new data
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);

        recyclerView.setLayoutManager(mLayoutManager);

        double total = 0;
        for (TransactionItem transactionItem: penjualan.getItems()){
            double sub = transactionItem.getPrice()*transactionItem.getQuantity();
            total = total + sub;
        }
        if (penjualan.getKKSNo().toString().equals("0")){
            lyNo.setVisibility(View.GONE);
            lyName.setVisibility(View.GONE);
        }
        tvKKS.setText(penjualan.getKKSNo().toString());
        tvName.setText(penjualan.getKKSOwner());
        tvTanggal.setText(new SimpleDateFormat("dd MMMM yyyy")
                .format(new Date().getTime()));
        tvTotal.setText(String.valueOf(total));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.setClass(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

}
