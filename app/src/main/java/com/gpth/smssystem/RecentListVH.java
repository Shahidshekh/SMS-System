package com.gpth.smssystem;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

public class RecentListVH extends RecyclerView.ViewHolder {

    ImageView userPic;
    TextView nameTV, timeTV, seenTV, LastM;

    public RecentListVH(@NonNull View itemView) {
        super(itemView);
    }

    public void setList(FragmentActivity application, String mCount, String time, String lastM, String name,
                        String url, String seen, String uid){

        userPic = itemView.findViewById(R.id.user_picR);
        nameTV = itemView.findViewById(R.id.user_nameR);
        timeTV = itemView.findViewById(R.id.msg_timing);
        seenTV = itemView.findViewById(R.id.seenTV);
        LastM = itemView.findViewById(R.id.lastMsg);

        nameTV.setText(name);
        timeTV.setText(time);
        seenTV.setText(seen);
        LastM.setText(lastM);

    }
}
