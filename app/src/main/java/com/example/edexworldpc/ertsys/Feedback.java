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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.kosalgeek.asynctask.*;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Feedback extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, AsyncResponse {

    public List<String> questionlist1 = new ArrayList<String>();
    public List<String> questionlist2 = new ArrayList<String>();
    public List<String> questionlist3 = new ArrayList<String>();
    public List<String> questionlist4 = new ArrayList<String>();
    public List<String> questionlist5 = new ArrayList<String>();

    public Spinner spinner1, spinner2, spinner3, spinner4, spinner5;
    public TextView questtext1, questtext2, questtext3, questtext4, questtext5;
    Button btn_feedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        btn_feedback = (Button)findViewById(R.id.btn_survey);

        btn_feedback.setOnClickListener(this);
        questtext1 = (TextView)findViewById(R.id.question1);
        spinner1 = (Spinner)findViewById(R.id.spinner1);
        questtext2 = (TextView)findViewById(R.id.question2);
        spinner2 = (Spinner)findViewById(R.id.spinner2);
        questtext3 = (TextView)findViewById(R.id.question3);
        spinner3 = (Spinner)findViewById(R.id.spinner3);
        questtext4 = (TextView)findViewById(R.id.question4);
        spinner4 = (Spinner)findViewById(R.id.spinner4);
        questtext5 = (TextView)findViewById(R.id.question5);
        spinner5 = (Spinner)findViewById(R.id.spinner5);

        questtext1.setText("How much can you relate to your recommendations?");
        questtext2.setText("How user friendly this application is?");
        questtext3.setText("Did you find the app useful in making career choices?");
        questtext4.setText("How would you rate this app?");
        questtext5.setText("Would you like to suggest this app to your friends?");

        questionlist1.add("Select");
        questionlist1.add("Above 85 %");
        questionlist1.add("70% to 85%");
        questionlist1.add("50% to 70%");
        questionlist1.add("Below 50%");
        questionlist2.add("Select");
        questionlist2.add("Very Good");
        questionlist2.add("Good");
        questionlist2.add("Need Improvement");

        questionlist3.add("Select");
        questionlist3.add("Yes");
        questionlist3.add("No");

        questionlist4.add("Select");
        questionlist4.add("1");
        questionlist4.add("2");
        questionlist4.add("3");
        questionlist4.add("4");
        questionlist4.add("5");

        questionlist5.add("Select");
        questionlist5.add("Yes");
        questionlist5.add("No");


        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, questionlist1);
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, questionlist2);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<String> dataAdapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, questionlist3);
        dataAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<String> dataAdapter4 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, questionlist4);
        dataAdapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<String> dataAdapter5 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, questionlist5);
        dataAdapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner1.setAdapter(dataAdapter1);
        spinner2.setAdapter(dataAdapter2);
        spinner3.setAdapter(dataAdapter3);
        spinner4.setAdapter(dataAdapter4);
        spinner5.setAdapter(dataAdapter5);
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
        getMenuInflater().inflate(R.menu.feedback, menu);
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_survey:
                String res1=String.valueOf(spinner1.getSelectedItem());
                String res2 = String.valueOf(spinner2.getSelectedItem());
                String res3 = String.valueOf(spinner3.getSelectedItem());
                String res4 = String.valueOf(spinner4.getSelectedItem());
                String res5 = String.valueOf(spinner5.getSelectedItem());

                HashMap postData = new HashMap();
                postData.put("res1", res1);
                postData.put("res2", res2);
                postData.put("res3", res3);
                postData.put("res4", res4);
                postData.put("res5", res5);
                PostResponseAsyncTask task = new PostResponseAsyncTask(Feedback.this, postData);
                task.execute("http://ertsys.esy.es/development/app/services/FeedbackService.php");
                break;
        }
    }

    @Override
    public void processFinish(String s) {
        try {
            JSONObject jsonObject = new JSONObject(s);
            Object jsonresult = jsonObject.get("id");
            Object jsonmessage = jsonObject.get("message");
            String result = jsonresult.toString();
            String message = jsonmessage.toString();
            if(result.equalsIgnoreCase("1")) {
                Toast.makeText(Feedback.this, message, Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, Dashboard.class));
            }
            else if(result.equalsIgnoreCase("2"))
            {
                Toast.makeText(Feedback.this, message, Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
