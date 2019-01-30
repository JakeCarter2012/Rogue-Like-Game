package SourceFiles.GameObjects.Animations;

import SourceFiles.GameObjects.GameObject;
import java.awt.Image;

public class Animation extends GameObject{
    /*
    Animations are game objects that cycle through an array of images of time.
    They use a timer that is incremented eaach update to specificy the duration/
    time an image should change.
    */
    private boolean Repeat, Finished;
    private Image[] Sprites;
    private int Timer, CurrentTime, CurrentSprite;
    private int Angle;
    
    public Animation(int x, int y, int angle, Image[] imgs, int timer, boolean repeat)
    {
        super(x, y, imgs[0].getWidth(null), imgs[0].getHeight(null));
        this.Angle = angle;
        this.Repeat = repeat;
        this.Sprites = imgs;
        this.Timer = timer;
        this.CurrentTime = 0;
        this.CurrentSprite = 0;
        this.Finished = false;
    }
    
    public Image getSprite()
    {
        return this.Sprites[CurrentSprite];
    }
    
    public int getAngle()
    {
        return this.Angle;
    }
    
    public boolean isDone()
    {
        return this.Finished;
    }
    
    public void updateAnimation()
    {
        if(this.CurrentTime == this.Timer)
        {
            this.CurrentTime = 0;
            
            if((this.CurrentSprite == this.Sprites.length - 1) && this.Repeat)
            {
                //If the Animation is set to repeat, restart from beginning
                this.CurrentSprite = 0;
            }
            else if((this.CurrentSprite == this.Sprites.length - 1) && !this.Repeat)
            {
                this.Finished = true;
            }
            else
            {
                this.CurrentSprite++;
            }
        }
        else
        {
            this.CurrentTime++;
        }
    }
}
