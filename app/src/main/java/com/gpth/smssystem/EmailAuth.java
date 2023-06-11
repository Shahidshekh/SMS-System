package com.gpth.smssystem;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class EmailAuth extends AppCompatActivity {
    private final String TAG = "EmailAuth";
    FirebaseAuth mAuth;
    TextView signUp;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_email_auth);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        Button loginbtn = findViewById(R.id.loginbtn);
        signUp = findViewById(R.id.login);
        loginbtn.setOnClickListener(view -> {
            EditText mail = findViewById(R.id.mail0);
            String Mail = mail.getText().toString();

            EditText password = findViewById(R.id.password0);
            String pass = password.getText().toString();
            if (!Mail.equals("") && !pass.equals("")) {
                mAuth = FirebaseAuth.getInstance();
                mAuth.signInWithEmailAndPassword(Mail, pass)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()){
                                Toast.makeText(EmailAuth.this, "Logged in!", Toast.LENGTH_SHORT).show();
                                Log.d(TAG, "Success!!!!!!!!!!!!!!");
                                Intent i = new Intent(getApplicationContext(), homeScreen.class);
                                startActivity(i);
                            } else {
                                Log.w(TAG, "error :"+task.getException());
                                Toast.makeText(EmailAuth.this, "Failed: "+task.getException(), Toast.LENGTH_SHORT).show();
                            }
                        });

            } else {
                Toast.makeText(EmailAuth.this, "Login Failed", Toast.LENGTH_SHORT).show();
            }
        });

        signUp.setOnClickListener(view -> {
            Intent intent = new Intent(EmailAuth.this, SignUP.class);
            startActivity(intent);
        });
    }

}