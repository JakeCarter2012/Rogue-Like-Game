package SourceFiles.GameObjects.StationaryObjects.GearObjects;

import java.awt.Image;
import java.util.Random;

public class Neck extends Gear{
    public Neck(int x, int y, Image sprite, int level, boolean vitality,
            boolean flame, boolean frost, boolean dark)
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
        
        this.Intellect = this.Level * 2 + rareValue;
        int primaryStat = this.Level * 2 + this.Rarity * 3;
        int secondaryStat = this.Level + rareValue;
        
        if(vitality)
        {
            this.Vitality = primaryStat;
            
            int secondaryElement = rnd.nextInt(3);
            
            if(this.Rarity >= 2)
            {
                if(secondaryElement == 0)
                {
                    this.Dark = secondaryStat;
                }
                else if(secondaryElement == 1)
                {
                    this.Flame = secondaryStat;
                }
                else
                {
                    this.Frost = secondaryStat;
                }
                
                if(this.Rarity == 2)
                {
                    this.ItemName = "Emerald Amulet";
                }
                else
                {
                    this.ItemName = "Pristine Emerald Amulet";
                }
            }
            else
            {
                this.ItemName = "Tarnished Emerald Amulet";
            }
        }
        else if(flame)
        {
            this.Flame = primaryStat;
            if(this.Rarity == 1)
            {
                this.ItemName = "Tarnished Ruby Amulet";
            }
            else if(this.Rarity == 2)
            {
                this.ItemName = "Ruby Amulet";
                this.Vitality = secondaryStat;
            }
            else
            {
                this.ItemName = "Pristine Ruby Amulet";
                this.Vitality = secondaryStat;
            }
        }
        else if(frost)
        {
            this.Frost = primaryStat;
            if(this.Rarity == 1)
            {
                this.ItemName = "Tarnished Saphire Amulet";
            }
            else if(this.Rarity == 2)
            {
                this.ItemName = "Saphire Amulet";
                this.Vitality = secondaryStat;
            }
            else
            {
                this.ItemName = "Pristine Saphire Amulet";
                this.Vitality = secondaryStat;
            }
        }
        else if(dark)
        {
            this.Dark = primaryStat;
            if(this.Rarity == 1)
            {
                this.ItemName = "Tarnished Amethyst Amulet";
            }
            else if(this.Rarity == 2)
            {
                this.ItemName = "Amethyst Amulet";
                this.Vitality = secondaryStat;
            }
            else
            {
                this.ItemName = "Pristine Amethyst Amulet";
                this.Vitality = secondaryStat;
            }
        }
    }
}
