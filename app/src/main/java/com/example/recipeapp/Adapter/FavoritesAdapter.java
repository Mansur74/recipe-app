package com.example.recipeapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.recipeapp.Models.FavoritesModel;
import com.example.recipeapp.R;
import com.example.recipeapp.RecipeViewAcitivity;

import java.util.ArrayList;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.MyViewHolder> {

    ArrayList<FavoritesModel> favorites;
    Context context;

    public FavoritesAdapter(ArrayList<FavoritesModel> favorites , Context context)
    {
        this.favorites = favorites;
        this.context = context;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        ImageView favorite_image;
        ImageView favorite_liked_icon;
        TextView favorite_type;
        TextView favorite_name;
        TextView favorite_calory;
        RatingBar favorite_rate;
        RelativeLayout favorite_layout;



        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            favorite_image = itemView.findViewById(R.id.favorite_recipe_image);
            favorite_liked_icon = itemView.findViewById(R.id.favorite_liked_icon);
            favorite_type = itemView.findViewById(R.id.favorite_type);
            favorite_name = itemView.findViewById(R.id.favorite_name);
            favorite_rate = itemView.findViewById(R.id.favorite_rate);
            favorite_calory = itemView.findViewById(R.id.favorite_calory);
            favorite_layout = itemView.findViewById(R.id.favorites_card);

        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorites_card, parent ,false);
        return new MyViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return favorites.size();
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.favorite_name.setText(favorites.get(position).getFavorite_name());
        holder.favorite_type.setText(favorites.get(position).getFavorite_type());
        holder.favorite_calory.setText(favorites.get(position).getFavorite_calory());
        holder.favorite_rate.setRating(favorites.get(position).getFavorite_rate());

        String url = "http://192.168.3.57/android/images/" + favorites.get(position).getImage_dir();

       Glide.with(holder.favorite_image.getContext()).load(url).into(holder.favorite_image);

        if(favorites.get(position).getIs_liked() == 1)
            holder.favorite_liked_icon.setBackgroundResource(R.drawable.ic_like);
        else
            holder.favorite_liked_icon.setBackgroundResource(R.drawable.ic_unlike);;

        int pos = position;
        holder.favorite_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecipeViewAcitivity.the_favorites = favorites;
                RecipeViewAcitivity.pos = pos;
                RecipeViewAcitivity.activityName = "Favorites";
                Intent intent = new Intent(context, RecipeViewAcitivity.class);
                context.startActivity(intent);
            }
        });

    }

    public void filterList(ArrayList<FavoritesModel> filterArrayList)
    {
        favorites = filterArrayList;
        notifyDataSetChanged();
    }


}
