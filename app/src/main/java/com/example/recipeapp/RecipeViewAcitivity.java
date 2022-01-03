package com.example.recipeapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

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
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class RecipeViewAcitivity extends AppCompatActivity {
    public static ArrayList<FavoritesModel> the_favorites;
    public static int pos;
    public static String activityName;

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


                if(the_favorites.get(pos).getIs_liked() == 1)
                {
                    recipe_view_like_but.setImageResource(R.drawable.ic_unlike);
                    the_favorites.get(pos).setIs_liked(0);

                }
                else
                {
                    recipe_view_like_but.setImageResource(R.drawable.ic_like);
                    the_favorites.get(pos).setIs_liked(1);
                }


                StringRequest request = new StringRequest(Request.Method.POST, Constants.update_recipe_URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try
                        {
                            JSONObject jsonObject = new JSONObject(response);
                            Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();

                            if(activityName.equals("Favorites"))
                            {
                                fetch_todays_recipes();
                                fetch_favorites_recipes();
                            }
                            else if(activityName.equals("TodaysRecipes"))
                            {
                                fetch_todays_recipes();
                            }

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
                        map.put("name", the_favorites.get(pos).getFavorite_name());
                        map.put("username", SharedPreferencedManager.getInstance(RecipeViewAcitivity.this).getUserName());

                        if(the_favorites.get(pos).getIs_liked() == 0)
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

        favorite_recipe_rating_bar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

                the_favorites.get(pos).setFavorite_rate((int) rating);

                StringRequest request = new StringRequest(Request.Method.POST, Constants.update_recipe_rate_URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try
                        {
                            JSONObject jsonObject = new JSONObject(response);
                            Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();


                            if(activityName.equals("Favorites"))
                            {
                                fetch_todays_recipes();
                                fetch_favorites_recipes();
                            }
                            else if(activityName.equals("TodaysRecipes"))
                            {
                                fetch_todays_recipes();
                            }

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
                        map.put("name", the_favorites.get(pos).getFavorite_name());
                        map.put("username", SharedPreferencedManager.getInstance(RecipeViewAcitivity.this).getUserName());
                        map.put("rate", Integer.toString((int) rating));

                        return map;
                    }
                };

                MySingleton.getInstance(RecipeViewAcitivity.this).addToRequestQueue(request);

            }
        });


    }

    void setAttributes()
    {

        if(the_favorites.get(pos).getIs_liked() == 1)
            recipe_view_like_but.setImageResource(R.drawable.ic_like);
        else
            recipe_view_like_but.setImageResource(R.drawable.ic_unlike);

        favorite_name_textview.setText(the_favorites.get(pos).getFavorite_name());
        favorite_type_textview.setText(the_favorites.get(pos).getFavorite_type());
        favorite_calory_textview.setText(the_favorites.get(pos).getFavorite_calory());
        favorite_display_time.setText(the_favorites.get(pos).getDisplay_time());
        favorite_serving_num.setText(Integer.toString(the_favorites.get(pos).getServing_num()) + " Serving");
        favorite_recipe_rating_bar.setRating(the_favorites.get(pos).getFavorite_rate());

        String url = "http://192.168.43.166/android/images/" + the_favorites.get(pos).getImage_dir();
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

    void fetch_todays_recipes()
    {

        HomeActivity.todays_recipes = new ArrayList<>();
        HomeActivity.recommended_recipes = new ArrayList<>();


        StringRequest request = new StringRequest(Request.Method.POST, Constants.get_recipes_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try
                {
                    JSONArray json_array = new JSONArray(response);
                    for(int i = 0; i < json_array.length(); i++)
                    {
                        String name = json_array.getJSONObject(i).getString("name");
                        String calory = json_array.getJSONObject(i).getString("calory");
                        String type = json_array.getJSONObject(i).getString("type");
                        int favorite_rate = json_array.getJSONObject(i).getInt("rate");
                        String display_time = json_array.getJSONObject(i).getString("time");
                        int serving_num = json_array.getJSONObject(i).getInt("serving_num");
                        int is_liked = json_array.getJSONObject(i).getInt("is_liked");
                        String image_dir = json_array.getJSONObject(i).getString("image_dir");



                        HomeActivity.todays_recipes.add(new FavoritesModel(name, calory, type, favorite_rate, display_time, serving_num, is_liked, image_dir));
                        HomeActivity.recommended_recipes.add(new FavoritesModel(name, calory, type, favorite_rate, display_time, serving_num, is_liked, image_dir));

                        HomeActivity.todaysRecipesAdapter.notifyDataSetChanged();
                        HomeActivity.recommendedAdapter.notifyDataSetChanged();

                    }
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
                map.put("user_name", SharedPreferencedManager.getInstance(RecipeViewAcitivity.this).getUserName());
                return map;
            }
        };

        MySingleton.getInstance(this).addToRequestQueue(request);


        HomeActivity.todaysRecipesAdapter = new TodaysRecipesAdapter(HomeActivity.todays_recipes, RecipeViewAcitivity.this);
        HomeActivity.todaysRecyclerView.setAdapter(HomeActivity.todaysRecipesAdapter);
        HomeActivity.todaysRecyclerView.setLayoutManager(new LinearLayoutManager(RecipeViewAcitivity.this, LinearLayoutManager.HORIZONTAL, false));

        HomeActivity.recommendedAdapter = new FavoritesAdapter(HomeActivity.recommended_recipes, RecipeViewAcitivity.this);
        HomeActivity.recommendedRecyclerView.setAdapter(HomeActivity.recommendedAdapter);
        HomeActivity.recommendedRecyclerView.setLayoutManager(new LinearLayoutManager(RecipeViewAcitivity.this, LinearLayoutManager.VERTICAL, false));

        HomeActivity.todaysRecipesAdapter.notifyDataSetChanged();
        HomeActivity.recommendedAdapter.notifyDataSetChanged();


    }

    void fetch_favorites_recipes()
    {
        FavoritesActivity.favorites = new ArrayList<>();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.get_favorites_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray json_array = new JSONArray(response);
                    for(int i = 0; i < json_array.length(); i++)
                    {
                        String name = json_array.getJSONObject(i).getString("name");
                        String calory = json_array.getJSONObject(i).getString("calory");
                        String type = json_array.getJSONObject(i).getString("type");
                        int favorite_rate = json_array.getJSONObject(i).getInt("rate");
                        String display_time = json_array.getJSONObject(i).getString("time");
                        int serving_num = json_array.getJSONObject(i).getInt("serving_num");
                        int is_liked = json_array.getJSONObject(i).getInt("is_liked");
                        String image_dir = json_array.getJSONObject(i).getString("image_dir");

                        FavoritesActivity.favorites.add(new FavoritesModel(name, calory, type, favorite_rate, display_time, serving_num, is_liked, image_dir));

                        FavoritesActivity.favoritesAdapter.notifyDataSetChanged();


                    }
                } catch (JSONException e) {
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
                map.put("username", SharedPreferencedManager.getInstance(RecipeViewAcitivity.this).getUserName());
                return map;
            }
        };

        MySingleton.getInstance(RecipeViewAcitivity.this).addToRequestQueue(stringRequest);

        FavoritesActivity.favoritesAdapter = new FavoritesAdapter(FavoritesActivity.favorites, RecipeViewAcitivity.this);
        FavoritesActivity.favorites_recycler.setAdapter(FavoritesActivity.favoritesAdapter);
        FavoritesActivity.favorites_recycler.setLayoutManager(new LinearLayoutManager(RecipeViewAcitivity.this));

        FavoritesActivity.favoritesAdapter.notifyDataSetChanged();
    }

}