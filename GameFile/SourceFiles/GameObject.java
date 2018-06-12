package SourceFiles;

abstract public class GameObject{
    private int x, y;
    private int width, height;
    
    public GameObject(int x, int y, int width, int height)
    {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    
    public int getX()
    {
        return this.x;
    }
    
    public int getY()
    {
        return this.y;
    }
    
    public int getCenterX()
    {
        return this.x + this.width * 1/2;
    }
    
    public int getCenterY()
    {
        return this.y + this.height * 1/2;
    }
    
    public int getWidth()
    {
        return this.width;
    }
    
    public int getHeight()
    {
        return this.height;
    }
}
