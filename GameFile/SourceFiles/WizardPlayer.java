package SourceFiles;

import java.awt.Image;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
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
    private int CurrentSpellPage;
    //placeholder
    private boolean RuneOne;
    private ArrayList<Spell> SpellBook;
    
    public WizardPlayer(int x, int y, Image[] imgs)
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
        this.SpellBook = new ArrayList<Spell>();
        this.CurrentSpellPage = 0;
        
        
        this.RuneOne= false;
    }
    
    public Image getSprite()
    {
        return this.Sprites[this.CurrentSprite];
    }
    
    public boolean isAlive()
    {
        if(this.Currenthealth > 0)
            return true;
        else
            return false;
    }
    
    public void takeDamage(int dmg)
    {
        this.Currenthealth -= dmg;
    }
    
    public void heal(int rest)
    {
        if(this.Currenthealth + rest > this.MaxHealth)
            this.Currenthealth = this.MaxHealth;
        else
            this.Currenthealth += rest;
    }
    
    public boolean isProjectileReady()
    {
        if(Fire && this.SpellBook.get(CurrentSpellPage).offCooldown() && 
                this.SpellBook.get(CurrentSpellPage) instanceof ProjectileSpell)
        {
            return true;
        }
        else
            return false;
    }
    
    public boolean isAoeReady()
    {
        if(Fire && this.SpellBook.get(CurrentSpellPage).offCooldown() && 
                this.SpellBook.get(CurrentSpellPage) instanceof AoeSpell)
        {
            return true;
        }
        else
            return false;
    }
    
    public Projectile fireProjectile()
    {
        //adjust x and y's later based on position facing
        //also need to edit speeds
        if(this.SpellBook.get(CurrentSpellPage) instanceof ProjectileSpell)
        {
            return(new Projectile(this.getCenterX(), this.getCenterY(), 
                    ((ProjectileSpell)this.SpellBook.get(CurrentSpellPage)).getSpeed(), 
                    ((ProjectileSpell)this.SpellBook.get(CurrentSpellPage)).getSpeed(), 
                    ((ProjectileSpell)this.SpellBook.get(CurrentSpellPage)).getDamage(), 
                    ((ProjectileSpell)this.SpellBook.get(CurrentSpellPage)).getSprite()));
        }
        else
            return null;
    }
    
    public AreaOfEffect fireAoe()
    {
        //adjust x and y's later based on position facing
        //also need to edit speeds
        if(this.SpellBook.get(CurrentSpellPage) instanceof AoeSpell)
        {
            return(new AreaOfEffect(this.getCenterX(), this.getCenterY(), 
                    ((AoeSpell)this.SpellBook.get(CurrentSpellPage)).getDuration(), 
                    ((AoeSpell)this.SpellBook.get(CurrentSpellPage)).getDamage(), 
                    ((AoeSpell)this.SpellBook.get(CurrentSpellPage)).getSprites()));
        }
        else
            return null;
    }
            
    //need take damage timer;
    public void updatePlayer(boolean generalCollision, 
            boolean horizontalCollision, boolean verticalCollision)
            //int mouseX, int mouseY,
    {
        
    }
    
    @Override
    public void update(Observable obj, Object arg){
        GameEvents ge = (GameEvents) arg;
        KeyEvent e = (KeyEvent) ge.event;
        //Left
        if(e.getKeyCode() == LeftKey){
            if(e.getID() == KeyEvent.KEY_RELEASED){
                Left = false;
            } else if (e.getID() == KeyEvent.KEY_PRESSED){
                Left = true;
            }
        }

        //Right
        if(e.getKeyCode() == RightKey){
            if(e.getID() == KeyEvent.KEY_RELEASED){
                Right = false;
            }else if (e.getID() == KeyEvent.KEY_PRESSED){
                Right = true;
            }
        }

        //Up
        if(e.getKeyCode() == UpKey){
            if(e.getID() == KeyEvent.KEY_RELEASED){
                Up = false;
            }else if (e.getID() == KeyEvent.KEY_PRESSED){
                Up = true;
            }
        }

        //Down
        if(e.getKeyCode() == DownKey){
            if(e.getID() == KeyEvent.KEY_RELEASED){
                Down = false;
            }else if (e.getID() == KeyEvent.KEY_PRESSED){
                Down = true;
            }
        }
    }
}
