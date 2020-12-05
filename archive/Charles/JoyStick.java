package com.example.myapplication;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

public class JoyStick implements GameObject{
    private RectPlayer player; // bind joystick to player

    private Point joyStickPoint = new Point(); // changing center of joystick
    private Point center; // center of base

    private float baseRadius; // base radius
    private float hatRadius; // hat radius

    private int baseColor; // base color
    private int hatColor; // base radius


    public JoyStick(RectPlayer player, Point center, float baseRadius, float hatRadius, int baseColor, int hatColor){
        this.player = player;
        this.center = center;
        this.baseRadius = baseRadius;
        this.hatRadius = hatRadius;
        this.baseColor = baseColor;
        this.hatColor = hatColor;

    }

    @Override
    public void update(){

    }

    public void update(Point point){
//        double norm = Math.sqrt(Math.pow((joyStickPoint.x-center.x),2) + Math.pow((joyStickPoint.y-center.y),2));
//        if ((int)norm < baseRadius)
            joyStickPoint.set(point.x, point.y);
//        else
//            joyStickPoint.set((int)(center.x + (point.x-center.x)*baseRadius/norm), (int)(center.y + (point.y-center.y)*baseRadius/norm));
    }

    @Override
    public void draw(Canvas canvas){
        Paint paint = new Paint();
        paint.setColor(baseColor);
        canvas.drawCircle(center.x, center.y, baseRadius, paint);
        paint.setColor(hatColor);
        canvas.drawCircle(joyStickPoint.x, joyStickPoint.y, hatRadius, paint);
    }

}
