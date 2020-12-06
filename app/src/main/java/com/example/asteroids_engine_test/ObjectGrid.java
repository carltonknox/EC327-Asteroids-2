package com.example.asteroids_engine_test;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.Random;

public class ObjectGrid {
    private int rows = 20;
    private int cols = 20;

    private boolean[][] grid = new boolean[rows][cols];

    ObjectGrid(){

    }

    public void setup() {
        Random rand = new Random();

        for (int i=0; i<grid.length; i++) {
            for (int j=0; j<grid[j].length; j++) {
                if(rand.nextFloat() < 0.2){
                    grid[i][j] = true;
                }
            }
        }

    }
    public void draw(Canvas canvas) {
        float w = Resources.getSystem().getDisplayMetrics().widthPixels/cols;
        float h = Resources.getSystem().getDisplayMetrics().heightPixels/rows;
        Paint paint = new Paint();
        paint.setColor(Color.TRANSPARENT);

        for (int i=0; i<rows; i++) {
            for (int j=0; j<cols; j++) {
                float x = w*j;
                float y = w*i;

                if(grid[i][j]) {
                    paint.setColor(Color.MAGENTA);

                }
                canvas.drawRect(x-w/2,y+h/2, x+w/2, y-h/2, paint);
            }
        }
    }


}
