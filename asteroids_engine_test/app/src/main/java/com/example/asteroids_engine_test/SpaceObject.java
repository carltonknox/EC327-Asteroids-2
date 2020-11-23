package com.example.asteroids_engine_test;

import android.widget.Space;

public class SpaceObject {
    protected int x,y,xMax,yMax,dx,dy;
    public SpaceObject(int x,int y,int dx,int dy,int xMax, int yMax)
    {
        this.x=x;
        this.y=y;
        this.dx=dx;
        this.dy=dy;
        this.xMax=xMax;
        this.yMax=yMax;
    }
    public void updateLocation()
    {
        this.x = bind((this.x+this.dx),xMax);
        this.y = bind((this.y+this.dy),yMax);
    }
    public int bind(int num,int bound)
    {
        if(num>bound)
            return num-bound;
        else if(num<0)
            return num+bound;
        else return num;
    }
}