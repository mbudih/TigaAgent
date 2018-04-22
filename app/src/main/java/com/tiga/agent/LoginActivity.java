package com.tiga.agent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.tiga.utils.ProgressBarCircularIndeterminate;

public class LoginActivity extends AppCompatActivity {

    private Button btLogin;
    private ProgressBarCircularIndeterminate pbLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
        initAction();
    }

    private void initView() {
        pbLogin = findViewById(R.id.pb_login);
        btLogin = findViewById(R.id.bt_login);

        pbLogin.setVisibility(View.GONE);
    }

    private void initAction() {
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pbLogin.setVisibility(View.VISIBLE);
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });
    }

}
