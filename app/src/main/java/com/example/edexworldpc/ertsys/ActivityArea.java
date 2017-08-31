package com.example.edexworldpc.ertsys;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
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
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.kosalgeek.asynctask.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static android.R.attr.name;
import static android.R.attr.start;

public class ActivityArea extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AsyncResponse, View.OnClickListener {

    public List<String> questionlist = new ArrayList<String>();
    public Spinner spinner1, spinner2, spinner3, spinner4, spinner5;
    public TextView questtext1, questtext2, questtext3, questtext4, questtext5;
    SharedPreferences spreferences;
    public Integer startsn;
    public Integer endsn;
    Button btn_survey;
    public String starsn, ensn;
    public List<String > paramList = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        btn_survey = (Button)findViewById(R.id.btn_survey);

        btn_survey.setOnClickListener(this);
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

        questionlist.add("Select");
        questionlist.add("Strongly Disagree");
        questionlist.add("Disagree");
        questionlist.add("Neutral");
        questionlist.add("Agree");
        questionlist.add("Strongly Agree");

        spreferences = getSharedPreferences("QuestionTracker", Context.MODE_PRIVATE);

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, questionlist);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner1.setAdapter(dataAdapter);
        spinner2.setAdapter(dataAdapter);
        spinner3.setAdapter(dataAdapter);
        spinner4.setAdapter(dataAdapter);
        spinner5.setAdapter(dataAdapter);
        HashMap postData = new HashMap();
        starsn = spreferences.getString("startsn", "1");
        ensn = spreferences.getString("endsn","5");
        postData.put("startsn", starsn);
        postData.put("endsn", ensn);
        int endvalue = Integer.valueOf(ensn);
        if(endvalue<=60)
        {
            postData.put("level","First Level");
        }
        else
        {
            postData.put("level","Second Level");
        }
        PostResponseAsyncTask task = new PostResponseAsyncTask(ActivityArea.this, postData);
        task.execute("http://ertsys.esy.es/development/app/services/SelectQuestionnaire.php");
    }

   @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            return;

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_area, menu);
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
    public void processFinish(String s) {
        try {
            JSONObject jsonObject = new JSONObject(s);
            Object jsonresult = jsonObject.get("id");
            JSONArray jsonmessage = jsonObject.getJSONArray("message");
            JSONArray jsonsn = jsonObject.getJSONArray("srno");
            JSONArray jsonparam = jsonObject.getJSONArray("subparam");
            String result = String.valueOf(jsonresult);
            if(result.equalsIgnoreCase("0")) {
                Toast.makeText(this, "No user found", Toast.LENGTH_SHORT).show();
            }
            else if(result.equalsIgnoreCase("1"))
            {
                    Object message = jsonmessage.get(0);
                    Object sno = jsonsn.get(0);
                    Object param = jsonparam.get(0);
                    String perparam = String.valueOf(param);
                    paramList.add(perparam);
                    String question1 = String.valueOf(message);
                    startsn = Integer.valueOf(String.valueOf(sno));
                    startsn = startsn+5;
                    starsn = String.valueOf(startsn);
                    questtext1.setText(question1);

                    Object message2 = jsonmessage.get(1);
                    String question2 = String.valueOf(message2);
                    questtext2.setText(question2);
                    Object param2 = jsonparam.get(1);
                    String perparam2 = String.valueOf(param2);
                    paramList.add(perparam2);

                    Object message3 = jsonmessage.get(2);
                    String question3 = String.valueOf(message3);
                    questtext3.setText(question3);
                    Object param3 = jsonparam.get(2);
                    String perparam3 = String.valueOf(param3);
                    paramList.add(perparam3);

                    Object message4 = jsonmessage.get(3);
                    String question4 = String.valueOf(message4);
                    questtext4.setText(question4);
                    Object param4 = jsonparam.get(3);
                    String perparam4 = String.valueOf(param4);
                    paramList.add(perparam4);

                    Object message5 = jsonmessage.get(4);
                    Object sno5 = jsonsn.get(4);
                    String question5 = String.valueOf(message5);
                    endsn = Integer.valueOf(String.valueOf(sno5));
                    endsn = endsn + 5;
                    ensn = String.valueOf(endsn);
                    questtext5.setText(question5);
                    Object param5 = jsonparam.get(4);
                    String perparam5 = String.valueOf(param5);
                    paramList.add(perparam5);

                    SharedPreferences.Editor editor = spreferences.edit();
                    editor.putString("startsn",starsn);
                    editor.putString("endsn",ensn);
                    editor.commit();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int ConvertStringtoValue(Spinner spinner)
    {
        if(String.valueOf(spinner.getSelectedItem()).equalsIgnoreCase("Strongly Disagree"))
        {
            return -2;
        }
        else if(String.valueOf(spinner.getSelectedItem()).equalsIgnoreCase("Disagree"))
        {
            return -1;
        }
        else if(String.valueOf(spinner.getSelectedItem()).equalsIgnoreCase("Neutral"))
        {
            return 0;
        }
        else if(String.valueOf(spinner.getSelectedItem()).equalsIgnoreCase("Agree"))
        {
            return 1;
        }
        else if(String.valueOf(spinner.getSelectedItem()).equalsIgnoreCase("Strongly Agree"))
        {
            return 2;
        }
        return 5;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_survey:
                spreferences = getSharedPreferences("QuestionTracker", Context.MODE_PRIVATE);
                SharedPreferences sprefs = getSharedPreferences("ScorePreference", Context.MODE_PRIVATE);
                SharedPreferences quesque = getSharedPreferences("QuestinPreference", Context.MODE_PRIVATE);
                ensn = spreferences.getString("endsn", "5");
                int endvalue = Integer.valueOf(ensn);
                if (endvalue > 60) {
                            int [] scoreprefresult = new int[10];
                            scoreprefresult[0] = sprefs.getInt("Introversion",0);
                            scoreprefresult[1] = sprefs.getInt("Extroversion",0);
                            scoreprefresult[2] = sprefs.getInt("Observant",0);
                            scoreprefresult[3] = sprefs.getInt("Intuition",0);
                            scoreprefresult[4] = sprefs.getInt("Feeling",0);
                            scoreprefresult[5] = sprefs.getInt("Thinking",0);
                            scoreprefresult[6] = sprefs.getInt("Perception",0);
                            scoreprefresult[7] = sprefs.getInt("Judging",0);
                            scoreprefresult[8] = sprefs.getInt("Assertive",0);
                            scoreprefresult[9] = sprefs.getInt("Turbulent",0);
                            String sprefresult = "Introversion: "+String.valueOf(sprefs.getInt("Introversion",0))+"\n"+"Extroversion: "+String.valueOf(sprefs.getInt("Extroversion",0))+"\n"+"Observant: "+String.valueOf(sprefs.getInt("Observant",0))+"\n"+"Intuition: "+String.valueOf(sprefs.getInt("Intuition",0))+"\n"+"Feeling: "+String.valueOf(sprefs.getInt("Feeling",0))+"\n"+"Thinking: "+String.valueOf(sprefs.getInt("Thinking",0))+"\n"+"Perception: "+String.valueOf(sprefs.getInt("Perception",0))+"\n"+"Judging: "+String.valueOf(sprefs.getInt("Judging",0))+"\n"+"Assertive: "+String.valueOf(sprefs.getInt("Assertive",0))+"\n"+"Turbulent: "+String.valueOf(sprefs.getInt("Turbulent",0))+"\n";
                            Toast.makeText(this, sprefresult,Toast.LENGTH_LONG).show();

                            float[] clusteredResult = new float[10];
                            CureClass getclstr = new CureClass();
                            clusteredResult = getclstr.getClusteredResult(scoreprefresult,scoreprefresult);
                            //String clusterresult = "Introversion: "+String.valueOf(clusteredResult[0])+"%\n"+"Extroversion: "+String.valueOf(clusteredResult[1])+"%\n"+"Observant: "+String.valueOf(clusteredResult[2])+"%\n"+"Intuition: "+String.valueOf(clusteredResult[3])+"%\n"+"Feeling: "+String.valueOf(clusteredResult[4])+"%\n"+"Thinking: "+String.valueOf(clusteredResult[5])+"%\n"+"Perception: "+String.valueOf(clusteredResult[6])+"%\n"+"Judging: "+String.valueOf(clusteredResult[7])+"%\n"+"Assertive: "+String.valueOf(clusteredResult[8])+"%\n"+"Turbulent: "+String.valueOf(clusteredResult[9])+"%\n";
                            String personalityTrait = getclstr.getPersonalityTrait(clusteredResult);

                           // Toast.makeText(this, clusterresult,Toast.LENGTH_LONG).show();
                            Toast.makeText(this,personalityTrait,Toast.LENGTH_LONG).show();
                        Intent i = new Intent(this, Report.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("report", personalityTrait);
                        i.putExtras(bundle);
                        startActivity(i);

                } else {

                    int spin1 = ConvertStringtoValue(spinner1);
                    int spin2 = ConvertStringtoValue(spinner2);
                    int spin3 = ConvertStringtoValue(spinner3);
                    int spin4 = ConvertStringtoValue(spinner4);
                    int spin5 = ConvertStringtoValue(spinner5);

                    if(spin1==5 || spin2==5 || spin3==5 || spin4==5 || spin5==5)
                    {
                        Toast.makeText(this, "Please select your appropriate choice.",Toast.LENGTH_LONG).show();
                        startActivity(new Intent(this, ActivityArea.class));
                    }
                    else {
                        List<Integer> set = new ArrayList<Integer>();
                        set.add(spin1);
                        set.add(spin2);
                        set.add(spin3);
                        set.add(spin4);
                        set.add(spin5);

                        List<Integer> question = new ArrayList<Integer>();
                        question.add(quesque.getInt("Introversion",0));
                        question.add(quesque.getInt("Extroversion",0));
                        question.add(quesque.getInt("Observant",0));
                        question.add(quesque.getInt("Intuition",0));
                        question.add(quesque.getInt("Feeling",0));
                        question.add(quesque.getInt("Thinking",0));
                        question.add(quesque.getInt("Perception",0));
                        question.add(quesque.getInt("Judging",0));
                        question.add(quesque.getInt("Assertive",0));
                        question.add(quesque.getInt("Turbulent",0));

                        List<Integer> prevscore = new ArrayList<Integer>();
                        prevscore.add(sprefs.getInt("Introversion", 0));
                        prevscore.add(sprefs.getInt("Extroversion", 0));
                        prevscore.add(sprefs.getInt("Observant", 0));
                        prevscore.add(sprefs.getInt("Intuition", 0));
                        prevscore.add(sprefs.getInt("Feeling", 0));
                        prevscore.add(sprefs.getInt("Thinking", 0));
                        prevscore.add(sprefs.getInt("Perception", 0));
                        prevscore.add(sprefs.getInt("Judging", 0));
                        prevscore.add(sprefs.getInt("Assertive", 0));
                        prevscore.add(sprefs.getInt("Turbulent", 0));

                        CureClass obj = new CureClass(set, paramList, prevscore, question);
                        try {
                            int[] scoreresult = new int[10];
                            int[] quesresult = new int[10];
                            obj.getResult();
                            scoreresult = obj.getScoreResult();
                            quesresult = obj.getQue();

                            SharedPreferences.Editor editque = quesque.edit();
                            editque.putInt("Introversion", quesresult[0]);
                            editque.putInt("Extroversion", quesresult[1]);
                            editque.putInt("Observant", quesresult[2]);
                            editque.putInt("Intuition", quesresult[3]);
                            editque.putInt("Feeling", quesresult[4]);
                            editque.putInt("Thinking", quesresult[5]);
                            editque.putInt("Perception", quesresult[6]);
                            editque.putInt("Judging", quesresult[7]);
                            editque.putInt("Assertive", quesresult[8]);
                            editque.putInt("Turbulent", quesresult[9]);
                            editque.commit();

                            SharedPreferences.Editor edit = sprefs.edit();
                            edit.putInt("Introversion",scoreresult[0]);
                            edit.putInt("Extroversion",scoreresult[1]);
                            edit.putInt("Observant",scoreresult[2]);
                            edit.putInt("Intuition",scoreresult[3]);
                            edit.putInt("Feeling",scoreresult[4]);
                            edit.putInt("Thinking",scoreresult[5]);
                            edit.putInt("Perception",scoreresult[6]);
                            edit.putInt("Judging",scoreresult[7]);
                            edit.putInt("Assertive",scoreresult[8]);
                            edit.putInt("Turbulent",scoreresult[9]);
                            edit.commit();
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }

                    }
                    startActivity(new Intent(this, ActivityArea.class));
                }
                break;


        }
    }



}
