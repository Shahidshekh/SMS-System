package com.gpth.smssystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class PhoneAuth extends AppCompatActivity {
    FirebaseAuth mAuth;
    EditText edtPhone;
    Button generateOTPBtn;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    String TAG = "PhoneAuth";

    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_auth);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button verify = findViewById(R.id.Verify);

        mAuth = FirebaseAuth.getInstance();
        
        edtPhone = findViewById(R.id.PhoneNum);

        verify.setOnClickListener(v -> {
            if (TextUtils.isEmpty(edtPhone.getText().toString())) {
                Toast.makeText(PhoneAuth.this, "Please enter a valid phone number.", Toast.LENGTH_SHORT).show();
            } else {
                String phone = edtPhone.getText().toString().trim();
                ProgressBar progressBar = findViewById(R.id.progressBar);
                progressBar.setVisibility(View.VISIBLE);
                verify.setVisibility(View.GONE);
                sendVerificationCode(phone);
            }
        });
        

    }

    private void sendVerificationCode(String number) {
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential credential) {
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Log.w(TAG, "Error :"+e.getLocalizedMessage());
                Toast.makeText(PhoneAuth.this, "Error :"+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                Intent i = new Intent(PhoneAuth.this, PinVerification.class);
                i.putExtra("id", verificationId);
                i.putExtra("number", number);
                startActivity(i);
            }
        };

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber("+91" + number)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(this)
                        .setCallbacks(mCallbacks)
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }
}