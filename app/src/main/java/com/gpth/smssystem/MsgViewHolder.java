package com.gpth.smssystem;

import android.app.Application;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MsgViewHolder extends RecyclerView.ViewHolder {
    public MsgViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public void setMessage(Application application, String message, String search, String delete, String time,
                           String suid, String ruid) {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        String currentUserId = user.getUid();

        TextView ReceivedMsg = itemView.findViewById(R.id.msg_received);
        TextView SentMsg = itemView.findViewById(R.id.message_sent);

        if (currentUserId.equals((suid))){
            ReceivedMsg.setVisibility(View.GONE);
            SentMsg.setText(message);
        } else if (currentUserId.equals(ruid)){
            SentMsg.setVisibility(View.GONE);
            ReceivedMsg.setText(message);
        }
    }

}
