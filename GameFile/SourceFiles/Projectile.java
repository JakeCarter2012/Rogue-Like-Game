package SourceFiles;

import java.awt.Image;

public class Projectile extends MovingObject{
    private Image Sprite;
    private int Damage;
    
    public Projectile(int x, int y, int leftbound, int rightbound, int upbound, int downbound,
            int Speed, int Angle, int damage, Image img)
    {
        super(x, y, leftbound,rightbound, upbound, downbound, img.getWidth(null),
                img.getHeight(null), Speed, Angle);
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
