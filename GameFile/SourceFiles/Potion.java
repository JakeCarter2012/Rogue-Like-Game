package SourceFiles;

import java.awt.Image;

public class Potion extends StationaryObject{
    private int HealAmount;
    
    public Potion(int x, int y, int heal, Image img)
    {
        super(x, y, img);
        this.HealAmount = heal;
    }
    
    public int getHealAmount()
    {
        return this.HealAmount;
    }
}
