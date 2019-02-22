package SourceFiles.GameObjects.MovingObjects.Player;

import java.awt.Image;

public abstract class Spell {
    /*
    Parent class for all spells; used for all spell cooldaowns/elements
    */
    private int CoolDownTime, Timer;
    private boolean Fire, Ice, Void;
    private Image Icon;
    private String SpellName;
    private int Rarity;
    private String Description;
    
    public Spell(String spellName, int cooldown, boolean fire, boolean ice,
            boolean Void, String description, int rarity, Image ico)
    {
        this.CoolDownTime = cooldown;
        this.Icon = ico;
        this.SpellName = spellName;
        this.Timer = -61;
        
        this.Fire = fire;
        this.Ice = ice;
        this.Void = Void;
        
        this.Rarity = rarity;
        this.Description = description;
    }
    
    public int getRarity()
    {
        return this.Rarity;
    }
    
    public String getDescription()
    {
        return this.Description;
    }
    
    public Image getIcon()
    {
        return this.Icon;
    }
    
    public String getSpellName()
    {
        return this.SpellName;
    }
    
    public boolean isFire()
    {
        return this.Fire;
    }
    
    public boolean isIce()
    {
        return this.Ice;
    }
    
    public boolean isVoid()
    {
        return this.Void;
    }
    
    public int getResetTime()
    {
        return this.CoolDownTime;
    }
    
    public int getCoolDown(int fps)
    {
        if((this.CoolDownTime < fps) || (this.Timer < 0))
        {
            return 0;
        }
        else
        {
            return 1 + this.Timer/fps;
        }
    }
    
    public void resetCoolDown()
    {
        this.Timer = this.CoolDownTime;
    }
    
    public void updateSpell()
    {
        Timer -= 1;
    }
    
    public boolean offCooldown()
    {
        if(this.Timer < 0)
            return true;
        else
            return false;
    }
}
