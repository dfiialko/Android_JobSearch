package com.example.calculator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    private Button btnPlus;
    private Button btnMinus;
    private Button btnMultiply;
    private Button btnDivide;
    private Button btnFactorial;
    private Button btnEqual;
    private Button btnNext;
    private EditText txtNumber;
    private EditText txtAge;
    private EditText txtName;
    private char specialChar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnPlus = findViewById(R.id.btnPlus);
        btnMinus = findViewById(R.id.btnMinus);
        btnDivide = findViewById(R.id.btnDivide);
        btnMultiply = findViewById(R.id.btnMultiply);
        btnFactorial = findViewById(R.id.btnFactorial);
        btnEqual = findViewById(R.id.btnEqual);
        txtNumber = findViewById(R.id.txtNumber);
        txtName = findViewById(R.id.txtName);
        txtAge = findViewById(R.id.txtAge);
        btnNext = findViewById(R.id.btnNext);
        char symb;
        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setText('+');
                specialChar = '+';
            }
        });

        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setText('-');
                specialChar = '-';
            }
        });

        btnDivide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setText('/');
                specialChar = '/';
            }
        });

        btnMultiply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setText('*');
                specialChar = '*';
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Persistence.class);
                startActivityForResult(intent,2);
            }
        });

        btnFactorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setText('%');
            }
        });

        btnEqual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input = txtNumber.getText().toString();
                Matcher match = Pattern.compile("[-+*/%]").matcher(input);
                if(match.find()){
                    int specialCharAt = match.start();
                    int first = Integer.valueOf(input.substring(0,specialCharAt));
                    int second = Integer.valueOf(input.substring(specialCharAt+1));
                    char special_char = input.charAt(specialCharAt);
                    int result = 0;
                    if ((special_char == '/' && first == 0) || (special_char == '/' && second == 0)){
                        result = 0;
                    }else {
                        switch (specialChar) {
                            case '+':
                                result = first + second;
                                break;
                            case '-':
                                result = first - second;
                                break;
                            case '*':
                                result = first * second;
                                break;
                            case '/':
                                result = first / second;
                                break;
                            case '%':
                                result = first % second;
                                break;
                        }
                    }
                    txtNumber.setText(Integer.toString(result));
                }

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if(requestCode==2)
        {
            String age=data.getStringExtra("age");
            String name = data.getStringExtra("name");
            txtAge.setText(age);
            txtName.setText(name);
        }
    }

    private void setText(char symbol){
        String firstEnteredValue = txtNumber.getText().toString();
        specialChar = symbol;
        if(firstEnteredValue.length() != 0 && !Pattern.compile("[-+\\*/%]").matcher(firstEnteredValue).find()){
            txtNumber.setText(firstEnteredValue + symbol);
        }
        txtNumber.setSelection(txtNumber.getText().length());
    }

}
