package SourceFiles.GameObjects.StationaryObjects;

import SourceFiles.GameObjects.Animations.Animation;
import SourceFiles.GameObjects.GameObject;
import java.awt.Image;

public class AnimatedWall extends GameObject{
    private Animation Sprites;
    
    public AnimatedWall(int x, int y, int width, int height, Animation ani)
    {
        super(x, y, width, height);
        this.Sprites = ani;
    }
    
    public Image getSprite()
    {
        return this.Sprites.getSprite();
    }
    
    public void update()
    {
        this.Sprites.updateAnimation();
    }
}
