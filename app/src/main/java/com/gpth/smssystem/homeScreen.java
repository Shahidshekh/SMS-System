package com.gpth.smssystem;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class homeScreen extends AppCompatActivity {
    FirebaseUser user;
    TextView msg;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null){
            String name = user.getDisplayName();
            String mail = user.getEmail();
            String number = user.getPhoneNumber();
            msg = findViewById(R.id.msg);
            msg.setText("Welcome "+name+"\n"+"Your Email Is : "+mail+"\n"+"Your Phone Number Is :"+number);
        }
        Button button = findViewById(R.id.button);
        button.setOnClickListener(view -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        });
    }
}

