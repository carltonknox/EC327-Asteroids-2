/**
 * Abstract Space Object Class for Asteroids, ships, and other potential objects
 * Contains coordinate system for screen-wrapping
 * update() and draw(canvas) functions can be called in the game loop
 */

package com.example.asteroids_engine_test;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;

import static com.example.asteroids_engine_test.MainThread.canvas;

public abstract class SpaceObject implements GameObject {
    protected int x,y,xMax,yMax,dx,dy;
    protected Bitmap img;
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
    public void draw(Canvas Canvas)//draws spaceObject in 9 places for screenwrapping
    {
        int w = img.getWidth();
        int h = img.getHeight();
        int imgx = x-w/2;
        int imgy = y-h/2;

        //draws object at 9 locations depending on screen dimensions
        //Could be optimized later
        canvas.drawBitmap(img,imgx,imgy,null);

        if(y<(h/2))
            canvas.drawBitmap(img,imgx,imgy+yMax,null);
        else if((yMax-y)<h/2)
            canvas.drawBitmap(img,imgx,imgy-yMax,null);

        if(x<(w/2)) {
            canvas.drawBitmap(img,imgx + xMax,imgy,null);
            if(y<(h/2))
                canvas.drawBitmap(img, imgx + xMax, imgy + yMax, null);
            else if((yMax-y)<h/2)
                canvas.drawBitmap(img, imgx + xMax, imgy - yMax, null);
        }
        else if((xMax-x)<(w/2)) {
            canvas.drawBitmap(img,imgx-xMax,imgy,null);
            if(y<(h/2))
                canvas.drawBitmap(img, imgx - xMax, imgy + yMax, null);
            else if((yMax-y)<h/2)
                canvas.drawBitmap(img, imgx - xMax, imgy - yMax, null);
        }
    }
}