package SourceFiles.GameObjects.MovingObjects.Player;

import SourceFiles.GameLogic.GameEvents;
import SourceFiles.GameObjects.MovingObjects.MovingObject;
import SourceFiles.GameObjects.StationaryObjects.PlayerAoe;
import SourceFiles.GameObjects.MovingObjects.Projectiles.PlayerProjectile;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Observer;
import java.util.Observable;

public class WizardPlayer extends MovingObject implements Observer{
    /*
    Class for the player's character
    */
    private Image[] WizRightForwardAttack, WizRightForward, WizRightBackAttack,
            WizRightAttack, WizRight, WizLeftForwardAttack, WizLeftForward,
            WizLeftBackwardAttack, WizLeftAttack, WizLeft, WizForwardAttack, 
            WizForward, WizBackAttack, WizBack;
    private Image[] CurrentSpriteSet;
    private Image CurrentSprite;
    private int LevitateCounter;
    private int BaseDamage, BonusDamage;
    private int MaxHealth, Currenthealth;
    private int Vitality, Intellect, Flame, Frost, Void;
    private int BurnTime, BurnDamage, FreezeTime, BonusBurnChance, BonusFreezeChance,
            BonusFireDamage, BonusFrostDamage, BonusVoidDamage;
    private double AimAngle;
    private int MouseX, MouseY;
    private int FireX, FireY;
    private int BaseSpeed;
    private boolean Up, Down, Left, Right, Fire, SwapDown, SwapUp;
    private int UpKey, DownKey, LeftKey, RightKey, UpKey2, DownKey2, LeftKey2, RightKey2,
            FireKey, SpellOneKey, SpellTwoKey, SpellThreeKey, SpellFourKey, PauseKey;
    private int CurrentSpellPage;
    //placeholder
    private boolean RuneOne;
    private ArrayList<Spell> SpellBook;
    private int DamageTimer, InternalCoolDownTimer;
    
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
        this.MaxHealth = 500;
        this.Currenthealth = 500;
        this.BaseDamage = 4;
        this.BonusDamage = 0;
        this.UpKey = KeyEvent.VK_W;
        this.DownKey = KeyEvent.VK_S;
        this.LeftKey = KeyEvent.VK_A;
        this.RightKey = KeyEvent.VK_D;
        this.UpKey2 = KeyEvent.VK_UP;
        this.DownKey2 = KeyEvent.VK_DOWN;
        this.LeftKey2 = KeyEvent.VK_LEFT;
        this.RightKey2 = KeyEvent.VK_RIGHT;
        this.SpellOneKey = KeyEvent.VK_1;
        this.SpellTwoKey = KeyEvent.VK_2;
        this.SpellThreeKey = KeyEvent.VK_3;
        this.SpellFourKey = KeyEvent.VK_4;
        this.PauseKey = KeyEvent.VK_P;
        this.Up = this.Down = this.Left = this.Right = this.Fire = this.SwapUp = 
                this.SwapDown = false;
        this.SpellBook = new ArrayList<Spell>();
        this.CurrentSpellPage = 0;
        this.AimAngle = 90;
        this.MouseX = 0;
        this.MouseY = 0;
        this.BaseSpeed = 6;
        this.LevitateCounter = 0;
        this.FireX = 0;
        this.FireY = 0;
        this.DamageTimer = 0;
        this.InternalCoolDownTimer = 0;
        
        this.Vitality = 0;  
        this.Intellect = 0;  
        this.Flame = 0;  
        this.Frost = 0;  
        this.Void = 0;
        this.BurnTime = 120;  
        this.BurnDamage = 1;  
        this.FreezeTime = 120;  
        this.BonusBurnChance = 0;  
        this.BonusFreezeChance = 0;  
        this.BonusFireDamage = 0;  
        this.BonusFrostDamage = 0;  
        this.BonusVoidDamage = 0;
        
        this.RuneOne= false;
    }
    
    public Spell addNewSpell(Spell spell)
    {
        //If spellbook is not full, add the spell; otherwise, swap it with the current spell
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
        //Returns the current sprite; if during the damage timer window, return
        //null for "blinking" immune effect
        if(this.DamageTimer >110 || (this.DamageTimer < 100 && this.DamageTimer > 90) 
                || (this.DamageTimer < 80 && this.DamageTimer > 70) || (this.DamageTimer < 60 && this.DamageTimer > 50)
                || (this.DamageTimer < 40 && this.DamageTimer > 30) || (this.DamageTimer < 20 && this.DamageTimer > 10))
        {
            return null;
        }
        else
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
        //if damage is over 50, start immune damage window for 2 seconds
        if(this.DamageTimer <= 0)
        {
            this.Currenthealth -= dmg;
            if(dmg >= 50)
                this.DamageTimer = 120;
        }
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
                this.SpellBook.get(CurrentSpellPage) instanceof ProjectileSpell &&
                this.InternalCoolDownTimer < 0)
        {
            return true;
        }
        else
            return false;
    }
    
    public int getBurnTime()
    {
        return this.BurnTime;
    }
    
    public int getFreezeTime()
    {
        return this.FreezeTime;
    }
    
    public int getBurnDamage()
    {
        return this.BurnDamage + this.Flame;
    }
    
    public int getCurrentHealth()
    {
        return this.Currenthealth;
    }
    
    public boolean isAoeReady()
    {
        if(Fire && this.SpellBook.get(CurrentSpellPage).offCooldown() && 
                this.SpellBook.get(CurrentSpellPage) instanceof AoeSpell &&
                this.InternalCoolDownTimer < 0)
        {
            return true;
        }
        else
            return false;
    }
    
    //fire/stopFire are used to signal the firing button is being pressed or not
    public void fire()
    {
        this.Fire = true;
    }
    
    public void stopFire()
    {
        this.Fire = false;
    }
    
    //scroll up/down are used to signal the scroll wheel has moved for spell swapping
    public void scrollDown()
    {
        this.SwapDown = true;
        this.SwapUp = false;
    }
    
    public void scrollUp()
    {
        this.SwapDown = false;
        this.SwapUp = true;
    }
    
    public PlayerProjectile fireProjectile()
    {
        /*
        fireProjectile creates a projectile from the currently selected projectile
        spell and creates it on the players location using the player's elemntal
        chance and damage
        */
        if(this.SpellBook.get(CurrentSpellPage) instanceof ProjectileSpell)
        {
            //internal cooldown is set to 30 to prevent swapping to another spell 
            //and immediately firing it
            this.InternalCoolDownTimer = 30;
            
            int spellDamage = 0, eleChance = 0;
            if(this.SpellBook.get(CurrentSpellPage).isFire())
            {
                spellDamage = spellDamage + this.BonusFireDamage + this.Flame;
                eleChance = eleChance + this.BonusBurnChance;
            }
            if(this.SpellBook.get(CurrentSpellPage).isIce())
            {
                spellDamage = spellDamage + this.BonusFrostDamage + this.Frost;
                eleChance = eleChance + this.BonusFreezeChance + this.Frost;
            }
            if(this.SpellBook.get(CurrentSpellPage).isVoid())
            {
                spellDamage = spellDamage + this.BonusVoidDamage + this.Void;
            }
            
            this.SpellBook.get(CurrentSpellPage).resetCoolDown();
            return(new PlayerProjectile(this.FireX, this.FireY, this.getLeftBound(),
                    this.getRightBound(), this.getUpBound(), this.getDownBound(),
                    ((ProjectileSpell)this.SpellBook.get(CurrentSpellPage)).getSpeed(), this.AimAngle, 
                    ((ProjectileSpell)this.SpellBook.get(CurrentSpellPage)).getDamage() + spellDamage + this.Intellect, 
                    this.SpellBook.get(CurrentSpellPage).isFire(),
                    this.SpellBook.get(CurrentSpellPage).isIce(),
                    this.SpellBook.get(CurrentSpellPage).isVoid(),
                    ((ProjectileSpell)this.SpellBook.get(CurrentSpellPage)).getElementChance() + eleChance,
                    ((ProjectileSpell)this.SpellBook.get(CurrentSpellPage)).getSprite(),
                    ((ProjectileSpell)this.SpellBook.get(CurrentSpellPage)).getShadow(),
                    ((ProjectileSpell)this.SpellBook.get(CurrentSpellPage)).getEndAnimation()));
        }
        else
            return null;
    }
    
    public PlayerAoe fireAoe()
    {
        /*
        fireAoe creates an aoe from the currently selected aoe spell and creates 
        it on the players location using the player's elemntal chance and damage
        */
        if(this.SpellBook.get(CurrentSpellPage) instanceof AoeSpell)
        {
            //internal cooldown is set to 30 to prevent swapping to another spell 
            //and immediately firing it
            this.InternalCoolDownTimer = 30;
            
            return(new PlayerAoe(this.MouseX, this.MouseY, 
                    ((AoeSpell)this.SpellBook.get(CurrentSpellPage)).getDuration(), 
                    ((AoeSpell)this.SpellBook.get(CurrentSpellPage)).getDamage(),
                    ((AoeSpell)this.SpellBook.get(CurrentSpellPage)).isFire(),
                    ((AoeSpell)this.SpellBook.get(CurrentSpellPage)).isIce(),
                    ((AoeSpell)this.SpellBook.get(CurrentSpellPage)).isVoid(),
                    ((AoeSpell)this.SpellBook.get(CurrentSpellPage)).getElementChance(),
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
            
    public void updatePlayer(int mouseX, int mouseY, boolean generalCollision, 
            boolean horizontalCollision, boolean verticalCollision)
    {
        //updatePlayer is used to call all update functions
        this.updatePosition(generalCollision, horizontalCollision, verticalCollision);
        
        this.updateAngle();
        
        this.swapSpells();
        
        for(int i = 0; i < this.SpellBook.size(); i++)
        {
            SpellBook.get(i).updateSpell();
        }
        
        this.MouseX = mouseX;
        this.MouseY = mouseY;
        this.AimAngle = 90 - Math.toDegrees(Math.atan2(mouseX - this.getCenterX(), mouseY - this.getCenterY()));
        
        this.DamageTimer--;
        this.InternalCoolDownTimer--;
        
        if(this.LevitateCounter < 60)
            this.LevitateCounter++;
        else
            this.LevitateCounter = 0;
    }
    
    private void swapSpells()
    {
        /*
        If the mouse wheel was scrolled, then the current spell is changed in the
        appropriate direction
        */
        if(SwapUp)
        {
            if(this.SpellBook.size() - 1 == this.CurrentSpellPage)
            {
                this.CurrentSpellPage = 0;
            }
            else
            {
                this.CurrentSpellPage++;
            }
            this.SwapUp = false;
        }
        else if(SwapDown)
        {
            if(this.CurrentSpellPage == 0)
            {
                this.CurrentSpellPage = this.SpellBook.size() - 1;
            }
            else
            {
                this.CurrentSpellPage--;
            }
        }
        this.SwapDown = false;
    }

    private void updateAngle() {
        /*
        updateAngle is used to decide both the directional angle the player is 
        moving, and which image array needs to be used to match it. it uses 
        player key inputs to determine which direction the player is moving and 
        facing. If the fire key is pressed, the player faces the direction of the
        aiming angle instead.
        */
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
                    this.FireY = this.getY() + 40;
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
    }

    private void updatePosition(boolean generalCollision, boolean horizontalCollision, boolean verticalCollision) 
    {
        /*
        If there is no general collision, move the player to the new location;
        otherwise "slide" the character vertically/horizontally based on which 
        collisions are not false.
        */
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
        
        //Move the player back into the game's bounds if they are outside the boundaries
        if(this.getX() < this.getLeftBound())
            this.setX(this.getLeftBound());
        if(this.getX() + this.getWidth() > this.getRightBound())
            this.setX(this.getRightBound() - this.getWidth());
        if(this.getY() < this.getUpBound())
            this.setY(this.getUpBound());
        if(this.getY() + this.getHeight() > this.getDownBound())
            this.setY(this.getDownBound() - this.getHeight());
    }
    
    @Override
    public void update(Observable obj, Object arg){
        /*
        GameEvent handler for keys being pressed to move the player
        */
        GameEvents ge = (GameEvents) arg;
        KeyEvent e = (KeyEvent) ge.event;
        //Left
        if(e.getKeyCode() == LeftKey || e.getKeyCode() == LeftKey2)
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
        if(e.getKeyCode() == RightKey || e.getKeyCode() == RightKey2)
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
        if(e.getKeyCode() == UpKey || e.getKeyCode() == UpKey2)
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
        if(e.getKeyCode() == DownKey || e.getKeyCode() == DownKey2)
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
