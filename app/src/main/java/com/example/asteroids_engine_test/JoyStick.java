package com.example.asteroids_engine_test;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class JoyStick implements GameObject{
    // Joystick is made up of two circles
    private int smallX,smallY,bigX,bigY,br,sr;
    private Paint bc,sc;
    private boolean pressed;
    private double pX,pY;
    private float angle;

    private final static int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;

    //needs coordinates so that it fits screen uses System resources to get width a height and set it
    // at a set place on any phone screen.
    public JoyStick(int x, int y)
    {
        // sets Joystick size and color
        bigX=x;
        bigY=y;
        smallX=x;
        smallY=y;
        br=90*screenWidth/1080;
        sr=60*screenWidth/1080;
        bc = new Paint();
        bc.setColor(Color.GRAY);
        sc=new Paint();
        sc.setColor(Color.BLUE);
    }
    // Draws both circles
    public void draw(Canvas canvas)
    {
        canvas.drawCircle(bigX,bigY,br,bc);
        canvas.drawCircle(smallX,smallY,sr,sc);

    }

    public void update()
    {
        // make sure to update the stick part of the joystick to its location relative to the center
        // like in real life.
        smallX=(int)(bigX+(pX*br));
        smallY=(int)(bigY+(pY*br));

    }

    public void setPosition(double x, double y)
    {
        double xd,yd;
        xd=x-bigX;
        yd=y-bigY;

        //sets magnitude of joystick and position of small part.
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
        //Origin of the screen is top left corner made this math a lot harder

        angle=(float)(Math.atan((pY/pX))*180/Math.PI);
        if(pX<0&&pY>0)
        {
            //done do not change
            angle+=270;
        }
        if(pX>0&&pY<0)
        {
            //done
            angle+=90;
        }
        if(pX>0&&pY>0)
        {
            //done
            angle+=90;
        }
        if(pX<0&&pY<0)
        {
            //done
            angle+=270;
        }


    }
    // Checks whether or not the stick is pressed
    public boolean isPressed(double x , double y)
    {
        double distance = Math.sqrt(Math.pow(x-bigX,2)+Math.pow(y-bigY,2));
        return (distance<br);
    }

    //sets boolean to yes or no wether or not it's pressed
    public void setIsPressed(boolean pressed)
    {
        this.pressed=pressed;
    }

    //Function so that it is reset like a forced feed back joy stick.
    public void resetPosition() {
        pX=0;
        pY=0;
        //angle=0;

    }

    //accessor functions
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

    public float getDegrees()
    {
        return angle;
    }
}