package com.example.a7a;

/**
 * Created by User on 2/7/2018.
 */

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class ListViewActivity extends AppCompatActivity{
    private Button btnLink;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        String appColor = sharedPref.getString("color","default");

        int appTheme = sharedPref.getInt("theme",0);
        boolean saveLinks = sharedPref.getBoolean("saveLinks",false);
        Log.d("OnCreateTheme",Integer.toString(Constant.theme));
        if(appColor == "default" || appTheme == 0){
            setTheme(android.R.style.Theme_Light);
        }
        else{
            setTheme(appTheme);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);
        Button btnSave = findViewById(R.id.btnSave);
        if(saveLinks){
            btnSave.setVisibility(View.VISIBLE);
        }else{
            btnSave.setVisibility(View.GONE);
        }
        btnLink = findViewById(R.id.btnLink);
        final ListItem news = (ListItem)getIntent().getSerializableExtra("key");
        TextView titleTextView =
                (TextView) findViewById(R.id.title);

        TextView subtitleTextView =
                (TextView) findViewById(R.id.des);

        TextView detailTextView =
                (TextView)findViewById(R.id.date);
        titleTextView.setText(news.title);
        subtitleTextView.setText(news.description);
        detailTextView.setText(news.pubDate);

        btnLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("newslink",news.link);
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(news.link));
                startActivity(intent);
            }
        });
    }



}