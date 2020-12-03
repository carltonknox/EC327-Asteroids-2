package com.ec327.asteroids;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;

public class SpaceShip
{
    //Ship main X,y coordinate
    private double x,y;
    // to determine how fast we want the ship to move;
    private final int velocity = 15;
    //we want ship to start in middle.
    private final int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    private final int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
    private Bitmap ship1;
    private Bitmap ship2;
    private Bitmap finalShip;
    private float degrees;//rotation degree
    private Matrix matrix = new Matrix();

        public SpaceShip(Bitmap shipon,Bitmap shipoff) {
           //scales my ships to an appropriate size;
            Bitmap example = Bitmap.createScaledBitmap(shipon,180,180,true);
            Bitmap example2 = Bitmap.createScaledBitmap(shipoff,180,180,true);
            this.ship1 = example;
            this.ship2 = example2;

            //have to set middle fo screen
            x=screenWidth/2;
            y=screenHeight/2;
        }

        public void draw(Canvas canvas)
        {
            int imgx = (int)x-finalShip.getWidth()/2;
            int imgy = (int)y-finalShip.getHeight()/2;

            canvas.drawBitmap(finalShip, (int)imgx, (int)imgy, null);
            canvas.drawBitmap(finalShip, (int)imgx+screenWidth, (int)imgy, null);
            canvas.drawBitmap(finalShip, (int)imgx-screenWidth, (int)imgy, null);
            canvas.drawBitmap(finalShip, (int)imgx, (int)imgy+screenHeight, null);
            canvas.drawBitmap(finalShip, (int)imgx, (int)imgy-screenHeight, null);
        }
        public void update(JoyStick j)
        {
            degrees=j.getDegrees();
            matrix.setRotate(degrees);
            if(j.getPositionY()==0&&j.getPositionX()==0)
            {
                finalShip = Bitmap.createBitmap(ship2, 0, 0, ship1.getWidth(), ship1.getHeight(), matrix, true);
            }
            else
            {
                finalShip = Bitmap.createBitmap(ship1, 0, 0, ship1.getWidth(), ship1.getHeight(), matrix, true);
            }



           x = bind((x+(velocity*j.getPositionX())),screenWidth);
           y = bind((y+(velocity*j.getPositionY())),screenHeight);

        }

    public void setPosition(float x,float y) {
            this.x=x;
            this.y=y;
    }
    public int bind(double num,double bound)
    {
        if(num>bound)
            return (int)(num-bound);
        else if(num<0)
            return (int)(num+bound);
        else return (int)num;
    }
    public int getX()
    {
        return (int) x;
    }
    public int getY()
    {
        return (int) y;
    }
    public float getA()
    {
        return (float) degrees;
    }

}
