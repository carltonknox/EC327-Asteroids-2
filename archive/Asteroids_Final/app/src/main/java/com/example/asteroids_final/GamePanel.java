package com.example.asteroids_final;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
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
    private long time;
    private final int initialAsteroids = 1;

    private int jstickP=0;
    private int bttnP=1;

    private int scaleSpeed;
    private int kills;

    private ArrayList<Laser> lasers;

    boolean collide = false;
    Rect r = new Rect();

    Bitmap astr = BitmapFactory.decodeResource(getResources(),R.drawable.pixel_asteroid);
    Bitmap astr2 = BitmapFactory.decodeResource(getResources(),R.drawable.asteroid_grey);
    Bitmap shp = BitmapFactory.decodeResource(getResources(),R.drawable.pixel_ship_red);
    Bitmap shp2;
    Bitmap bg;

    Random rand = new Random();

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
        scaleSpeed=1;
        kills = 0;
        time = System.currentTimeMillis();

        stick = new JoyStick(w/2-300,(h-200));
        button = new FireButton(w/2+300,h-200);

        asterList = new ArrayList<Asteroid>();

        //Generate random asteroids
        for(int i = 0;i<initialAsteroids;i++){
            asterList.add(generateRandomAsteroid());
        }

        lasers = new ArrayList<Laser>();

        bg = Bitmap.createScaledBitmap((BitmapFactory.decodeResource(getResources(),R.drawable.background_black)),w,h,false);
        shp2 = Bitmap.createScaledBitmap(shp,shp.getWidth()*2/3,shp.getHeight()*2/3,false);

        ship = new SpaceShip(shp2);

    }
    public void newGame()
    {
        lasers.clear();
        asterList.clear();

        //asterList = new ArrayList<Asteroid>();

        for(int i = 0;i<initialAsteroids;i++){
            asterList.add(generateRandomAsteroid());
        }

        //lasers = new ArrayList<Laser>();
    }

    public Asteroid generateRandomAsteroid(){


        int spawnRadius = 500;//radius to spawn asteroids
        int newX = 0;
        int newY = 0;
        while((newX<=spawnRadius)&&newY<=spawnRadius) {
            newX = rand.nextInt(w);
            newY = rand.nextInt(h);
        }

        int newDX = (rand.nextInt(41)-20)*scaleSpeed;
        int newDY = (rand.nextInt(41)-20)*scaleSpeed;
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
                if (!collide) {
                    if(stick.isPressed(event.getX(), event.getY()))
                    {
                        stick.setIsPressed(true);
                    }
                    else if(button.isPressed(event.getX(), event.getY()))
                    {

                        button.setIsPressed(true);
                    }
                }
                else {
                    newGame();
                    collide = false;
                }
                return true;
            case MotionEvent.ACTION_MOVE:
                if(!collide && stick.getIsPressed())
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
        if  (!collide) {
            ship.update(stick);
            button.update();
            stick.update();
            if (button.getIsPressed() && (System.currentTimeMillis() - time) > 500) {
                this.lasers.add(new Laser(ship.getX(), ship.getY(), ship.getA()));
                time = System.currentTimeMillis();
            }
            for (int i = 0; i < asterList.size(); i++) {
                this.asterList.get(i).update();
                this.asterList.get(i).updateHitBox();
                if (asterList.get(i).collision(ship)) {
                    collide = true;
                    break;
                }
            }
            for (int i = 0; i < lasers.size(); i++) {
                lasers.get(i).update();
                if (!lasers.get(i).isInBounds())
                    lasers.remove(i--);
            }
            for (int i = 0; i < asterList.size(); i++) {
                for (int j = 0; j < lasers.size(); j++) {
                    if (asterList.get(i).collision(lasers.get(j))) {
                        lasers.remove(j--);
                        asterList.remove(i--);
                        kills++;
                    }
                }
            }
            if (kills >= 10) {
                scaleSpeed++;
                kills = 0;
                //score
            }
            //has as 1/100 chance of generating an asteroid each loop
            if (rand.nextInt(100) == 1 && asterList.size() < 4)
                asterList.add(generateRandomAsteroid());
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

        if (collide){
            Paint paint = new Paint();
            paint.setTextSize(100);
            paint.setColor(Color.MAGENTA);
            drawCenterText(canvas, paint, "Game Over");
        }
    }
    private void drawCenterText(Canvas canvas, Paint paint, String text) {
        paint.setTextAlign(Paint.Align.LEFT);
        canvas.getClipBounds(r);
        int cHeight = r.height();
        int cWidth = r.width();
        paint.getTextBounds(text, 0, text.length(), r);
        float x = cWidth / 2f - r.width() / 2f - r.left;
        float y = cHeight / 2f + r.height() / 2f - r.bottom;
        canvas.drawText(text, x, y, paint);
    }
}