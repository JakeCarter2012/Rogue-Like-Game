package SourceFiles.GameObjects.StationaryObjects;

import SourceFiles.GameObjects.GameObject;
import java.awt.Image;

public class AreaOfEffect extends GameObject{
    private Image[] Sprites;
    private int Damage;
    private int Timer;
    private int CurrentImage;
    
    public AreaOfEffect(int x, int y, int time, int damage, Image[] imgs)
    {
        super(x, y, imgs[0].getWidth(null), imgs[0].getHeight(null));
        this.Sprites = imgs;
        this.Damage = damage;
        this.Timer = time;
        this.CurrentImage = 0;
    }
    
    public Image getSprite()
    {
        return this.Sprites[this.CurrentImage];
    }
    
    public int getDamage()
    {
        return this.Damage;
    }
    
    public boolean isDone()
    {
        if (this.Timer < 0)
            return true;
        else
            return false;
    }
    
    public void updateObject()
    {
        
    }
}
