package SourceFiles;


import java.awt.Image;

public class SpikeTrap extends GameObject{
    private Image[] sprites;
    private int currentSprite;
    private int damage;
    private boolean armed;
    private int trapTimer;
    
    public SpikeTrap(int x, int y, Image[] imgs)
    {
        super(x, y, imgs[0].getWidth(null), imgs[0].getHeight(null));
        this.sprites = imgs;
        this.currentSprite = 0;
        this.damage = 20;
        this.armed = false;
        this.trapTimer = 180;
    }
    
    public boolean isArmed()
    {
        return this.armed;
    }
    
    public int getDamage()
    {
        return this.damage;
    }
    
    public Image getSprite()
    {
        return this.sprites[currentSprite];
    }
    
    public void updateObject()
    {
        this.trapTimer--;
        
        //if trap is not armed, arm it when timer reaches 0
        if(!this.armed && this.trapTimer < 0)
        {
            this.armed = true;
            this.trapTimer = 60;
        }
        
        if(this.armed)
        {
            //move through sprites[] depending on timing for display image
            if(this.trapTimer < 5 || this.trapTimer > 55)
            {
                this.currentSprite = 1;
            }
            else if(this.trapTimer < 10 || this.trapTimer > 50)
            {
                this.currentSprite = 2;
            }
            else
                this.currentSprite = 3;
            
            if(this.trapTimer < 0)
            {
                this.armed = false;
                this.trapTimer = 180;
                this.currentSprite = 0;
            }
        }
    }
}
