package com.gpth.smssystem;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

public class RecyclerNetwork extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
