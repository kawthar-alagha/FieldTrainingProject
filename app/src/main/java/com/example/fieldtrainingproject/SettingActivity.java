package com.example.fieldtrainingproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_setting);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation_bar_setting);
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.nav_jobs) {
                    startActivity(new Intent(SettingActivity.this, MainActivity.class));
                    return true;
                } else if (id == R.id.nav_bookmark) {
                    startActivity(new Intent(SettingActivity.this, bookmarkScreen.class));

                    return true;
                }
                return false;
            }
        });
    }
}
