package com.tiga.stok;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.tiga.adapter.TrackingAdapter;
import com.tiga.agent.R;
import com.tiga.firebase.model.Invoice;
import com.tiga.firebase.model.InvoiceItem;

public class StokActivity extends AppCompatActivity {
    TextView tvProductList, tvInv;
    RecyclerView recyclerViewTracking;

    Invoice invoice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stok);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tvProductList = (TextView) findViewById(R.id.tv_tracking_item);
        tvInv = (TextView) findViewById(R.id.tv_invoice);
        recyclerViewTracking = (RecyclerView) findViewById(R.id.rv_tracking);

        invoice = (Invoice) getIntent().getSerializableExtra("INVOICE");

        StringBuilder sb = new StringBuilder();
        int i = 0;
        for (InvoiceItem ti : invoice.getItems()) {
            i++;
            sb.append(i+". "+ti.getProduct() + " - " + ti.getQuantity() + " Unit \n");
        }
        tvProductList.setText(sb.toString());
        tvInv.setText(invoice.getInvoiceId());


        TrackingAdapter itemAdapter = new TrackingAdapter(invoice.getTracking());
        recyclerViewTracking.swapAdapter(itemAdapter,false);

        // Set layout manager to position the items
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        // Reverse adding new data
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);

        recyclerViewTracking.setLayoutManager(mLayoutManager);

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
