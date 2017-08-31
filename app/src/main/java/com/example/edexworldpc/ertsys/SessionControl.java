package com.example.edexworldpc.ertsys;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class SessionControl extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences;
        sharedPreferences = getSharedPreferences("LoginPreference", Context.MODE_PRIVATE);
        String pref = sharedPreferences.getString("email", null);

        if(pref == null)
        {
            startActivity(new Intent(this, Login.class));
        }
        else
        {
            startActivity(new Intent(this, Dashboard.class));
        }
    }

}
