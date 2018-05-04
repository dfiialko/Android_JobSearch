package ca.jodygillis.winter2018s1;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;


public class OtherActivity extends AppCompatActivity {
private EditText etAge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other);

        etAge = findViewById(R.id.etAge);
    }

    // onActivitResult pattern,return data to main activity
    @Override
    public void onBackPressed() {
        int age = Integer.parseInt(etAge.getText().toString());


        Intent intent = new Intent();
        intent.putExtra("age",age);

        setResult(Activity.RESULT_OK,intent);
        super.onBackPressed(); // cals finish
    }
}
