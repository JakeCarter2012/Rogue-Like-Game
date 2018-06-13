package SourceFiles;

import java.awt.Image;
import java.awt.event.KeyEvent;
import java.util.Observer;
import java.util.Observable;

public class WizardPlayer extends MovingObject implements Observer{
    private Image[] Sprites;
    private int CurrentSprite;
    private int BaseDamage, BonusDamage;
    private int MaxHealth, Currenthealth;
    private boolean Up, Down, Left, Right, Fire;
    private int UpKey, DownKey, LeftKey, RightKey, FireKey, SpellOneKey, 
            SpellTwoKey, SpellThreeKey, SpellFourKey;
    private int SCrrentSpellPage;
    //placeholder
    private boolean RuneOne;
    private Spell SpellOne, SpellTwo, SpellThree, SpellFour;
    
    public WizardPlayer(int x, int y, int health, Image[] imgs)
    {
        super(x, y, imgs[0].getWidth(null), imgs[0].getHeight(null), 0, 0);
        this.Sprites = imgs;
        this.CurrentSprite = 0;
        this.MaxHealth = 100;
        this.Currenthealth = 100;
        this.BaseDamage = 4;
        this.BonusDamage = 0;
        this.UpKey = KeyEvent.VK_W;
        this.DownKey = KeyEvent.VK_S;
        this.LeftKey = KeyEvent.VK_A;
        this.RightKey = KeyEvent.VK_D;
        //mouse event for lmb
        this.SpellOneKey = KeyEvent.VK_1;
        this.SpellTwoKey = KeyEvent.VK_2;
        this.SpellThreeKey = KeyEvent.VK_3;
        this.SpellFourKey = KeyEvent.VK_4;
        this.Up = this.Down = this.Left = this.Right = this.Fire = false;
        
        this.RuneOne= false;
    }
    
    
    
    public void updatePlayer(int mouseX, int mouseY)
    {
        
    }
    
    @Override
    public void update(Observable obj, Object arg){
        
        
    }
}
