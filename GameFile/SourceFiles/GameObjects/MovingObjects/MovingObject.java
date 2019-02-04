package SourceFiles.GameObjects.MovingObjects;

import SourceFiles.GameObjects.GameObject;

abstract public class MovingObject extends GameObject{
    /*
    MovingObject is the parent class for moving objects, ie characters/projectiles.
    It uses speed/angle compenets to determine the direction the object is moving
    */
    private int Speed;
    private double Angle;
    
    public MovingObject(int x, int y, int width, int height, int speed, double angle)
    {
        super(x, y, width, height);
        this.Speed = speed;
        this.Angle = angle;
    }
    
    public double getAngle()
    {
        return this.Angle;
    }
    
    public int getSpeed()
    {
        return this.Speed;
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
