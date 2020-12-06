package com.example.asteroids_engine_test;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class AsteroidMenu extends SpaceObject {
    protected int size;
    protected Bitmap img;
    AsteroidMenu(int x,int y,int dx,int dy,int size,Bitmap img)
    {
        super(x,y,dx,dy);

        this.size=size;
        this.img = Bitmap.createScaledBitmap(img,(int)(2.5*size),(int)(2.5*size),false);
    }

    public void draw(Canvas canvas)
    {

        /*Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        canvas.drawCircle(this.x,this.y,this.size,paint);*/

        int imgx = x-img.getWidth()/2;
        int imgy = y-img.getHeight()/2;

        canvas.drawBitmap(img,imgx,imgy,null);
        canvas.drawBitmap(img,imgx+xMax,imgy,null);
        canvas.drawBitmap(img,imgx-xMax,imgy,null);
        canvas.drawBitmap(img,imgx,imgy+yMax,null);
        canvas.drawBitmap(img,imgx,imgy-yMax,null);
    }
}