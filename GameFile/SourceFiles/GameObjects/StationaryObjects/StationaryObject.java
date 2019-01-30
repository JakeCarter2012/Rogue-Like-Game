package SourceFiles.GameObjects.StationaryObjects;

import SourceFiles.GameObjects.GameObject;
import java.awt.Image;

public class StationaryObject extends GameObject{
    /*
    Parent class for stationary objects that don't require movement
    */
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
