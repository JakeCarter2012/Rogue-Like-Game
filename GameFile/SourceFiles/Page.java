package SourceFiles;

import java.awt.Image;


public class Page extends StationaryObject {
    private Spell PageSpell;
    
    public Page(int x, int y, Spell spell, Image img)
    {
        super(x, y, img);
        this.PageSpell = spell;
    }
    
    public String getSpellName()
    {
        return this.PageSpell.getSpellName();
    }
    
    public Spell getSpell()
    {
        return this.PageSpell;
    }
}
