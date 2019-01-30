package SourceFiles.GameObjects.MovingObjects;

import SourceFiles.GameObjects.GameObject;

abstract public class MovingObject extends GameObject{
    /*
    MovingObject is the parent class for moving objects, ie characters/projectiles.
    It uses speed/angle compenets to determine the direction the object is moving
    */
    private int Speed;
    private double Angle;
    //the "Bounds" are used incase an object moves outside of the game's boundaries;
    private int LeftBound, RightBound, UpBound, DownBound;
    
    public MovingObject(int x, int y, int leftbound, int rightbound, int upbound, 
            int downbound, int width, int height, int speed, double angle)
    {
        super(x, y, width, height);
        this.Speed = speed;
        this.Angle = angle;
        this.LeftBound = leftbound;
        this.RightBound = rightbound;
        this.DownBound = downbound;
        this.UpBound = upbound;
    }
    
    public double getAngle()
    {
        return this.Angle;
    }
    
    public int getSpeed()
    {
        return this.Speed;
    }
    
    public int getLeftBound()
    {
        return this.LeftBound;
    }
    
    public int getRightBound()
    {
        return this.RightBound;
    }
    
    public int getUpBound()
    {
        return this.UpBound;
    }
    
    public int getDownBound()
    {
        return this.DownBound;
    }
    
    public void setAngle(int i)
    {
        this.Angle = i;
    }
    
    public void setSpeed(int i)
    {
        this.Speed = i;
    }
}
