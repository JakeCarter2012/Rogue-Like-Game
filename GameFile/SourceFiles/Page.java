package SourceFiles;

import java.awt.Image;


public class Page extends StationaryObject {
    private int SpellNumber;
    
    public Page(int x, int y, int spell, Image img)
    {
        super(x, y, img);
        this.SpellNumber = spell;
    }
    
    public int getSpell()
    {
        return this.SpellNumber;
    }
}
