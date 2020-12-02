package com.example.asteroids_final;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Asteroid extends SpaceObject {
    protected int size;
    //protected Bitmap img;
    Asteroid(int x,int y,int dx,int dy,int size,Bitmap img)
    {
        super(x,y,dx,dy);

        this.size=size;
        this.img = Bitmap.createScaledBitmap(img,(int)(2.5*size),(int)(2.5*size),false);
    }
}