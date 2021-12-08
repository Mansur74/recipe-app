package com.example.recipeapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipeapp.Models.FavoritesModel;
import com.example.recipeapp.R;
import com.example.recipeapp.RecipeViewAcitivity;

import java.util.ArrayList;

public class TodaysRecipesAdapter extends RecyclerView.Adapter<TodaysRecipesAdapter.MyViewHolder> {

    public static ArrayList<FavoritesModel> todaysRecipes;
    Context context;



    public TodaysRecipesAdapter(ArrayList<FavoritesModel> todaysRecipes, Context context) {
        this.todaysRecipes = todaysRecipes;
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
        RelativeLayout todays_card_layout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            favorite_image = itemView.findViewById(R.id.food_image);
            favorite_liked_icon = itemView.findViewById(R.id.todays_liked_icon);
            favorite_type = itemView.findViewById(R.id.food_type);
            favorite_name = itemView.findViewById(R.id.food_name);
            favorite_rate = itemView.findViewById(R.id.food_rate);
            favorite_calory = itemView.findViewById(R.id.food_calory);
            todays_card_layout = itemView.findViewById(R.id.todays_recipes_layout);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.todays_recipes_card, parent ,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.favorite_name.setText(todaysRecipes.get(position).getFavorite_name());
        holder.favorite_type.setText(todaysRecipes.get(position).getFavorite_type());
        holder.favorite_calory.setText(todaysRecipes.get(position).getFavorite_calory());
        holder.favorite_rate.setRating(todaysRecipes.get(position).getFavorite_rate());
        if(todaysRecipes.get(position).getIs_liked())
            holder.favorite_liked_icon.setBackgroundResource(R.drawable.ic_like);

        holder.todays_card_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecipeViewAcitivity.favorite_pos = position;
                Intent intent = new Intent(context, RecipeViewAcitivity.class);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return todaysRecipes.size();
    }


}
