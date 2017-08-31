package com.example.edexworldpc.ertsys;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kosalgeek.asynctask.AsyncResponse;
import com.kosalgeek.asynctask.PostResponseAsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class ValidateUser extends AppCompatActivity implements View.OnClickListener, AsyncResponse {

    EditText editText_OTP;
    Button btn_validate;
    String phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validate_user);

        editText_OTP = (EditText)findViewById(R.id.edittext_OTP);
        btn_validate = (Button)findViewById(R.id.btn_validate);
        Bundle bdl = getIntent().getExtras();
        phone = bdl.getString("contact");
        btn_validate.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        HashMap postData = new HashMap();
        postData.put("OTP", editText_OTP.getText().toString());
        postData.put("Contact", phone);
        PostResponseAsyncTask task = new PostResponseAsyncTask(ValidateUser.this, postData);
        task.execute("http://ertsys.esy.es/development/app/services/ValidateUserService.php");

    }

    @Override
    public void processFinish(String s) {
        try {
            JSONObject jsonObject = new JSONObject(s);
            Object jsonresult = jsonObject.get("id");
            Object jsonmessage = jsonObject.get("message");
            String resultid = String.valueOf(jsonresult);
            String resultmessage = String.valueOf(jsonmessage);

            if(resultid.equalsIgnoreCase("0")) {
                Toast.makeText(ValidateUser.this, resultmessage, Toast.LENGTH_SHORT).show();
            }
            else if(resultid.equalsIgnoreCase("1") && resultmessage.equalsIgnoreCase("True"))
            {
                Toast.makeText(ValidateUser.this, "Please Login to continue..", Toast.LENGTH_LONG).show();
                startActivity(new Intent(ValidateUser.this, Login.class));
            }
            else if(resultid.equalsIgnoreCase("2"))
            {
                Toast.makeText(ValidateUser.this, resultmessage, Toast.LENGTH_SHORT).show();

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}
