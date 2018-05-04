package com.example.jobsearch;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.jobsearch.Adapter.FeedAdapter;
import com.example.jobsearch.HTTP.HTTPData;
import com.example.jobsearch.RSS.RssObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Modifier;
import java.util.ArrayList;

public class SearchParameters extends AppCompatActivity {
    private EditText txtTitle;
    private EditText txtCity;

    private Button btnSearch, btnMyJobs;

    private String userId;
    private static String craigslist = "https%3A%2F%2Fwinnipeg.craigslist.ca%2Fsearch%2Fjjj%3Fformat%3Drss%26query%3DDeveloper";
    private static String monsterRSS = "http%3A%2F%2Frss.jobsearch.monster.com%2Frssquery.ashx%3Fq%3D";
    private static String converter = "https://api.rss2json.com/v1/api.json?rss_url=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_parameters);
        txtCity = findViewById(R.id.txtCity);
        txtTitle = findViewById(R.id.txtTiel);
        btnSearch = findViewById(R.id.btnSearch);
        btnMyJobs = findViewById(R.id.btnMyJobs);



        StringBuilder sb = new StringBuilder();
        SharedPreferences sharedPref = this.getSharedPreferences("saved", MODE_PRIVATE);
        userId = sharedPref.getString("userID", null);
        btnMyJobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchParameters.this, SavedJobs.class);
                if (userId != null) {
                    startActivity(intent);
                } else {
                    Toast.makeText(SearchParameters.this, "User is not Logged in", Toast.LENGTH_LONG);
                }

            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtCity.getText().toString().isEmpty()) {
                    txtCity.setText("Cannot be empty");
                }
                if (txtTitle.getText().toString().isEmpty()) {
                    txtTitle.setText("Cannot be empty");
                } else {
                    loadRSS();
                }
            }
        });
        txtCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtCity.setText("");
            }
        });
        txtTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtTitle.setText("");
            }
        });
    }


    private void loadRSS() {
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.rbGroup);
        int radioButtonID = radioGroup.getCheckedRadioButtonId();

        AsyncTask<String, String, String> loadAsyncRSS = new AsyncTask<String, String, String>() {
            @Override
            protected String doInBackground(String... strings) {
                String result;
                HTTPData handler = new HTTPData();
                result = handler.GetHTTPData(strings[0]);

                return result;
            }

            @Override
            protected void onPostExecute(String s) {
                Intent intent = new Intent(SearchParameters.this, Feed.class);
                intent.putExtra("rssobject", s);
                startActivity(intent);
            }
        };

        StringBuilder url_get_data = new StringBuilder(converter);

        if (radioButtonID == 2131296310) {
            url_get_data.append(monsterRSS);
            StringBuilder workReq = new StringBuilder(txtTitle.getText().toString().replace(" ", "%2520"));
            workReq.append("%2520");
            workReq.append(txtCity.getText().toString().replace(" ", "%2520"));
            workReq.append(".xml");
            url_get_data.append(workReq.toString());
            loadAsyncRSS.execute(url_get_data.toString());
        }
        if (radioButtonID == 2131296309) {
            url_get_data.append("https%3A%2F%2F");
            url_get_data.append(txtCity.getText().toString().replace(" ", ""));
            url_get_data.append(".craigslist.ca%2Fsearch%2Fjjj%3Fformat%3Drss%26query%3D");
            url_get_data.append(txtTitle.getText().toString().replace(" ", "%20"));
            loadAsyncRSS.execute(url_get_data.toString());
        }
    }
}