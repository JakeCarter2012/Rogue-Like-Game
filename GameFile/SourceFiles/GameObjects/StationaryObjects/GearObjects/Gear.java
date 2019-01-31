package SourceFiles.GameObjects.StationaryObjects.GearObjects;

import SourceFiles.GameObjects.StationaryObjects.StationaryObject;
import java.awt.Image;

public class Gear extends StationaryObject{
    /*
    Parent class for gear
    */
    protected int Vitality, Intellect, Flame, Frost, Dark, MoveSpeed;
    protected int Rarity, Level;
    protected String ItemName;
    private boolean Dropped;
    
    public Gear(int x, int y, Image sprite, int level)
    {
        super(x, y, sprite);
        
        this.Level = level;
        
        this.Dark = 0;
        this.Flame = 0;
        this.Frost = 0;
        this.Intellect = 0;
        this.Vitality = 0;
        this.MoveSpeed = 0;
        this.ItemName = "";
        this.Dropped = false;
    }
    
    public int getVitality()
    {
        return this.Vitality;
    }
    
    public int getIntellect()
    {
        return this.Intellect;
    }
    
    public int getFlame()
    {
        return this.Flame;
    }
    
    public int getFrost()
    {
        return this.Frost;
    }
    
    public int getDark()
    {
        return this.Dark;
    }
    
    public int getRarity()
    {
        return this.Rarity;
    }
    
    public int getLevel()
    {
        return this.Level;
    }
    
    public String getItemName()
    {
        return this.ItemName;
    }
    
    public boolean wasDropped()
    {
        return this.Dropped;
    }
    
    public void setDropped(boolean drop)
    {
        this.Dropped = drop;
    }
            
}
