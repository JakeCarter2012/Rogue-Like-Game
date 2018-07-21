package SourceFiles;

import java.awt.Image;

public abstract class Spell {
    private int CoolDownTime, Timer;
    private Image Icon;
    private String SpellName;
    
    public Spell(String spellName, int cooldown, Image ico)
    {
        this.CoolDownTime = cooldown;
        this.Icon = ico;
        this.SpellName = spellName;
        this.Timer = -61;
    }
    
    public Image getIcon()
    {
        return this.Icon;
    }
    
    public String getSpellName()
    {
        return this.SpellName;
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
