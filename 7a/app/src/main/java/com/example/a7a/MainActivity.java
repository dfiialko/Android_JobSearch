package com.example.a7a;

import android.annotation.TargetApi;
import android.app.ListActivity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;
import com.example.a7a.R;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import android.app.ActionBar;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.zip.Inflater;


import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;


public class MainActivity extends AppCompatActivity {

    private Toolbar toolBar;
    private ImageButton ctvNew;
    private ImageButton foxSport;
    private ArrayList<ListItem> list;
    private ListView listView;
    private boolean ctvNews;
    private boolean foxSports;
    private boolean finishedExecuting;
    private SharedPreferences sharedPref;
    public static final String NOTIFICATION_CHANNEL_ID = "465512";
    public static final int NOTIFICATION_CHANNEL = 465512;
    private NotificationManager mNotific;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setupScreen();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Refresh activity
        ctvNews = false;
        foxSports = false;
        finishedExecuting = true;
        list = new ArrayList<ListItem>();
        listView = MainActivity.this.findViewById(android.R.id.list);

        toolBar = (android.support.v7.widget.Toolbar) MainActivity.this.findViewById(R.id.my_toolbar);
        setSupportActionBar(toolBar);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            ListItem it = new ListItem();
            it = (ListItem)adapterView.getItemAtPosition(i);
            Log.d("listitem",it.title);
            Intent intent = new Intent(MainActivity.this,ListViewActivity.class);
            intent.putExtra("key", it);
            startActivity(intent);
        }
    });
    }
    public void setupScreen(){
        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        String appColor = sharedPref.getString("color","default");
        int appTheme = sharedPref.getInt("theme",0);
        int appTextSize = sharedPref.getInt("textSize",0);
        boolean notific = sharedPref.getBoolean("notificationEnabled",false);
        if(appColor == "default" || appTheme == 0){
            setTheme(android.R.style.Theme_Light);
        }
        else{
            setTheme(appTheme);
        }
        if(notific){
            sendNotification(findViewById(android.R.id.content));
        }else {
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.cancelAll();
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    public void sendNotification(View view) {
        Intent intent = new Intent(this,MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        mNotific=
                (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        CharSequence name="Notification";
        String desc="Testing notifications";
        int imp=NotificationManager.IMPORTANCE_HIGH;
        final String ChannelID="my_channel_01";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            NotificationChannel mChannel = new NotificationChannel(ChannelID, name,
                    imp);
            mChannel.setDescription(desc);
            mChannel.setLightColor(Color.CYAN);
            mChannel.canShowBadge();
            mChannel.setShowBadge(true);
            mNotific.createNotificationChannel(mChannel);
        }
        final int ncode=101;
        String Body="Notifications Enabled";
        Notification n= new Notification.Builder(this,ChannelID)
                .setContentTitle(getPackageName())
                .setContentText(Body)
                .setNumber(5)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .build();
        mNotific.notify(ncode, n);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);

        return true;
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            // Refresh Activity
            case R.id.action_refresh:
                recreate();
                return true;
            case R.id.action_settings:
                Intent settingsIntent = new Intent(MainActivity.this,Settings.class);
                startActivity(settingsIntent);
                return true;
                default:
                    return true;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        ProcessRSSTask processRSSTask = new ProcessRSSTask();
        processRSSTask.execute();
        ctvNew = findViewById(R.id.pinButton);
        ctvNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ctvNews = true;
                if(ctvNews && finishedExecuting){
                    ProcessRSSTask ctvNewsTask = new ProcessRSSTask();
                    ctvNewsTask.execute();
                }
            }
        });
        foxSport = findViewById(R.id.foxSports);
        foxSport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ctvNews = false;
                    ProcessRSSTask foxSportsTask = new ProcessRSSTask();
                    foxSportsTask.execute();}
        });
    }

    class ProcessRSSTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            finishedExecuting = false;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            URL url = null;
            try {
                if(ctvNews == true){
                    url = new URL("https://www.ctvnews.ca/rss/ctvnews-ca-top-stories-public-rss-1.822009");
                    Log.d("background","ctvnews");
                }
                else{
                    url = new URL("https://api.foxsports.com/v1/rss?partnerKey=zBaFxRyGKCfxBagJG9b8pqLyndmvo7UU&tag=nhl");
                    Log.d("background","foxSports");
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            SAXParser saxParser = null;
            try {
                saxParser = SAXParserFactory.newInstance().newSAXParser();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            }

            HttpURLConnection connection = null;
            try {
                connection = (HttpURLConnection)url.openConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }

            InputStream inputStream = null;
            try {
                inputStream = connection.getInputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }

            FreeHandler freeHandler = new FreeHandler();

            try {
                saxParser.parse(inputStream, freeHandler);
            } catch (SAXException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            finishedExecuting = true;
            ArrayList<String> items = new ArrayList<>();
            int textSizeLayout = sharedPref.getInt("layout",0);
            if(textSizeLayout == 0){
                final ArrayAdapter adapter = new ArrayAdapter(MainActivity.this,R.layout.activity_listitem_large,list);
                listView.setAdapter(adapter);
            }
            else{
                final ArrayAdapter adapter = new ArrayAdapter(MainActivity.this,textSizeLayout,list);
                Log.d("textsize","CHANGE SIZE");
                listView.setAdapter(adapter);
            }
        }
    }


    class FreeHandler extends DefaultHandler {

        private boolean inLink, inTitle, inPubDate, inDesc;
        private StringBuilder desc;
        private StringBuilder title;
        private String date;
        private StringBuilder link;
        private ListItem listItem;
        {
            list = new ArrayList<ListItem>(10);
            listItem = new ListItem();}

        @Override
        public void startDocument() throws SAXException {
            super.startDocument();}

        @Override
        public void endDocument() throws SAXException {
            super.endDocument();}

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            super.startElement(uri, localName, qName, attributes);
                desc = new StringBuilder();
                title = new StringBuilder();
                date = new String();
                link = new StringBuilder();
                switch(qName){
                    case "item":
                        listItem = new ListItem();
                        break;
                    case "title":
                        inTitle = true;
                        break;
                    case "guid":
                        inLink = true;
                        Log.d("inguid","START");
                        break;
                    case "url":
                        inLink = true;
                        break;
                    case "description":
                        inDesc = true;
                        break;
                    case "pubDate":
                        inPubDate = true;
                        break;

                }
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            super.endElement(uri, localName, qName);
            switch (qName) {
                    case "title":
                        inTitle = false;
                        listItem.title = title.toString();
                        break;
                    case "guid":
                        inLink = false;
                        listItem.link = link.toString();
                        break;
                    case "url":
                        inLink = false;
                        listItem.link = link.toString();
                        break;
                    case "description":
                        inDesc = false;
                        listItem.description = desc.toString();
                        break;
                    case "pubDate":
                        inPubDate = false;
                        listItem.pubDate = date;
                        break;
                    case "item":
                        list.add(listItem);
                        break;
                }
        }


        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            super.characters(ch, start, length);

            String t = new String(ch, start, length);
            if(inTitle) {
                title.append(t.replace("\t",""));
            }
            else if(inLink){
                link.append(t.replace("\n","").replace("\t",""));
            }
            else if(inDesc){
                desc.append(t.replace("mdash;","").replace("rsquo;","").replace("\n",""));
            }
            else if(inPubDate){
                date = t;
            }


        }
    }

}
