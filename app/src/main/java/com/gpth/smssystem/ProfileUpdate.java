package com.gpth.smssystem;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ProfileUpdate extends AppCompatActivity {

    FirebaseFirestore firebaseFirestore;
    DocumentReference documentReference;
    String currentUserId;
    EditText name;
    EditText about;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_update);

        firebaseFirestore = FirebaseFirestore.getInstance();


        name = findViewById(R.id.name);
        about = findViewById(R.id.abt);

        currentUserId = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();

        documentReference = firebaseFirestore.collection("user").document(currentUserId);

        documentReference.get()
                .addOnCompleteListener(task -> {
                    if (task.getResult().exists()){
                        name.setText(task.getResult().getString("name"));
                        about.setText(task.getResult().getString("about"));
                    }
                });


        Button submit = findViewById(R.id.submit);
        submit.setOnClickListener(view -> {
            name = findViewById(R.id.name);
            about = findViewById(R.id.abt);
            String abt = about.getText().toString().trim();
            String Name = name.getText().toString().trim();
            String url = "";

            currentUserId = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
            String phone =  Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getPhoneNumber();
            documentReference = firebaseFirestore.collection("user").document(currentUserId);

            Map<String, Object> profile = new HashMap<>();
            profile.put("name", Name);
            profile.put("about", abt);
            profile.put("phone", phone);
            profile.put("url", url);
            profile.put("uid", currentUserId);


            documentReference.set(profile)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()){
                            Toast.makeText(ProfileUpdate.this, "Updated!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(ProfileUpdate.this, TabAct.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        } else {
                            Toast.makeText(ProfileUpdate.this, "Error-"+task.getException(), Toast.LENGTH_SHORT).show();

                        }
                    });
        });
    }
}