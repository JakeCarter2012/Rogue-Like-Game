package SourceFiles;

import java.awt.Image;

public abstract class Spell {
    private int CoolDownTime, Timer;
    private boolean Fire, Ice, Void;
    private Image Icon;
    private String SpellName;
    private int ElementChance;
    
    public Spell(String spellName, int cooldown, boolean fire, boolean ice,
            boolean Void, int elementChance, Image ico)
    {
        this.CoolDownTime = cooldown;
        this.Icon = ico;
        this.SpellName = spellName;
        this.Timer = -61;
        
        this.Fire = fire;
        this.Ice = ice;
        this.Void = Void;
        this.ElementChance = elementChance;
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
    
    public int getElementChance()
    {
        return this.ElementChance;
    }
    
    public int getCoolDown(int fps)
    {
        if(this.CoolDownTime < fps)
            return 0;
        else
            return 1 + this.Timer/fps;
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
