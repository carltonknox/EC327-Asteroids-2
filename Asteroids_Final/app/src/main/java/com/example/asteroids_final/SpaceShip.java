package com.example.asteroids_final;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;

public class SpaceShip extends SpaceObject
{
    // to determine how fast we want the ship to move;
    private final int velocity = 10;
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
        x+=(velocity*j.getPositionX());
        y+=(velocity*j.getPositionY());

    }

    public void setPosition(int x,int y) {
        this.x=x;
        this.y=y;
    }
}
