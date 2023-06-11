package com.gpth.smssystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.Objects;

public class PinVerification extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_verification);
        String verificationId = getIntent().getStringExtra("id");

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button ver = findViewById(R.id.pinVerify);
        ver.setOnClickListener(view -> {
            PinView pin = findViewById(R.id.PinView);
            ProgressBar progressBar = findViewById(R.id.progressBar2);
            ver.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
            String code = Objects.requireNonNull(pin.getText()).toString().trim();
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
            FirebaseAuth.getInstance().signInWithCredential(credential)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()){
                            Intent i = new Intent(PinVerification.this, ProfileUpdate.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(i);
                        } else {
                            Toast.makeText(PinVerification.this, "OTP is Invalid!", Toast.LENGTH_SHORT).show();
                        }
                    });
        });

    }
}