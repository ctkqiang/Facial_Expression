package com.johnmelodyme.facialexpression;
/*
 *  I Actually hate creating this app but My baby
 *  encourage me and say that I can ,
 *  SO I do So.
 */
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.camerakit.CameraKitView;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * @Author : John Melody Melissa
 * @Copyright: John Melody Melissa  © Copyright 2020
 * @INPIREDBYGF : Sin Dee <3
 */

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
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
        PROBABILITY =findViewById(R.id.probability);
        ANALYSE = findViewById(R.id.analyse);
        RESET = findViewById(R.id.reset);

        ANALYSE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ANALYSE.setText(R.string.an_done);
                ANALYSE.setText(R.string.analyse);
            }
        });
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

        if (ID == R.id.about){
            Log.d(TAG, "onOptionsItemSelected: \"Developed by John Melody Melissa\" ");
            new SweetAlertDialog(MainActivity.this)
                    .setTitleText("Version 1.0.0")
                    .setContentText("Developed by John Melody Melissa")
                    .show();
            return true;
        }

        if (ID == R.id.upload){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
