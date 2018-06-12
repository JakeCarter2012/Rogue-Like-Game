package SourceFiles;

import java.awt.Image;
import java.awt.event.KeyEvent;
import java.util.Observer;
import java.util.Observable;

public class WizardPlayer extends MovingObject implements Observer{
    private Image[] sprites;
    private int currentSprite;
    private int baseDamage, bonusDamage;
    private int maxHealth, currenthealth;
    private boolean up, down, left, right, fire;
    private int upKey, downKey, leftKey, rightKey, fireKey, spellOneKey, 
            spellTwoKey, spellThreeKey, spellFourKey;
    private int currentSpellPage;
    //placeholder
    private boolean runeOne;
    private Spell spellOne, spellTwo, spellThree, spellFour;
    
    public WizardPlayer(int x, int y, int health, Image[] imgs)
    {
        super(x, y, imgs[0].getWidth(null), imgs[0].getHeight(null), 0, 0);
        this.sprites = imgs;
        this.currentSprite = 0;
        this.maxHealth = 100;
        this.currenthealth = 100;
        this.baseDamage = 4;
        this.bonusDamage = 0;
        this.upKey = KeyEvent.VK_W;
        this.downKey = KeyEvent.VK_S;
        this.leftKey = KeyEvent.VK_A;
        this.rightKey = KeyEvent.VK_D;
        //mouse event for lmb
        this.spellOneKey = KeyEvent.VK_1;
        this.spellTwoKey = KeyEvent.VK_2;
        this.spellThreeKey = KeyEvent.VK_3;
        this.spellFourKey = KeyEvent.VK_4;
        this.up = this.down = this.left = this.right = this.fire = false;
        
        this.runeOne= false;
    }
    
    public void updatePlayer(int mouseX, int mouseY)
    {
        
    }
    
    @Override
    public void update(Observable obj, Object arg){
        
        
    }
}
