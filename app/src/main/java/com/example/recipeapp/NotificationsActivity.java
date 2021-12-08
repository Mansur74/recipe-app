package com.example.recipeapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.recipeapp.Adapter.NotificationsAdapter;
import com.example.recipeapp.Models.FavoritesModel;
import com.example.recipeapp.Models.NotificationsModel;

import java.util.ArrayList;

public class NotificationsActivity extends AppCompatActivity {

    RecyclerView notifications_recycler_view;
    NotificationsAdapter notificationsAdapter;
    ArrayList<NotificationsModel> notifications;
    ImageView side_but;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        notifications = new ArrayList<>();
        for(int i = 0; i < 10; i++)
        {
            notifications.add(new NotificationsModel("Message", "20-02-1999"));
        }
        notificationsAdapter = new NotificationsAdapter(notifications, NotificationsActivity.this);

        notifications_recycler_view = findViewById(R.id.notifications_recyclerview);
        notifications_recycler_view.setAdapter(notificationsAdapter);
        notifications_recycler_view.setLayoutManager(new LinearLayoutManager(this));
        notifications_recycler_view.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));


        side_but = findViewById(R.id.side_menu);
        side_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SideMenuActivity.menu_choice = 0;
                Intent intent = new Intent(NotificationsActivity.this, SideMenuActivity.class);
                startActivity(intent);
            }
        });


    }
}