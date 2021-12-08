package com.example.recipeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        ImageView side_but = findViewById(R.id.side_menu);
        ImageView notifications_but = findViewById(R.id.notifications);

        side_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SideMenuActivity.menu_choice = 4;
                Intent intent = new Intent(SettingsActivity.this, SideMenuActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });

        notifications_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, NotificationsActivity.class);
                startActivity(intent);
            }
        });
    }
}