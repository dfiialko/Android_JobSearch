package com.example.camerademo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.os.Bundle;
import android.content.pm.PackageInfo;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private Button btnCamera;
    private ImageView imgView;
    // Create request_image code to return the result from Activity on Result
    static final int REQUEST_IMAGE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnCamera = findViewById(R.id.btnCapture);
        imgView = findViewById(R.id.imgView);
        // Disable button if camera not found,avoid errors
        if(!hasCamera()){
            btnCamera.setEnabled(false);
        }
        // Call method to launch camera
    btnCamera.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            openCamera();
        }
    });
    }
    private boolean hasCamera(){
        // True if there is front or back camera
        return getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY);
    }
    // Start intent to open Camera
    private void openCamera(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,REQUEST_IMAGE);
    }
    // Result of StartActivity for Result openCamera
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_IMAGE && resultCode == RESULT_OK){
            Bundle extras = data.getExtras();
            Bitmap photo = (Bitmap) extras.get("data");
            imgView.setImageBitmap(photo);
        }
    }
}
