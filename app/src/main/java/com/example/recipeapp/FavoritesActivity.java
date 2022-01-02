package com.example.recipeapp;

        import androidx.appcompat.app.AppCompatActivity;
        import androidx.recyclerview.widget.LinearLayoutManager;
        import androidx.recyclerview.widget.RecyclerView;

        import android.content.Intent;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.ImageView;
        import android.widget.TextView;

        import com.example.recipeapp.Adapter.FavoritesAdapter;
        import com.example.recipeapp.Models.FavoritesModel;

        import java.util.ArrayList;

public class FavoritesActivity extends AppCompatActivity {

    RecyclerView favorites_recycler;
    ArrayList<FavoritesModel> favorites;
    FavoritesAdapter favoritesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        ImageView side_but = findViewById(R.id.side_menu);
        ImageView notifications_but = findViewById(R.id.notifications);


        favorites_recycler = findViewById(R.id.favorites_recyclerview);
        favorites = new ArrayList<>();
        for(int i = 0; i < 10; i++)
        {
            favorites.add(new FavoritesModel("Blueberry Muffins", "120 Calory", "Breakfast", 0, "sdf", 0, 0, "http://192.168.43.166/android/v1"));
        }

        favoritesAdapter = new FavoritesAdapter(favorites, FavoritesActivity.this);
        favorites_recycler.setAdapter(favoritesAdapter);
        favorites_recycler.setLayoutManager(new LinearLayoutManager(FavoritesActivity.this));

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

}