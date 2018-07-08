package SourceFiles;

import java.awt.Image;

public abstract class Spell {
    private int CoolDownTime, Timer;
    private Image Icon;
    
    public Spell(int cooldown, Image ico)
    {
        this.CoolDownTime = cooldown;
        this.Icon = ico;
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
