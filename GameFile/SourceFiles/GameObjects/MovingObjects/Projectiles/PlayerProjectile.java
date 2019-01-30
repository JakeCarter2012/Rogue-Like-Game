package SourceFiles.GameObjects.MovingObjects.Projectiles;

import SourceFiles.GameObjects.MovingObjects.Projectiles.Projectile;
import java.awt.Image;

public class PlayerProjectile extends Projectile{
    /*
    Child of Projectile class; conatins additional info unique to the player's
    projectiles - elements/element chance
    */
    private Boolean Fire, Ice, Void;
    private int ElementChance;
    
    public PlayerProjectile(int x, int y, int leftbound, int rightbound, int upbound, int downbound,
            int Speed, double Angle, int damage, boolean fire, boolean ice,
            boolean Void, int elementChance, Image img, Image shadow, Image[] endAnimation)
    {
        super(x, y, leftbound, rightbound, upbound, downbound, Speed, Angle, damage, img, shadow, endAnimation);
        
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
