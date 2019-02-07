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
    
    public PlayerProjectile(int x, int y, int Speed, double Angle, int damage, 
            boolean fire, boolean ice, boolean Void, int elementChance, 
            Image img, Image shadow, Image[] endAnimation)
    {
        super(x, y,  Speed, Angle, damage, img, shadow, endAnimation);
        
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
