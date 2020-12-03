package com.example.asteroids_final;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;

public class SpaceShip extends SpaceObject {
    // to determine how fast we want the ship to move;
    private final int velocity = 30;
    //we want ship to start in middle.
    private final static int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    private final static int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
    private Bitmap ship1;
    private Bitmap ship2;
    private Bitmap finalShip;
    private float degrees;//rotation degree
    private Matrix matrix = new Matrix();

    public SpaceShip(Bitmap ship) {
        super(screenWidth / 2, screenHeight / 2, 0, 0);
        this.img = ship;
        ship1=ship;
        ship2=ship;
    }

    public SpaceShip(Bitmap shipon, Bitmap shipoff) {
        super(screenWidth / 2, screenHeight / 2, 0, 0);
        //scales my ships to an appropriate size;
        Bitmap example = Bitmap.createScaledBitmap(shipon, 180, 180, true);
        Bitmap example2 = Bitmap.createScaledBitmap(shipoff, 180, 180, true);
        this.ship1 = example;
        this.ship2 = example2;
    }

    public void update(JoyStick j) {
        this.dx = (int) (velocity * j.getPositionX());
        this.dy = (int) (velocity * j.getPositionY());
        this.x = bind((this.x + this.dx), xMax);
        this.y = bind((this.y + this.dy), yMax);

        degrees = j.getDegrees();
        matrix.setRotate(degrees);
        if (j.getPositionY() == 0 && j.getPositionX() == 0) {
            img = Bitmap.createBitmap(ship2, 0, 0, ship1.getWidth(), ship1.getHeight(), matrix, true);
        } else {
            img = Bitmap.createBitmap(ship1, 0, 0, ship1.getWidth(), ship1.getHeight(), matrix, true);
        }
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return (int) x;
    }

    public int getY() {
        return (int) y;
    }

    public float getA() {
        return (float) degrees;
    }
}