package SourceFiles.GameObjects.MovingObjects.Player;

import java.awt.Image;

public class DamagingSpell extends Spell{
    private int Damage;
    private String SecondDescription;
    
    public DamagingSpell(String spellName, int dmg, int cooldown, boolean fire, boolean ice,
            boolean Void, String description, String description2, int rarity, Image ico)
    {
        super(spellName, cooldown, fire, ice, Void, description, rarity, ico);
        this.Damage = dmg;
        SecondDescription = description2;
    }
    
    public int getDamage()
    {
        return this.Damage;
    }
    
    public String getSecondDescription()
    {
        return this.SecondDescription;
    }
}
