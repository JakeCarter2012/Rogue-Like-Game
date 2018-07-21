package SourceFiles;

import java.awt.Image;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Observer;
import java.util.Observable;

public class WizardPlayer extends MovingObject implements Observer{
    private Image[] WizRightForwardAttack, WizRightForward, WizRightBackAttack,
            WizRightAttack, WizRight, WizLeftForwardAttack, WizLeftForward,
            WizLeftBackwardAttack, WizLeftAttack, WizLeft, WizForwardAttack, 
            WizForward, WizBackAttack, WizBack;
    private Image[] CurrentSpriteSet;
    private Image CurrentSprite;
    private int LevitateCounter;
    private int BaseDamage, BonusDamage;
    private int MaxHealth, Currenthealth;
    private double AimAngle;
    private int MouseX, MouseY;
    private int FireX, FireY;
    private int BaseSpeed;
    private boolean Up, Down, Left, Right, Fire;
    private int UpKey, DownKey, LeftKey, RightKey, FireKey, SpellOneKey, 
            SpellTwoKey, SpellThreeKey, SpellFourKey;
    private int CurrentSpellPage;
    //placeholder
    private boolean RuneOne;
    private ArrayList<Spell> SpellBook;
    
    
    public WizardPlayer(int x, int y, int leftbound, int rightbound, int upbound, 
            int downbound, Image[] WizRightForwardAttack, Image[] WizRightForward, 
            Image[] WizRightBackAttack, Image[] WizRightAttack, Image[] WizRight, 
            Image[] WizLeftForwardAttack, Image[] WizLeftForward,
            Image[] WizLeftBackwardAttack, Image[] WizLeftAttack, Image[] WizLeft, 
            Image[] WizForwardAttack, Image[] WizForward, Image[] WizBackAttack, 
            Image[] WizBack)
    {
        super(x, y, leftbound,rightbound, upbound, downbound, WizForward[0].getWidth(null), 
                WizForward[0].getHeight(null), 0, 90);
        
        this.WizRightForwardAttack = WizRightForwardAttack;
        this.WizRightForward = WizRightForward;
        this.WizRightBackAttack = WizRightBackAttack;
        this.WizRightAttack = WizRightAttack;
        this.WizRight = WizRight;
        this.WizLeftForwardAttack = WizLeftForwardAttack;
        this.WizLeftForward = WizLeftForward;
        this.WizLeftBackwardAttack = WizLeftBackwardAttack;
        this.WizLeftAttack = WizLeftAttack;
        this.WizLeft = WizLeft;
        this.WizForwardAttack = WizForwardAttack;
        this.WizForward = WizForward;
        this.WizBackAttack = WizBackAttack;
        this.WizBack = WizBack;
        
        this.CurrentSpriteSet = this.WizForward;
        this.CurrentSprite = this.WizForward[0];
        this.MaxHealth = 100;
        this.Currenthealth = 100;
        this.BaseDamage = 4;
        this.BonusDamage = 0;
        this.UpKey = KeyEvent.VK_W;
        this.DownKey = KeyEvent.VK_S;
        this.LeftKey = KeyEvent.VK_A;
        this.RightKey = KeyEvent.VK_D;
        this.SpellOneKey = KeyEvent.VK_1;
        this.SpellTwoKey = KeyEvent.VK_2;
        this.SpellThreeKey = KeyEvent.VK_3;
        this.SpellFourKey = KeyEvent.VK_4;
        this.Up = this.Down = this.Left = this.Right = this.Fire = false;
        this.SpellBook = new ArrayList<Spell>();
        this.CurrentSpellPage = 0;
        this.AimAngle = 90;
        this.MouseX = 0;
        this.MouseY = 0;
        this.BaseSpeed = 8;
        this.LevitateCounter = 0;
        this.FireX = 0;
        this.FireY = 0;
        
        this.RuneOne= false;
    }
    
    public Spell addNewSpell(Spell spell)
    {
        if(this.SpellBook.size() <= 3)
        {
            this.SpellBook.add(spell);
            return null;
        }
        else
        {
            this.SpellBook.add(this.CurrentSpellPage, spell);
            Spell tempSpell = SpellBook.get(this.CurrentSpellPage + 1);
            this.SpellBook.remove(this.CurrentSpellPage + 1);
            return tempSpell;
        }
    }
    
    public int getCurrentSpellNumber()
    {
        return this.CurrentSpellPage;
    }
    
    public Image getSprite()
    {
        return this.CurrentSprite;
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
            return(new Projectile(this.FireX, this.FireY, this.getLeftBound(),
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
    
    public Spell getSpell(int index)
    {
        if(index > this.SpellBook.size() - 1 || index < 0)
            return null;
        
        return this.SpellBook.get(index);
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
                
                if(this.AimAngle <= 135 && this.AimAngle >= 45 && this.Fire)
                {
                    this.CurrentSpriteSet = this.WizForwardAttack;
                    this.FireX = this.getX() + 30;
                    this.FireY = this.getY() + 54;
                }
                else if(this.AimAngle <= 225 && this.AimAngle >= 135 && this.Fire)
                {
                    this.CurrentSpriteSet = this.WizLeftBackwardAttack;
                    this.FireX = this.getX();
                    this.FireY = this.getY() + 54;
                }
                else if(((this.AimAngle <= 315 && this.AimAngle >= 225) || (this.AimAngle <= -45)) && this.Fire)
                {
                    this.CurrentSpriteSet = this.WizBackAttack;
                    this.FireX = this.getX() + 30;
                    this.FireY = this.getY() + 30;
                }
                else if((this.AimAngle <= 45 || this.AimAngle >= 315) && this.Fire)
                {
                    this.CurrentSpriteSet = this.WizRightForwardAttack;
                    this.FireX = this.getX() + 62;
                    this.FireY = this.getY() + 54;
                }
                else
                {
                    this.CurrentSpriteSet = this.WizRightForward;
                    this.FireX = this.getX() + 62;
                    this.FireY = this.getY() + 54;
                }
            }
            else if(!Up && Down)
            {
                this.setAngle(45);
                
                if(this.AimAngle <= 135 && this.AimAngle >= 45 && this.Fire)
                {
                    this.CurrentSpriteSet = this.WizForwardAttack;
                    this.FireX = this.getX() + 30;
                    this.FireY = this.getY() + 54;
                }
                else if(this.AimAngle <= 225 && this.AimAngle >= 135 && this.Fire)
                {
                    this.CurrentSpriteSet = this.WizLeftBackwardAttack;
                    this.FireX = this.getX();
                    this.FireY = this.getY() + 54;
                }
                else if(((this.AimAngle <= 315 && this.AimAngle >= 225) || (this.AimAngle <= -45)) && this.Fire)
                {
                    this.CurrentSpriteSet = this.WizBackAttack;
                    this.FireX = this.getX() + 30;
                    this.FireY = this.getY() + 30;
                }
                else if((this.AimAngle <= 45 || this.AimAngle >= 315) && this.Fire)
                {
                    this.CurrentSpriteSet = this.WizRightForwardAttack;
                    this.FireX = this.getX() + 62;
                    this.FireY = this.getY() + 54;
                }
                else
                {
                    this.CurrentSpriteSet = this.WizRightForward;
                    this.FireX = this.getX() + 62;
                    this.FireY = this.getY() + 54;
                }
            }
            else
            {
                this.setAngle(0);
                
                if(this.AimAngle <= 135 && this.AimAngle >= 45 && this.Fire)
                {
                    this.CurrentSpriteSet = this.WizForwardAttack;
                    this.FireX = this.getX() + 30;
                    this.FireY = this.getY() + 54;
                }
                else if(this.AimAngle <= 225 && this.AimAngle >= 135 && this.Fire)
                {
                    this.CurrentSpriteSet = this.WizLeftBackwardAttack;
                    this.FireX = this.getX();
                    this.FireY = this.getY() + 54;
                }
                else if(((this.AimAngle <= 315 && this.AimAngle >= 225) || (this.AimAngle <= -45)) && this.Fire)
                {
                    this.CurrentSpriteSet = this.WizBackAttack;
                    this.FireX = this.getX() + 30;
                    this.FireY = this.getY() + 30;
                }
                else if((this.AimAngle <= 45 || this.AimAngle >= 315) && this.Fire)
                {
                    this.CurrentSpriteSet = this.WizRightForwardAttack;
                    this.FireX = this.getX() + 62;
                    this.FireY = this.getY() + 54;
                }
                else
                {
                    this.CurrentSpriteSet = this.WizRightForward;
                    this.FireX = this.getX() + 62;
                    this.FireY = this.getY() + 54;
                }
            }
        }
        else if(!Right && Left)
        {
            this.setSpeed(BaseSpeed);
            if(Up && !Down)
            {
                this.setAngle(225);
                if(this.AimAngle <= 135 && this.AimAngle >= 45 && this.Fire)
                {
                    this.CurrentSpriteSet = this.WizForwardAttack;
                    this.FireX = this.getX() + 30;
                    this.FireY = this.getY() + 54;
                }
                else if(this.AimAngle <= 225 && this.AimAngle >= 135 && this.Fire)
                {
                    this.CurrentSpriteSet = this.WizLeftForwardAttack;
                    this.FireX = this.getX();
                    this.FireY = this.getY() + 54;
                }
                else if(((this.AimAngle <= 315 && this.AimAngle >= 225) || (this.AimAngle <= -45)) && this.Fire)
                {
                    this.CurrentSpriteSet = this.WizBackAttack;
                    this.FireX = this.getX() + 30;
                    this.FireY = this.getY() + 30;
                }
                else if((this.AimAngle <= 45 || this.AimAngle >= 315) && this.Fire)
                {
                    this.CurrentSpriteSet = this.WizRightBackAttack;
                    this.FireX = this.getX() + 62;
                    this.FireY = this.getY() + 54;
                }
                else
                {
                    this.CurrentSpriteSet = this.WizLeftForward;
                    this.FireX = this.getX();
                    this.FireY = this.getY() + 54;
                }
            }
            else if(!Up && Down)
            {
                this.setAngle(135);
                
                if(this.AimAngle <= 135 && this.AimAngle >= 45 && this.Fire)
                {
                    this.CurrentSpriteSet = this.WizForwardAttack;
                    this.FireX = this.getX() + 30;
                    this.FireY = this.getY() + 54;
                }
                else if(this.AimAngle <= 225 && this.AimAngle >= 135 && this.Fire)
                {
                    this.CurrentSpriteSet = this.WizLeftForwardAttack;
                    this.FireX = this.getX();
                    this.FireY = this.getY() + 54;
                }
                else if(((this.AimAngle <= 315 && this.AimAngle >= 225) || (this.AimAngle <= -45)) && this.Fire)
                {
                    this.CurrentSpriteSet = this.WizBackAttack;
                    this.FireX = this.getX() + 30;
                    this.FireY = this.getY() + 30;
                }
                else if((this.AimAngle <= 45 || this.AimAngle >= 315) && this.Fire)
                {
                    this.CurrentSpriteSet = this.WizRightBackAttack;
                    this.FireX = this.getX() + 62;
                    this.FireY = this.getY() + 54;
                }
                else
                {
                    this.CurrentSpriteSet = this.WizLeftForward;
                    this.FireX = this.getX();
                    this.FireY = this.getY() + 54;
                }
            }
            else
            {
                this.setAngle(180);
                
                if(this.AimAngle <= 135 && this.AimAngle >= 45 && this.Fire)
                {
                    this.CurrentSpriteSet = this.WizForwardAttack;
                    this.FireX = this.getX() + 30;
                    this.FireY = this.getY() + 54;
                }
                else if(this.AimAngle <= 225 && this.AimAngle >= 135 && this.Fire)
                {
                    this.CurrentSpriteSet = this.WizLeftForwardAttack;
                    this.FireX = this.getX();
                    this.FireY = this.getY() + 54;
                }
                else if(((this.AimAngle <= 315 && this.AimAngle >= 225) || (this.AimAngle <= -45)) && this.Fire)
                {
                    this.CurrentSpriteSet = this.WizBackAttack;
                    this.FireX = this.getX() + 30;
                    this.FireY = this.getY() + 30;
                }
                else if((this.AimAngle <= 45 || this.AimAngle >= 315) && this.Fire)
                {
                    this.CurrentSpriteSet = this.WizRightBackAttack;
                    this.FireX = this.getX() + 62;
                    this.FireY = this.getY() + 54;
                }
                else
                {
                    this.CurrentSpriteSet = this.WizLeftForward;
                    this.FireX = this.getX();
                    this.FireY = this.getY() + 54;
                }
            }
        }
        else if(Up && !Down)
        {
            this.setSpeed(BaseSpeed);
            this.setAngle(270);
            
            if(this.AimAngle <= 135 && this.AimAngle >= 45 && this.Fire)
            {
                this.CurrentSpriteSet = this.WizForwardAttack;
                this.FireX = this.getX() + 30;
                this.FireY = this.getY() + 54;
            }
            else if(this.AimAngle <= 225 && this.AimAngle >= 135 && this.Fire)
            {
                this.CurrentSpriteSet = this.WizLeftAttack;
                this.FireX = this.getX();
                this.FireY = this.getY() + 54;
            }
            else if(((this.AimAngle <= 315 && this.AimAngle >= 225) || (this.AimAngle <= -45)) && this.Fire)
            {
                this.CurrentSpriteSet = this.WizBackAttack;
                this.FireX = this.getX() + 30;
                this.FireY = this.getY() + 30;
            }
            else if((this.AimAngle <= 45 || this.AimAngle >= 315) && this.Fire)
            {
                this.CurrentSpriteSet = this.WizRightAttack;
                this.FireX = this.getX() + 62;
                this.FireY = this.getY() + 54;
            }
            else
            {
                this.CurrentSpriteSet = this.WizBack;
                this.FireX = this.getX() + 30;
                this.FireY = this.getY() + 30;
            }
        }
        else if(!Up && Down)
        {
            this.setSpeed(BaseSpeed);
            this.setAngle(90);
            
            if(this.AimAngle <= 135 && this.AimAngle >= 45 && this.Fire)
            {
                this.CurrentSpriteSet = this.WizForwardAttack;
                this.FireX = this.getX() + 30;
                this.FireY = this.getY() + 54;
            }
            else if(this.AimAngle <= 225 && this.AimAngle >= 135 && this.Fire)
            {
                this.CurrentSpriteSet = this.WizLeftAttack;
                this.FireX = this.getX();
                this.FireY = this.getY() + 54;
            }
            else if(((this.AimAngle <= 315 && this.AimAngle >= 225) || (this.AimAngle <= -45)) && this.Fire)
            {
                this.CurrentSpriteSet = this.WizBackAttack;
                this.FireX = this.getX() + 30;
                this.FireY = this.getY() + 30;
            }
            else if((this.AimAngle <= 45 || this.AimAngle >= 315) && this.Fire)
            {
                this.CurrentSpriteSet = this.WizRightAttack;
                this.FireX = this.getX() + 62;
                this.FireY = this.getY() + 54;
            }
            else
            {
                this.CurrentSpriteSet = this.WizForward;
                this.FireX = this.getX() + 30;
                this.FireY = this.getY() + 54;
            }
        }
        else
        {
            this.setSpeed(0);
            
            if(this.AimAngle <= 135 && this.AimAngle >= 45 && this.Fire)
            {
                this.setAngle(90);
                this.CurrentSpriteSet = this.WizForwardAttack;
                this.FireX = this.getX() + 30;
                this.FireY = this.getY() + 54;
            }
            else if(this.AimAngle <= 225 && this.AimAngle >= 135 && this.Fire)
            {
                this.setAngle(180);
                this.CurrentSpriteSet = this.WizLeftAttack;
                this.FireX = this.getX();
                this.FireY = this.getY() + 54;
            }
            else if(((this.AimAngle <= 315 && this.AimAngle >= 225) || (this.AimAngle <= -45)) && this.Fire)
            {
                this.setAngle(270);
                this.CurrentSpriteSet = this.WizBackAttack;
                this.FireX = this.getX() + 30;
                this.FireY = this.getY() + 30;
            }
            else if((this.AimAngle <= 45 || this.AimAngle >= 315) && this.Fire)
            {
                this.setAngle(0);
                this.CurrentSpriteSet = this.WizRightAttack;
                this.FireX = this.getX() + 62;
                this.FireY = this.getY() + 54;
            }
            else
            {
                if(this.getAngle() <= 135 && this.getAngle() >= 45)
                {
                    this.CurrentSpriteSet = this.WizForward;
                    this.FireX = this.getX() + 30;
                    this.FireY = this.getY() + 54;
                }
                else if(this.getAngle() <= 225 && this.getAngle() >= 135)
                {
                    this.CurrentSpriteSet = this.WizLeft;
                    this.FireX = this.getX();
                    this.FireY = this.getY() + 54;
                }
                else if(this.getAngle() <= 315 && this.getAngle() >= 225)
                {
                    this.CurrentSpriteSet = this.WizBack;
                    this.FireX = this.getX() + 30;
                    this.FireY = this.getY() + 30;
                }
                else if(this.getAngle() <= 45 || this.getAngle() >= 315)
                {
                    this.CurrentSpriteSet = this.WizRight;
                    this.FireX = this.getX() + 62;
                    this.FireY = this.getY() + 54;
                }
            }
        }
        
        
        if(this.LevitateCounter < 15)
        {
            this.CurrentSprite = this.CurrentSpriteSet[0];
        }
        else if(this.LevitateCounter < 45 && this.LevitateCounter > 30)
        {
            this.CurrentSprite = this.CurrentSpriteSet[2];
        }
        
        else
        {
            this.CurrentSprite = this.CurrentSpriteSet[1];
        }
        
        
        for(int i = 0; i < this.SpellBook.size(); i++)
        {
            SpellBook.get(i).updateSpell();
        }
        
        this.MouseX = mouseX;
        this.MouseY = mouseY;
        this.AimAngle = 90 - Math.toDegrees(Math.atan2(mouseX - this.getCenterX(), mouseY - this.getCenterY()));
        if(this.LevitateCounter < 60)
            this.LevitateCounter++;
        else
            this.LevitateCounter = 0;
    }
    
    @Override
    public void update(Observable obj, Object arg){
        GameEvents ge = (GameEvents) arg;
        KeyEvent e = (KeyEvent) ge.event;
        //Left
        if(e.getKeyCode() == LeftKey)
        {
            if(e.getID() == KeyEvent.KEY_RELEASED)
            {
                Left = false;
            } 
            else if (e.getID() == KeyEvent.KEY_PRESSED)
            {
                Left = true;
            }
        }

        //Right
        if(e.getKeyCode() == RightKey)
        {
            if(e.getID() == KeyEvent.KEY_RELEASED)
            {
                Right = false;
            }
            else if (e.getID() == KeyEvent.KEY_PRESSED)
            {
                Right = true;
            }
        }

        //Up
        if(e.getKeyCode() == UpKey)
        {
            if(e.getID() == KeyEvent.KEY_RELEASED)
            {
                Up = false;
            }
            else if (e.getID() == KeyEvent.KEY_PRESSED)
            {
                Up = true;
            }
        }

        //Down
        if(e.getKeyCode() == DownKey)
        {
            if(e.getID() == KeyEvent.KEY_RELEASED)
            {
                Down = false;
            }
            else if (e.getID() == KeyEvent.KEY_PRESSED)
            {
                Down = true;
            }
        }
        
        //SpellSwapping
        if(e.getKeyCode() == SpellOneKey){
            if (e.getID() == KeyEvent.KEY_PRESSED)
            {
                if(this.SpellBook.size() > 0)
                    this.CurrentSpellPage = 0;
            }
        }
        
        if(e.getKeyCode() == SpellTwoKey){
            if (e.getID() == KeyEvent.KEY_PRESSED)
            {
                if(this.SpellBook.size() > 1)
                    this.CurrentSpellPage = 1;
            }
        }
        
        if(e.getKeyCode() == SpellThreeKey){
            if (e.getID() == KeyEvent.KEY_PRESSED)
            {
                if(this.SpellBook.size() > 2)
                    this.CurrentSpellPage = 2;
            }
        }
        
            if(e.getKeyCode() == SpellFourKey){
            if (e.getID() == KeyEvent.KEY_PRESSED)
            {
                if(this.SpellBook.size() > 3)
                    this.CurrentSpellPage = 3;
            }
        }
    }
}
