package com.example.asteroids_final;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback{

    private MainThread thread;
    protected int w,h;
    protected ArrayList<Asteroid> asterList;
    protected SpaceShip ship;
    protected JoyStick stick;
    private FireButton button;

    private ArrayList<Laser> lasers;

    Bitmap astr = BitmapFactory.decodeResource(getResources(),R.drawable.pixel_asteroid);
    Bitmap astr2 = BitmapFactory.decodeResource(getResources(),R.drawable.asteroid_grey);
    Bitmap shp = BitmapFactory.decodeResource(getResources(),R.drawable.pixel_ship_red);
    Bitmap shp2;
    Bitmap bg;

    public GamePanel(Context context)
    {
        super(context);

        getHolder().addCallback(this);

        thread = new MainThread(getHolder(), this);

        setFocusable(true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
    {
        this.w=width;
        this.h=height;
        System.out.print(w);
        System.out.print(" ");
        System.out.println(h);
    }


    public void initiate()
    {
        stick = new JoyStick(w/2-300,(h-200));
        button = new FireButton(w/2+300,h-200);

        asterList = new ArrayList<Asteroid>();

        //Generate random asteroids
        for(int i = 0;i<3;i++){
            asterList.add(generateRandomAsteroid());
        }

        lasers = new ArrayList<Laser>();

        bg = Bitmap.createScaledBitmap((BitmapFactory.decodeResource(getResources(),R.drawable.background_black)),w,h,false);
        shp2 = Bitmap.createScaledBitmap(shp,shp.getWidth()*2/3,shp.getHeight()*2/3,false);

        ship = new SpaceShip(shp2);

    }

    public Asteroid generateRandomAsteroid(){
        Random rand = new Random();

        int spawnRadius = 500;//radius to spawn asteroids
        int newX = 0;
        int newY = 0;
        while((newX<=spawnRadius)&&newY<=spawnRadius) {
            newX = rand.nextInt(w);
            newY = rand.nextInt(h);
        }

        int newDX = rand.nextInt(41)-20;
        int newDY = rand.nextInt(41)-20;
        int newSize = rand.nextInt(76)+75;
        return new Asteroid(newX+w/2,newY+h/2,newDX,newDY,newSize,astr);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder)
    {
        thread = new MainThread(getHolder(),this);

        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder)
    {
        boolean retry = true;
        while(retry)//replace with retry?
        {
            try {
                thread.setRunning(false);
                thread.join();
            }catch (Exception e) { e.printStackTrace();}
            retry=false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if(stick.isPressed(event.getX(), event.getY()))
                {
                    stick.setIsPressed(true);
                }
                else if(button.isPressed(event.getX(), event.getY()))
                {

                    button.setIsPressed(true);
                }
                return true;
            case MotionEvent.ACTION_MOVE:
                if(stick.getIsPressed())
                {
                    stick.setPosition(event.getX(), event.getY());
                }
                return true;
            case MotionEvent.ACTION_UP:
                stick.setIsPressed(false);
                button.setIsPressed(false);
                stick.resetPosition();
                return true;
        }
        return super.onTouchEvent(event);
    }

    public void update() {
        ship.update(stick);
        button.update();
        stick.update();
        if(button.getIsPressed()==true)
            this.lasers.add(new Laser(ship.getX(),ship.getY(),ship.getA()));
        for(int i = 0;i<asterList.size();i++) {
            this.asterList.get(i).update();
        }
        for(int i = 0;i<lasers.size();i++){
            lasers.get(i).update();
            if(!lasers.get(i).isInBounds())
                lasers.remove(i--);
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawBitmap(bg,0,0,null);//paint background

        ship.draw(canvas);

        for(int i = 0;i<asterList.size();i++) {
            this.asterList.get(i).draw(canvas);//paint each asteroid
        }
        for(int i = 0;i<lasers.size();i++){
            lasers.get(i).draw(canvas);
        }
        stick.draw(canvas);
        button.draw(canvas);
    }
}
