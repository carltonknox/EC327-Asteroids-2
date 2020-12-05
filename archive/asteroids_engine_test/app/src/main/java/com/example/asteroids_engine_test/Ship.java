package com.example.asteroids_engine_test;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Ship extends SpaceObject {
    protected int angle;
    protected Bitmap img;
    public Ship(int x,int y,int dx,int dy,int xMax, int yMax,Bitmap img)
    {
        super(x,y,dx,dy,xMax,yMax);
        this.angle=0;
        this.img = img;
    }

    public void draw(Canvas canvas)
    {
        int imgx = x-img.getWidth()/2;
        int imgy = y-img.getHeight()/2;

        canvas.drawBitmap(img,imgx,imgy,null);
        canvas.drawBitmap(img,imgx+xMax,imgy,null);
        canvas.drawBitmap(img,imgx-xMax,imgy,null);
        canvas.drawBitmap(img,imgx,imgy+yMax,null);
        canvas.drawBitmap(img,imgx,imgy-yMax,null);
    }
}
