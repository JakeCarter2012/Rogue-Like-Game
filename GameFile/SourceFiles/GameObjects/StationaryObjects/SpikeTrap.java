package SourceFiles.GameObjects.StationaryObjects;


import SourceFiles.GameObjects.GameObject;
import java.awt.Image;

public class SpikeTrap extends GameObject{
    private Image[] Sprites;
    private int CurrentSprite;
    private int Damage;
    private boolean Armed;
    private int TrapTimer;
    
    public SpikeTrap(int x, int y, Image[] imgs)
    {
        super(x, y, imgs[0].getWidth(null), imgs[0].getHeight(null));
        this.Sprites = imgs;
        this.CurrentSprite = 0;
        this.Damage = 20;
        this.Armed = false;
        this.TrapTimer = 180;
    }
    
    public boolean isArmed()
    {
        return this.Armed;
    }
    
    public int getDamage()
    {
        return this.Damage;
    }
    
    public Image getSprite()
    {
        return this.Sprites[CurrentSprite];
    }
    
    public void updateObject()
    {
        this.TrapTimer--;
        
        //if trap is not armed, arm it when timer reaches 0
        if(!this.Armed && this.TrapTimer < 0)
        {
            this.Armed = true;
            this.TrapTimer = 60;
        }
        
        if(this.Armed)
        {
            //move through sprites[] depending on timing for display image
            if(this.TrapTimer < 5 || this.TrapTimer > 55)
            {
                this.CurrentSprite = 1;
            }
            else if(this.TrapTimer < 10 || this.TrapTimer > 50)
            {
                this.CurrentSprite = 2;
            }
            else
                this.CurrentSprite = 3;
            
            if(this.TrapTimer < 0)
            {
                this.Armed = false;
                this.TrapTimer = 180;
                this.CurrentSprite = 0;
            }
        }
    }
}
