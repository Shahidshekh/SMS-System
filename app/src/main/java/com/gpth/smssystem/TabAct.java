package com.gpth.smssystem;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.gpth.smssystem.databinding.ActivityTabBinding;
import com.gpth.smssystem.ui.main.SectionsPagerAdapter;

public class TabAct extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        com.gpth.smssystem.databinding.ActivityTabBinding binding = ActivityTabBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = binding.viewPager;
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = binding.tabs;
        tabs.setupWithViewPager(viewPager);
        FloatingActionButton fab = binding.fab;
        ImageView moreBtn = findViewById(R.id.moreBtn);

        tabs.setSelectedTabIndicatorColor(Color.parseColor("#ffffff"));
        tabs.setSelectedTabIndicatorHeight((int) (5 * getResources().getDisplayMetrics().density));
        tabs.setTabTextColors(Color.parseColor("#FF8887A8"), Color.parseColor("#ffffff"));

        fab.setOnClickListener(view -> {
            Intent intent = new Intent(TabAct.this, ShowUsers.class);
            startActivity(intent);
        });

        moreBtn.setOnClickListener(view -> {
            PopupMenu popupMenu = new PopupMenu(TabAct.this, view);
            popupMenu.setOnMenuItemClickListener(TabAct.this);
            popupMenu.inflate(R.menu.menufile);
            popupMenu.show();
        });
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {

        switch (menuItem.getItemId()) {
            case R.id.profile:
                Intent intent = new Intent(TabAct.this, ProfileUpdate.class);
                startActivity(intent);
                return true;
            case R.id.settings:
                Toast.makeText(this, "Not Yet! #TODO", Toast.LENGTH_SHORT).show();
                break;
            case R.id.logout:
                Toast.makeText(this, "Logging Out...", Toast.LENGTH_SHORT).show();
                FirebaseAuth.getInstance().signOut();
                Intent intent1 = new Intent(TabAct.this, MainActivity.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent1);
                return true;
        }
        return false;
    }
}