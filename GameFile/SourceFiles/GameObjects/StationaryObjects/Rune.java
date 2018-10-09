package SourceFiles.GameObjects.StationaryObjects;

import SourceFiles.GameObjects.StationaryObjects.StationaryObject;
import java.awt.Image;

public class Rune extends StationaryObject{
    private String RuneName;
    
    public Rune(String name, int x, int y, Image img)
    {
        super(x, y, img);
        this.RuneName = name;
    }
    
    public String getRuneName()
    {
        return this.RuneName;
    }
    
}
