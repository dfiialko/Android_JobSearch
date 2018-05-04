package ca.jodygillis.winter2018s1;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    //instance variables for UI widgets
    private CheckBox cbHappy;
    private EditText etName;
    private TextView tvHello;
    private SharedPreferences sharedPreferences;

    //runs once -- when Activity is created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //inflates and displays the layout file
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("general",0);
        //object of nested editor class
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("name","Bob");
        editor.putInt("age",33);
        editor.putBoolean("happy",true);
        editor.commit();
        //get references to UI widgets...
        cbHappy = (CheckBox)findViewById(R.id.cbHappy);
        tvHello = (TextView)findViewById(R.id.tvHello);
        //WHY DON'T I HAVE TO CAST?????????????????????????????????????
        etName = findViewById(R.id.etName);

        cbHappy.setChecked(true);
        tvHello.setText("HEllo hello test 123");

    }

    //from onClick attribute of the button
    public void startOtherActivity(View view) {
        //create and Intent to start the other activity
        Intent intent = new Intent(this, OtherActivity.class );

        startActivityForResult(intent,0);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        int age = data.getIntExtra("age",0);
        Log.d("Denys","age = " + age);
    }

    // Nested class
    class ProcesRSSTask extends AsyncTask<Void,Void,Void>{
        @Override
        protected void onPreExecute{
            super onPreExecute();
        }
    }


}

