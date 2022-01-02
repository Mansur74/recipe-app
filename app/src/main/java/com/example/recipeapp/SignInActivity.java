package com.example.recipeapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignInActivity extends AppCompatActivity {

    EditText email;
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        TextView register_but = findViewById(R.id.register_TextView);
        Button signIn_but = findViewById(R.id.sign_in_button);

        email = findViewById(R.id.editTextTextEmailAddressLogin);
        password = findViewById(R.id.editTextTextPasswordLogin);


        register_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInActivity.this, FavoritesActivity.class);
                startActivity(intent);
                finish();
            }
        });

        signIn_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInUser();
            }
        });


    }

    public void signInUser()
    {
        StringRequest request = new StringRequest(Request.Method.POST, Constants.login_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try
                {
                    JSONObject json_object = new JSONObject(response);
                    Toast.makeText(getApplicationContext(), json_object.getString("message"), Toast.LENGTH_LONG).show();
                    if(!json_object.getBoolean("error"))
                    {
                        SharedPreferencedManager.getInstance(SignInActivity.this).user_login(json_object.getInt("ID"), json_object.getString("username"), json_object.getString("email"));
                        Intent intent = new Intent(SignInActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
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
                map.put("email", email.getText().toString());
                map.put("password", password.getText().toString());

                return map;
            }
        };

        MySingleton.getInstance(this).addToRequestQueue(request);

    }
}