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
import android.widget.Button;
import android.widget.Toast;

import com.kosalgeek.asynctask.AsyncResponse;
import com.kosalgeek.asynctask.PostResponseAsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Backup extends AppCompatActivity implements AsyncResponse, View.OnClickListener {

    Button backup_yes, backup_no;
    SharedPreferences sprefsreport, sprefs, trackerprefs;
    String report, appid, ensn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backup);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /**
         * Call to stored values
         */
        backup_yes = (Button)findViewById(R.id.backup_yes);
        backup_no = (Button)findViewById(R.id.backup_no);

        sprefsreport = getSharedPreferences("ReportPreferences", Context.MODE_PRIVATE);
        report = sprefsreport.getString("report", null);
        sprefs = getSharedPreferences("LoginPreference", Context.MODE_PRIVATE);
        appid = sprefs.getString("appid", null);
        trackerprefs = getSharedPreferences("QuestionTracker", Context.MODE_PRIVATE);
        ensn = trackerprefs.getString("endsn", "5");

        /*Toast.makeText(this, appid, Toast.LENGTH_LONG).show();
        Toast.makeText(this, report, Toast.LENGTH_LONG).show();
*/
        backup_yes.setOnClickListener(this);
        backup_no.setOnClickListener(this);


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
  /*              Toast.makeText(Backup.this, message, Toast.LENGTH_SHORT).show();*/

                /**
                 * If Backup success, Destroy the stored value
                 */
                SharedPreferences.Editor editorreport = sprefsreport.edit();
                editorreport.clear();
                editorreport.apply();

                SharedPreferences.Editor editor = sprefs.edit();
                editor.clear();
                editor.apply();

                SharedPreferences.Editor editortrk = trackerprefs.edit();
                editortrk.clear();
                editortrk.apply();

                /**
                 * SharedPreference destruction end
                 */

                startActivity(new Intent(this, Login.class));
            }
            else
            {
                Toast.makeText(Backup.this, message, Toast.LENGTH_SHORT).show();

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.backup_no:
                startActivity(new Intent(Backup.this, Dashboard.class));
                break;
            case R.id.backup_yes:
                //Toast.makeText(this, appid, Toast.LENGTH_LONG).show();
                HashMap postData = new HashMap();
                postData.put("appid",appid);
                postData.put("loginpreference", appid);
                postData.put("reportpreferences", report);
                postData.put("questiontracker", ensn);
                PostResponseAsyncTask task = new PostResponseAsyncTask((AsyncResponse) this,postData);
                task.execute("http://ertsys.esy.es/development/app/services/BackupServices.php");
                break;

        }
    }
}
