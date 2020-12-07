package com.example.asteroids_engine_test;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
//not using a as a physical button anymore as multitouch controls didn't seem to work so using as
//more of a relay switch for the whole screen
public class FireButton
{
    private int x,y,r;
    private Paint c,cb;
    private boolean pressed;

    public FireButton(int x, int y)
    {
        this.x=x;
        this.y=y;
        r=75;
        c=new Paint();
        c.setColor(Color.RED);
        cb=new Paint();
        cb.setColor(Color.GRAY);
    }


    public void update()
    {
    }

    public void draw(Canvas canvas)
    {
        canvas.drawCircle(x,y,r,cb);
        canvas.drawCircle(x,y,r-15,c);

    }

    public boolean isPressed(float x, float y)
    {
        double distance = Math.sqrt(Math.pow(x-this.x,2)+Math.pow(y-this.y,2));
        return (distance<r);
    }

    public void setIsPressed(boolean b)
    {
        pressed=b;
    }

    public boolean getIsPressed()
    {
        return pressed;
    }
}

