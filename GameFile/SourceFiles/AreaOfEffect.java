package SourceFiles;

import java.awt.Image;

public class AreaOfEffect extends GameObject{
    private Image[] sprites;
    private int damage;
    private int timer;
    
    public AreaOfEffect(int x, int y, int time, int damage, Image[] imgs)
    {
        super(x, y, imgs[0].getWidth(null), imgs[0].getHeight(null));
        this.sprites = imgs;
        this.damage = damage;
        this.timer = time;
    }
    
    public int getDamage()
    {
        return this.damage;
    }
    
    public boolean isDone()
    {
        if (this.timer < 0)
            return true;
        else
            return false;
    }
    
    public void updateObject()
    {
        
    }
}
