package com.example.recipeapp.Adapter;

import android.app.Notification;
import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.example.recipeapp.Models.NotificationsModel;
import com.example.recipeapp.R;

import java.util.ArrayList;


public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.MyViewHolder>{


    ViewBinderHelper viewBinderHelper;
    ArrayList<NotificationsModel> notifications;
    Context context;


    public NotificationsAdapter(ArrayList<NotificationsModel> notifications, Context context) {
        this.viewBinderHelper = new ViewBinderHelper();
        this.notifications = notifications;
        this.context = context;



    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.notifications_card, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        viewBinderHelper.setOpenOnlyOne(true);
        viewBinderHelper.bind(holder.swipeRevealLayout, String.valueOf(notifications.get(position).getMessage()));
        viewBinderHelper.closeLayout(String.valueOf(notifications.get(position).getMessage()));
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder
    {

        SwipeRevealLayout swipeRevealLayout;
        ImageView delete, unread;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            swipeRevealLayout = itemView.findViewById(R.id.swipper_layout);
            delete = itemView.findViewById(R.id.remove);
            unread = itemView.findViewById(R.id.unread);
        }
    }


}
