/*
 * Carlton Knox - 11/29/2023
 * SpaceObject class defines an object with a position, velocity, and bounds for grid
 * includes bind function to wrap x or y position if exceeds bounds of grid
 * updateLocation() moves the object in accordance with its velocity and the bounds of the screen
 * The way bounds are used, as well as the data types of values are subject to change
 *
 * includes draw function
 */

package com.example.asteroids_engine_test;

import android.graphics.Canvas;
import android.graphics.Point;
import android.widget.Space;

import com.example.asteroids_engine_test.GameObject;

public abstract class SpaceObject implements GameObject {
    protected int x,y,xMax,yMax,dx,dy;
    //Point xy,velocity;
    public SpaceObject(int x,int y,int dx,int dy,int xMax, int yMax)
    {
        /*xy= new Point(x,y);
        velocity = new Point(dx,dy);*/

        this.x=x;
        this.y=y;
        this.dx=dx;
        this.dy=dy;
        this.xMax=xMax;
        this.yMax=yMax;
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