package SourceFiles;

abstract public class MovingObject extends GameObject{
    
    //private int VerticalSpeed, HorizontalSpeed;
    private int Speed, Angle;
    private int LeftBound, RightBound, UpBound, DownBound;
    
    public MovingObject(int x, int y, int leftbound, int rightbound, int upbound, 
            int downbound, int width, int height, int speed, int angle)
    {
        super(x, y, width, height);
        this.Speed = speed;
        this.Angle = angle;
        this.LeftBound = leftbound;
        this.RightBound = rightbound;
        this.DownBound = downbound;
        this.UpBound = upbound;
    }
    
    public int getAngle()
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
