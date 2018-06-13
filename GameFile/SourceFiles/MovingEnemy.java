package SourceFiles;

import java.awt.Image;

abstract public class MovingEnemy extends MovingObject{
    private Image[] Sprites;
    private int CurrentSprite;
    private int Health;
    private boolean Frozen, Burning;
    
    public MovingEnemy(int x, int y, int health, Image[] imgs)
    {
        super(x, y, imgs[0].getWidth(null), imgs[0].getHeight(null), 0, 0);
        this.Sprites = imgs;
        this.CurrentSprite = 0;
        this.Health = health;
        this.Frozen = false;
        this.Burning = false;
    }
    
    public Image getSprtie()
    {
        return this.Sprites[CurrentSprite];
    }
    
    public void dealDamage(int dmg)
    {
        this.Health -= dmg;
    }
    
    public boolean isDead()
    {
        if(this.Health <= 0)
            return true;
        else
            return false;
    }
    
    abstract public void updateMovingEnemy(int playerX, int playerY, boolean generalCollision,
            boolean horizontalCollision, boolean verticalCollision);
}
