package com.johnmelodyme.facialexpression;
/*
 *  I Actually hate creating this app but My baby
 *  encourage me and say that I can ,
 *  SO I do So.
 */

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.camerakit.CameraKitView;
import com.johnmelodyme.facialexpression.Helper.MyHelper;

import java.io.File;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * @Author : John Melody Melissa
 * @Copyright: John Melody Melissa  © Copyright 2020
 * @INPIREDBYGF : Sin Dee <3
 */

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    public static final int WRITE_STORAGE = 100;
    public static final int CAMERA = 102;
    public static final int SELECT_PHOTO = 103;
    public static final int TAKE_PHOTO = 104;
    public File Photofile;
    private CameraKitView CameraView;
    private ImageView USER_IMAGE;
    private TextView USER_EMOTION, PROBABILITY;
    private Button ANALYSE, RESET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: Starting Application...");
        USER_IMAGE = findViewById(R.id.user_image);
        USER_EMOTION = findViewById(R.id.Emotion);
        PROBABILITY = findViewById(R.id.probability);
        ANALYSE = findViewById(R.id.analyse);
        RESET = findViewById(R.id.reset);

        ANALYSE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ANALYSE.setText(R.string.an_done);
            }
        });
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onStart(){
        super.onStart();
        USER_EMOTION.setText("Emotion:   ");
        PROBABILITY.setText("Probability:  ");
    }

    // MENU ITEMS:
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int ID = item.getItemId();

        if (ID == R.id.about) {
            Log.d(TAG, "onOptionsItemSelected: \"Developed by John Melody Melissa\" ");
            new SweetAlertDialog(MainActivity.this)
                    .setTitleText("Version 1.0.0")
                    .setContentText("Developed by John Melody Melissa")
                    .show();
            return true;
        }

        if (ID == R.id.upload) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void CHECK_PERMISSION(int REQUEST_CODE){
        switch (REQUEST_CODE){
            case CAMERA:
                int hasTheCameraPermission;
                hasTheCameraPermission = ActivityCompat
                        .checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA);
                if (hasTheCameraPermission == PackageManager.PERMISSION_GRANTED){
                    LAUNCH_CAMERA();
                } else {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[] {
                            Manifest.permission.CAMERA
                    },REQUEST_CODE);
                }
                break;
            case WRITE_STORAGE:
                break;
        }
    }

    private void LAUNCH_CAMERA() {
        Intent MEDIA;
        Uri Photo;

        Photofile = MyHelper.createTempFile(Photofile);
        MEDIA = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Photo = FileProvider.getUriForFile(MainActivity.this, getPackageName()
                + ".provider", Photofile);
        MEDIA.putExtra(MediaStore.EXTRA_OUTPUT, Photo);
        startActivityForResult(MEDIA, TAKE_PHOTO);
    }
}
