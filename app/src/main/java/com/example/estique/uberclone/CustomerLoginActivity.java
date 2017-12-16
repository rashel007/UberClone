package com.example.estique.uberclone;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by estique on 12/16/17.
 */

public class CustomerLoginActivity extends AppCompatActivity {
    private EditText etEmail, etPassword;

    private FirebaseAuth mAuth;

    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_login);

        init();
    }


    private void init(){

        mAuth = FirebaseAuth.getInstance();

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                if(user != null) {
                    Intent intent   = new Intent(CustomerLoginActivity.this, MapActivity.class);
                    startActivity(intent);
                    finish();
                    return;
                }

            }
        };

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);

        Button btnLogin = findViewById(R.id.btnLogin);
        Button btnRegistration = findViewById(R.id.btnRegistration);

        btnLogin.setOnClickListener(clickListener);
        btnRegistration.setOnClickListener(clickListener);

    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){

                case R.id.btnLogin:
                    if(!TextUtils.isEmpty( etEmail.getText().toString().trim() ) && !TextUtils.isEmpty(etPassword.getText().toString().trim())){
                        initLogin(etEmail.getText().toString().trim(), etPassword.getText().toString().trim());
                    }else {
                        Toast.makeText(CustomerLoginActivity.this, "Field Missing", Toast.LENGTH_SHORT).show();
                    }
                    break;

                case R.id.btnRegistration:
                    if(!TextUtils.isEmpty( etEmail.getText().toString().trim() ) && !TextUtils.isEmpty(etPassword.getText().toString().trim())){
                        initRegistration(etEmail.getText().toString().trim(), etPassword.getText().toString().trim());
                    }else {
                        Toast.makeText(CustomerLoginActivity.this, "Field Missing", Toast.LENGTH_SHORT).show();
                    }
                    break;

            }
        }
    };


    private void initRegistration(final String email, final String password){

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this,
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(!task.isSuccessful()){
                            Toast.makeText(CustomerLoginActivity.this, "sign up error !", Toast.LENGTH_SHORT).show();
                        }else {

                            String user_id = mAuth.getCurrentUser().getUid();

                            DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("Users").child("Customers").child(user_id);
                            current_user_db.setValue(true);
                        }

                    }
                });


    }

    private void initLogin(final String email, final String password){
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(!task.isSuccessful()){
                    Toast.makeText(CustomerLoginActivity.this, "sign in error !", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(authStateListener);
    }
}
