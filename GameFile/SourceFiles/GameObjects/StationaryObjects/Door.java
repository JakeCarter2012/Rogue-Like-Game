package SourceFiles.GameObjects.StationaryObjects;

import SourceFiles.GameObjects.GameObject;
import java.awt.Image;

public class Door extends GameObject{
    private Image[] Sprites;
    private int CurrentFrame, ImageTimer;
    private boolean Lock;
    
    public Door(int x, int y, int width, int height, Image[] imgs){
        super(x, y, width, height);
        this.Sprites = imgs;
        this.Lock = false;
        this.CurrentFrame = 0;
        this.ImageTimer = 0;
    }
    
    public boolean isLocked()
    {
        return this.Lock;
    }
    
    public void lockDoor()
    {
        this.Lock = true;
        this.ImageTimer = 0;
    }
    
    public void unlockDoor()
    {
        this.Lock = false;
        this.ImageTimer = 0;
    }
    
    public Image getSprite()
    {
        return this.Sprites[CurrentFrame];
    }
    
    public void updateDoor()
    {
        if(ImageTimer < 3)
        {
            ImageTimer++;
        }
        else if(ImageTimer == 3)
        {
            if(Lock)
            {
                if(CurrentFrame >= Sprites.length - 2)
                {
                    CurrentFrame = Sprites.length - 1;
                    ImageTimer++;
                }
                else
                {
                    CurrentFrame++;
                    ImageTimer = 0;
                }
            }
            else
            {
                if(CurrentFrame <= 1)
                {
                    CurrentFrame = 0;
                    ImageTimer++;
                }
                else
                {
                    CurrentFrame--;
                    ImageTimer = 0;
                }
            }
        }
    }
}
