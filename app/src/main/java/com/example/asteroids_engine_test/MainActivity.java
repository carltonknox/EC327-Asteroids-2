package com.example.asteroids_engine_test;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.IOException;

public class MainActivity extends Activity {

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        String path = Environment.DIRECTORY_DOCUMENTS;

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            File data = new File(Environment.getExternalStorageDirectory()+"/"+Environment.DIRECTORY_DOCUMENTS+"/data.dat");
            File parent = new File(Environment.getExternalStorageDirectory()+"/"+Environment.DIRECTORY_DOCUMENTS+"/");

            if (!data.exists()) {
                try {
                    data.createNewFile();
                    //Toast.makeText(this, "Creating leaderboards file...", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    //Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                    //Toast.makeText(this, data.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }
        else{
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    100);

        }

        ImageButton playBut = (ImageButton) findViewById(R.id.playbutton);
        ImageButton leadBut = (ImageButton)findViewById(R.id.leadbutton);
        TextView logo = (TextView)findViewById(R.id.mainText);

        playBut.bringToFront();
        leadBut.bringToFront();
        logo.bringToFront();

        FrameLayout fl = (FrameLayout)findViewById(R.id.frLayout);
        fl.addView(new GamePanelMain(this));
        //This is here just to set the background to an older version of GamePanel





    }
    public void animPlay(final View view) {
        final ImageButton playBut = (ImageButton) findViewById(R.id.playbutton);
        final ImageButton leadBut = (ImageButton)findViewById(R.id.leadbutton);
        final TextView logo = (TextView)findViewById(R.id.mainText);
        ObjectAnimator butAnim1 = ObjectAnimator.ofFloat(playBut, "translationY", 7000f);
        ObjectAnimator butAnim2 = ObjectAnimator.ofFloat(leadBut, "translationY", 7000f);
        ObjectAnimator titleAnim = ObjectAnimator.ofFloat(logo, "translationY", -5000f);
        butAnim1.setDuration(1000);
        butAnim2.setDuration(1000);
        titleAnim.setDuration(1000);

        butAnim1.start();
        butAnim2.start();
        titleAnim.start();
        //Starts the little animation that's played before the GamePanelActivity is started

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                Intent i = new Intent(view.getContext(), GamePanelActivity.class);
                ObjectAnimator butAnim1 = ObjectAnimator.ofFloat(playBut, "translationY", 0f);
                ObjectAnimator butAnim2 = ObjectAnimator.ofFloat(leadBut, "translationY", 0f);
                ObjectAnimator titleAnim = ObjectAnimator.ofFloat(logo, "translationY", 0f);
                butAnim1.setDuration(1000);
                butAnim2.setDuration(1000);
                titleAnim.setDuration(1000);

                butAnim1.start();
                butAnim2.start();
                titleAnim.start();
                //The buttons are animated back to their original places, so that they're there when the
                //user exists GamePanelActivity

                startActivity(i);
            }
        }, 500 );//Delays 0.5 seconds before starting the activity for no particular reason apart from aesthetics
    }

    public void animLead(View view) {
        Intent intent = new Intent(view.getContext(), Leaderboards.class);
        startActivity(intent);
        //Just goes to the Leaderboards activity
    }

}