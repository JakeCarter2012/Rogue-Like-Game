package SourceFiles;

import java.awt.Image;

public class ProjectileSpell extends Spell{
    private int Damage;
    private int Speed;
    private Image Sprite;
    
    public ProjectileSpell(String spellName, int dmg, int speed, int cooldown, 
            boolean fire, boolean ice, boolean Void, int elementChance, Image img, Image ico)
    {
        super(spellName, cooldown, fire, ice, Void, elementChance, ico);
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
