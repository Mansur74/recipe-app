package com.example.recipeapp;

        import androidx.annotation.Nullable;
        import androidx.appcompat.app.AppCompatActivity;
        import androidx.recyclerview.widget.LinearLayoutManager;
        import androidx.recyclerview.widget.RecyclerView;

        import android.content.Intent;
        import android.os.Bundle;
        import android.text.Editable;
        import android.text.TextWatcher;
        import android.view.View;
        import android.widget.EditText;
        import android.widget.ImageView;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.android.volley.AuthFailureError;
        import com.android.volley.Request;
        import com.android.volley.Response;
        import com.android.volley.VolleyError;
        import com.android.volley.toolbox.StringRequest;
        import com.example.recipeapp.Adapter.FavoritesAdapter;
        import com.example.recipeapp.Models.FavoritesModel;

        import org.json.JSONArray;
        import org.json.JSONException;

        import java.util.ArrayList;
        import java.util.HashMap;
        import java.util.Map;

public class FavoritesActivity extends AppCompatActivity {

    public static RecyclerView favorites_recycler;
    public static ArrayList<FavoritesModel> favorites;
    public static FavoritesAdapter favoritesAdapter;

    EditText searchBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        ImageView side_but = findViewById(R.id.side_menu);
        ImageView notifications_but = findViewById(R.id.notifications);
        searchBox = findViewById(R.id.favorites_searchBox);

        adapterSettings();


        searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });

        side_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SideMenuActivity.menu_choice = 2;
                Intent intent = new Intent(FavoritesActivity.this, SideMenuActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });

        notifications_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FavoritesActivity.this, NotificationsActivity.class);
                startActivity(intent);
            }
        });

    }

    public void adapterSettings()
    {
        favorites_recycler = findViewById(R.id.favorites_recyclerview);
        favorites = new ArrayList<>();

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

                        favorites.add(new FavoritesModel(name, calory, type, favorite_rate, display_time, serving_num, is_liked, image_dir));
                        favoritesAdapter.notifyDataSetChanged();


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
                map.put("username", SharedPreferencedManager.getInstance(FavoritesActivity.this).getUserName());
                return map;
            }
        };

        MySingleton.getInstance(FavoritesActivity.this).addToRequestQueue(stringRequest);

        favoritesAdapter = new FavoritesAdapter(favorites, FavoritesActivity.this);
        favorites_recycler.setAdapter(favoritesAdapter);
        favorites_recycler.setLayoutManager(new LinearLayoutManager(FavoritesActivity.this));
    }

    void filter(String text)
    {
        ArrayList<FavoritesModel> filterArrayList = new ArrayList<>();
        for(FavoritesModel recipe : this.favorites)
        {
            if(recipe.getFavorite_name().toLowerCase().contains(text.toLowerCase()))
            {
                filterArrayList.add(recipe);
            }
        }
        favoritesAdapter.filterList(filterArrayList);

    }


}