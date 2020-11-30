package com.example.asteroids_engine_test;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback{
    private MainThread thread;

    public int x;

    public int w,h;
    private Asteroid aster1,aster2;
    private Asteroid[] asteroids;
    private int size;//size of array
    Bitmap img = BitmapFactory.decodeResource(getResources(),R.drawable.pixel_asteroid);
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
        size = 3;
        this.asteroids = new Asteroid[size];
        asteroids[0] = new Asteroid(0,0,5,5,w,h,100,img);
        asteroids[1] = new Asteroid(0,0,-20,7,w,h,150,img);
        asteroids[2] = new Asteroid(0,0,16,-15,w,h,130,img);

        bg = Bitmap.createScaledBitmap((BitmapFactory.decodeResource(getResources(),R.drawable.background_black)),w,h,false);

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
        return super.onTouchEvent(event);
    }

    public void update() {

        for(int i = 0;i<size;i++)
            this.asteroids[i].update();
        //aster1.update();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        canvas.drawBitmap(bg,0,0,null);

        for(int i = 0;i<size;i++)
            this.asteroids[i].draw(canvas);
        //aster1.draw(canvas);
    }
}
