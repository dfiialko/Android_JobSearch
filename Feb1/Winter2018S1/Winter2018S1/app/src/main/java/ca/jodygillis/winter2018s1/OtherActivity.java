package ca.jodygillis.winter2018s1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

public class OtherActivity extends AppCompatActivity {

    private EditText etAge;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other);

        etAge = findViewById(R.id.etAge);
    }

    //onActivityResult pattern; returning data
    //to MainActivity
    @Override
    public void onBackPressed() {

        int age = Integer.parseInt(etAge.getText().toString());

        //create and Intent as a container for the data to pass
        Intent dataIntent = new Intent();
        dataIntent.putExtra("age", age);

        //set the result -- resultCode can be RESULT_CANCELLED
        //or RESULT_OK
        if(age >= 0) {
            setResult(RESULT_OK, dataIntent);
        } else {
            setResult(RESULT_CANCELED);
        }

        finish();
        //super.onBackPressed(); //calls finish()
    }
}
