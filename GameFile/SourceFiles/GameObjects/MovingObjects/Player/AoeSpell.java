package SourceFiles.GameObjects.MovingObjects.Player;

import java.awt.Image;

public class AoeSpell extends Spell{
    /*
    Child class for AoeSpells, contains info about current aoe spell;
    this class is not the aoe itself, but the details about the aoe
    this spell creates.
    */
    private Image[] Sprites;
    private int Damage;
    private int Duration;
    
    public AoeSpell(String spellName, int damage, int duration, int cooldown, 
            boolean fire, boolean ice, boolean Void, int elementChance, Image[] imgs, Image ico)
    {
        super(spellName, cooldown, fire, ice, Void, elementChance, ico);
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
