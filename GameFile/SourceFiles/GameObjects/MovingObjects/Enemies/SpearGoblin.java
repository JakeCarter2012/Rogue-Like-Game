package SourceFiles.GameObjects.MovingObjects.Enemies;

import SourceFiles.GameObjects.MovingObjects.Projectiles.Projectile;
import SourceFiles.GameObjects.StationaryObjects.AreaOfEffect;
import java.awt.Image;

public class SpearGoblin extends MovingEnemy{
    private Image[] MoveLeftImages, MoveRightImages;
    private int ImageTimer, CurrentFrame;
    private boolean FacingRight;
    private Image CurrentSprite;
    
    public SpearGoblin(int x, int y, int floor, Image[] moveLeft, Image[] moveRight, Image shadow)
    {
        super(x, y, floor, moveLeft[0].getWidth(null), moveLeft[0].getHeight(null), 
                100 + 50 * floor, 5, 50 + 25 * floor, 0, 0, shadow);
        this.MoveLeftImages = moveLeft;
        this.MoveRightImages = moveRight;
        this.ImageTimer = 0;
        this.CurrentFrame = 0;
        this.FacingRight = true;
        
        this.ShadowY = 105;
        this.ShadowX = 48;
        
        this.CurrentSprite = this.MoveRightImages[0];
    }
    
    public int getExperience()
    {
        return(this.EnemyLevel * 5);
    }
    
    //SpearGoblin returns no projectiles/aoe's/summons
    public boolean isProjectileReady()
    {
        return false;
    }
    
    public Projectile fireProjectile()
    {
        return null;
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
    
    public void updateMovingEnemy(int playerX, int playerY)
    {
        this.updateStatus();
        
        if(this.Frozen)
            return;
        
        this.updateImage(playerX);
        
        this.updatePosition();
        
        this.setAngle((int)(90 - Math.toDegrees(Math.atan2(playerX - this.getCenterX(), playerY - this.getCenterY()))));
    }
    
    private void updateImage(int playerX)
    {
        /*
        Image controller for SpearGoblin, uses a timer to move through image 
        array to determine which image needs to currently be used
        */
        if(this.ImageTimer == 7)
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
                this.ShadowX = 49; 
            }
            else
            {
                this.FacingRight = true;
                this.ShadowX = 48;
            }
            
            if(this.FacingRight)
            {
                this.CurrentSprite = this.MoveRightImages[this.CurrentFrame];
            }
            else
            {
                this.CurrentSprite = this.MoveLeftImages[this.CurrentFrame];
            }
            
            this.ImageTimer = 0;
        }
        this.ImageTimer++;
    }
    
}
