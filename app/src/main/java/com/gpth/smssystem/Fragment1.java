package com.gpth.smssystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

public class Fragment1 extends Fragment {
    RecyclerView recyclerView;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference chatList;
    String currentUid;
    FirebaseFirestore db;
    DocumentReference documentReference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment1, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        recyclerView = requireActivity().findViewById(R.id.frag1);

        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);

        db = FirebaseFirestore.getInstance();


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        currentUid = user.getUid();

        chatList = database.getReference("chat list").child(currentUid);
        chatList.keepSynced(true);


    }

    private void checkAccount() {

        documentReference = db.collection("user").document(currentUid);
        documentReference.get()
                .addOnCompleteListener(task -> {

                    if (task.getResult().exists()){


                    }else {

                        Intent intent = new Intent(getActivity(), ProfileUpdate.class);
                        startActivity(intent);

                    }

                });

    }

    @Override
    public void onStart() {
        super.onStart();

        checkAccount();

        FirebaseRecyclerOptions<ListModel> options =
                new FirebaseRecyclerOptions.Builder<ListModel>()
                        .setQuery(chatList, ListModel.class)
                        .build();

        FirebaseRecyclerAdapter<ListModel, RecentListVH> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<ListModel, RecentListVH>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull RecentListVH holder, int position, @NonNull ListModel model) {
                        holder.setList(getActivity(), model.getmCount(), model.getTime(),model.getLastM(),model.getName(),model.getUrl(),model.getSeen(),model.getUid());

                        String postKey = getRef(position).getKey();
                        String ruid = getItem(position).getUid();

                        holder.nameTV.setOnClickListener(view -> {
                            Intent intent = new Intent(getActivity(), ChatAct.class);
                            intent.putExtra("uid", ruid);
                            startActivity(intent);
                        });
                    }

                    @NonNull
                    @Override
                    public RecentListVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.recent_list, parent, false);

                        return new RecentListVH(view);
                    }
                };
        recyclerView.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();
    }
}
