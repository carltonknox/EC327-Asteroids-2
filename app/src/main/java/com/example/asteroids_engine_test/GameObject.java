/*
GameObject class defines an object with a draw and update function. Any game object can be updated
 on the screen.
 */
package com.example.asteroids_engine_test;

import android.graphics.Canvas;

public interface GameObject {
    void draw(Canvas Canvas);
    void update();
}