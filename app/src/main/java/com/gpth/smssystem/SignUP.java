package com.gpth.smssystem;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class SignUP extends AppCompatActivity {
    private FirebaseAuth mAuth;
    public FirebaseUser user;
    private final String TAG = "SignUP";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_signup);
        mAuth = FirebaseAuth.getInstance();
        Button button = findViewById(R.id.loginbtn);
        button.setOnClickListener(view -> signIn());
    }

    @SuppressLint("SetTextI18n")
    public void signIn(){
        EditText email = findViewById(R.id.email);
        TextView ewarn = findViewById(R.id.ewarn);
        EditText password = findViewById(R.id.pass);
        TextView pwarn = findViewById(R.id.pwarn);
        EditText CPassword = findViewById(R.id.password2);
        String Email = email.getText().toString();
        EditText username = findViewById(R.id.USER);
        String userName = username.getText().toString();
        Log.d(TAG, "The username is "+userName);
        if (Email.equals("")){ewarn.setText("*Required!"); return;}
        else {ewarn.setText("Done ✔"); ewarn.setTextColor(Color.GREEN);}
        String pass = password.getText().toString();
        if (pass.equals("")){pwarn.setText("*Required!"); return;} else {pwarn.setText("Done ✔"); pwarn.setTextColor(Color.GREEN);}
        String CPass = CPassword.getText().toString();
        if (pass.equals(CPass)){
            mAuth.createUserWithEmailAndPassword(Email, pass)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()){
                            Log.d(TAG, "Login Succeeded!!!!!!!!");
                            user = mAuth.getCurrentUser();
                            UserProfileChangeRequest Profile = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(userName)
                                    .build();
                            user.updateProfile(Profile)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()){
                                                Log.d(TAG, "Update Successful!");
                                            }
                                        }
                                    });
                            Intent i = new Intent(getApplicationContext(), homeScreen.class);
                            startActivity(i);
                        } else {
                            Log.w(TAG, "Login Failed!!!!!!!!!!!!!!!", task.getException());
                            Toast.makeText(SignUP.this, "Login Failed", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
        else{
            Toast.makeText(this, "Password did not match!", Toast.LENGTH_SHORT).show();
        }
    }
}
