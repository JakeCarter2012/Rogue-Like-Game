package SourceFiles;

import java.awt.Rectangle;

public class CollisionDetector {
    public boolean normalCollision(MovingObject mover, GameObject obj)
    {
        Rectangle moverRec = new Rectangle(mover.getX() + mover.getHorizontalSpeed(), 
                mover.getY() + mover.getVerticalSpeed(), mover.getWidth(), mover.getHeight());
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
                mover.getVerticalSpeed(), mover.getWidth(), mover.getHeight());
        Rectangle objRec = new Rectangle(obj.getX(), obj.getY(), obj.getWidth(), 
                obj.getHeight());
        
        if(moverRec.intersects(objRec))
            return true;
        else
            return false;
    }
    
    public boolean horizontalCollision(MovingObject mover, GameObject obj)
    {
        Rectangle moverRec = new Rectangle(mover.getX() + mover.getHorizontalSpeed(), 
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
