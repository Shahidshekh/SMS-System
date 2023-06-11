package com.gpth.smssystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView phone = findViewById(R.id.PhoneLogin);
        TextView email = findViewById(R.id.EmailLogin);
        phone.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, PhoneAuth.class);
            startActivity(intent);
        });
        email.setOnClickListener(view -> {
            Intent intent2 = new Intent(MainActivity.this, EmailAuth.class);
            startActivity(intent2);
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null){
            Intent i = new Intent(getApplicationContext(), TabAct.class);
            startActivity(i);
        }
    }
}