package SourceFiles;

abstract public class MovingObject extends GameObject{
    
    private int verticalSpeed, horizontalSpeed;
    
    public MovingObject(int x, int y, int width, int height, int horiSpeed,
            int vertSpeed)
    {
        super(x, y, width, height);
        this.horizontalSpeed = horiSpeed;
        this.verticalSpeed = vertSpeed;
    }
    
    public int getVerticalSpeed()
    {
        return this.verticalSpeed;
    }
    
    public int getHorizontalSpeed()
    {
        return this.horizontalSpeed;
    }
    
    public void setVerticalSpeed(int y)
    {
        this.verticalSpeed = y;
    }
    
    public void setHorizontalSpeed(int x)
    {
        this.horizontalSpeed = x;
    }
}
