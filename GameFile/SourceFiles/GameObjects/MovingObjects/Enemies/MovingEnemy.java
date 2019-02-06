package SourceFiles.GameObjects.MovingObjects.Enemies;

import SourceFiles.GameObjects.MovingObjects.Projectiles.Projectile;
import SourceFiles.GameObjects.MovingObjects.MovingObject;
import SourceFiles.GameObjects.StationaryObjects.AreaOfEffect;
import java.awt.Image;
import java.util.Random;

abstract public class MovingEnemy extends MovingObject{
    /*
    Parent class for all MovingEnemies, contains accesors and update status method
    */
    private int Health;
    private int BumpDamage;
    protected int CurrentSpeed, NormalSpeed;
    private int BurnResist, FreezeResist;
    private int BurnDamage;
    protected boolean Frozen, Chilled, Burning;
    private int StatusTimer;
    boolean GeneralCollision, CollisionUp, CollisionDown, CollisionRight,
            CollisionLeft, RequestMoveRight, RequestMoveLeft, RequestMoveUp,
            RequestMoveDown, PlayerCollision;
    
    public MovingEnemy(int x, int y, int width, int height, int health, int speed, 
            int bumpDmg, int burnRes, int iceRes)
    {
        super(x, y, width, height, speed, 0);
        this.Health = health;
        this.BumpDamage = bumpDmg;
        this.Frozen = false;
        this.Burning = false;
        this.BurnResist = burnRes;
        this.FreezeResist = iceRes;
        this.Chilled = false;
        this.StatusTimer = 0;
        this.BumpDamage = bumpDmg;
        this.NormalSpeed = this.CurrentSpeed = speed;
        this.GeneralCollision = CollisionDown = CollisionRight = CollisionUp = 
                CollisionLeft = RequestMoveRight = RequestMoveLeft = RequestMoveUp =
                RequestMoveDown = false;
    }
    
    public boolean isChilled()
    {
        return this.Chilled;
    }
    
    public boolean isFrozen()
    {
        return this.Frozen;
    }
    
    public boolean isBurning()
    {
        return this.Burning;
    }
    
    public int getBumpDamage()
    {
        return this.BumpDamage;
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
    
    public void ignite(int eleChance, int burnDmg, int burnTime)
    {
        //If BurnResist is at/above 100, enemy is immune to burning
        if (this.BurnResist >= 100)
            return;
        int burnChance = eleChance - this.BurnResist;
        if (burnChance <= 0)
            return;
        Random rnd = new Random();
        
        //Generate random number; if it's within the burnChance, enemy is burned
        if(burnChance > rnd.nextInt(100))
        {
            this.StatusTimer = burnTime;
            this.Frozen = false;
            this.Chilled = false;
            this.CurrentSpeed = this.NormalSpeed;
            this.Burning = true;
            this.BurnDamage = burnDmg;
        }
    }
    
    public void freeze(int eleChance, int freezeTime)
    {
        //If BurnResist is at/above 100, enemy is immune to freezing
        if (this.FreezeResist == 100 || this.Frozen)
            return;
        int freezeChance = eleChance - this.FreezeResist;
        if (freezeChance <= 0)
            return;
        Random rnd = new Random();
        
        //Generate random number; if it's within the freezeChance, enemy is chilled
        if(freezeChance > rnd.nextInt(100))
        {
            this.StatusTimer = freezeTime;
            this.Burning = false;
            //Roll the dice again for a chance to freeze enemy solid
            if(freezeChance > rnd.nextInt(100))
            {
                this.Frozen = true;
                this.Chilled = false;
                this.CurrentSpeed = this.NormalSpeed;
            }
            else
            {
                this.Frozen = false;
                this.Chilled = true;
                this.CurrentSpeed = this.NormalSpeed/2;
            }
        }
    }
    
    public void frostBurn(int eleChance, int burnDmg, int burnTime, int freezeTime)
    {
        //If enemy is immune to freeze/burns, return
        if (this.BurnResist >= 100 || this.FreezeResist >= 100)
            return;
        int chance = eleChance - this.BurnResist - this.FreezeResist;
        if (chance <= 0)
            return;
        Random rnd = new Random();
        
       //Random number to determine if element takes effect
        if(chance > rnd.nextInt(100))
        {
            if(burnTime > freezeTime)
                this.StatusTimer = freezeTime;
            else
                this.StatusTimer = burnTime;

            this.Frozen = false;
            this.Burning = true;
            this.Chilled = true;
            this.CurrentSpeed = this.NormalSpeed/2;
        }
    }
    
    protected void updateStatus()
    {
        if(this.Burning || this.Chilled || this.Frozen)
        {
            this.StatusTimer--;
            if(this.StatusTimer == 0)
            {
                this.Burning = false;
                this.Chilled = false;
                this.Frozen = false;
                this.CurrentSpeed = this.NormalSpeed;
                return;
            }
            if(this.Burning)
                this.Health = this.Health - this.BurnDamage;
        }
    }
    
    public int getSpeed()
    {
        return this.CurrentSpeed;
    }
    
    protected void updatePosition() 
    {
        if(!PlayerCollision)
        {
            if(!GeneralCollision)
            {
                this.setX(this.getX() + (int)Math.round(this.getSpeed()*Math.cos(Math.toRadians(this.getAngle()))));
                this.setY(this.getY() + (int)Math.round(this.getSpeed()*Math.sin(Math.toRadians(this.getAngle()))));
            }
            else if(this.RequestMoveUp && !this.RequestMoveDown && !this.CollisionUp)
            {
                this.setY(this.getY() - this.getSpeed());
            }
            else if(this.RequestMoveDown && !this.RequestMoveUp && !this.CollisionDown)
            {
                this.setY(this.getY() + this.getSpeed());
            }
            else if(this.RequestMoveLeft && !this.RequestMoveRight && !this.CollisionLeft)
            {
                this.setX(this.getX() - this.getSpeed());
            }
            else if(this.RequestMoveRight && !this.RequestMoveLeft && !this.CollisionRight)
            {
                this.setX(this.getX() + this.getSpeed());
            }
        }
        GeneralCollision = CollisionDown = CollisionRight = CollisionUp = 
                CollisionLeft = RequestMoveRight = RequestMoveLeft = RequestMoveUp =
                RequestMoveDown = PlayerCollision = false;
    }
    
    public void setPlayerCollsion()
    {
        this.PlayerCollision = true;
    }
    
    public boolean getGeneralCollision()
    {
        return this.GeneralCollision;
    }
    
    public void setGeneralCollision()
    {
        this.GeneralCollision = true;
    }
    
    public void setCollisionUp()
    {
        this.CollisionUp = true;
    }
    
    public void setCollisionDown()
    {
        this.CollisionDown = true;
    }
    
    public void setCollisionLeft()
    {
        this.CollisionLeft = true;
    }
    
    public void setCollisionRight()
    {
        this.CollisionRight = true;
    }
    
    public boolean getCollisionUp()
    {
        return this.CollisionUp;
    }
    
    public boolean getCollisionDown()
    {
        return this.CollisionDown;
    }
    
    public boolean getCollisionLeft()
    {
        return this.CollisionLeft;
    }
    
    public boolean getCollisionRight()
    {
        return this.CollisionRight;
    }
    
    public void requestMoveDown()
    {
        this.RequestMoveDown = true;
    }
    
    public void requestMoveUp()
    {
        this.RequestMoveUp = true;
    }
    
    public void requestMoveLeft()
    {
        this.RequestMoveLeft = true;
    }
    
    public void requestMoveRight()
    {
        this.RequestMoveRight = true;
    }
    
    abstract public boolean isProjectileReady();
    
    abstract public Projectile fireProjectile();
    
    abstract public boolean isAoeReady();
    
    abstract public AreaOfEffect fireAoe();
    
    abstract public boolean isSummonReady();
    
    abstract public MovingEnemy getSummon();
    
    abstract public Image getSprite();
    
    abstract public void updateMovingEnemy(int playerX, int playerY);
}
