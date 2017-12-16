package com.example.estique.uberclone;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init(){

        Button btnDriver = findViewById(R.id.btnDriver);
        Button btnCustomer = findViewById(R.id.btnCustomer);

        btnDriver.setOnClickListener(clickListener);
        btnCustomer.setOnClickListener(clickListener);
    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            Intent intent;

            switch (view.getId()){

                case R.id.btnDriver:

                    intent   = new Intent(MainActivity.this, DriverLoginActivity.class);
                    startActivity(intent);
                    finish();
                    break;

                case R.id.btnCustomer:
                    intent = new Intent(MainActivity.this, CustomerLoginActivity.class);
                    startActivity(intent);
                    finish();
                    break;

            }
        }
    };
}
