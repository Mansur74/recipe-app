package com.example.recipeapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.example.recipeapp.Adapter.FavoritesAdapter;
import com.example.recipeapp.Adapter.TodaysRecipesAdapter;
import com.example.recipeapp.Models.FavoritesModel;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class RecipeViewAcitivity extends AppCompatActivity {
    public static FavoritesModel the_favorite;
    public static int the_favorite_pos;

    BottomSheetBehavior bottomSheetBehavior;


    LinearLayout bottomSheetLayout;
    ImageView back_but;
    ImageView recipe_view_like_but;
    ImageView recipe_view_image;
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

        recipe_view_image = findViewById(R.id.recipe_view_favorite_image);
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


                if(the_favorite.getIs_liked() == 1)
                {
                    recipe_view_like_but.setImageResource(R.drawable.ic_unlike);
                    the_favorite.setIs_liked(0);

                }
                else
                {
                    recipe_view_like_but.setImageResource(R.drawable.ic_like);
                    the_favorite.setIs_liked(1);
                }

                TodaysRecipesAdapter.todaysRecipes.set(the_favorite_pos, the_favorite);
                HomeActivity.todaysRecipesAdapter.notifyDataSetChanged();


                StringRequest request = new StringRequest(Request.Method.POST, Constants.update_recipe_URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try
                        {
                            JSONObject jsonObject = new JSONObject(response);
                            Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();


                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                })
                {
                    @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String, String> map = new HashMap<>();
                        map.put("name", the_favorite.getFavorite_name());

                        if(the_favorite.getIs_liked() == 0)
                        {
                            map.put("is_liked", "0");

                        }
                        else
                        {
                            map.put("is_liked", "1");

                        }

                        return map;
                    }
                };

                MySingleton.getInstance(RecipeViewAcitivity.this).addToRequestQueue(request);


            }
        });


    }

    void setAttributes()
    {

        if(the_favorite.getIs_liked() == 1)
            recipe_view_like_but.setImageResource(R.drawable.ic_like);
        else
            recipe_view_like_but.setImageResource(R.drawable.ic_unlike);

        favorite_name_textview.setText(the_favorite.getFavorite_name());
        favorite_type_textview.setText(the_favorite.getFavorite_type());
        favorite_calory_textview.setText(the_favorite.getFavorite_calory());
        favorite_display_time.setText(the_favorite.getDisplay_time());
        favorite_serving_num.setText(Integer.toString(the_favorite.getServing_num()) + " Serving");
        favorite_recipe_rating_bar.setRating(the_favorite.getFavorite_rate());

        String url = "http://192.168.43.166/android/images/" + the_favorite.getImage_dir();
        Glide.with(this).load(url).into(recipe_view_image);


    }

    public static String findDifference(String start_date) {

        // SimpleDateFormat converts the
        // string format to date object
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // Try Block
        try {

            Date date = new Date();

            Date d1 = sdf.parse(start_date);
            Date d2 = sdf.parse(sdf.format(date));

            // in milliseconds
            long difference_In_Time = d2.getTime() - d1.getTime();


            long difference_In_Minutes
                    = (difference_In_Time
                    / (1000 * 60));

            if(difference_In_Minutes < 60)
                return difference_In_Minutes + " mins";
            else if(difference_In_Minutes / 60 < 24)
                return (int) difference_In_Minutes / 60 + " h " + difference_In_Minutes % 60 + " mins";
            else
                return (int) difference_In_Minutes / (60 * 24) + " d " + difference_In_Minutes % (60 * 24) + " h " + difference_In_Minutes % 60 + " mins";

        }
        catch (ParseException e) {
            e.printStackTrace();
            return "";
        }

    }
}