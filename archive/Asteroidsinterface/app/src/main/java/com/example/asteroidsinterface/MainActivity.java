package com.example.asteroidsinterface;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void animPlay(View view) {
        ImageButton playBut = (ImageButton) findViewById(R.id.playbutton);
        ImageButton leadBut = (ImageButton)findViewById(R.id.leadbutton);
        TextView logo = (TextView)findViewById(R.id.mainText);
        ObjectAnimator butAnim1 = ObjectAnimator.ofFloat(playBut, "translationY", 7000f);
        ObjectAnimator butAnim2 = ObjectAnimator.ofFloat(leadBut, "translationY", 7000f);
        ObjectAnimator titleAnim = ObjectAnimator.ofFloat(logo, "translationY", -5000f);
        butAnim1.setDuration(1000);
        butAnim2.setDuration(1000);
        titleAnim.setDuration(1000);

        butAnim1.start();
        butAnim2.start();
        titleAnim.start();
        final Intent gameIntent = new Intent(this, GameActivity.class);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                startActivity(gameIntent);
            }
        }, 2000 );//time in milisecond


    }

    public void animLead(View view) {
        Toast.makeText(this, "Leaderboards button", Toast.LENGTH_SHORT).show();
    }
}