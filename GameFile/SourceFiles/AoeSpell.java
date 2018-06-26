package SourceFiles;

import java.awt.Image;

public class AoeSpell extends Spell{
    private Image[] Sprites;
    private int Damage;
    private int Duration;
    
    public AoeSpell(int damage, int duration, int cooldown, Image[] imgs, Image ico)
    {
        super(cooldown, ico);
        this.Damage = damage;
        this.Duration = duration;
        this.Sprites = imgs;
    }
    
    public Image[] getSprites()
    {
        return this.Sprites;
    }
    
    public int getDamage()
    {
        return this.Damage;
    }
    
    public int getDuration()
    {
        return this.Duration;
    }
}
