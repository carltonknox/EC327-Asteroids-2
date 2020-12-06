package com.example.asteroids_engine_test;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintStream;

public class GamePanelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_game_panel);


        FrameLayout fl = (FrameLayout)findViewById(R.id.frameLayout);
        fl.addView(new GamePanel(this));
    }

    public void gameOverState(int score) {
        final Dialog gameoverDialog = new Dialog(this);
        gameoverDialog.setContentView(R.layout.layout_gameover);
        gameoverDialog.setCancelable(false);
        gameoverDialog.show();
        String scoreGet = "Score: " + String.valueOf(score);

        Button menuButton = (Button) gameoverDialog.findViewById(R.id.button);
        final EditText nameBar = (EditText) gameoverDialog.findViewById(R.id.editTextTextPersonName);
        final String scoreFinal = String.valueOf(score);
        TextView scoreText = (TextView)gameoverDialog.findViewById(R.id.scoreView);
        scoreText.setText(scoreGet);

        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameBar.getText().toString();

                File file = new File(GamePanelActivity.this.getFilesDir(), "cache");
                if (!file.exists())
                    file.mkdir();

                if (name.isEmpty())
                    nameBar.setError("Please enter a name!");
                else if (name.length() > 10)
                    nameBar.setError("Please enter a shorter name!");
                else {
                    try {
                        updateLeaderboards(name, scoreFinal);
                        gameoverDialog.dismiss();
                    } catch (Exception e) {
                        Toast.makeText(GamePanelActivity.this, "Leaderboards update failed!", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void updateLeaderboards(String name, String score) throws IOException{
        String fileName = "data.dat";


        try
        {
            File root = new File(Environment.getExternalStorageDirectory()+File.separator+"Asteroids");
            File gpxfile = new File(root, fileName);

            FileWriter writer = new FileWriter(gpxfile,true);
            writer.append(name + "\n" + score + "\n");
            writer.flush();
            writer.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

}