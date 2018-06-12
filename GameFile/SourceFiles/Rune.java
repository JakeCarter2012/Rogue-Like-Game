package SourceFiles;

import java.awt.Image;

public class Rune extends StationaryObject{
    private int runeNumber;
    
    public Rune(int x, int y, int buff, Image img)
    {
        super(x, y, img);
        this.runeNumber = buff;
    }
    
    public int getRune()
    {
        return this.runeNumber;
    }
    
}
