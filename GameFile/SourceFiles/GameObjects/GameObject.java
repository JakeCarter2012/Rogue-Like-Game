package SourceFiles.GameObjects;

abstract public class GameObject{
    /*
    GameObject is the parent object of all objects in the game; it contains
    the accesors/setters that all objects have in common
    */
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
    
    public void setX(int x)
    {
        this.x = x;
    }
    
    public void setY(int y)
    {
        this.y = y;
    }
    
    public int getCenterX()
    {
        return this.x + this.width/2;
    }
    
    public int getCenterY()
    {
        return this.y + this.height/2;
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
