/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SourceFiles.GameObjects.StationaryObjects.GearObjects;

import java.awt.Image;
import java.util.Random;

/**
 *
 * @author Jake
 */
public class Boots extends Gear{
    public Boots(int x, int y, Image sprite, int level, boolean flame, 
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
        
        this.Vitality = this.Level * 3 + rareValue;
        int primaryStat = this.Level * 2 + this.Rarity * 3;
        
        if(flame)
        {
            this.Flame = primaryStat;
            if(this.Rarity == 1)
            {
                this.MoveSpeed = 1;
                this.ItemName = "Worn Slippers of Flame";
            }
            else if (this.Rarity == 2)
            {
                this.MoveSpeed = 2;
                this.ItemName = "Slippers of Flame";
            }
            else
            {
                this.MoveSpeed = 2;
                this.ItemName = "Slippers of the Burning Path";
            }
        }
        else if(frost)
        {
            this.Frost = primaryStat;
            if(this.Rarity == 1)
            {
                this.MoveSpeed = 1;
                this.ItemName = "Worn Slippers of Frost";
            }
            else if (this.Rarity == 2)
            {
                this.MoveSpeed = 2;
                this.ItemName = "Slippers of Frost";
            }
            else
            {
                this.MoveSpeed = 2;
                this.ItemName = "Slippers of the Frozen Path";
            }
        }
        else if(dark)
        {
            this.Dark = primaryStat;
            if(this.Rarity == 1)
            {
                this.MoveSpeed = 1;
                this.ItemName = "Worn Slippers of the Void";
            }
            else if (this.Rarity == 2)
            {
                this.MoveSpeed = 2;
                this.ItemName = "Slippers of the Void";
            }
            else
            {
                this.MoveSpeed = 2;
                this.ItemName = "Slippers of the Astral Path";
            }
        }
    }
}
