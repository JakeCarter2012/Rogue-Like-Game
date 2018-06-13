package SourceFiles;

import java.awt.Image;

public class AreaOfEffect extends GameObject{
    private Image[] Sprites;
    private int Damage;
    private int Timer;
    
    public AreaOfEffect(int x, int y, int time, int damage, Image[] imgs)
    {
        super(x, y, imgs[0].getWidth(null), imgs[0].getHeight(null));
        this.Sprites = imgs;
        this.Damage = damage;
        this.Timer = time;
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
