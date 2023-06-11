package com.gpth.smssystem;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ChatAct extends AppCompatActivity {

    EditText messageET;
    TextView nameTV;
    ImageButton moreBtn, sendBtn;
    RecyclerView recyclerView;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference messageRef, sChatList, rChatList, sRef, rRef;
    String rname, rurl, rabout, rphone, suid, ruid, sname, sabout, sphone, surl;
    ImageView userPic;
    MessageModel model;
    ListModel listModel, rListModel;
    DocumentReference documentReference, documentReferenceSender;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        userPic = findViewById(R.id.user_pic);
        messageET = findViewById(R.id.EtMsg);
        nameTV = findViewById(R.id.nameMsg);
        moreBtn = findViewById(R.id.msgMoreBtn);
        sendBtn = findViewById(R.id.send);
        recyclerView = findViewById(R.id.RV_Msg);


        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);


        listModel = new ListModel();
        rListModel = new ListModel();
        model = new MessageModel();


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        suid = user.getUid();

        ruid = getIntent().getStringExtra("uid");

        documentReference = db.collection("user").document(ruid);
        documentReferenceSender = db.collection("user").document(suid);


        documentReference.get()
                .addOnCompleteListener(task -> {
                    if (task.getResult().exists()){
                        rname = task.getResult().getString("name");
                        rabout = task.getResult().getString("about");
                        rphone = task.getResult().getString("phone");
                        rurl = task.getResult().getString("url");

                        nameTV.setText(rname);

                    } else {
                        Toast.makeText(ChatAct.this, "Something went wrong...", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> Toast.makeText(ChatAct.this, e+"", Toast.LENGTH_SHORT).show());

        documentReferenceSender.get()
                .addOnCompleteListener(task -> {
                    if (task.getResult().exists()){
                        sname = task.getResult().getString("name");
                        sabout = task.getResult().getString("about");
                        sphone = task.getResult().getString("phone");
                        surl = task.getResult().getString("url");
                    } else {
                        Toast.makeText(ChatAct.this, "Something went wrong...", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(e -> Toast.makeText(ChatAct.this, e+"", Toast.LENGTH_SHORT).show());

        sRef = database.getReference("Message").child(suid).child(ruid);
        rRef = database.getReference("Message").child(ruid).child(suid);
        sChatList = database.getReference("chat list").child(suid);
        rChatList = database.getReference("chat list").child(ruid);

        Calendar time1 = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        final String saveTime = currentTime.format(time1.getTime());

        sendBtn.setOnClickListener(view -> {

            String message = messageET.getText().toString().trim();

            if (message.isEmpty()){
                Toast.makeText(ChatAct.this, "Type Something!", Toast.LENGTH_SHORT).show();
            } else {

                model.setMessage(message);
                model.setSearch("not yet");
                model.setRuid(ruid);
                model.setSuid(suid);
                model.setTime(saveTime);
                model.setDelete(String.valueOf(System.currentTimeMillis()));

                String key = sRef.push().getKey();
                assert key != null;
                sRef.child(key).setValue(model);
                messageET.setText("");

                String key2 = rRef.push().getKey();
                assert key2 != null;
                rRef.child(key2).setValue(model);
                messageET.setText("");

                listModel.setLastM(message);
                listModel.setName(rname);
                listModel.setmCount("1");
                listModel.setSeen("unseen");
                listModel.setUid(ruid);
                listModel.setUrl(rurl);
                listModel.setTime(saveTime);

                sChatList.child(ruid).setValue(listModel);

                rListModel.setLastM(message);
                rListModel.setName(sname);
                rListModel.setmCount("1");
                rListModel.setSeen("unseen");
                rListModel.setUid(suid);
                rListModel.setUrl(surl);
                rListModel.setTime(saveTime);

                rChatList.child(suid).setValue(rListModel);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();


        FirebaseRecyclerOptions<MessageModel> options =
                new FirebaseRecyclerOptions.Builder<MessageModel>()
                        .setQuery(sRef, MessageModel.class)
                        .build();

        FirebaseRecyclerAdapter<MessageModel, MsgViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<MessageModel, MsgViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull MsgViewHolder holder, int position, @NonNull MessageModel model) {

                        holder.setMessage(getApplication(), model.getMessage(),model.getSearch(), model.getDelete(),
                                model.getTime(),model.getSuid(), model.getRuid());

                    }

                    @NonNull
                    @Override
                    public MsgViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.text_item, parent, false);
                        return new MsgViewHolder(view);
                    }
                };

        recyclerView.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();

    }
}