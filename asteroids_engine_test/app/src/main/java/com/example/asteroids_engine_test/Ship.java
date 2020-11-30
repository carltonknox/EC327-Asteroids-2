package com.example.asteroids_engine_test;

import android.graphics.Canvas;

public class Ship extends SpaceObject {
    protected int size,angle;
    public Ship(int x,int y,int dx,int dy,int xMax, int yMax,int size,int angle)
    {
        super(x,y,dx,dy,xMax,yMax);
        this.size=size;
        this.angle=angle;
    }

    public void draw(Canvas canvas)
    {

    }
}
