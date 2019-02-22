package SourceFiles.GameObjects.MovingObjects.Player;

import java.awt.Image;

public class ProjectileSpell extends DamagingSpell{
    /*
    Child class for ProjectileSpells, contains info about currentprojectile spell;
    this class is not the projectile itself, but the details about the projectile
    this spell creates.
    */
    private int Speed;
    private Image Sprite, Shadow;
    private Image[] EndAnimationImage;
    
    public ProjectileSpell(String spellName, int dmg, int speed, int cooldown, 
            boolean fire, boolean ice, boolean Void, String description, 
            int rarity,Image img, Image ico, Image shadow, Image[] endAnimation)
    {
        super(spellName, dmg, cooldown, fire, ice, Void, description, rarity, ico);
        this.Speed = speed;
        this.Sprite = img;
        this.Shadow = shadow;
        this.EndAnimationImage = endAnimation;
    }
    
    public int getSpeed()
    {
        return this.Speed;
    }
    
    public Image getSprite()
    {
        return this.Sprite;
    }
    
    public Image getShadow()
    {
        return this.Shadow;
    }
    
    public Image[] getEndAnimation()
    {
        return this.EndAnimationImage;
    }
}
