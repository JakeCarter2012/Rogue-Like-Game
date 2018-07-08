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
    private double AimAngle;
    private int MouseX, MouseY;
    private int BaseSpeed;
    private boolean Up, Down, Left, Right, Fire;
    private int UpKey, DownKey, LeftKey, RightKey, FireKey, SpellOneKey, 
            SpellTwoKey, SpellThreeKey, SpellFourKey;
    private int CurrentSpellPage;
    //placeholder
    private boolean RuneOne;
    private ArrayList<Spell> SpellBook;
    
    public WizardPlayer(int x, int y, int leftbound, int rightbound, int upbound, 
            int downbound, Image[] imgs)
    {
        super(x, y, leftbound,rightbound, upbound, downbound, imgs[0].getWidth(null), 
                imgs[0].getHeight(null), 0, 0);
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
        this.AimAngle = 0;
        this.MouseX = 0;
        this.MouseY = 0;
        this.BaseSpeed = 8;
        
        SpellBook.add(new ProjectileSpell(5, 12, 30, imgs[0],imgs[0]));
        
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
    
    public void fire()
    {
        this.Fire = true;
    }
    
    public void stopFire()
    {
        this.Fire = false;
    }
    
    public Projectile fireProjectile()
    {
        //adjust x and y's later based on position facing
        //also need to edit speeds
        if(this.SpellBook.get(CurrentSpellPage) instanceof ProjectileSpell)
        {
            this.SpellBook.get(CurrentSpellPage).resetCoolDown();
            return(new Projectile(this.getCenterX(), this.getCenterY(), this.getLeftBound(), 
                    this.getRightBound(), this.getUpBound(), this.getDownBound(),
                    ((ProjectileSpell)this.SpellBook.get(CurrentSpellPage)).getSpeed(), 
                    this.AimAngle, ((ProjectileSpell)this.SpellBook.get(CurrentSpellPage)).getDamage(), 
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
    public void updatePlayer(int mouseX, int mouseY, boolean generalCollision, 
            boolean horizontalCollision, boolean verticalCollision)
            //int mouseX, int mouseY,?
    {
        //update player first, then change angles; just didnt collision detection
        //for previous direction
        if(!generalCollision)
        {
            this.setX(this.getX() + (int)Math.round(this.getSpeed()*Math.cos(Math.toRadians(this.getAngle()))));
            this.setY(this.getY() + (int)Math.round(this.getSpeed()*Math.sin(Math.toRadians(this.getAngle()))));
        }
        else if(!horizontalCollision)
        {
            this.setX(this.getX() + (int)Math.round(this.getSpeed()*Math.cos(Math.toRadians(this.getAngle()))));
        }
        else if(!verticalCollision)
        {
            this.setY(this.getY() + (int)Math.round(this.getSpeed()*Math.sin(Math.toRadians(this.getAngle()))));
        }
        
        if(this.getX() < this.getLeftBound())
            this.setX(this.getLeftBound());
        if(this.getX() + this.getWidth() > this.getRightBound())
            this.setX(this.getRightBound() - this.getWidth());
        if(this.getY() < this.getUpBound())
            this.setY(this.getUpBound());
        if(this.getY() + this.getHeight() > this.getDownBound())
            this.setY(this.getDownBound() - this.getHeight());
        
        if(Right && !Left)
        {
            this.setSpeed(BaseSpeed);
            if(Up && !Down)
            {
                this.setAngle(315);
            }
            else if(!Up && Down)
            {
                this.setAngle(45);
            }
            else
            {
                this.setAngle(0);
            }
        }
        else if(!Right && Left)
        {
            this.setSpeed(BaseSpeed);
            if(Up && !Down)
            {
                this.setAngle(225);
            }
            else if(!Up && Down)
            {
                this.setAngle(135);
            }
            else
            {
                this.setAngle(180);
            }
        }
        else if(Up && !Down)
        {
            this.setSpeed(BaseSpeed);
            this.setAngle(270);
        }
        else if(!Up && Down)
        {
            this.setSpeed(BaseSpeed);
            this.setAngle(90);
        }
        else
        {
            this.setSpeed(0);
        }
        
        for(int i = 0; i < this.SpellBook.size(); i++)
        {
            SpellBook.get(i).updateSpell();
        }
        
        this.MouseX = mouseX;
        this.MouseY = mouseY;
        this.AimAngle = 90 - Math.toDegrees(Math.atan2(mouseX - this.getCenterX(), mouseY - this.getCenterY()));
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
