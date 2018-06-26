package SourceFiles;

import java.awt.Image;

public class ProjectileSpell extends Spell{
    private int Damage;
    private int Speed;
    private Image Sprite;
    
    public ProjectileSpell(int dmg, int speed, int cooldown, Image img, Image ico)
    {
        super(cooldown, ico);
        this.Damage = dmg;
        this.Speed = speed;
        this.Sprite = img;
    }
    
    public int getDamage()
    {
        return this.Damage;
    }
    
    public int getSpeed()
    {
        return this.Speed;
    }
    
    public Image getSprite()
    {
        return this.Sprite;
    }
}
