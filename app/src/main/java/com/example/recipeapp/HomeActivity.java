package com.example.recipeapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
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

public class HomeActivity extends AppCompatActivity {

    public static TodaysRecipesAdapter todaysRecipesAdapter;
    public static FavoritesAdapter recommendedAdapter;

    RecyclerView recommendedRecyclerView;
    RecyclerView todaysRecyclerView;

    ArrayList<FavoritesModel> todays_recipes;
    ArrayList<FavoritesModel> recommended_recipes;

    BottomSheetBehavior bottomSheetBehavior;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        adapterSettings();

        LinearLayout bottomSheetLayout = findViewById(R.id.filter_bottom_sheet);
        ImageView side_but = findViewById(R.id.side_menu);
        ImageView notifications_but = findViewById(R.id.notifications);
        ImageView filter_but = findViewById(R.id.filter);



        /* Builds fullscreen mode
        if(Build.VERSION.SDK_INT >= 21)
        {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }

         */

        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetLayout);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                bottomSheetBehavior.setPeekHeight(0);
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });




        side_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SideMenuActivity.menu_choice = 1;
                Intent intent = new Intent(HomeActivity.this, SideMenuActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);


            }
        });

        notifications_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, NotificationsActivity.class);
                startActivity(intent);
            }
        });


        filter_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetBehavior.setPeekHeight(200);
                bottomSheetBehavior.setHideable(false);

            }
        });

    }

    void adapterSettings()
    {
        this.todaysRecyclerView = findViewById(R.id.todays_recyclerview);
        this.recommendedRecyclerView = findViewById(R.id.recommended_recyclerview);

        todays_recipes = new ArrayList<>();
        recommended_recipes = new ArrayList<>();


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



                        todays_recipes.add(new FavoritesModel(name, calory, type, favorite_rate, display_time, serving_num, is_liked, image_dir));
                        recommended_recipes.add(new FavoritesModel(name, calory, type, favorite_rate, display_time, serving_num, is_liked, image_dir));

                        todaysRecipesAdapter.notifyDataSetChanged();
                        recommendedAdapter.notifyDataSetChanged();

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
        });

        MySingleton.getInstance(this).addToRequestQueue(request);


        this.todaysRecipesAdapter = new TodaysRecipesAdapter(todays_recipes, HomeActivity.this);
        this.todaysRecyclerView.setAdapter(todaysRecipesAdapter);
        this.todaysRecyclerView.setLayoutManager(new LinearLayoutManager(HomeActivity.this, LinearLayoutManager.HORIZONTAL, false));

        this.recommendedAdapter = new FavoritesAdapter(recommended_recipes, HomeActivity.this);
        this.recommendedRecyclerView.setAdapter(recommendedAdapter);
        this.recommendedRecyclerView.setLayoutManager(new LinearLayoutManager(HomeActivity.this, LinearLayoutManager.VERTICAL, false));

        todaysRecipesAdapter.notifyDataSetChanged();
        recommendedAdapter.notifyDataSetChanged();


    }


}