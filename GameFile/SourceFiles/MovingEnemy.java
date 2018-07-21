package SourceFiles;

import java.awt.Image;

abstract public class MovingEnemy extends MovingObject{
    private Image[] Sprites;
    private int CurrentSprite;
    private int Health;
    private int BumpDamage;
    
    private boolean Frozen, Burning;
    
    public MovingEnemy(int x, int y, int leftbound, int rightbound, int upbound, 
            int downbound, int health, int bumpDmg, Image[] imgs)
    {
        super(x, y, leftbound,rightbound, upbound, downbound, imgs[0].getWidth(null), imgs[0].getHeight(null), 0, 0);
        this.Sprites = imgs;
        this.CurrentSprite = 0;
        this.Health = health;
        this.BumpDamage = bumpDmg;
        this.Frozen = false;
        this.Burning = false;
    }
    
    public int getBumpDamage()
    {
        return this.BumpDamage;
    }
    
    public Image getSprite()
    {
        return this.Sprites[CurrentSprite];
    }
    
    public void takeDamage(int dmg)
    {
        this.Health -= dmg;
    }
    
    public boolean isDead()
    {
        if(this.Health <= 0)
            return true;
        else
            return false;
    }
    
    abstract public boolean isProjectileReady();
    
    abstract public Projectile fireProjectile();
    
    abstract public boolean isAoeReady();
    
    abstract public AreaOfEffect fireAoe();
    
    abstract public boolean isSummonReady();
    
    abstract public MovingEnemy getSummon();
    
    abstract public void updateMovingEnemy(int playerX, int playerY, boolean generalCollision,
            boolean horizontalCollision, boolean verticalCollision);
}
