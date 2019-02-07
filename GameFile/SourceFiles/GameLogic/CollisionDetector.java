package SourceFiles.GameLogic;

import SourceFiles.GameObjects.GameObject;
import SourceFiles.GameObjects.MovingObjects.MovingObject;
import SourceFiles.GameObjects.MovingObjects.Enemies.MovingEnemy;
import SourceFiles.GameObjects.MovingObjects.Player.WizardPlayer;
import java.awt.Rectangle;
import java.util.ArrayList;

public class CollisionDetector {
    /*
    CollisionDetector is used to test for collisions of GameObjects; it creates 
    two rectangless for the two objects being tested, and returns true if there 
    will be a collision, or false if there is no collision.
    */
    public boolean normalCollision(MovingObject mover, GameObject obj)
    {
        Rectangle moverRec = new Rectangle(mover.getX() + (int)Math.round(mover.getSpeed()*Math.cos(Math.toRadians(mover.getAngle()))), 
                mover.getY() + (int)Math.round(mover.getSpeed()*Math.sin(Math.toRadians(mover.getAngle()))), mover.getWidth(), mover.getHeight());
        Rectangle objRec = new Rectangle(obj.getX(), obj.getY(), obj.getWidth(), 
                obj.getHeight());
        
        if(moverRec.intersects(objRec))
            return true;
        else
            return false;
    }
    
    public void EnemyCollision(MovingEnemy enemy, GameObject obj, int playerX, int playerY)
    {
        Rectangle enemyRec = new Rectangle(enemy.getX() + (int)Math.round(enemy.getSpeed()*Math.cos(Math.toRadians(enemy.getAngle()))), 
                enemy.getY() + (int)Math.round(enemy.getSpeed()*Math.sin(Math.toRadians(enemy.getAngle()))), enemy.getWidth(), enemy.getHeight());
        Rectangle enemyRecRight = new Rectangle(enemy.getX() + enemy.getSpeed(), enemy.getY(), 
                enemy.getWidth(), enemy.getHeight());
        Rectangle enemyRecLeft = new Rectangle(enemy.getX() - enemy.getSpeed(), enemy.getY(),
                enemy.getWidth(), enemy.getHeight());
        Rectangle enemyRecDown = new Rectangle(enemy.getX(), enemy.getY() + 
                enemy.getSpeed(), enemy.getWidth(), enemy.getHeight());
        Rectangle enemyRecUp = new Rectangle(enemy.getX(), enemy.getY() - 
                enemy.getSpeed(), enemy.getWidth(), enemy.getHeight());
                
        calculateEnemyCollision(enemy, playerX, playerY, obj, enemyRec, enemyRecRight, 
            enemyRecLeft, enemyRecDown, enemyRecUp);
    }
    
    private void calculateEnemyCollision(MovingEnemy enemy, int playerX, int playerY, 
            GameObject obj, Rectangle enemyRec, Rectangle enemyRecRight, 
            Rectangle enemyRecLeft, Rectangle enemyRecDown, Rectangle enemyRecUp)
    {
        Rectangle objRec = new Rectangle(obj.getX(), obj.getY(), obj.getWidth(), 
                obj.getHeight());
        
        if(enemyRec.intersects(objRec))
        {
            enemy.setGeneralCollision();
        }
        if(enemyRecRight.intersects(objRec))
        {
            enemy.setCollisionRight();
            if(playerY - obj.getCenterY() > 0)
            {
                enemy.requestMoveDown();
            }
            else
            {
                enemy.requestMoveUp();
            }
        }
        if(enemyRecLeft.intersects(objRec))
        {
            enemy.setCollisionLeft();
            if(playerY - obj.getCenterY() > 0)
            {
                enemy.requestMoveDown();
            }
            else
            {
                enemy.requestMoveUp();
            }
        }
        if(enemyRecUp.intersects(objRec))
        {
            enemy.setCollisionUp();
            if(playerX - obj.getCenterX() > 0)
            {
                enemy.requestMoveRight();
            }
            else
            {
                enemy.requestMoveLeft();
            }
        }
        if(enemyRecDown.intersects(objRec))
        {
            enemy.setCollisionDown();
            if(playerX - obj.getCenterX() > 0)
            {
                enemy.requestMoveRight();
            }
            else
            {
                enemy.requestMoveLeft();
            }
        }
    }
    
    public void playerCollision(WizardPlayer player, GameObject obj)
    {
        Rectangle objRec = new Rectangle(obj.getX(), obj.getY(), obj.getWidth(), 
                obj.getHeight());
        
        if(!player.getGeneralCollision())
        {
            Rectangle playerRec = new Rectangle(player.getX() + (int)Math.round(player.getSpeed()*Math.cos(Math.toRadians(player.getAngle()))), 
                    player.getY() + (int)Math.round(player.getSpeed()*Math.sin(Math.toRadians(player.getAngle()))), player.getWidth(), player.getHeight());
            if(playerRec.intersects(objRec))
            {
                player.setGeneralCollision();
            }
        }
        
        if(player.getGeneralCollision())
        {
            if(!player.getVerticalCollision())
            {
                Rectangle verticalRec = new Rectangle(player.getX(), player.getY() + 
                        (int)Math.round(player.getSpeed()*Math.sin(Math.toRadians(player.getAngle()))), 
                        player.getWidth(), player.getHeight());
                
                if(verticalRec.intersects(objRec))
                {
                    player.setVerticalCollision();
                }
            }
            if(!player.getHorizontalCollision())
            {
                Rectangle horizontalRec = new Rectangle(player.getX() + 
                        (int)Math.round(player.getSpeed()*Math.cos(Math.toRadians(player.getAngle()))), 
                        player.getY(), player.getWidth(),player.getHeight());
                
                if(horizontalRec.intersects(objRec))
                {
                    player.setHorizontalCollision();
                }
            }
        }
    }
    
    public boolean verticalSlideCollision(MovingObject mover, GameObject obj)
    {
        Rectangle moverRec = new Rectangle(mover.getX(), mover.getY() + 
                (int)Math.round(mover.getSpeed()*Math.sin(Math.toRadians(mover.getAngle()))), mover.getWidth(), mover.getHeight());
        Rectangle objRec = new Rectangle(obj.getX(), obj.getY(), obj.getWidth(), 
                obj.getHeight());
        
        if(moverRec.intersects(objRec))
            return true;
        else
            return false;
    }
    
    public boolean horizontalSlideCollision(MovingObject mover, GameObject obj)
    {
        Rectangle moverRec = new Rectangle(mover.getX() + (int)Math.round(mover.getSpeed()*Math.cos(Math.toRadians(mover.getAngle()))), 
                mover.getY(), mover.getWidth(), mover.getHeight());
        Rectangle objRec = new Rectangle(obj.getX(), obj.getY(), obj.getWidth(), 
                obj.getHeight());
        
        if(moverRec.intersects(objRec))
            return true;
        else
            return false;
    }
    
    public boolean standingCollision(GameObject stander, GameObject obj)
    {
        Rectangle moverRec = new Rectangle(stander.getX(), stander.getY(), 
                stander.getWidth(), stander.getHeight());
        Rectangle objRec = new Rectangle(obj.getX(), obj.getY(), obj.getWidth(), 
                obj.getHeight());
        
        if(moverRec.intersects(objRec))
            return true;
        else
            return false;
    }
}
