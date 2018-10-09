package SourceFiles.GameObjects.StationaryObjects;

import java.awt.Image;

public class Coin extends StationaryObject{
    private int Worth;
    
    public Coin(int x, int y, int val, Image img)
    {
        super(x, y, img);
        this.Worth = val;
    }
    
    public int getValue()
    {
        return this.Worth;
    }
}
