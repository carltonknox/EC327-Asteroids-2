/*
Asteroid class defines an SpaceObject with a radius and a hitbox
 */
package com.example.asteroids_engine_test;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;

public class Asteroid extends SpaceObject {
    protected int radius;
    private Circle hitBox = new Circle();
    Asteroid(int x,int y,int dx,int dy,int size,Bitmap img)
    {
        super(x,y,dx,dy);
        this.radius=size;
        this.img = Bitmap.createScaledBitmap(img,(int)(2.5*size),(int)(2.5*size),false);
    }
    public void updateHitBox() {
        hitBox.set(this.getXY(), radius, Color.MAGENTA);
    }

    public boolean collision(SpaceShip ship) {
        return this.getHitBox().intersects(ship.getHitBox());
    }

    public boolean collision(Laser laser){
        return this.getHitBox().intersects(laser.getXY());
    }

    public Circle getHitBox() {
        return hitBox;
    }
}