package com.example.asteroids_final;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

public class Circle {
    private int radius;
    private Point point;
    private int color;

    public Circle(){
        this.radius = 1;
        this.point = new Point();
        this.color = Color.MAGENTA;
    }

    public Circle (Point point, int radius, int color){
        this.point = point;
        this.radius = radius;
        this.color = color;
    }

    public double getRadius() {
        return radius;
    }

    public Point getPoint() {
        return  point;
    }

    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(color);
        canvas.drawCircle(point.x, point.y, radius, paint);
    }

    public void set(Point point, int radius, int color){
        this.point = point;
        this.radius = radius;
        this.color = color;
    }

    public boolean intersects(Circle circle){
        int dr = (int) Math.sqrt((double)(Math.pow(this.getPoint().x - circle.getPoint().x,2)) +
                (double)(Math.pow(this.getPoint().y - circle.getPoint().y,2)));
        return dr <= (this.radius + circle.radius);
    }

    public boolean intersects(Point point){
        int dr = (int) (int) Math.sqrt((double)(Math.pow(this.getPoint().x - point.x,2)) +
                (double)(Math.pow(this.getPoint().y - point.y,2)));
        return dr <= (this.radius);
    }
}
