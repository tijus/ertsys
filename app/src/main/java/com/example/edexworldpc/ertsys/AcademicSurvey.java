package com.example.edexworldpc.ertsys;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.kosalgeek.asynctask.*;
import com.kosalgeek.asynctask.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class AcademicSurvey extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, RatingBar.OnRatingBarChangeListener, AsyncResponse {

    private RatingBar ratingBar1, ratingBar2, ratingBar3, ratingBar4, ratingBar5;

    SharedPreferences sharedPreferences;
    Button btn_acadsurvey;
    public HashMap responses = new HashMap();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_academic_survey);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        btn_acadsurvey = (Button)findViewById(R.id.btn_acadsurvey);

        btn_acadsurvey.setOnClickListener(this);
        ratingBar1 = (RatingBar) findViewById(R.id.acadopt1);
        ratingBar1.setOnRatingBarChangeListener(this);
        ratingBar2 = (RatingBar) findViewById(R.id.acadopt2);
        ratingBar2.setOnRatingBarChangeListener(this);
        ratingBar3 = (RatingBar) findViewById(R.id.acadopt3);
        ratingBar3.setOnRatingBarChangeListener(this);
        ratingBar4 = (RatingBar) findViewById(R.id.acadopt4);
        ratingBar4.setOnRatingBarChangeListener(this);
        ratingBar5 = (RatingBar) findViewById(R.id.acadopt5);
        ratingBar5.setOnRatingBarChangeListener(this);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.academic_survey, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            SharedPreferences sharedPreferences = getSharedPreferences("LoginPreference", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();
            startActivity(new Intent(this, Login.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            startActivity(new Intent(this, Dashboard.class));

        } else if (id == R.id.nav_about_us) {
            startActivity(new Intent(this, MainActivity.class));

        }else if (id == R.id.nav_profile) {
            startActivity(new Intent(this, Profile.class));

        }else if (id == R.id.nav_notify) {
            startActivity(new Intent(this, ActivityArea.class));

        }else if (id == R.id.nav_share) {
            startActivity(new Intent(this, Feedback.class));
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public void onClick(View view) {
        Set set = responses.entrySet();

        // Get an iterator
        Iterator i = set.iterator();
        HashMap postData = new HashMap();
        sharedPreferences = getSharedPreferences("LoginPreference", Context.MODE_PRIVATE);
        String pref = sharedPreferences.getString("appid", "");

        postData.put("userappid", pref);
        // Display elements
        while(i.hasNext()) {
            Map.Entry me = (Map.Entry)i.next();
            String keyid = String.valueOf(me.getKey());

            if(keyid.equalsIgnoreCase("1"))
            {
                postData.put("art", String.valueOf(me.getValue()));
            }
            else if(keyid.equalsIgnoreCase("2"))
            {
                postData.put("science", String.valueOf(me.getValue()));
            }
            else if(keyid.equalsIgnoreCase("3"))
            {
                postData.put("maths", String.valueOf(me.getValue()));
            }
            else if(keyid.equalsIgnoreCase("4"))
            {
                postData.put("language", String.valueOf(me.getValue()));
            }
            else if(keyid.equalsIgnoreCase("5"))
            {
                postData.put("sst", String.valueOf(me.getValue()));
            }


        }
        PostResponseAsyncTask task = new PostResponseAsyncTask(AcademicSurvey.this, postData);
        task.execute("http://ertsys.esy.es/development/app/services/InsertAcadSurveyService.php");
    }

    @Override
    public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {

        int i = ratingBar.getId();
        if(i == R.id.acadopt1) {
            Toast.makeText(this, String.valueOf(ratingBar.getRating()), Toast.LENGTH_SHORT).show();
            responses.put("1", String.valueOf(ratingBar.getRating()));
        }
        else if(i == R.id.acadopt2) {
            Toast.makeText(this, String.valueOf(ratingBar.getRating()), Toast.LENGTH_SHORT).show();
            responses.put("2", String.valueOf(ratingBar.getRating()));
        }
        else if(i == R.id.acadopt3) {
            Toast.makeText(this, String.valueOf(ratingBar.getRating()), Toast.LENGTH_SHORT).show();
            responses.put("3", String.valueOf(ratingBar.getRating()));
        }
        else if(i == R.id.acadopt4) {
            Toast.makeText(this, String.valueOf(ratingBar.getRating()), Toast.LENGTH_SHORT).show();
            responses.put("4", String.valueOf(ratingBar.getRating()));
        }
        else if(i == R.id.acadopt5) {
            Toast.makeText(this, String.valueOf(ratingBar.getRating()), Toast.LENGTH_SHORT).show();
            responses.put("5", String.valueOf(ratingBar.getRating()));
        }

    }

    @Override
    public void processFinish(String s) {
        try {
            JSONObject jsonObject = new JSONObject(s);
            Object jsonresult = jsonObject.get("id");
            Object jsonmessage = jsonObject.get("message");

            String result = String.valueOf(jsonresult);
            String message = String.valueOf(jsonmessage);

            if(result.equalsIgnoreCase("0")) {
                Toast.makeText(AcademicSurvey.this, message, Toast.LENGTH_SHORT).show();
            }
            else if(result.equalsIgnoreCase("1") && message.equalsIgnoreCase("True"))
            {
                Toast.makeText(AcademicSurvey.this, "Successfully inserted data ...", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, ActivityArea.class));
            }
            else if(result.equalsIgnoreCase("2") && message.equalsIgnoreCase("Error"))
            {
                Toast.makeText(AcademicSurvey.this, "Error updating database. Contact administrator ...", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, ActivityArea.class));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
