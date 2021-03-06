package SourceFiles.GameObjects.MovingObjects.Enemies;

import SourceFiles.GameObjects.MovingObjects.Projectiles.Projectile;
import SourceFiles.GameObjects.StationaryObjects.AreaOfEffect;
import java.awt.Image;
import java.util.Random;

public class DartGoblin extends MovingEnemy{
    private Image[] MoveLeftImages, MoveRightImages, AttackLeft, AttackRight;
    private Image[] EndProjectile;
    private Image Dart, DartShadow;
    private int ProjectileTimer, ImageTimer, CurrentFrame, ShotsInBurst;
    private boolean FacingRight, CloseToPlayer;
    private Image CurrentSprite;
    
    public DartGoblin(int x, int y, int floor, Image[] moveLeft, Image[] moveRight, 
            Image[] attackLeft, Image[] attackRight, Image shadow, Image dart, Image dartShadow,
            Image[] endProjectile)
    {
        super(x, y, floor, moveLeft[0].getWidth(null), moveLeft[0].getHeight(null), 
                100 + 50 * floor, 4, 50 + 25 * floor, 0, 0, shadow);
        this.MoveLeftImages = moveLeft;
        this.MoveRightImages = moveRight;
        this.AttackLeft = attackLeft;
        this.AttackRight = attackRight;
        this.Dart = dart;
        this.DartShadow = dartShadow;
        this.EndProjectile = endProjectile;
        this.ProjectileTimer = 120;
        this.ShotsInBurst = 3;
        this.ImageTimer = 0;
        this.CurrentFrame = 0;
        this.FacingRight = true;
        this.CloseToPlayer = false;
        
        this.ShadowX = 8;
        this.ShadowY = 97;
        
        this.CurrentSprite = this.AttackRight[0];
        
        Random rnd = new Random();
        this.ProjectileTimer = rnd.nextInt(60) + 50;
    }
    
    public int getExperience()
    {
        return(this.EnemyLevel * 5);
    }
    
    public boolean isProjectileReady()
    {
        if(this.ProjectileTimer < 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    public Projectile fireProjectile()
    {
        /*
        DartGoblins shoot three round bursts; when firing, reduce shots remaining,
        then either reset the timer if no shots are left or set it to 1/4 a second.
        */
        this.ShotsInBurst--;
        if(this.ShotsInBurst <= 0)
        {
            this.ProjectileTimer = 120;
            this.ShotsInBurst = 3;
        }
        else
        {
            this.ProjectileTimer = 15;
        }
        
        int dartX;
        if(this.FacingRight)
        {
            dartX = 40;
        }
        else
        {
            dartX = 0;
        }
        
        Projectile dart = new Projectile(this.getX() + dartX, this.getY() + 36, 
                8, this.getAngle(), 50, this.Dart, this.DartShadow, this.EndProjectile);
        
        return dart;
    }
    
    //DartGoblin has no aoe's/summons
    public boolean isAoeReady()
    {
        return false;
    }
    
    public AreaOfEffect fireAoe()
    {
        return null;
    }
    
    public boolean isSummonReady()
    {
        return false;
    }
    
    public MovingEnemy getSummon()
    {
        return null;
    }
    
    public Image getSprite()
    {
        return this.CurrentSprite;
    }
    
    public void updateMovingEnemy(int playerX, int playerY)
    {
        this.updateStatus();
        
        //Don't update further if frozen
        if(this.Frozen)
            return;
        
        double yDiff = Math.abs(playerY - this.getCenterY());
        double xDiff = Math.abs(playerX - this.getCenterX());
        
        //If within 300 distance of player, don't move closer
        if(Math.hypot(yDiff, xDiff) < 300)
        {
            this.CloseToPlayer = true;
        }
        else
        {
            this.CloseToPlayer = false;
        }
        
        this.updateImage(playerX);
        
        //if attacking or close to the player, don't need to move closer
        if(!this.CloseToPlayer && !(this.ProjectileTimer < 30) && !(this.ProjectileTimer > 110))
        {
            this.updatePosition();
        }
        
        
        this.setAngle((int)(90 - Math.toDegrees(Math.atan2(playerX - this.getCenterX(), playerY - this.getCenterY()))));
        
        this.ProjectileTimer--;
    }
    
    private void updateImage(int playerX)
    {
        /*
        CurrentSprite controller for dart goblin; uses image timer to move through
        images, and determines wich image array should be used (left/right/attack)
        based on state of the object
        */
        if(this.ImageTimer == 9)
        {
            if(this.CurrentFrame == 5)
            {
                this.CurrentFrame = 0;
            }
            else
            {
                this.CurrentFrame++;
            }
            
            if(playerX < this.getCenterX())
            {
                this.FacingRight = false;
                this.ShadowY = 99;
            }
            else
            {
                this.FacingRight = true;
                this.ShadowY = 97;
            }
            
            if(this.FacingRight)
            {
                if(this.ProjectileTimer < 30 || this.ProjectileTimer > 110)
                {
                    this.CurrentSprite = this.AttackRight[1];
                }
                else if(this.CloseToPlayer)
                {
                    this.CurrentSprite = this.AttackRight[0];
                }
                else
                {
                    this.CurrentSprite = this.MoveRightImages[this.CurrentFrame];
                }
            }
            else
            {
                if(this.ProjectileTimer < 30 || this.ProjectileTimer > 110)
                {
                    this.CurrentSprite = this.AttackLeft[1];
                }
                else if(this.CloseToPlayer)
                {
                    this.CurrentSprite = this.AttackLeft[0];
                }
                else
                {
                    this.CurrentSprite = this.MoveLeftImages[this.CurrentFrame];
                }
            }
            
            this.ImageTimer = 0;
        }
        this.ImageTimer++;
    }
}
