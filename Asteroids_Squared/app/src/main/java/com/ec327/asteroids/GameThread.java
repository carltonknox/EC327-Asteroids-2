package com.ec327.asteroids;

import android.graphics.Canvas;
import android.view.SurfaceHolder;
//This thread class serves as the game loop.
public class GameThread extends Thread
{
    private boolean isRunning;
    public static Canvas canvas;
    private Game game;
    private SurfaceHolder surfaceholder;

    public GameThread(SurfaceHolder surfaceholder, Game game)
    {
        super();
        this.surfaceholder = surfaceholder;
        this.game=game;

    }


    public void startGame()
    {
        isRunning=true;
        start();
        //this.game.initiate();


    }
    public void endGame()
    {

        boolean succesful = false;
        while (!succesful) {
            try {
                isRunning=false;
                join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            succesful = true;
        }

    }
    //Had to implement a try catch block as the app would crash if for some reason a different
    //thread or instance had locked the canvas, this unblocks it so that this thread can use it
    //Need to lock canvas in order to draw on it
    @Override
    public void run()
    {
        super.run();
        while (isRunning)
        {
            canvas = null;
            try
            {
                canvas = this.surfaceholder.lockCanvas();
                //only one thread can access my surfaceholder with synchronized keyword.
                synchronized(surfaceholder)
                {
                    this.game.update();
                    this.game.draw(canvas);
                }
            }
            catch (Exception e)
            {}
            finally
            {
                if (canvas != null)
                {
                    //Unlocks canvas if locked
                    try
                    {
                        surfaceholder.unlockCanvasAndPost(canvas);
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}
