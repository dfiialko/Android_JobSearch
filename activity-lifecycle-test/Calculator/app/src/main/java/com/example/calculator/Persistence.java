package com.example.calculator;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

public class Persistence extends AppCompatActivity {

    private Button btnSave;
    private Button btnLoad;
    private EditText txtName;
    private EditText txtAge;
    private DatePicker datePicker;
    private Calendar calendar;
    public static final String MyPREFERENCES = "Shared" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_persistence);

        btnLoad = findViewById(R.id.btnLoad);
        btnSave = findViewById(R.id.btnSave);
        txtAge = findViewById(R.id.txtPercistanceAge);
        txtName = findViewById(R.id.txtPersistanceName);
        datePicker = findViewById(R.id.datePicker);
        calendar = Calendar.getInstance();
        datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener(){
                    @Override
                    public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
                        int age = calendar.get(Calendar.YEAR) - datePicker.getYear();
                        txtAge.setText(String.valueOf(age));
                    }
                });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences(MyPREFERENCES,MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                String name = txtName.getText().toString();
                String age = txtAge.getText().toString();
                editor.putString("name",name);
                editor.putString("age",age);
            }
        });

        btnLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences(MyPREFERENCES,MODE_PRIVATE);
                txtAge.setText(sharedPreferences.getString("age","Age"));
                txtName.setText(sharedPreferences.getString("name","Name"));
            }
        });
    }


    @Override
    public void onBackPressed() {
        String age =  txtAge.getText().toString();
        String name = txtName.getText().toString();

        Intent intent = new Intent();
        intent.putExtra("age",age);
        intent.putExtra("name",name);
        setResult(Activity.RESULT_OK,intent);
        super.onBackPressed();
    }
}
