package SourceFiles.GameObjects.StationaryObjects;

import SourceFiles.GameObjects.GameObject;
import java.awt.Image;

public class Wall extends GameObject{
    private Image Sprite;
    
    public Wall(int x, int y, int width, int height, Image img)
    {
        super(x, y, width, height);
        this.Sprite = img;
    }
    
    public Image getSprite()
    {
        return this.Sprite;
    }
}
