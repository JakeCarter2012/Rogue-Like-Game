package SourceFiles.GameObjects.MovingObjects.Player;

import java.awt.Image;

public class AoeSpell extends DamagingSpell{
    /*
    Child class for AoeSpells, contains info about current aoe spell;
    this class is not the aoe itself, but the details about the aoe
    this spell creates.
    */
    private Image[] Sprites;
    private int Duration;
    
    public AoeSpell(String spellName, int damage, int duration, int cooldown, 
            boolean fire, boolean ice, boolean Void, String description, 
            int rarity, Image[] imgs, Image ico)
    {
        super(spellName, damage, cooldown, fire, ice, Void, description, rarity, ico);
        this.Duration = duration;
        this.Sprites = imgs;
    }
    
    public Image[] getSprites()
    {
        return this.Sprites;
    }
    
    public int getDuration()
    {
        return this.Duration;
    }
}
