package com.johnmelodyme.facialexpression;
/*
 *  I Actually hate creating this app but My baby
 *  encourage me and say that I can ,
 *  SO I do So.
 */
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
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
    private Bitmap bitmap;

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

        // TODO ANALYSE_BUTTON:
        ANALYSE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ANALYSE.setText(R.string.an_done);
                Log.d(TAG, "onClick: ANALYSE");
            }
        });

        // TODO RESET_BUTTON:
        RESET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: RESET");
            }
        });
    }

    // TODO MAIN:
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent Data) {
        super.onActivityResult(requestCode, resultCode, Data);
        if (requestCode == RESULT_OK){
            switch (requestCode){
                case WRITE_STORAGE:
                    CHECK_PERMISSION(requestCode);
                    break;
                case CAMERA:
                    CHECK_PERMISSION(requestCode);
                    break;
                case SELECT_PHOTO:
                    Uri dataUri;
                    String path;
                    dataUri = Data.getData();
                    path = MyHelper.getPath(this, dataUri);
                    if (path == null) {
                        bitmap = MyHelper.resizePhoto(Photofile, this, dataUri, USER_IMAGE); 
                    } else {
                        bitmap = MyHelper.resizePhoto(Photofile, path, USER_IMAGE); 
                    }

                    if (bitmap != null){
                        // ==========
                    }
                    break;

            }
        }
    }

    // TODO : ON_START:
    @SuppressLint("SetTextI18n")
    @Override
    public void onStart(){
        super.onStart();
        USER_EMOTION.setText("Emotion:   ");
        PROBABILITY.setText("Probability:  ");
    }

    // TODO MENU ITEMS:
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.about:
                Log.d(TAG, "onOptionsItemSelected: \"Developed by John Melody Melissa\" ");
                new SweetAlertDialog(MainActivity.this)
                        .setTitleText("Version 1.0.0")
                        .setContentText("Developed by John Melody Melissa")
                        .show();
                Log.d(TAG, "onOptionsItemSelected: ");
                break;
            case R.id.upload:
                Log.d(TAG, "onOptionsItemSelected: UPLOAD");
                break;
            case R.id.camera:
                CHECK_PERMISSION(CAMERA);
                Log.d(TAG, "onOptionsItemSelected: CAMERA");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    // TODO CHECK_PERMISSION:
    public void CHECK_PERMISSION(int REQUEST_CODE){
        switch (REQUEST_CODE){
            case CAMERA:
                int hasTheCameraPermission;
                hasTheCameraPermission = ActivityCompat
                        .checkSelfPermission(this, Manifest.permission.CAMERA);
                if (hasTheCameraPermission == PackageManager.PERMISSION_GRANTED){
                    LAUNCH_CAMERA();
                } else {
                    ActivityCompat.requestPermissions(this, new String[] {
                            Manifest.permission.CAMERA
                    },REQUEST_CODE);
                }
                break;

            case WRITE_STORAGE:
                int hasWriteStoragePermission;
                hasWriteStoragePermission = ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE);
                if(hasWriteStoragePermission == PackageManager.PERMISSION_GRANTED){
                    SELECT_IMAGE();
                } else {
                    ActivityCompat.requestPermissions(this, new String[]{
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    }, REQUEST_CODE);
                }
                break;
        }
    }

    // TODO SELECT_IMAGE:
    private void SELECT_IMAGE() {
        Photofile = MyHelper.createTempFile(Photofile);
        Intent intent;
        intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, SELECT_PHOTO);
    }

    // TODO LAUNCH_CAMERA:
    private void LAUNCH_CAMERA() {
        Intent MEDIA;
        Uri Photo;
        Photofile = MyHelper.createTempFile(Photofile);
        MEDIA = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Photo = FileProvider.getUriForFile(this, getPackageName()
                + ".provider", Photofile);
        MEDIA.putExtra(MediaStore.EXTRA_OUTPUT, Photo);
        startActivityForResult(MEDIA, TAKE_PHOTO);
    }

    // TODO onRequestPermissionsResult:
    @Override
    public void onRequestPermissionsResult(int REQUESTCODE, @NonNull String[] PERMISSIONS, @NonNull int[] GRANT_RESULTS){
        super.onRequestPermissionsResult(REQUESTCODE, PERMISSIONS, GRANT_RESULTS);
        switch(REQUESTCODE){
            case CAMERA:
                if (GRANT_RESULTS.length > 0 && GRANT_RESULTS[0] == PackageManager.PERMISSION_GRANTED){
                    LAUNCH_CAMERA();
                } else {
                    REQUEST_PERMISSION(this, REQUESTCODE, R.string.Camera_denied);
                }
                break;
            case WRITE_STORAGE:
                if (GRANT_RESULTS.length > 0 && GRANT_RESULTS[0] == PackageManager.PERMISSION_GRANTED){
                    SELECT_IMAGE();
                } else {
                    REQUEST_PERMISSION(this, REQUESTCODE, R.string.storage_denied);
                }
                break;
        }
    }

    // TODO REQUEST_PERMISSION:
    private void REQUEST_PERMISSION(final MainActivity mainActivity, final int requestcode, int message) {
        Context context;
        context = MainActivity.this;
        AlertDialog.Builder ALERT;
        ALERT = new AlertDialog.Builder(context);
        ALERT.setMessage(message);
        ALERT.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                dialogInterface.dismiss();
                Intent intent;
                intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.setData(Uri.parse("package: " + mainActivity.getPackageName()));
                mainActivity.startActivityForResult(intent, requestcode);
            }
        }).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                dialogInterface.dismiss();
            }
        }).setCancelable(false).show();
    }
}
