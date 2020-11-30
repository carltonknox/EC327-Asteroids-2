package com.example.asteroids_final;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class JoyStick implements GameObject{

    private int smallX,smallY,bigX,bigY,br,sr;
    private Paint bc,sc;
    private boolean pressed;
    private double pX,pY;

    public JoyStick(int x, int y)
    {
        bigX=x;
        bigY=y;
        smallX=x;
        smallY=y;
        br=75;
        sr=45;
        bc = new Paint();
        bc.setColor(Color.GRAY);
        sc=new Paint();
        sc.setColor(Color.RED);
    }
    public void draw(Canvas canvas)
    {
        canvas.drawCircle(bigX,bigY,br,bc);
        canvas.drawCircle(smallX,smallY,sr,sc);
    }

    public void update()
    {
        smallX=(int)(bigX+(pX*br));
        smallY=(int)(bigY+(pY*br));

    }

    public void setPosition(double x, double y)
    {
        double xd,yd;
        xd=x-bigX;
        yd=y-bigY;
        double distance = Math.sqrt(Math.pow(x-bigX,2)+Math.pow(y-bigY,2));
        if(distance<br)
        {
            pX=xd/br;
            pY=yd/br;
        }
        else
        {
            pX=xd/distance;
            pY=yd/distance;
        }


    }

    public boolean isPressed(double x , double y)
    {
        double distance = Math.sqrt(Math.pow(x-bigX,2)+Math.pow(y-bigY,2));
        return (distance<br);
    }

    public void setIsPressed(boolean pressed)
    {
        this.pressed=pressed;
    }

    public void resetPosition() {
        pX=0;
        pY=0;
    }

    public boolean getIsPressed()
    {
        return pressed;
    }

    public double getPositionX()
    {
        return pX;
    }

    public double getPositionY() {
        return pY;
    }
}
