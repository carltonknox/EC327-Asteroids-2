package com.example.asteroids_engine_test;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.AudioManager;
import android.media.SoundPool;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Random;

import static java.lang.Math.abs;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback{

    public boolean shoke=false;
    private MainThread thread;
    private GamePanelActivity gamePanelActivity;
    protected int w,h;
    protected ArrayList<Asteroid> asterList;
    protected SpaceShip ship;
    protected JoyStick stick;
    private FireButton button;
    private long time;
    private final int initialAsteroids = 3;
    private final int spawnRadius = 350;//radius to spawn asteroids

    private int jstickP=0;
    private int bttnP=1;

    private int scaleSpeed;
    private int kills;
    private int score;
    private int move;

    private ArrayList<Laser> lasers;

    boolean collide = false;
    boolean ready=false;
    Rect r = new Rect();

    Bitmap astr = BitmapFactory.decodeResource(getResources(),R.drawable.pixel_asteroid);
    Bitmap astr2 = BitmapFactory.decodeResource(getResources(),R.drawable.asteroid_grey);
    Bitmap shp = BitmapFactory.decodeResource(getResources(),R.drawable.shipon);
    Bitmap shp2= BitmapFactory.decodeResource(getResources(),R.drawable.shippoff);
    Bitmap bg;
    boolean change_bg;

    SoundPool soundPool = new SoundPool(2, AudioManager.STREAM_MUSIC,0);
    private int pew_sound = soundPool.load(getContext(),R.raw.pew,1);
    private int hit_sound = soundPool.load(getContext(),R.raw.hit_02,1);
    private int boom_sound = soundPool.load(getContext(),R.raw.boom,1);

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
        change_bg = false;
        scaleSpeed=1;
        kills = 0;
        score = 0;
        move=0;
        time = System.currentTimeMillis();

        stick = new JoyStick(w/2-300,(h-200));
        button = new FireButton(w/2+300,h-200);

        asterList = new ArrayList<Asteroid>();

        //Generate random asteroids
        for(int i = 0;i<initialAsteroids;i++){
            asterList.add(generateRandomAsteroid(w/2,h/2));
        }

        lasers = new ArrayList<Laser>();

        bg = Bitmap.createScaledBitmap((BitmapFactory.decodeResource(getResources(),R.drawable.background_black)),w,h,false);


        ship = new SpaceShip(shp,shp2);

    }
    public void newGame()
    {
        bg = Bitmap.createScaledBitmap((BitmapFactory.decodeResource(getResources(),R.drawable.background_black)),w,h,false);
        change_bg = false;
        kills=0;
        score=0;
        move=0;
        scaleSpeed=1;
        time = System.currentTimeMillis();
        lasers.clear();
        asterList.clear();
        ship = new SpaceShip(shp,shp2);
        ready=false;

        //asterList = new ArrayList<Asteroid>();

        for(int i = 0;i<initialAsteroids;i++){
            asterList.add(generateRandomAsteroid(w/2,h/2));
        }

        //lasers = new ArrayList<Laser>();
    }

    public Asteroid generateRandomAsteroid(int shipX,int shipY){
        int newX = 0;
        int newY = 0;
        while((abs(newX-shipX)<=spawnRadius)&&abs(newY-shipY)<=spawnRadius) {
            newX = rand.nextInt(w);
            newY = rand.nextInt(h);
        }
        System.out.print("shipX: ");
        System.out.println(shipX);
        System.out.print("newX: ");
        System.out.println(newX);
        System.out.print("X: ");

        int newDX = (int)((rand.nextInt(41)-20)*scaleSpeed);
        int newDY = (int)((rand.nextInt(41)-20)*scaleSpeed);

        if(newDX==0) newDX++;
        if(newDY==0) newDY++;
        int newSize = rand.nextInt(71)+70;
        return new Asteroid(newX,newY,newDX,newDY,newSize,astr);
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
    int counter = 0;

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        switch(event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                if (!collide) {
                    if (stick.getIsPressed())
                    {
                        if(button.isPressed(event.getX(), event.getY()))
                        {
                            bttnP=event.getPointerId(event.getActionIndex());
                            button.setIsPressed(true);
                        }

                    }
                    else if(stick.isPressed(event.getX(), event.getY()))
                    {
                        jstickP=event.getPointerId(event.getActionIndex());
                        stick.setIsPressed(true);
                    }
                    else if(button.isPressed(event.getX(), event.getY()))
                    {
                        bttnP=event.getPointerId(event.getActionIndex());

                        button.setIsPressed(true);
                    }
                }
                else if (collide && (counter == 0)){
                    counter++;
                    gamePanelActivity = new GamePanelActivity(score);
                    gamePanelActivity.gameOverState(getContext());
                }
                else if (collide && (counter!=0)){
                    counter = 0;
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
            case MotionEvent.ACTION_POINTER_UP:

                if(jstickP==event.getPointerId(event.getActionIndex()))
                {
                    stick.setIsPressed(false);
                    stick.resetPosition();
                }
                if(bttnP==event.getPointerId(event.getActionIndex()))
                {
                    button.setIsPressed(false);
                }

                return true;
        }
        return super.onTouchEvent(event);
    }


    public void update() {
        if  (!collide) {
            ship.update(stick);
            if (move>3)
            {
                move=0;
                ready=true;
            }
            if(shoke)
            {
                System.out.println("Shoken");

            }
            button.update();
            stick.update();
            if(shoke&&ready)
            {
                asterList.clear();
                shoke=false;
                ready=false;
                move=0;
            }
            if (button.getIsPressed() && (System.currentTimeMillis() - time) > 300) {
                this.lasers.add(new Laser(ship.getX(), ship.getY(), ship.getA()));
                soundPool.play(pew_sound,1.0f,1.0f,1,0,1.0f);
                time = System.currentTimeMillis();
            }
            for (int i = 0; i < asterList.size(); i++) {
                this.asterList.get(i).update();
                this.asterList.get(i).updateHitBox();
                if (asterList.get(i).collision(ship)) {
                    soundPool.play(boom_sound,1.0f,1.0f,1,0,1.0f);
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
                        soundPool.play(hit_sound,1.0f,1.0f,1,0,1.0f);
                        kills++;
                        score++;
                        move++;
                    }
                }
            }
            if (kills >= 5) {
                scaleSpeed+=0.2;
                kills = 0;
            }
            //has as 1/100 chance of generating an asteroid each loop
            if (rand.nextInt(90) == 1 && asterList.size() < 6)
                asterList.add(generateRandomAsteroid(ship.getX(),ship.getY()));
            if(change_bg)
                switch(score) {
                    case 5:
                        bg = Bitmap.createScaledBitmap((BitmapFactory.decodeResource(getResources(),R.drawable.background_purple)),w,h,false);
                        break;
                    case 10:
                        bg = Bitmap.createScaledBitmap((BitmapFactory.decodeResource(getResources(),R.drawable.background_blue)),w,h,false);
                        break;
                    case 15:
                        bg = Bitmap.createScaledBitmap((BitmapFactory.decodeResource(getResources(),R.drawable.background_red)),w,h,false);
                        break;
                }
            if(score%5==0)
                change_bg = false;
            else if(score%5==1) change_bg = true;
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawBitmap(bg,0,0,null);//paint background

        ship.draw(canvas);
        if(ready)
        {
            ship.drawSpecial(canvas);
        }
        //ship.getHitBox().draw(canvas);

        for(int i = 0;i<asterList.size();i++) {
            this.asterList.get(i).draw(canvas);//paint each asteroid
        }
        for(int i = 0;i<lasers.size();i++){
            lasers.get(i).draw(canvas);
        }
        stick.draw(canvas);
        button.draw(canvas);

        if(collide && (counter == 0)){
            Paint paint = new Paint();
            paint.setTextSize(50);
            paint.setColor(Color.WHITE);
            drawCenterText(canvas, paint, "Game over! Press the screen");
        }
        else if(collide && (counter!=0)) {
            Paint paint = new Paint();
            paint.setTextSize(50);
            paint.setColor(Color.WHITE);
            drawCenterText(canvas, paint, "Press the screen for new game");
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