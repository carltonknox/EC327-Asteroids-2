package com.example.myapplication;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;

public class GameplayScene implements Scene {
    //private SceneManager manager;

    private RectPlayer player;
    private Point playerPoint;

    private JoyStick joyStick;
    private Point center;
    private Point joyStickPoint;

    private ObstacleManager obstacleManager;

    private boolean movingPlayer = false;
    private boolean movingStick = false;

    private boolean gameOver = false;
    private long gameOverTime;

    private Rect r = new Rect();

    public GameplayScene(){
        player = new RectPlayer(new Rect(100, 100, 200, 200), Color.rgb(255,0,0));
        playerPoint = new Point(Constants.SCREEN_WIDTH/2, 2*Constants.SCREEN_HEIGHT/3);
        player.update(playerPoint);

        center = new Point((int) Constants.SCREEN_WIDTH/2, (int) 7*Constants.SCREEN_HEIGHT/8);
        joyStickPoint = new Point(center);
        joyStick = new JoyStick(player, center, 70, 20, Color.rgb(0,0,255), Color.rgb(0,255,0));
        joyStick.update(joyStickPoint);

        obstacleManager = new ObstacleManager(200, 350, 75, Color.rgb(0,255,0));
    }

    public void reset(){
        playerPoint = new Point(Constants.SCREEN_WIDTH/2, 2*Constants.SCREEN_HEIGHT/3);
        player.update(playerPoint);

        joyStickPoint = new Point(center);
        joyStick = new JoyStick(player, center, 70, 20, Color.rgb(0,0,255), Color.rgb(0,255,0));
        joyStick.update(joyStickPoint);

        obstacleManager = new ObstacleManager(200, 350, 75, Color.rgb(0,255,0));
        movingPlayer = false;
        movingStick = false;

    }

    @Override
    public void terminate(){
        SceneManager.ACTIVE_SCENE = 0;
    }

    @Override
    public void receiveTouch(MotionEvent event){

            float norm;

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (!gameOver && player.getRectangle().contains((int) event.getX(), (int) event.getY()))
//                        movingPlayer = true;
                        movingStick = true;
                    if (gameOver && System.currentTimeMillis() - gameOverTime >= 2000) {
                        reset();
                        gameOver = false;
                    }
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (!gameOver && movingStick)
                        norm = (float) Math.sqrt(Math.pow((joyStickPoint.x-center.x),2) + Math.pow((joyStickPoint.y-center.y),2));
                        if (event.getY() <= 3*Constants.SCREEN_HEIGHT/4 && (((event.getX()-center.x) * (event.getX()-center.x))+ ((event.getY()-center.y) * (event.getY()-center.y)) <= 70*70))
                            playerPoint.set((int) (event.getX() - center.x + player.getRectangle().centerX()),
                                    (int) (event.getY() - center.y + player.getRectangle().centerY()));
                        else
                            playerPoint.set((int)(event.getX() - center.x + player.getRectangle().centerX()),
                                    (int) 3*Constants.SCREEN_HEIGHT/4);

                    break;
                case MotionEvent.ACTION_UP:
//                    movingPlayer = false;
                    movingStick = false;
                    break;
            }
//        float norm = (float)(Math.sqrt(Math.pow((joyStickPoint.x-center.x),2) + Math.pow((joyStickPoint.y-center.y),2)));
//
//        switch (event.getAction()) {
//
//            case MotionEvent.ACTION_DOWN:
//                if (!gameOver && (((event.getX()-center.x) * (event.getX()-center.x))+ ((event.getY()-center.y) * (event.getY()-center.y)) <= 70*70))
//                    movingStick = true;
//                if (gameOver && System.currentTimeMillis() - gameOverTime >= 2000) {
//                    reset();
//                    gameOver = false;
//                }
//                break;
//            case MotionEvent.ACTION_MOVE:
//                if (!gameOver && movingStick)
//                    if(((event.getX()-center.x) * (event.getX()-center.x))+ ((event.getY()-center.y) * (event.getY()-center.y)) <= 70*70)
//                        joyStickPoint.set((int) event.getX(), (int) event.getY());
//                    else

//                        joyStickPoint.set((int)(center.x + (event.getX()-center.x)*70/norm), (int)(center.y + (event.getY()-center.y)*70/norm));
//                break;
//            case MotionEvent.ACTION_UP:
//                movingStick = false;
//                break;
//        }
//
    }

    @Override
    public void draw(Canvas canvas){
        canvas.drawColor(Color.WHITE);

        player.draw(canvas);
        joyStick.draw(canvas);
        obstacleManager.draw(canvas);


        if (gameOver){
            canvas.drawColor(Color.BLACK);
            Paint paint = new Paint();
            paint.setTextSize(100);
            paint.setColor(Color.MAGENTA);
            drawCenterText(canvas, paint, "Game Over", 0, 0);
            drawCenterText(canvas, paint, "Score: " + obstacleManager.score, 0,100);

        }
    }

    @Override
    public void update(){
        if (!gameOver) {
            player.update(playerPoint);

            joyStick.update(joyStickPoint);

            obstacleManager.update();

            if(obstacleManager.playerCollide(player)){
                gameOver = true;
                gameOverTime = System.currentTimeMillis();
            }
        }
    }

    private void drawCenterText(Canvas canvas, Paint paint, String text, float dx, float dy){
        paint.setTextAlign(Paint.Align.LEFT);
        canvas.getClipBounds(r);
        int cHeight = r.height();
        int cWidth = r.width();
        paint.getTextBounds(text, 0, text.length(), r);
        float x = cWidth / 2f - r.width() / 2f - r.left + dx;
        float y = cHeight / 2f + r.height() / 2f - r.bottom + dy;
        canvas.drawText(text, x, y, paint);

    }
}

