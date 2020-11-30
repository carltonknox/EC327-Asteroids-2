package com.example.asteroids_final;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;

public class SpaceShip extends SpaceObject
{
    private double x,y;
    // to determine how fast we want the ship to move;
    private final int velocity = 10;
    //we want ship to start in middle.
    private final int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    private final int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
    private Bitmap ship;

    public SpaceShip(Bitmap ship) {
        this.ship = ship;
        //have to set middle fo screen
        x=screenWidth/2;
        y=screenHeight/2;
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(ship, (int)x, (int)y, null);
    }
    public void update(JoyStick j)
    {
        x+=(velocity*j.getPositionX());
        y+=(velocity*j.getPositionY());

    }

    public void setPosition(float x,float y) {
        this.x=x;
        this.y=y;
    }
}
