package SourceFiles;

import java.awt.Image;

public class Rune extends StationaryObject{
    private int RuneNumber;
    
    public Rune(int x, int y, int buff, Image img)
    {
        super(x, y, img);
        this.RuneNumber = buff;
    }
    
    public int getRune()
    {
        return this.RuneNumber;
    }
    
}
