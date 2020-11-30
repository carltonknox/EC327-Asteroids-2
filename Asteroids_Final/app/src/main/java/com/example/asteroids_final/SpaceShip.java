package com.example.asteroids_final;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;

public class SpaceShip extends SpaceObject
{
    // to determine how fast we want the ship to move;
    private final int velocity = 30;
    //we want ship to start in middle.
    private final static int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    private final static int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
    private Bitmap ship;

    public SpaceShip(Bitmap ship) {
        super(screenWidth/2,screenHeight/2,0,0);
        this.ship = ship;
        //have to set middle fo screen

    }

    public void draw(Canvas canvas) {
        int imgx = x-ship.getWidth()/2;
        int imgy = y-ship.getHeight()/2;

        canvas.drawBitmap(ship,imgx,imgy,null);
        canvas.drawBitmap(ship,imgx+xMax,imgy,null);
        canvas.drawBitmap(ship,imgx-xMax,imgy,null);
        canvas.drawBitmap(ship,imgx,imgy+yMax,null);
        canvas.drawBitmap(ship,imgx,imgy-yMax,null);
    }
    public void update(JoyStick j)
    {
        this.dx= (int) (velocity*j.getPositionX());
        this.dy= (int) (velocity*j.getPositionY());
        this.x = bind((this.x+this.dx),xMax);
        this.y = bind((this.y+this.dy),yMax);
    }

    public void setPosition(int x,int y) {
        this.x=x;
        this.y=y;
    }
}
