package com.example.asteroids_engine_test;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.hardware.SensorManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.seismic.ShakeDetector;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class GamePanelActivity extends AppCompatActivity implements ShakeDetector.Listener {
    GamePanel Game;
    public int score;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_game_panel);

        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        ShakeDetector shakeDetector = new ShakeDetector(this);

        shakeDetector.start(sensorManager);

        FrameLayout fl = (FrameLayout)findViewById(R.id.frameLayout);
        Game= new GamePanel(this);
        fl.addView(Game);
        //GamePanel is not an Activity (since it already extends SurfaceView), so it cannot be started by an Intent
        //Instead, we start GamePanelActivity, and then put the GamePanel inside a Frame Layout.

    }
    public GamePanelActivity(int score) {
        this.score = score;

    }


    public GamePanelActivity() {
        this.score = 0;
    }
    //Android activities don't require constructors to be written, but we need them so that
    //they can be called from other classes


    public void gameOverState(final Context context) {
        final Dialog gameoverDialog = new Dialog(context);
        gameoverDialog.setContentView(R.layout.layout_gameover);
        gameoverDialog.setCancelable(false);
        gameoverDialog.show();
        String scoreGet = "Score: " + String.valueOf(score);
        //This function is not used by GamePanelActivity, but rather when it is called from GamePanel itself
        //It defines a dialog box, which uses the game over layout

        Button menuButton = (Button) gameoverDialog.findViewById(R.id.button);
        final EditText nameBar = (EditText) gameoverDialog.findViewById(R.id.editTextTextPersonName);
        final String scoreFinal = String.valueOf(score);
        TextView scoreText = (TextView)gameoverDialog.findViewById(R.id.scoreView);
        scoreText.setText(scoreGet);
        //The game over layout includes a field for getting a name

        menuButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                String name = nameBar.getText().toString();

                if (name.isEmpty())
                    try {
                        StringBuilder text = new StringBuilder();
                        File path = new File(Environment.getExternalStorageDirectory()+"/"+Environment.DIRECTORY_DOCUMENTS+"/");
                        File file = new File(path,"asteroidsname.dat");

                        if(file.exists()) {
                            try {
                                BufferedReader bruh = new BufferedReader(new FileReader(file));
                                String line;

                                while ((line = bruh.readLine()) != null) {
                                    text.append(line);
                                    name = line;
                                }
                                bruh.close();
                            } catch (IOException e) {
                                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
                            }
                        }
                        else{
                            name = "Player 1";
                        }
                        updateLeaderboards(name, scoreFinal);
                        gameoverDialog.dismiss();
                    } catch(Exception e) {
                        //Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                else if (name.length() > 20)
                    nameBar.setError("Please enter a shorter name!");
                else {
                    try {
                        updateLeaderboards(name, scoreFinal);
                        gameoverDialog.dismiss();
                    } catch (Exception e) {
                        Toast.makeText(context, "Leaderboards update failed!", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void updateLeaderboards(String name, String score) {
        String fileName = "data.dat";
        try {
            File root = new File(Environment.getExternalStorageDirectory()+"/"+Environment.DIRECTORY_DOCUMENTS);
            File gpxfile = new File(root, fileName);
            //This gets the path to the file to be written to, namely, data.dat

            FileWriter writer = new FileWriter(gpxfile, true);
            writer.append(name + "\n" + score + "\n");
            //This line ensures that names are written on even lines, whereas scores are written to odd lines
            //This is ensures that the data.dat file can be parsed by the Leaderboards activity later
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace(); //Because file operations can go wrong :(
        }
    }



    @Override
    public void hearShake()
    {
        Game.shoke=true;

    }
}