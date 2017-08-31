package com.example.edexworldpc.ertsys;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.kosalgeek.asynctask.AsyncResponse;
import com.kosalgeek.asynctask.PostResponseAsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class Dashboard extends AppCompatActivity
        implements View.OnClickListener, AsyncResponse {

    boolean doubleBackToExitPressedOnce=false;
    String report, appid;
    ImageButton Home, AboutUs, Profile, Notification, MailReport, SendFeedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();*/

        /*NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);*/
        SharedPreferences trackerprefs = getSharedPreferences("QuestionTracker", Context.MODE_PRIVATE);
        String ensn = trackerprefs.getString("endsn", "5");
        int endvalue = Integer.valueOf(ensn);
        Home = (ImageButton) findViewById(R.id.Home);
        AboutUs = (ImageButton) findViewById(R.id.AboutUs);
        Profile = (ImageButton) findViewById(R.id.Profile);
        Notification = (ImageButton) findViewById(R.id.Notification);
        MailReport = (ImageButton) findViewById(R.id.MailReport);
        SendFeedback = (ImageButton) findViewById(R.id.SendFeedback);
        Home.setOnClickListener(this);
        AboutUs.setOnClickListener(this);
        Profile.setOnClickListener(this);
        Notification.setOnClickListener(this);
        MailReport.setOnClickListener(this);
        SendFeedback.setOnClickListener(this);

        SharedPreferences sprefsreport = getSharedPreferences("ReportPreferences", Context.MODE_PRIVATE);
        report = sprefsreport.getString("report", null);
        SharedPreferences sprefs = getSharedPreferences("LoginPreference", Context.MODE_PRIVATE);
        appid = sprefs.getString("appid", null);
    }
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
            @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dashboard, menu);
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
            startActivity(new Intent(this, Backup.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case  R.id.Home:
                break;
            case R.id.AboutUs:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.Profile:
                startActivity(new Intent(this, Profile.class));
                break;
            case R.id.Notification:
                startActivity(new Intent(this, ActivityArea.class));
                break;
            case R.id.SendFeedback:
                startActivity(new Intent(this, Feedback.class));
                break;
            case R.id.MailReport:
                HashMap postData = new HashMap();
                postData.put("appid", appid);
                postData.put("report", report);
                PostResponseAsyncTask task = new PostResponseAsyncTask((AsyncResponse) this,postData);
                task.execute("http://ertsys.esy.es/development/app/services/SendEmailReportService.php");
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
                Toast.makeText(Dashboard.this, message, Toast.LENGTH_SHORT).show();
            }
            else if(result.equalsIgnoreCase("2"))
            {
                Toast.makeText(Dashboard.this, message, Toast.LENGTH_SHORT).show();

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
