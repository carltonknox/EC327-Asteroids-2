package com.example.asteroids_engine_test;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Point;

public abstract class SpaceObject implements GameObject {
    protected int x,y,xMax,yMax,dx,dy;
    //Point xy,velocity;
    public SpaceObject(int x,int y,int dx,int dy)
    {
        this.x=x;
        this.y=y;
        this.dx=dx;
        this.dy=dy;

        this.xMax = Resources.getSystem().getDisplayMetrics().widthPixels;
        this.yMax = Resources.getSystem().getDisplayMetrics().heightPixels;
    }
    public void update()
    {
        this.x = bind((this.x+this.dx),xMax);
        this.y = bind((this.y+this.dy),yMax);
    }
    public Point getXY()//Returns coordinates of object
    {
        Point xy = new Point(x,y);
        return xy;
    }
    public int bind(int num,int bound)
    {
        if(num>bound)
            return num-bound;
        else if(num<0)
            return num+bound;
        else return num;
    }
    public abstract void draw(Canvas Canvas);
}