package SourceFiles;

import java.awt.Image;

abstract public class MovingEnemy extends MovingObject{
    private Image[] sprites;
    private int currentSprite;
    private int health;
    private boolean frozen, burning;
    
    public MovingEnemy(int x, int y, int health, Image[] imgs)
    {
        super(x, y, imgs[0].getWidth(null), imgs[0].getHeight(null), 0, 0);
        this.sprites = imgs;
        this.currentSprite = 0;
        this.health = health;
        this.frozen = false;
        this.burning = false;
    }
    
    public Image getSprtie()
    {
        return this.sprites[currentSprite];
    }
    
    public void dealDamage(int dmg)
    {
        this.health -= dmg;
    }
    
    public boolean isDead()
    {
        if(this.health <= 0)
            return true;
        else
            return false;
    }
    
    abstract public void updateMovingEnemy(int playerX, int playerY, boolean generalCollision,
            boolean horizontalCollision, boolean verticalCollision);
}
