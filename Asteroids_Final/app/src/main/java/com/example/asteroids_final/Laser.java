package com.example.asteroids_final;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Laser
{
    private int x,y;
    private double vX,vY;
    private int tV=50;
    private int r=15;
    private float angle;
    Paint c;

    Laser(int x, int y, float angle)
    {
        this.x=x;
        this.y=y;
        this.angle=angle;
        angle = (float)Math.toRadians((double) angle);

        vX=(double)Math.sin(angle)*tV;
        vY=(double)Math.cos(angle)*tV;
        c= new Paint();
        //c.setARGB();
        c.setColor(Color.YELLOW);

        System.out.println(angle);

        System.out.println(vX);
        System.out.println(vY);
    }

    public void draw(Canvas canvas)
    {
        canvas.drawCircle(x,y,r,c);


    }
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
}
