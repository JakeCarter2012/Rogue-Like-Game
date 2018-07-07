package SourceFiles;

import java.awt.Image;

public class Projectile extends MovingObject{
    private Image Sprite;
    private int Damage;
    
    public Projectile(int x, int y, int Speed, int Angle, int damage, Image img)
    {
        super(x, y, img.getWidth(null), img.getHeight(null), Speed, Angle);
        this.Sprite = img;
        this.Damage = damage;
    }
    
    public Image getSprite()
    {
        return this.Sprite;
    }
    
    public int getDamage()
    {
        return this.Damage;
    }
}
