package com.ec327.asteroids;

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
import android.view.View;

import androidx.annotation.NonNull;
//This class is responsible for game rendering
// Surface view provides a dedicated drawing surface so we are using this to "render" our game
//Surface holder allows the code to interact with the Surface View/screen
public class Game extends SurfaceView implements SurfaceHolder.Callback {


    // Make a new thread for game computation

    private int w=Resources.getSystem().getDisplayMetrics().widthPixels;;
    private int h=Resources.getSystem().getDisplayMetrics().heightPixels;;
    //private Asteroid aster1,aster2;
    private Asteroid[] asteroids;
    private int size;//size of array
    private GameThread thread;
    private SpaceShip  ship = new SpaceShip(BitmapFactory.decodeResource(getResources(),R.drawable.shipon)
            ,BitmapFactory.decodeResource(getResources(),R.drawable.shippoff));
    private JoyStick joyStick = new JoyStick(200,(Resources.getSystem().getDisplayMetrics().heightPixels-200));
    private FireButton button = new FireButton((Resources.getSystem().getDisplayMetrics().widthPixels-200),
             (Resources.getSystem().getDisplayMetrics().heightPixels-200));
    private Laser shot = new Laser((Resources.getSystem().getDisplayMetrics().widthPixels+100),
            Resources.getSystem().getDisplayMetrics().heightPixels+100,
            0);
    Bitmap astr = BitmapFactory.decodeResource(getResources(),R.drawable.pixel_asteroid);
    Bitmap bg;
    public Game(Context context) {
        super(context);
        getHolder().addCallback(this);
        thread = new GameThread(getHolder(), this);
        //Add something about touch screen
        setFocusable(true);
    }
    //Update ships asteroids, other game objects
    public void update()
    {
        ship.update(joyStick);
        button.update();
        joyStick.update();
        if(button.getIsPressed()==true)
        {

            this.shot=new Laser(ship.getX(),ship.getY(),ship.getA());
        }
        shot.update();
        for(int i = 0;i<size;i++) {
            this.asteroids[i].update();
        }

    }
    public void initiate(){



            size = 3;
           this.asteroids = new Asteroid[size];
            asteroids[0] = new Asteroid(0,0,5,5,w,h,30,astr);
           asteroids[1] = new Asteroid(0,0,-20,7,w,h,40,astr);
            asteroids[2] = new Asteroid(0,0,16,-15,w,h,50,astr);
          bg =Bitmap.createScaledBitmap((BitmapFactory.decodeResource(getResources(),R.drawable.background_black)),w,h,false);



    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);


        canvas.drawBitmap(bg,0,0,null);//paint background
        joyStick.draw(canvas);
        button.draw(canvas);
        ship.draw(canvas);
        shot.draw(canvas);
        for(int i = 0;i<size;i++) {
           this.asteroids[i].draw(canvas);//paint each asteroid

        }
    }


    // Need to override these methods
    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        this.initiate();
        thread.startGame();

    }


    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
    {
        this.w=width;
        this.h=height;
        System.out.print(w);
        System.out.print(" ");
        System.out.println(h);
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder)
    {
        thread.endGame();

    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if(joyStick.isPressed(event.getX(), event.getY()))
                {
                    joyStick.setIsPressed(true);
                }
                else if(button.isPressed(event.getX(), event.getY()))
                {

                    button.setIsPressed(true);
                }
                return true;
            case MotionEvent.ACTION_MOVE:
                if(joyStick.getIsPressed())
                {
                    joyStick.setPosition(event.getX(), event.getY());
                }
                return true;
            case MotionEvent.ACTION_UP:
              joyStick.setIsPressed(false);
              button.setIsPressed(false);
              joyStick.resetPosition();
                return true;
        }
        return super.onTouchEvent(event);
    }
}
