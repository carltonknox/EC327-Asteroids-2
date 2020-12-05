package com.example.asteroids_engine_test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;

public class Leaderboards extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboards);

        File path = new File(Environment.getExternalStorageDirectory()+File.separator+"Asteroids");
        File file = new File(path,"data.dat");
        String scores[] = new String[256];
        String names[] = new String[256];
        TextView nameText[] = new TextView[5];
        TextView scoreText[] = new TextView[5];

        nameText[0] = (TextView)findViewById(R.id.name1);
        nameText[1] = (TextView)findViewById(R.id.name2);
        nameText[2] = (TextView)findViewById(R.id.name3);
        nameText[3] = (TextView)findViewById(R.id.name4);
        nameText[4] = (TextView)findViewById(R.id.name5);
        scoreText[0] = (TextView)findViewById(R.id.score1);
        scoreText[1] = (TextView)findViewById(R.id.score2);
        scoreText[2] = (TextView)findViewById(R.id.score3);
        scoreText[3] = (TextView)findViewById(R.id.score4);
        scoreText[4] = (TextView)findViewById(R.id.score5);

        StringBuilder text = new StringBuilder();

        try {
            BufferedReader bruh = new BufferedReader(new FileReader(file));
            String line;
            int satir = 0;
            int iter = 0;

            while ((line = bruh.readLine()) != null) {
                text.append(line);
                text.append('\n');
                if(satir%2==0 && !line.isEmpty()) {
                    names[iter] = line;
                }
                else if(satir%2!=0 && !line.isEmpty()) {
                    scores[iter] = line;
                    iter++;
                }
                satir++;

            }
            bruh.close();
        }
        catch (IOException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

        for(int i=0;i<=4;i++) {
            try{
                nameText[i].setText(names[i]);
                scoreText[i].setText(scores[i]);
            }
            catch (Exception e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void resetLeaderboards(View view) {
        File path = new File(Environment.getExternalStorageDirectory()+File.separator+"Asteroids");
        File file = new File(path,"data.dat");
        Toast.makeText(this, "The leaderboards have been reset!", Toast.LENGTH_SHORT).show();
        if (!file.exists()) {
            Toast.makeText(this, "There are no values to reset!", Toast.LENGTH_SHORT).show();
        }
        try {
            new File(file.toString()).delete();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Well, this is awkward...", Toast.LENGTH_SHORT).show();
        }
    }
}