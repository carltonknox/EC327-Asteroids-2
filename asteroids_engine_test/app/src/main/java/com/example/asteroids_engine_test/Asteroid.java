/*
 * Asteroid object class is derived from SpaceObject
 * Asteroids can move like spaceObjects, but also have a size value,
 * which should be helpful for collision and drawing the asteroid
 */

package com.example.asteroids_engine_test;

public class Asteroid extends SpaceObject {
    protected int size;
    Asteroid(int x,int y,int dx,int dy,int xMax, int yMax, int size)
    {
        super(x,y,dx,dy,xMax,yMax);
        this.size=size;
    }

}
