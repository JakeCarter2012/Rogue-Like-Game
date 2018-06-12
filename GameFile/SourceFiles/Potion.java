package SourceFiles;

import java.awt.Image;

public class Potion extends StationaryObject{
    private int healAmount;
    
    public Potion(int x, int y, int heal, Image img)
    {
        super(x, y, img);
        this.healAmount = heal;
    }
    
    public int getHealAmount()
    {
        return this.healAmount;
    }
}
