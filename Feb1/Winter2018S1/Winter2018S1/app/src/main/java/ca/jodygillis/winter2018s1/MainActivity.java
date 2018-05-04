package ca.jodygillis.winter2018s1;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;


public class MainActivity extends AppCompatActivity {

    //instance variables for UI widgets
    private CheckBox cbHappy;
    private EditText etName;
    private TextView tvHello;
    private Button btnOtherActivity, btnTest;

    private SharedPreferences sharedPreferences;

    //runs once -- when Activity is created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("Jody", "onCreate");
        super.onCreate(savedInstanceState);
        //inflates and displays the layout file
        setContentView(R.layout.activity_main);


        //shared prefs example *****************
        sharedPreferences = getSharedPreferences("general", 0);


        //*********************

        //get references to UI widgets...
        cbHappy = (CheckBox)findViewById(R.id.cbHappy);
        tvHello = (TextView)findViewById(R.id.tvHello);
        //WHY DON'T I HAVE TO CAST?????????????????????????????????????
        //because: https://stackoverflow.com/questions/44902651/no-need-to-cast-the-result-of-findviewbyid
        etName = findViewById(R.id.etName);
        btnOtherActivity = findViewById(R.id.btnOtherActivity);
        btnTest = findViewById(R.id.btnTest);

        //int i = 3333/0;

        //cbHappy.setChecked(true);
        //tvHello.setText("HEllo hello test 123");

        btnOtherActivity.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                return false;
            }
        });






        //can set up anonymous event listeners in onCreate()
/*        btnOtherActivity.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Log.d("Jody", "long click!");
                Toast.makeText(MainActivity.this, "long click!", Toast.LENGTH_LONG).show();
                return false;
            }
        });*/

/*        cbHappy.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Toast.makeText(MainActivity.this, "checked? " + b, Toast.LENGTH_LONG).show();
            }
        });*/

    //using object of nested event handling class...
    EventHandler eventHandler = new EventHandler();
    btnOtherActivity.setOnLongClickListener(eventHandler);
    cbHappy.setOnCheckedChangeListener(eventHandler);
    cbHappy.setOnLongClickListener(eventHandler);
    btnTest.setOnClickListener(eventHandler);

    } //onCreate

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("Jody", "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Jody", "onResume");
        //loading...
        String name = sharedPreferences.getString("name", "NO NAME");
        boolean happy = sharedPreferences.getBoolean("happy", false);

        etName.setText(name);
        cbHappy.setChecked(happy);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("Jody", "onPause");
        //get data from UI
        String name = etName.getText().toString();
        boolean happy = cbHappy.isChecked();

        //use object of the nested Editor class to edit prefs
        SharedPreferences.Editor editor = sharedPreferences.edit();
        //save some data
        editor.putString("name", name);
        editor.putBoolean("happy", happy);
        boolean success = editor.commit(); //or apply()
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("Jody", "onStop");
    }

    //from onClick attribute of the button
    //must have parameter of type View
    public void startOtherActivity(View view) {
        if(view.getId() == R.id.btnOtherActivity) {
            //create and Intent to start the other activity
            Intent intent = new Intent(this, OtherActivity.class);
            //startActivity(intent);

            //step 1 of startActivityForResult pattern
            startActivityForResult(intent, 0);
        }

    }

    //last step of startActivityForResult pattern

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK) {
            int age = data.getIntExtra("age", -1);
            Log.d("Jody", "RESULT_OK; age = " + age);
        } else {
            //assuming RESULT_CANCELLED
            Log.d("Jody", "RESULT_CANCELLED");
        }


    }

    //nested class for event handling (multiple types of events)
    class EventHandler implements View.OnClickListener, View.OnLongClickListener, CompoundButton.OnCheckedChangeListener {
        @Override
        public void onClick(View view) {
            if(view.getId() == R.id.btnTest) {
                //AsyncTask test
                //for(int i = -999999999; i < 999999999; i++);
                /*try {
                    Thread.sleep(5000); //5 second sleep
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/
                ProcessRSSTask processRSSTask = new ProcessRSSTask();
                processRSSTask.execute();

            }
        }

        @Override
        public boolean onLongClick(View view) {
            switch (view.getId()) {
                case R.id.btnOtherActivity:
                    Log.d("Jody", "long click!");
                    Toast.makeText(MainActivity.this, "long click!", Toast.LENGTH_LONG).show();
                    return true;

                case R.id.cbHappy:
                    //do something else...
                    return true;
                default:
                    return false;
            }

        }

        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            Toast.makeText(MainActivity.this, "checked? " + b, Toast.LENGTH_LONG).show();


        }
    }


    //nested class (AsyncTask) to process RSS feeds
    class ProcessRSSTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d("Jody", "onPreExecute");
        }


        @Override
        protected Void doInBackground(Void... voids) {
            Log.d("Jody", "doInBackground");
            //for(int i = -999999999; i < 999999999; i++);
            /*try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/

            //use SAXParser to parse Winnipeg Free Press RSS feed
            URL url = null;
            try {
                url = new URL("https://www.winnipegfreepress.com/rss/?path=%2Flocal");
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

            //create instance of our FreepHandler
            FreepHandler freepHandler = new FreepHandler();

            try {
                saxParser.parse(inputStream, freepHandler);
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
            Log.d("Jody", "onPostExecute");
        }


    }

    //Subclass of DefaultHandler to parse Wpg Free Press
    //RSS feeds
    class FreepHandler extends DefaultHandler {
        // flags to keep track of what elements are in
        private boolean inTitle;
        private ArrayList<String> title;

        private StringBuilder stringBuilder;

        //initialization block
        {
            title = new ArrayList<String>(10);
        }
        @Override
        public void startDocument() throws SAXException {
            super.startDocument();
            Log.d("Jody", "startDoc");
        }

        @Override
        public void endDocument() throws SAXException {
            super.endDocument();
            Log.d("Jody", "endDoc");

            for(String s:title){
                Log.d("Jody",s);
            }
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            super.startElement(uri, localName, qName, attributes);
            Log.d("Jody", "startElement: " + qName);
            if(qName == "title"){
                inTitle = true;
                stringBuilder = new StringBuilder(30);
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            super.endElement(uri, localName, qName);
            Log.d("Jody", "endElement: " + qName);

            if(qName == "title"){
                inTitle = false;
                title.add(stringBuilder.toString());
            }
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            super.characters(ch, start, length);

            String s = new String(ch,start,length);
            Log.d("Jody","characters"+s);

            if(inTitle){
                stringBuilder.append(ch,start,length);
            }
        }
    }

} //MainActivity
