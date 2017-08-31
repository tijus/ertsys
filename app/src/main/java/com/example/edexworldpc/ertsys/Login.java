package com.example.edexworldpc.ertsys;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kosalgeek.asynctask.AsyncResponse;
import com.kosalgeek.asynctask.PostResponseAsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Login extends AppCompatActivity
        implements  View.OnClickListener, AsyncResponse {

    boolean doubleBackToExitPressedOnce=false;
    EditText edittext_email, edittext_password;
    Button btn_login;
    TextView linkRegister;
    SharedPreferences sharedpreferences, sprefsreport, trackerprefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        edittext_email = (EditText)findViewById(R.id.edittext_email);
        edittext_password = (EditText)findViewById(R.id.edittext_password);
        linkRegister = (TextView)findViewById(R.id.linkRegister);
        btn_login = (Button)findViewById(R.id.btn_login);

        btn_login.setOnClickListener(this);
        linkRegister.setOnClickListener(this);

        sharedpreferences = getSharedPreferences("LoginPreference", Context.MODE_PRIVATE);
        sprefsreport = getSharedPreferences("ReportPreferences", Context.MODE_PRIVATE);
        trackerprefs = getSharedPreferences("QuestionTracker", Context.MODE_PRIVATE);
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

    @SuppressWarnings("StatementWithEmptyBody")

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.linkRegister:
                startActivity(new Intent(Login.this, Register.class ));
                break;
            case R.id.btn_login:
                if(edittext_email.getText().toString().equals("") || !(edittext_email.getText().toString().contains("@")))
                {
                    Toast.makeText(Login.this, "Please Fill a valid email id", Toast.LENGTH_SHORT).show();
                }
                else if(edittext_password.getText().toString().equals(""))
                {
                    Toast.makeText(Login.this, "Please Fill your correct password", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    HashMap postData = new HashMap();
                    postData.put("email", edittext_email.getText().toString());
                    postData.put("password", edittext_password.getText().toString());
                    PostResponseAsyncTask task = new PostResponseAsyncTask(Login.this, postData);
                    task.execute("http://ertsys.esy.es/development/app/services/LoginService.php");
                }
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

            if(result.equalsIgnoreCase("0") && message.equalsIgnoreCase("False")) {
                Toast.makeText(Login.this, "No user found", Toast.LENGTH_SHORT).show();
            }
            else if(result.equalsIgnoreCase("1") && message.equalsIgnoreCase("True"))
            {
                Object  appid = jsonObject.get("appid");
                String jsonappid = String.valueOf(appid);
                Object report = jsonObject.get("reportpreferences");
                String jsonreport = String.valueOf(report);
                Object tracker = jsonObject.get("quesetiontracker");
                String jsontrkr = String.valueOf(tracker);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("email",edittext_email.getText().toString() );
                editor.putString("appid",jsonappid );
                editor.commit();

                SharedPreferences.Editor editreport = sprefsreport.edit();
                editreport.putString("report",jsonreport);
                editreport.commit();

                SharedPreferences.Editor edittracker = trackerprefs.edit();
                edittracker.putString("endsn", jsontrkr);
                edittracker.commit();

                String retappid = sharedpreferences.getString("appid","");
                Toast.makeText(Login.this, retappid, Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, AcademicSurvey.class));
            }
            else if(result.equalsIgnoreCase("2") && message.equalsIgnoreCase("Redirection"))
            {
                Object  appid = jsonObject.get("appid");
                String jsonappid = String.valueOf(appid);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("email",edittext_email.getText().toString() );
                editor.putString("appid",jsonappid );
                editor.commit();
                String retappid = sharedpreferences.getString("appid","");
                Toast.makeText(Login.this, retappid, Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, Dashboard.class));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
