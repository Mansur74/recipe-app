package com.example.recipeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.w3c.dom.Text;

public class SideMenuActivity extends AppCompatActivity {

    public static int menu_choice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_side_menu);

        TextView settings_but = findViewById(R.id.settings);
        TextView home_but = findViewById(R.id.home);
        TextView favorites_but = findViewById(R.id.favorites);
        TextView signOut_but = findViewById(R.id.sign_out);

        for (Drawable drawable : home_but.getCompoundDrawables()) {
            home_but.setTextColor(getResources().getColor(R.color.gray));
            if (drawable != null) {
                drawable.setColorFilter(new PorterDuffColorFilter(getResources().getColor(R.color.gray), PorterDuff.Mode.SRC_IN));
            }
        }

        if (menu_choice == 1)
        {
            change_drawable(home_but);
        }

        else if(menu_choice == 2)
        {
            change_drawable(favorites_but);
        }

        else if(menu_choice == 4)
        {
            change_drawable(settings_but);
        }

        else if(menu_choice == 7)
        {
            change_drawable(signOut_but);
        }




        settings_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                change_drawable(settings_but);
                Intent intent = new Intent(SideMenuActivity.this, SettingsActivity.class);
                startActivity(intent);
                finish();

            }
        });

        home_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                change_drawable(home_but);
                Intent intent = new Intent(SideMenuActivity.this, HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });

        favorites_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                change_drawable(favorites_but);
                Intent intent = new Intent(SideMenuActivity.this, FavoritesActivity.class);
                startActivity(intent);
                finish();
            }
        });

        signOut_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferencedManager.getInstance(SideMenuActivity.this).userLogout();
                change_drawable(signOut_but);
                Intent intent = new Intent(SideMenuActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }

    @Override
    public void finish()
    {
        super.finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    void change_drawable(TextView textView)
    {
        textView.setTextColor(getResources().getColor(R.color.colorPrimary));
        for (Drawable drawable : textView.getCompoundDrawables()) {
            if (drawable != null) {
                drawable.setColorFilter(new PorterDuffColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_IN));
            }
        }
    }
}