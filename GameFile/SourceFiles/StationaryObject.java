package SourceFiles;

import java.awt.Image;

public class StationaryObject extends GameObject{
    private Image Sprite;
    
    public StationaryObject(int x, int y, Image img)
    {
        super(x, y, img.getWidth(null), img.getHeight(null));
        Sprite = img;
    }
    
    public Image getSprite()
    {
        return this.Sprite;
    }
}
