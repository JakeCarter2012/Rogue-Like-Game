package SourceFiles.GameLogic;

import SourceFiles.GameObjects.GameObject;
import SourceFiles.GameObjects.MovingObjects.MovingObject;
import SourceFiles.GameObjects.MovingObjects.Enemies.MovingEnemy;
import java.awt.Rectangle;

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
        Rectangle objRec = new Rectangle(obj.getX(), obj.getY(), obj.getWidth(), 
                obj.getHeight());
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
    
    public boolean verticalCollision(MovingObject mover, GameObject obj)
    {
        Rectangle moverRec = new Rectangle(mover.getX(), mover.getY() + mover.getSpeed(),
                mover.getWidth(), mover.getHeight());
        Rectangle objRec = new Rectangle(obj.getX(), obj.getY(), obj.getWidth(), 
                obj.getHeight());
        
        if(moverRec.intersects(objRec))
            return true;
        else
            return false;
    }
    
    public boolean horizontalCollision(MovingObject mover, GameObject obj)
    {
        Rectangle moverRec = new Rectangle(mover.getX() + mover.getSpeed(), 
                mover.getY(), mover.getWidth(), mover.getHeight());
        Rectangle objRec = new Rectangle(obj.getX(), obj.getY(), obj.getWidth(), 
                obj.getHeight());
        
        if(moverRec.intersects(objRec))
            return true;
        else
            return false;
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
    
    public boolean standingCollision(GameObject mover, GameObject obj)
    {
        Rectangle moverRec = new Rectangle(mover.getX(), mover.getY(), 
                mover.getWidth(), mover.getHeight());
        Rectangle objRec = new Rectangle(obj.getX(), obj.getY(), obj.getWidth(), 
                obj.getHeight());
        
        if(moverRec.intersects(objRec))
            return true;
        else
            return false;
    }
}
