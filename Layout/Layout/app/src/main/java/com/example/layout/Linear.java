package com.example.layout;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.ToggleButton;

public class Linear extends AppCompatActivity {

    private Button btnRelative;
    private Button btnWebView;
    private ToggleButton btnOff;
    private ProgressBar progressBar;
    private CheckBox cbCheck;
    private FloatingActionButton btnFloat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linear);

        btnRelative = findViewById(R.id.btnRelative);
        btnWebView = findViewById(R.id.btnWebView);
        btnOff = findViewById(R.id.btnOff);
        progressBar = findViewById(R.id.progressBar);
        cbCheck = findViewById(R.id.cbCheck);
        btnFloat = findViewById(R.id.btnFloat);
        btnRelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),Relative.class);
                startActivity(intent);
            }
        });

        btnWebView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(view.getContext(),MyWebView.class);
                startActivity(intent);
            }
        });


        btnOff.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Toast.makeText(Linear.this,"Already Off",Toast.LENGTH_LONG).show();
                progressBar.incrementProgressBy(50);
                return true;
            }
        });

       cbCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
               if(cbCheck.isChecked()){
                   progressBar.setProgress(0);
               }
               else{
                   progressBar.setProgress(25);
               }
           }
       });

        btnFloat.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View view, DragEvent dragEvent) {
                Toast.makeText(Linear.this,"Drag",Toast.LENGTH_LONG);
                return false;
            }
        });

    }
}
