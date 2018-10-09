package SourceFiles.GameLogic;

import SourceFiles.GameObjects.GameObject;
import SourceFiles.GameObjects.MovingObjects.MovingObject;
import java.awt.Rectangle;

public class CollisionDetector {
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
    
    public boolean verticalCollision(MovingObject mover, GameObject obj)
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
    
    public boolean horizontalCollision(MovingObject mover, GameObject obj)
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
