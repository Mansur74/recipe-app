package com.example.recipeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.recipeapp.Adapter.TodaysRecipesAdapter;
import com.example.recipeapp.Models.FavoritesModel;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

public class RecipeViewAcitivity extends AppCompatActivity {
    public static int favorite_pos;

    BottomSheetBehavior bottomSheetBehavior;


    LinearLayout bottomSheetLayout;
    ImageView back_but;
    ImageView recipe_view_like_but;
    TextView favorite_name_textview;
    TextView favorite_type_textview;
    TextView favorite_calory_textview;
    TextView favorite_display_time;
    TextView favorite_serving_num;
    RatingBar favorite_recipe_rating_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_view_acitivity);

        back_but = findViewById(R.id.back_button);
        recipe_view_like_but = findViewById(R.id.recipe_view_liked_but);
        favorite_name_textview = findViewById(R.id.recipe_view_favorite_name);
        favorite_type_textview = findViewById(R.id.recipe_view_favorite_type);
        favorite_calory_textview = findViewById(R.id.recipe_view_favorite_calory);
        favorite_display_time = findViewById(R.id.recipe_view_favorite_display_time);
        favorite_serving_num = findViewById(R.id.recipe_view_favorite_serving_num);
        favorite_recipe_rating_bar = findViewById(R.id.recipe_view_favorite_rate);
        ImageView notifications_but = findViewById(R.id.notifications);

        setAttributes();



        bottomSheetLayout = findViewById(R.id.directions_bottom_sheet);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetLayout);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

        bottomSheetBehavior.setHideable(false);

        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
               @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {

        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {

        }
    });


        back_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        notifications_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecipeViewAcitivity.this, NotificationsActivity.class);
                startActivity(intent);
            }
        });

        recipe_view_like_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Here will be backend opereations.
                if(TodaysRecipesAdapter.todaysRecipes.get(favorite_pos).getIs_liked())
                {
                    recipe_view_like_but.setImageResource(R.drawable.ic_unlike);
                    TodaysRecipesAdapter.todaysRecipes.get(favorite_pos).setIs_liked(false);

                }


                else
                {
                    recipe_view_like_but.setImageResource(R.drawable.ic_like);
                    TodaysRecipesAdapter.todaysRecipes.get(favorite_pos).setIs_liked(true);
                }

            }
        });



    }

    void setAttributes()
    {
        FavoritesModel the_favorite = TodaysRecipesAdapter.todaysRecipes.get(favorite_pos);

        if(the_favorite.getIs_liked())
            recipe_view_like_but.setImageResource(R.drawable.ic_like);
        else
            recipe_view_like_but.setImageResource(R.drawable.ic_unlike);

        favorite_name_textview.setText(the_favorite.getFavorite_name());
        favorite_type_textview.setText(the_favorite.getFavorite_type());
        favorite_calory_textview.setText(the_favorite.getFavorite_calory());
        favorite_display_time.setText(Integer.toString(the_favorite.getDisplay_time()) + "mins");
        favorite_serving_num.setText(Integer.toString(the_favorite.getServing_num()) + "1 Serving");
        favorite_recipe_rating_bar.setRating(the_favorite.getFavorite_rate());


    }
}