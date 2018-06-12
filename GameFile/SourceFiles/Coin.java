package SourceFiles;

import java.awt.Image;

public class Coin extends StationaryObject{
    private int worth;
    
    public Coin(int x, int y, int val, Image img)
    {
        super(x, y, img);
        this.worth = val;
    }
    
    public int getValue()
    {
        return this.worth;
    }
}
