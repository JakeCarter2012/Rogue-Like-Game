package SourceFiles.GameObjects.MovingObjects.Projectiles;

import SourceFiles.GameObjects.MovingObjects.MovingObject;
import SourceFiles.GameObjects.Animations.Animation;
import java.awt.Image;

public class Projectile extends MovingObject{
    /*
    Parent class for projectiles, holds projectile end animation/shadow/damage
    */
    private Image Sprite, Shadow;
    private Image[] EndAnimationImage;
    private double AccurateX, AccurateY;
    private int Damage;
    
    public Projectile(int x, int y, int Speed, double Angle, int damage, 
            Image img, Image shadow, Image[] endAnimation)
    {
        super(x, y, img.getWidth(null), img.getHeight(null), Speed, Angle);
        this.Sprite = img;
        this.Shadow = shadow;
        this.EndAnimationImage = endAnimation;
        this.Damage = damage;
        this.AccurateX = x - (0.5)*img.getWidth(null);
        this.AccurateY = y - (0.5)*img.getHeight(null);
    }
    
    public Image getSprite()
    {
        return this.Sprite;
    }
    
    public Image getShadow()
    {
        return this.Shadow;
    }
    
    public Animation getEndAnimation()
    {
        if(this.EndAnimationImage == null)
        {
            return null;
        }
        return new Animation(this.getX(), this.getY(), (int)this.getAngle(), this.EndAnimationImage, 1, false);
    }
    
    public int getDamage()
    {
        return this.Damage;
    }
    
    public void updateProjectile()
    {
        this.AccurateX = this.AccurateX + this.getSpeed()*Math.cos(Math.toRadians(this.getAngle()));
        this.AccurateY = this.AccurateY + this.getSpeed()*Math.sin(Math.toRadians(this.getAngle()));
    }
    
    /*
    Get X/Y are overwritten for projectiles- x and y are stored as doubles to be
    more precise, so that they can be updated correctly and not "jump"
    */
    @Override
    public int getX()
    {
        return (int)this.AccurateX;
    }
    
    @Override
    public int getY()
    {
        return (int)this.AccurateY;
    }
}
