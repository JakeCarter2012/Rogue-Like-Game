package SourceFiles;

abstract public class MovingObject extends GameObject{
    
    private int VerticalSpeed, HorizontalSpeed;
    
    public MovingObject(int x, int y, int width, int height, int horiSpeed,
            int vertSpeed)
    {
        super(x, y, width, height);
        this.HorizontalSpeed = horiSpeed;
        this.VerticalSpeed = vertSpeed;
    }
    
    public int getVerticalSpeed()
    {
        return this.VerticalSpeed;
    }
    
    public int getHorizontalSpeed()
    {
        return this.HorizontalSpeed;
    }
    
    public void setVerticalSpeed(int y)
    {
        this.VerticalSpeed = y;
    }
    
    public void setHorizontalSpeed(int x)
    {
        this.HorizontalSpeed = x;
    }
}
