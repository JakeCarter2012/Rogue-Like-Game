package SourceFiles;

import java.awt.Image;

public class Projectile extends MovingObject{
    private Image Sprite;
    private int Damage;
    
    public Projectile(int x, int y, int horizontalSpeed, int verticalSpeed, int damage, Image img)
    {
        super(x, y, img.getWidth(null), img.getHeight(null), horizontalSpeed, verticalSpeed);
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
