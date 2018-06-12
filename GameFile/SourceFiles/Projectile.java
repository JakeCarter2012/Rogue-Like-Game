package SourceFiles;

import java.awt.Image;

public class Projectile extends MovingObject{
    private Image sprite;
    private int damage;
    
    public Projectile(int x, int y, int horizontalSpeed, int verticalSpeed, int damage, Image img)
    {
        super(x, y, img.getWidth(null), img.getHeight(null), horizontalSpeed, verticalSpeed);
        this.sprite = img;
        this.damage = damage;
    }
    
    public Image getSprite()
    {
        return this.sprite;
    }
    
    public int getDamage()
    {
        return this.damage;
    }
}
