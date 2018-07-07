package SourceFiles;

abstract public class MovingObject extends GameObject{
    
    //private int VerticalSpeed, HorizontalSpeed;
    private int Speed, Angle;
    
    public MovingObject(int x, int y, int width, int height, int speed, int angle)
    {
        super(x, y, width, height);
        this.Speed = speed;
        this.Angle = angle;
    }
    
    public int getAngle()
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
    
    public void setHorizontalSpeed(int i)
    {
        this.Speed = i;
    }
}
