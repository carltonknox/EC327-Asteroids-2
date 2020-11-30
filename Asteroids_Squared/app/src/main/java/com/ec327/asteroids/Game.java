package com.ec327.asteroids;

import android.content.Context;
import android.content.res.Resources;
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
    private GameThread thread;
    private SpaceShip  ship = new SpaceShip(BitmapFactory.decodeResource(getResources(),R.drawable.ship1));;
    private JoyStick joyStick = new JoyStick(200,(Resources.getSystem().getDisplayMetrics().heightPixels-200));
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
        joyStick.update();

    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        joyStick.draw(canvas);
        ship.draw(canvas);
    }

    // Need to override these methods
    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        thread.startGame();

    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

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
                return true;
            case MotionEvent.ACTION_MOVE:
                if(joyStick.getIsPressed())
                {
                    joyStick.setPosition(event.getX(), event.getY());
                }
                return true;
            case MotionEvent.ACTION_UP:
              joyStick.setIsPressed(false);
              joyStick.resetPosition();
                return true;
        }
        return super.onTouchEvent(event);
    }
}
