package SourceFiles.GameLogic;

import SourceFiles.GameObjects.GameObject;
import SourceFiles.GameObjects.MovingObjects.MovingObject;
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
    public boolean EnemyCollision()
    {
        
        // for if both x/y collide
        return true;
        //collsion should move based on x/y between center of plsyer and object
        //collisions based off mid og player/object colliding with
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
