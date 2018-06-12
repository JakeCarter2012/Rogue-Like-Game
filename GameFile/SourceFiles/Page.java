package SourceFiles;

import java.awt.Image;


public class Page extends StationaryObject {
    private int spellNumber;
    
    public Page(int x, int y, int spell, Image img)
    {
        super(x, y, img);
        this.spellNumber = spell;
    }
    
    public int getSpell()
    {
        return this.spellNumber;
    }
}
