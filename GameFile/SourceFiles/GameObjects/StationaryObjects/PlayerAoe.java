package SourceFiles.GameObjects.StationaryObjects;

import java.awt.Image;

public class PlayerAoe extends AreaOfEffect{
    private Boolean Fire, Ice, Void;
    
    public PlayerAoe(int x, int y, int time, int damage,boolean fire, boolean ice,
            boolean Void, Image[] imgs)
    {
        super(x, y, time, damage, imgs);
        
        this.Fire = fire;
        this.Ice = ice;
        this.Void = Void;
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
}
