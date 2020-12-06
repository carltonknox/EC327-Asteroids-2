package com.example.asteroids_engine_test;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Laser extends SpaceObject
{
    //private int x,y;
    private double vX,vY;
    private int tV=50;
    private int r=15;
    private float angle;
    Paint c;

    Laser(int x, int y, float angle)
    {
        super(x,y,0,0);
        this.x=x;
        this.y=y;
        this.angle=angle;
        angle = (float)Math.toRadians((double) angle);

        vX=(double)Math.sin(angle)*tV;
        vY=(double)Math.cos(angle)*tV;
        c= new Paint();
        //c.setARGB();
        c.setColor(Color.YELLOW);
    }

    @Override
    public void draw(Canvas canvas)
    {
        canvas.drawCircle(x,y,r,c);
    }
    @Override
    public void update()
    {
        if(angle <=90)
        {
            x+=vX;
            y-=vY;
        }
        else if(angle <=180)
        {
            x+=vX;
            y-=vY;
        }
        else if(angle <=270)
        {
            x+=vX;
            y-=vY;
        }
        else if(angle <=360)
        {
            x+=vX;
            y-=vY;
        }
    }

    public boolean isInBounds()
    {
        if(x>xMax||x<0||y>yMax||y<0)
            return false;
        return true;
    }
}
