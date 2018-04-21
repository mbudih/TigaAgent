package com.tiga.beli.subsidi;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.tiga.agent.R;
import com.tiga.firebase.FirebaseDB;
import com.tiga.firebase.model.KKSData;

public class SubsidiActivy extends AppCompatActivity {
    private Button btVerifikasi;
    View btMagic;
    private EditText etKKSNumber;
    private KKSData kksData;
    private static final String NO_CARD = "7890-1201-2222-0092";
    ProgressDialog dialog;

    public KKSData getKksData() {
        return kksData;
    }

    public void setKksData(KKSData kksData) {
        this.kksData = kksData;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subsidi_activy);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btVerifikasi = (Button) findViewById(R.id.bt_verifikasi);
        btMagic = (View) findViewById(R.id.bt_magic);
        etKKSNumber = (EditText) findViewById(R.id.et_kks_number);

        btMagic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etKKSNumber.setText(NO_CARD);
                etKKSNumber.setSelection(etKKSNumber.getText().length());
            }
        });
        dialog = new ProgressDialog(this);
        btVerifikasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etKKSNumber.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), getString(R.string.message_kksno_mandatory),Toast.LENGTH_SHORT).show();
                }else{

                    dialog.setMessage("Memverifikasi...");
                    dialog.show();

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            String kksno = etKKSNumber.getText().toString();
                            if (kksno.equals(NO_CARD)) {
                                startActivity(new Intent(getApplicationContext(), SubsidiKKSActivity.class));
                            }else{
                                Toast.makeText(getApplicationContext(), "Nomor Tidak Terdaftar, Periksa Kembali Nomor KKS Anda", Toast.LENGTH_SHORT).show();
                            }
                            dialog.dismiss();
                        }
                    }, 3000);
                }

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

    public KKSData getKKSDataFB(String kksno){
        KKSData result = null;
        DatabaseReference query = FirebaseDB.init().getDBReference(FirebaseDB.REF_KKS);

        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                 KKSData d = dataSnapshot.getValue(KKSData.class);
                 setKksData(d);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                KKSData d = dataSnapshot.getValue(KKSData.class);
                setKksData(d);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        result = getKksData();
        //options.getSnapshots().getSnapshot(0).getValue(KKSData)

//        int i = options.getSnapshots().size();
//        if (i>0){
//            result = options.getSnapshots().get(0);
//        }
        return result;
    }
}
