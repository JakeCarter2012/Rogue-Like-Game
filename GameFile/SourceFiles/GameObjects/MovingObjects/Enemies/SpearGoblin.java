package SourceFiles.GameObjects.MovingObjects.Enemies;

import SourceFiles.GameObjects.MovingObjects.Projectiles.Projectile;
import SourceFiles.GameObjects.StationaryObjects.AreaOfEffect;
import java.awt.Image;

public class SpearGoblin extends MovingEnemy{
    private Image[] MoveLeftImages, MoveRightImages;
    private int ImageTimer, CurrentFrame;
    private boolean FacingRight;
    private Image CurrentSprite;
    
    public SpearGoblin(int x, int y, int leftbound, int rightbound, int upbound, 
            int downbound, int floor, Image[] moveLeft, Image[] moveRight)
    {
        super(x, y, moveLeft[0].getWidth(null), moveLeft[0].getHeight(null), 
                leftbound, rightbound, upbound, downbound, 100 + 50 * floor, 5, 50 + 25 * floor,
                0, 0);
        this.MoveLeftImages = moveLeft;
        this.MoveRightImages = moveRight;
        this.ImageTimer = 0;
        this.CurrentFrame = 0;
        this.FacingRight = true;
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
    
    public void updateMovingEnemy(int playerX, int playerY, boolean generalCollision,
            boolean horizontalCollision, boolean verticalCollision)
    {
        this.updateStatus();
        
        if(this.Frozen)
            return;
        
        this.updateImage(playerX);
        
        this.updatePosition(generalCollision, horizontalCollision, verticalCollision);
        
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
            }
            else
            {
                this.FacingRight = true;
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
    
    private void updatePosition(boolean generalCollision, boolean horizontalCollision, boolean verticalCollision) 
    {
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
        
        //If outside of game's bounds, move back into the game's boundss
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
