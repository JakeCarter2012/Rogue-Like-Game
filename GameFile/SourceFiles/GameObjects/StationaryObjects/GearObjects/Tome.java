package SourceFiles.GameObjects.StationaryObjects.GearObjects;

import java.awt.Image;
import java.util.Random;

public class Tome extends Gear{
    public Tome(int x, int y, Image sprite, int level, boolean flame, 
            boolean frost, boolean dark)
    {
        super(x, y, sprite, level);
        
        Random rnd = new Random();
        
        int rareValue = rnd.nextInt(10);
        
        if(rareValue < 5)
        {
            this.Rarity = 1;
        }
        else if (rareValue < 8)
        {
            this.Rarity = 2;
        }
        else
        {
            this.Rarity = 3;
        }
        
        this.Intellect = this.Level * 3 + rareValue;
        int primaryStat = this.Level * 2 + this.Rarity * 3;
        int secondaryStat = this.Level + rareValue;
        
        if(flame)
        {
            this.Flame = primaryStat;
            if(this.Rarity == 1)
            {
                this.ItemName = "Worn Tome of Flames";
            }
            else if(this.Rarity == 2)
            {
                this.ItemName = "Tome of Flames";
                if(rnd.nextInt(2) == 1)
                {
                    this.Dark = secondaryStat;
                }
                else
                {
                    this.Frost = secondaryStat;
                }
            }
            else if(this.Rarity == 3)
            {
                this.ItemName = "Master's Tome of Flames";
                this.Dark = secondaryStat;
                this.Frost = secondaryStat;
            }
        }
        else if(frost)
        {
            this.Frost = primaryStat;
            if(this.Rarity == 1)
            {
                this.ItemName = "Worn Tome of Frost";
            }
            else if(this.Rarity == 2)
            {
                this.ItemName = "Tome of Frost";
                if(rnd.nextInt(2) == 1)
                {
                    this.Dark = secondaryStat;
                }
                else
                {
                    this.Flame = secondaryStat;
                }
            }
            else if(this.Rarity == 3)
            {
                this.ItemName = "Master's Tome of Frost";
                this.Dark = secondaryStat;
                this.Flame = secondaryStat;
            }
        }
        else if(dark)
        {
            this.Dark = primaryStat;
            if(this.Rarity == 1)
            {
                this.ItemName = "Worn Tome of the Void";
            }
            else if(this.Rarity == 2)
            {
                this.ItemName = "Tome of the Void";
                if(rnd.nextInt(2) == 1)
                {
                    this.Flame = secondaryStat;
                }
                else
                {
                    this.Frost = secondaryStat;
                }
            }
            else if(this.Rarity == 3)
            {
                this.ItemName = "Master's Tome of the Void";
                this.Flame = secondaryStat;
                this.Frost = secondaryStat;
            }
        }
    }
}

