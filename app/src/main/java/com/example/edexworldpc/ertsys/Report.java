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
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.kosalgeek.asynctask.AsyncResponse;
import com.kosalgeek.asynctask.PostResponseAsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import static com.example.edexworldpc.ertsys.R.id.edittext_password;

public class Report extends AppCompatActivity implements AsyncResponse, View.OnClickListener {
     WebView webView;
    ProgressBar progress;
    String userappid,report;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);

        progress = (ProgressBar)findViewById(R.id.progressBar1);
        progress.setMax(100);
        progress.setVisibility(View.VISIBLE);

        SharedPreferences sharedPreferences = getSharedPreferences("LoginPreference", Context.MODE_PRIVATE);
        userappid = sharedPreferences.getString("appid", null);

        Bundle bdl = getIntent().getExtras();
        report = bdl.getString("report");
        SharedPreferences sprefreport = getSharedPreferences("ReportPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editreport = sprefreport.edit();
        editreport.putString("report",report);
        editreport.commit();
        webView=(WebView)findViewById(R.id.report);
        final WebSettings webSettings=webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
//        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        String url = "http://ertsys.esy.es/development/app/services/GetRecommendations.php?report="+report+"&appid="+userappid;
        webView.loadUrl(url);
        //webView.loadUrl("https://docs.google.com/gview?embedded=true&url="+url);
        webView.setWebViewClient(new MyAppWebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                progress.setVisibility(View.GONE);

                findViewById(R.id.report).setVisibility(View.VISIBLE);
            }
            public void onReceivedError(final WebView view, int errorCode, String description,
                                        final String failingUrl) {
                Toast.makeText(Report.this, "Please connect to internet",Toast.LENGTH_LONG).show();
                webView.loadUrl("about:blank");
                startActivity(new Intent(Report.this, NoInternet.class));
                //super.onReceivedError(view, errorCode, description, failingUrl);
            }
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url)
            {
                if (url.endsWith(".pdf")) {
                    //                  webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
                    webView.loadUrl(url);
                    return true;
                }
                return false;
            }

        });
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
                Toast.makeText(Report.this, message, Toast.LENGTH_SHORT).show();
            }
            else if(result.equalsIgnoreCase("2"))
            {
                Toast.makeText(Report.this, message, Toast.LENGTH_SHORT).show();

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onBackPressed() {
            startActivity(new Intent(this, Dashboard.class));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.fab:
                HashMap postData = new HashMap();
                postData.put("appid", userappid);
                postData.put("report", report);
                PostResponseAsyncTask task = new PostResponseAsyncTask((AsyncResponse) this,postData);
                task.execute("http://ertsys.esy.es/development/app/services/SendEmailReportService.php");
                break;
        }
    }
}
