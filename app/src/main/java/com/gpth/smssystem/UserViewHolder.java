package com.gpth.smssystem;

import android.app.Application;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class UserViewHolder extends RecyclerView.ViewHolder {

    ImageView imageView;
    TextView nameTv, aboutTv;

    public UserViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public void setUser(Application application, String name, String phone, String about, String url, String uid){

        imageView = itemView.findViewById(R.id.user_pic);
        nameTv = itemView.findViewById(R.id.user_name);
        aboutTv = itemView.findViewById(R.id.abt_user);

        nameTv.setText(name);

        if (about.equals("")){
            aboutTv.setVisibility(View.GONE);
        } else {
            aboutTv.setText(about);
        }

    }
}
