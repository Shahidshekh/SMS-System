package com.gpth.smssystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class ShowUsers extends AppCompatActivity {

    RecyclerView recyclerView;
    DocumentReference documentReference;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_users);

        recyclerView = findViewById(R.id.RV_ShowUsers);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        currentUser = user.getUid();
        documentReference = db.collection("user").document(currentUser);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);
    }

    @Override
    protected void onStart() {
        super.onStart();

        Query query = db.collection("user");
        FirestoreRecyclerOptions<UserModel> options =
                new FirestoreRecyclerOptions.Builder<UserModel>()
                        .setQuery(query, UserModel.class)
                        .build();

        FirestoreRecyclerAdapter firestoreRecyclerAdapter =
                new FirestoreRecyclerAdapter<UserModel, UserViewHolder>(options){

                    @NonNull
                    @Override
                    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                        View view = LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.user_item, parent, false);
                        return new UserViewHolder(view);
                    }

                    @Override
                    protected void onBindViewHolder(@NonNull UserViewHolder holder, int position, @NonNull UserModel model) {

                        holder.setUser(getApplication(), model.getName(), model.getPhone(), model.getAbout(),model.getUrl(),model.getUid());

                        String uid = getItem(position).getUid();

                        holder.nameTv.setOnClickListener(view -> {
                            Intent intent = new Intent(ShowUsers.this, ChatAct.class);
                            intent.putExtra("uid", uid);
                            startActivity(intent);
                        });

                    }
                };

        firestoreRecyclerAdapter.startListening();
        recyclerView.setAdapter(firestoreRecyclerAdapter);

    }
}