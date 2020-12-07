package com.example.asteroids_engine_test;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.asteroids_engine_test.Person;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;

@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class Leaderboards extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboards);

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            File data = new File(Environment.getExternalStorageDirectory()+"/"+Environment.DIRECTORY_DOCUMENTS+"/data.dat");
            File parent = new File(Environment.getExternalStorageDirectory()+"/"+Environment.DIRECTORY_DOCUMENTS+"/");

            if (!data.exists()) {
                try {
                    data.createNewFile();
                    Toast.makeText(this, "Creating leaderboards file...", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                    //Toast.makeText(this, data.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }
        else{
            ActivityCompat.requestPermissions(Leaderboards.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    100);
        }

        File path = new File(Environment.getExternalStorageDirectory()+"/"+Environment.DIRECTORY_DOCUMENTS+"/");
        File file = new File(path,"data.dat");
        ArrayList<Person> list = new ArrayList<Person>();
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
        } catch (IOException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

        Person[] pe = new Person[256];
        for(int i=0;i<names.length;i++) {
            pe[i] = new Person();
            pe[i].setName(names[i]);
            pe[i].setScore(scores[i]);
            list.add(i, pe[i]);
        }
        //Toast.makeText(this, "is the list empty? -> " + list.isEmpty(), Toast.LENGTH_SHORT).show();
        Collections.sort(list);

        for(int i=0;i<list.size();i++) {
            try{
                nameText[i].setText(list.get(i).name);
                scoreText[i].setText(list.get(i).score);
            }
            catch (Exception e) {
                //Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void resetLeaderboards(View view) {
        File path = new File(Environment.getExternalStorageDirectory()+"/"+Environment.DIRECTORY_DOCUMENTS+"/");
        File file = new File(path,"data.dat");
        Toast.makeText(this, "The leaderboards have been reset!", Toast.LENGTH_SHORT).show();
        if (!file.exists()) {
            Toast.makeText(this, "There are no values to reset!", Toast.LENGTH_SHORT).show();
        }
        try {
            new File(file.toString()).delete();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Sorry, the leaderboards couldn't be reset...", Toast.LENGTH_LONG).show();
        }
    }
    public void updateName(View view) {
        final EditText nameText = (EditText)findViewById(R.id.editTextName);
        final Button submit = (Button)findViewById(R.id.buttonSubmit);


        String defName = nameText.getText().toString();
        if(defName.isEmpty()) {
            nameText.setError("Please enter a default name!");
        }
        else if (defName.length() > 20)
            nameText.setError("Please enter a shorter name!");
        else {
            try {
                setDefaultName(defName);
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(Leaderboards.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }



    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void setDefaultName(String s) throws IOException {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            File data = new File(Environment.getExternalStorageDirectory()+"/"+Environment.DIRECTORY_DOCUMENTS+"/asteroidsname.dat");
            File parent = new File(Environment.getExternalStorageDirectory()+"/"+Environment.DIRECTORY_DOCUMENTS+"/");


            if (data.exists()) {
                try {
                    File path = new File(Environment.getExternalStorageDirectory()+"/"+Environment.DIRECTORY_DOCUMENTS+"/");
                    File file = new File(path,"asteroidsname.dat");
                    new File(file.toString()).delete();
                    Toast.makeText(this, "New default name set!", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Error occured setting default name", Toast.LENGTH_SHORT).show();
                }
            }
            try {
                data.createNewFile();
            } catch (Exception e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        else {
            ActivityCompat.requestPermissions(Leaderboards.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    100);

        }
        String fileName = "asteroidsname.dat";
        try {
            File root = new File(Environment.getExternalStorageDirectory()+"/"+Environment.DIRECTORY_DOCUMENTS);
            File gpxfile = new File(root, fileName);
            //This gets the path to the file to be written to, namely, data.dat

            FileWriter writer = new FileWriter(gpxfile, true);
            writer.append(s);
            //This line ensures that names are written on even lines, whereas scores are written to odd lines
            //This is ensures that the data.dat file can be parsed by the Leaderboards activity later
            writer.flush();
            writer.close();
            Toast.makeText(this, "Default name set!", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace(); //Because file operations can go wrong :(
        }
    }

}