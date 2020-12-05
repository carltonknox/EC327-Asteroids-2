package com.example.asteroids_engine_test;

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
        canvas.drawBitmap(ship, x, y, null);
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
