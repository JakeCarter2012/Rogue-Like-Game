package SourceFiles.GameObjects.MovingObjects.Player;

import java.awt.Image;

public class DamagingSpell extends Spell{
    private int Damage;
    
    public DamagingSpell(String spellName, int dmg, int cooldown, boolean fire, boolean ice,
            boolean Void, String description, int rarity, Image ico)
    {
        super(spellName, cooldown, fire, ice, Void, description, rarity, ico);
        this.Damage = dmg;
    }
    
    public int getDamage()
    {
        return this.Damage;
    }
}
