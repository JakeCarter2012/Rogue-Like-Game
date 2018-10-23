package SourceFiles.GameObjects.MovingObjects.Enemies;

import SourceFiles.GameObjects.MovingObjects.Projectiles.Projectile;
import SourceFiles.GameObjects.StationaryObjects.AreaOfEffect;
import java.awt.Image;

public class DartGoblin extends MovingEnemy{
    private Image[] MoveLeftImages, MoveRightImages, AttackLeft, AttackRight;
    private Image Dart;
    private int ProjectileTimer, ImageTimer, CurrentFrame, ShotsInBurst;
    private boolean FacingRight, CloseToPlayer;
    private Image CurrentSprite;
    
    public DartGoblin(int x, int y, int leftbound, int rightbound, int upbound, 
            int downbound, int floor, Image[] moveLeft, Image[] moveRight, 
            Image[] attackLeft, Image[] attackRight, Image dart)
    {
        super(x, y, moveLeft[0].getWidth(null), moveLeft[0].getHeight(null), 
                leftbound, rightbound, upbound, downbound, 100 + 50 * floor, 3, 50 + 25 * floor,
                0, 0);
        this.MoveLeftImages = moveLeft;
        this.MoveRightImages = moveRight;
        this.AttackLeft = attackLeft;
        this.AttackRight = attackRight;
        this.Dart = dart;
        this.ProjectileTimer = 120;
        this.ShotsInBurst = 3;
        this.ImageTimer = 0;
        this.CurrentFrame = 0;
        this.FacingRight = true;
        this.CloseToPlayer = false;
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
        if(this.ShotsInBurst > 0)
        {
            this.ProjectileTimer = 15;
            this.ShotsInBurst--;
            
            if(this.ShotsInBurst <= 0)
            {
                this.ProjectileTimer = 120;
                this.ShotsInBurst = 3;
            }
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
                this.getLeftBound(), this.getRightBound(), this.getUpBound(), 
                this.getDownBound(), 8, this.getAngle(), 50, this.Dart);
        
        return dart;
    }
    
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
    
    public void updateMovingEnemy(int playerX, int playerY, boolean generalCollision,
            boolean horizontalCollision, boolean verticalCollision)
    {
        this.updateStatus();
        
        if(this.Frozen)
            return;
        
        double yDiff = Math.abs(playerY - this.getCenterY());
        double xDiff = Math.abs(playerX -this.getCenterX());
        
        if(Math.hypot(yDiff, xDiff) < 300)
        {
            this.CloseToPlayer = true;
        }
        else
        {
            this.CloseToPlayer = false;
        }
        
        this.updateImage(playerX);
        
        this.updatePosition(generalCollision, horizontalCollision, verticalCollision);
        
        this.setAngle((int)(90 - Math.toDegrees(Math.atan2(playerX - this.getCenterX(), playerY - this.getCenterY()))));
        
        this.ProjectileTimer--;
    }
    
    private void updateImage(int playerX)
    {
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
            }
            else
            {
                this.FacingRight = true;
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
    
    private void updatePosition(boolean generalCollision, boolean horizontalCollision, boolean verticalCollision) 
    {
        if(this.CloseToPlayer || this.ProjectileTimer < 30 || this.ProjectileTimer > 110)
        {
            return;
        }
        
        if(!generalCollision)
        {
            this.setX(this.getX() + (int)Math.round(this.getSpeed()*Math.cos(Math.toRadians(this.getAngle()))));
            this.setY(this.getY() + (int)Math.round(this.getSpeed()*Math.sin(Math.toRadians(this.getAngle()))));
        }
        else if(!horizontalCollision)
        {
            this.setX(this.getX() + (int)Math.round(this.getSpeed()*Math.cos(Math.toRadians(this.getAngle()))));
        }
        else if(!verticalCollision)
        {
            this.setY(this.getY() + (int)Math.round(this.getSpeed()*Math.sin(Math.toRadians(this.getAngle()))));
        }
        
        if(this.getX() < this.getLeftBound())
            this.setX(this.getLeftBound());
        if(this.getX() + this.getWidth() > this.getRightBound())
            this.setX(this.getRightBound() - this.getWidth());
        if(this.getY() < this.getUpBound())
            this.setY(this.getUpBound());
        if(this.getY() + this.getHeight() > this.getDownBound())
            this.setY(this.getDownBound() - this.getHeight());
    }
}
