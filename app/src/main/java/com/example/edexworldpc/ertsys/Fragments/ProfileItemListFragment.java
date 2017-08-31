package com.example.edexworldpc.ertsys.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.edexworldpc.ertsys.NoInternet;
import com.example.edexworldpc.ertsys.R;
import com.kosalgeek.asynctask.AsyncResponse;
import com.kosalgeek.asynctask.PostResponseAsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;
import java.util.HashMap;


public class ProfileItemListFragment extends Fragment  {

    private static WebView webView;
    private static ProgressBar progress;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile_item_list, container, false);

    }
    @Override
    public void onViewCreated(View gview, Bundle savedInstanceState) {
        // Setup any handles to view objects here
        // EditText etFoo = (EditText) view.findViewById(R.id.etFoo);
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("LoginPreference", Context.MODE_PRIVATE);
        String userappid = sharedPreferences.getString("appid", null);

        Toast.makeText(getContext(), userappid, Toast.LENGTH_SHORT).show();
        progress = (ProgressBar) gview.findViewById(R.id.progressBar1);
        progress.setMax(100);
        progress.setVisibility(View.VISIBLE);

        webView = (WebView) gview.findViewById(R.id.profile_item_list);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        String url = "http://ertsys.esy.es/development/app/services/GetProfileService.php";
        String postData = "userappid="+userappid;
        //webView.loadUrl("");
        webView.postUrl(url, postData.getBytes());

        webView.setWebViewClient(new com.example.edexworldpc.ertsys.MyAppWebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {
                progress.setVisibility(View.GONE);

                getView().findViewById(R.id.profile_item_list).setVisibility(View.VISIBLE);
            }
            public void onReceivedError(final WebView view, int errorCode, String description,
                                        final String failingUrl) {
                //control you layout, show something like a retry button, and
                //call view.loadUrl(failingUrl) to reload.
                webView.loadUrl("about:blank");
                Toast.makeText(getContext(), "Please connect to internet",Toast.LENGTH_LONG).show();
                startActivity(new Intent(getContext(), NoInternet.class));
                //super.onReceivedError(view, errorCode, description, failingUrl);
            }


        });



    }

}
