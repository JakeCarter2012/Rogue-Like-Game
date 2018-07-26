package SourceFiles;

import java.awt.Image;

public class PlayerAoe extends AreaOfEffect{
    private Boolean Fire, Ice, Void;
    private int ElementChance;
    
    PlayerAoe(int x, int y, int time, int damage,boolean fire, boolean ice,
            boolean Void, int elementChance, Image[] imgs)
    {
        super(x, y, time, damage, imgs);
        
        this.Fire = fire;
        this.Ice = ice;
        this.Void = Void;
        this.ElementChance = elementChance;
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
}
