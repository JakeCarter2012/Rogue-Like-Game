package SourceFiles;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.JPanel;
import java.awt.Image;

public class PlayGame extends JPanel {
    private ArrayList<ArrayList<Room>> Rooms;
    private int RoomsI, RoomsJ;
    private BufferedImage bufImg;
    private Graphics2D g2d;
    private Image Floor;
    private final int ScreenWidth = 1280, ScreenHeight = 960;
    private final int FPS = 60;
    private boolean levelFinished;
    private WizardPlayer Player;
    private int Money;
    
    public void init()
    {
        try{
            
        } catch (Exception e) {
            System.out.print(e.getStackTrace() + " Error loading resources");
        }
    }
    
    public void levelInit()
    {
        Rooms = new ArrayList<ArrayList<Room>>();
        
    }
    
    public void timerLoop()
    {
        //previous time previous loop started
        long currTime;
        //target time hoping to aim for each loop
        long targetTime = 1000000000 / FPS;

        while ((Player.isAlive()) && !levelFinished) {
            currTime = System.nanoTime();

            updateGame();
            repaint();

            //sleep for any remaining time
            //if statement for potential negative time, then don't sleep
            if ((currTime - System.nanoTime() + targetTime) > 0) {
                try {
                    Thread.sleep((currTime - System.nanoTime() + targetTime) / 1000000);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
    
    public void updateGame()
    {
        CollisionDetector col = new CollisionDetector();
        
        boolean generalCol = false;
        boolean verticalCol = false;
        boolean horizontalCol = false;
        
        //first test moving collisions for the player first
        for(int i = 0; i < Rooms.get(RoomsI).get(RoomsJ).WallSize(); i++)
        {
            //if both collisions are already true, no need to waste resources and
            //continue checking
            if(verticalCol && horizontalCol)
            {
                break;
            }
            
            //test for normal collision first, and if there is a collision, then
            //test for vertical/horizontal collisions
            if(col.normalCollision(Player, Rooms.get(RoomsI).get(RoomsJ).getWall(i)))
            {
                generalCol = true;
                
                if(col.horizontalCollision(Player, Rooms.get(RoomsI).get(RoomsJ).getWall(i)))
                {
                    horizontalCol = true;
                }
                
                if(col.verticalCollision(Player, Rooms.get(RoomsI).get(RoomsJ).getWall(i)))
                {
                    verticalCol = true;
                }
            }
        }
        
        for(int i = 0; i < Rooms.get(RoomsI).get(RoomsJ).BarrelSize(); i++)
        {
            if(verticalCol && horizontalCol)
            {
                break;
            }
            
            if(col.normalCollision(Player, Rooms.get(RoomsI).get(RoomsJ).getBarrel(i)))
            {
                generalCol = true;
                
                if(col.horizontalCollision(Player, Rooms.get(RoomsI).get(RoomsJ).getBarrel(i)))
                {
                    horizontalCol = true;
                }
                
                if(col.verticalCollision(Player, Rooms.get(RoomsI).get(RoomsJ).getBarrel(i)))
                {
                    verticalCol = true;
                }
            }
        }
        
        for(int i = 0; i < Rooms.get(RoomsI).get(RoomsJ).WallSize(); i++)
        {
            //if both collisions are already true, no need to waste resources and
            //continue checking
            if(verticalCol && horizontalCol)
            {
                break;
            }
            
            //test for normal collision first, and if there is a collision, then
            //test for vertical/horizontal collisions
            if(col.normalCollision(Player, Rooms.get(RoomsI).get(RoomsJ).getWall(i)))
            {
                generalCol = true;
                
                if(col.horizontalCollision(Player, Rooms.get(RoomsI).get(RoomsJ).getWall(i)))
                {
                    horizontalCol = true;
                }
                
                if(col.verticalCollision(Player, Rooms.get(RoomsI).get(RoomsJ).getWall(i)))
                {
                    verticalCol = true;
                }
            }
        }
        
        for(int i = 0; i < Rooms.get(RoomsI).get(RoomsJ).EnemySize(); i++)
        {
            if(col.normalCollision(Player, Rooms.get(RoomsI).get(RoomsJ).getEnemy(i)))
            {
                generalCol = true;
                
                Player.takeDamage(Rooms.get(RoomsI).get(RoomsJ).getEnemy(i).getBumpDamage());
                
                if(col.horizontalCollision(Player, Rooms.get(RoomsI).get(RoomsJ).getEnemy(i)))
                {
                    horizontalCol = true;
                }
                
                if(col.verticalCollision(Player, Rooms.get(RoomsI).get(RoomsJ).getEnemy(i)))
                {
                    verticalCol = true;
                }
            }
        }
        
        //now update the player's position
        Player.updatePlayer(generalCol, horizontalCol, verticalCol);
        
        //now test for remaining collisions based on where the player ends up standing
        for(int i = 0; i < Rooms.get(RoomsI).get(RoomsJ).CoinSize(); i++)
        {
            if(col.standingCollision(Player, Rooms.get(RoomsI).get(RoomsJ).getCoin(i)))
            {
                Money += Rooms.get(RoomsI).get(RoomsJ).getCoin(i).getValue();
                Rooms.get(RoomsI).get(RoomsJ).removeCoin(i);
                i--;
            }
        }
        
        for(int i = 0; i < Rooms.get(RoomsI).get(RoomsJ).PotionSize(); i++)
        {
            if(col.standingCollision(Player, Rooms.get(RoomsI).get(RoomsJ).getPotion(i)))
            {
                Player.heal(Rooms.get(RoomsI).get(RoomsJ).getPotion(i).getHealAmount());
                Rooms.get(RoomsI).get(RoomsJ).removePotion(i);
                i--;
            }
        }
        
        for(int i = 0; i < Rooms.get(RoomsI).get(RoomsJ).EnemyAoeSize(); i++)
        {
            if(col.standingCollision(Player, Rooms.get(RoomsI).get(RoomsJ).getEnemyAoe(i)))
            {
                Player.takeDamage(Rooms.get(RoomsI).get(RoomsJ).getEnemyAoe(i).getDamage());
            }
        }
        
        for(int i = 0; i < Rooms.get(RoomsI).get(RoomsJ).SpikeTrapSize(); i++)
        {
            if(col.standingCollision(Player, Rooms.get(RoomsI).get(RoomsJ).getSpikeTrap(i)))
            {
                Player.takeDamage(Rooms.get(RoomsI).get(RoomsJ).getSpikeTrap(i).getDamage());
                //break after touching a trap; dont want to take double damage if
                //standing on two traps
                break;
            }
        }
        
        //now have player fire spell if ready
        if(Player.isProjectileReady())
        {
            Rooms.get(RoomsI).get(RoomsJ).addPlayerProjectile(Player.fireProjectile());
        }
        
        if(Player.isAoeReady())
        {
            Rooms.get(RoomsI).get(RoomsJ).addPlayerAoe(Player.fireAoe());
        }
        
        //now test for collisions for enemies 
        
        ////HAVE AOE COLLISIONS WITH BARRELS CHECK WHEN THEY'RE CREATED
        for(int i = 0; i < Rooms.get(RoomsI).get(RoomsJ).EnemySize(); i++)
        {
            generalCol = false;
            verticalCol = false;
            horizontalCol = false;
            
            for(int j = 0; j < Rooms.get(RoomsI).get(RoomsJ).WallSize(); j++)
            {
                if(verticalCol && horizontalCol)
                {
                    break;
                }

                if(col.normalCollision(Rooms.get(RoomsI).get(RoomsJ).getEnemy(i), 
                        Rooms.get(RoomsI).get(RoomsJ).getWall(j)))
                {
                    generalCol = true;

                    if(col.horizontalCollision(Rooms.get(RoomsI).get(RoomsJ).getEnemy(i), 
                            Rooms.get(RoomsI).get(RoomsJ).getWall(j)))
                    {
                        horizontalCol = true;
                    }

                    if(col.verticalCollision(Rooms.get(RoomsI).get(RoomsJ).getEnemy(i), 
                            Rooms.get(RoomsI).get(RoomsJ).getWall(j)))
                    {
                        verticalCol = true;
                    }
                }
            }

            for(int j = 0; j < Rooms.get(RoomsI).get(RoomsJ).BarrelSize(); j++)
            {
                if(verticalCol && horizontalCol)
                {
                    break;
                }

                if(col.normalCollision(Rooms.get(RoomsI).get(RoomsJ).getEnemy(i), 
                        Rooms.get(RoomsI).get(RoomsJ).getBarrel(j)))
                {
                    generalCol = true;

                    if(col.horizontalCollision(Rooms.get(RoomsI).get(RoomsJ).getEnemy(i), 
                            Rooms.get(RoomsI).get(RoomsJ).getBarrel(j)))
                    {
                        horizontalCol = true;
                    }

                    if(col.verticalCollision(Rooms.get(RoomsI).get(RoomsJ).getEnemy(i), 
                            Rooms.get(RoomsI).get(RoomsJ).getBarrel(j)))
                    {
                        verticalCol = true;
                    }
                }
            }
            
            if(col.normalCollision(Rooms.get(RoomsI).get(RoomsJ).getEnemy(i), Player))
            {
                generalCol = true;

                if(col.horizontalCollision(Rooms.get(RoomsI).get(RoomsJ).getEnemy(i), Player))
                {
                    horizontalCol = true;
                }

                if(col.verticalCollision(Rooms.get(RoomsI).get(RoomsJ).getEnemy(i), Player))
                {
                    verticalCol = true;
                }
            }
            
            Rooms.get(RoomsI).get(RoomsJ).getEnemy(i).updateMovingEnemy(Player.getCenterX(), 
                    Player.getCenterY(), generalCol, horizontalCol, verticalCol);
            
            for(int j = 0; j < Rooms.get(RoomsI).get(RoomsJ).PlayerAoeSize(); j++)
            {
                if(col.standingCollision(Rooms.get(RoomsI).get(RoomsJ).getEnemy(i), 
                        Rooms.get(RoomsI).get(RoomsJ).getPlayerAoe(j)))
                {
                    Rooms.get(RoomsI).get(RoomsJ).getEnemy(i).takeDamage(Rooms.get(RoomsI).get(RoomsJ).getPlayerAoe(j).getDamage());
                }
            }
            
            for(int j = 0; j < Rooms.get(RoomsI).get(RoomsJ).SpikeTrapSize(); j++)
            {
                if(col.standingCollision(Rooms.get(RoomsI).get(RoomsJ).getEnemy(i), 
                        Rooms.get(RoomsI).get(RoomsJ).getSpikeTrap(i)))
                {
                    Rooms.get(RoomsI).get(RoomsJ).getEnemy(i).takeDamage(Rooms.get(RoomsI).get(RoomsJ).getSpikeTrap(i).getDamage());
                }
            }
            
            //fire for enemies
            if(Rooms.get(RoomsI).get(RoomsJ).getEnemy(i).isProjectileReady())
            {
                Rooms.get(RoomsI).get(RoomsJ).addEnemyProjectile(Rooms.get(RoomsI).get(RoomsJ).getEnemy(i).fireProjectile());
            }
            
            if(Rooms.get(RoomsI).get(RoomsJ).getEnemy(i).isAoeReady())
            {
                Rooms.get(RoomsI).get(RoomsJ).addEnemyAoe(Rooms.get(RoomsI).get(RoomsJ).getEnemy(i).fireAoe());
            }
        }
        
        //now test for collisions for player projectiles
        //also need to add elemental effects handling here?
        for(int i = 0; i < Rooms.get(RoomsI).get(RoomsJ).PlayerProjectileSize(); i++)
        {
            for(int j = 0; j < Rooms.get(RoomsI).get(RoomsJ).EnemySize(); j++)
            {
                if(col.normalCollision(Rooms.get(RoomsI).get(RoomsJ).getPlayerProjectile(i), Rooms.get(RoomsI).get(RoomsJ).getEnemy(j)))
                {
                    Rooms.get(RoomsI).get(RoomsJ).getEnemy(j).takeDamage(Rooms.get(RoomsI).get(RoomsJ).getPlayerProjectile(i).getDamage());
                    Rooms.get(RoomsI).get(RoomsJ).removePlayerProjectile(i);
                    i--;
                }
            }
            for(int j = 0; j < Rooms.get(RoomsI).get(RoomsJ).WallSize(); j++)
            {
                if(col.normalCollision(Rooms.get(RoomsI).get(RoomsJ).getPlayerProjectile(i), Rooms.get(RoomsI).get(RoomsJ).getWall(j)))
                {
                    Rooms.get(RoomsI).get(RoomsJ).removePlayerProjectile(i);
                    i--;
                }
            }
            for(int j = 0; j < Rooms.get(RoomsI).get(RoomsJ).BarrelSize(); j++)
            {
                if(col.normalCollision(Rooms.get(RoomsI).get(RoomsJ).getPlayerProjectile(i), Rooms.get(RoomsI).get(RoomsJ).getBarrel(j)))
                {
                    Rooms.get(RoomsI).get(RoomsJ).removeBarrel(j);
                    j--;
                    Rooms.get(RoomsI).get(RoomsJ).removePlayerProjectile(i);
                    i--;
                }
            }
        }
        
        //enemy projectiles
        for(int i = 0; i < Rooms.get(RoomsI).get(RoomsJ).EnemyProjectileSize(); i++)
        {
            if(col.normalCollision(Rooms.get(RoomsI).get(RoomsJ).getEnemyProjectile(i), Player))
            {
                Player.takeDamage(Rooms.get(RoomsI).get(RoomsJ).getEnemyProjectile(i).getDamage());
                Rooms.get(RoomsI).get(RoomsJ).removeEnemyProjectile(i);
                i--;
            }
            for(int j = 0; j < Rooms.get(RoomsI).get(RoomsJ).WallSize(); j++)
            {
                if(col.normalCollision(Rooms.get(RoomsI).get(RoomsJ).getEnemyProjectile(i), Rooms.get(RoomsI).get(RoomsJ).getWall(j)))
                {
                    Rooms.get(RoomsI).get(RoomsJ).removeEnemyProjectile(i);
                    i--;
                }
            }
            for(int j = 0; j < Rooms.get(RoomsI).get(RoomsJ).BarrelSize(); j++)
            {
                if(col.normalCollision(Rooms.get(RoomsI).get(RoomsJ).getEnemyProjectile(i), Rooms.get(RoomsI).get(RoomsJ).getBarrel(j)))
                {
                    Rooms.get(RoomsI).get(RoomsJ).removeBarrel(j);
                    j--;
                    Rooms.get(RoomsI).get(RoomsJ).removeEnemyProjectile(i);
                    i--;
                }
            }
        }
        
        //now update traps/aoes; timers for non-movers
        for(int i = 0; i < Rooms.get(RoomsI).get(RoomsJ).EnemyAoeSize(); i++)
        {
            Rooms.get(RoomsI).get(RoomsJ).getEnemyAoe(i).updateObject();
            if(Rooms.get(RoomsI).get(RoomsJ).getEnemyAoe(i).isDone())
            {
                Rooms.get(RoomsI).get(RoomsJ).removeEnemyAoe(i);
                i--;
            }
        }
        
        for(int i = 0; i < Rooms.get(RoomsI).get(RoomsJ).PlayerAoeSize(); i++)
        {
            Rooms.get(RoomsI).get(RoomsJ).getPlayerAoe(i).updateObject();
            if(Rooms.get(RoomsI).get(RoomsJ).getPlayerAoe(i).isDone())
            {
                Rooms.get(RoomsI).get(RoomsJ).removePlayerAoe(i);
                i--;
            }
        }
        
        for(int i = 0; i < Rooms.get(RoomsI).get(RoomsJ).SpikeTrapSize(); i++)
        {
            Rooms.get(RoomsI).get(RoomsJ).getSpikeTrap(i).updateObject();
        }
    }
    
    public void mainMenu()
    {
        
    }
    
    @Override
    public void paintComponent(Graphics g) {
        if (bufImg == null) {
            bufImg = (BufferedImage) createImage(ScreenWidth, ScreenHeight);
        }
        Graphics2D gtemp = (Graphics2D) g;
        g2d = bufImg.createGraphics();
        super.paintComponent(gtemp);
        
        g2d.drawImage(Floor, 0, 0, this);
        //paint order: player > enemy> projectile > itmes > wall
        //lowest prio is first
        
        for(int i = 0; i < Rooms.get(RoomsI).get(RoomsJ).BarrelSize(); i++)
        {
           g2d.drawImage(Rooms.get(RoomsI).get(RoomsJ).getBarrel(i).getSprite(), 
                   Rooms.get(RoomsI).get(RoomsJ).getBarrel(i).getX(), 
                   Rooms.get(RoomsI).get(RoomsJ).getBarrel(i).getY(), this);
        }
        
        for(int i = 0; i < Rooms.get(RoomsI).get(RoomsJ).SpikeTrapSize(); i++)
        {
            g2d.drawImage(Rooms.get(RoomsI).get(RoomsJ).getSpikeTrap(i).getSprite(), 
                    Rooms.get(RoomsI).get(RoomsJ).getSpikeTrap(i).getX(), 
                    Rooms.get(RoomsI).get(RoomsJ).getSpikeTrap(i).getY(), this);
        }
        
        for(int i = 0; i < Rooms.get(RoomsI).get(RoomsJ).EnemyAoeSize(); i++)
        {
            g2d.drawImage(Rooms.get(RoomsI).get(RoomsJ).getEnemyAoe(i).getSprite(),
                    Rooms.get(RoomsI).get(RoomsJ).getEnemyAoe(i).getX(), 
                    Rooms.get(RoomsI).get(RoomsJ).getEnemyAoe(i).getY(), this);
        }
        
        for(int i = 0; i < Rooms.get(RoomsI).get(RoomsJ).PlayerAoeSize(); i++)
        {
            g2d.drawImage(Rooms.get(RoomsI).get(RoomsJ).getPlayerAoe(i).getSprite(), 
                    Rooms.get(RoomsI).get(RoomsJ).getPlayerAoe(i).getX(), 
                    Rooms.get(RoomsI).get(RoomsJ).getPlayerAoe(i).getY(), this);
        }
        
        for(int i = 0; i < Rooms.get(RoomsI).get(RoomsJ).WallSize(); i++)
        {
           g2d.drawImage(Rooms.get(RoomsI).get(RoomsJ).getWall(i).getSprite(), 
                   Rooms.get(RoomsI).get(RoomsJ).getWall(i).getX(), 
                   Rooms.get(RoomsI).get(RoomsJ).getWall(i).getY(), this);
        }
        
        for(int i = 0; i < Rooms.get(RoomsI).get(RoomsJ).CoinSize(); i++)
        {
           g2d.drawImage(Rooms.get(RoomsI).get(RoomsJ).getCoin(i).getSprite(), 
                   Rooms.get(RoomsI).get(RoomsJ).getCoin(i).getX(),
                   Rooms.get(RoomsI).get(RoomsJ).getCoin(i).getY(), this);
        }
        
        for(int i = 0; i < Rooms.get(RoomsI).get(RoomsJ).PotionSize(); i++)
        {
           g2d.drawImage(Rooms.get(RoomsI).get(RoomsJ).getPotion(i).getSprite(), 
                   Rooms.get(RoomsI).get(RoomsJ).getPotion(i).getX(), 
                   Rooms.get(RoomsI).get(RoomsJ).getPotion(i).getY(), this);
        }
        
        for(int i = 0; i < Rooms.get(RoomsI).get(RoomsJ).PotionSize(); i++)
        {
           g2d.drawImage(Rooms.get(RoomsI).get(RoomsJ).getPotion(i).getSprite(),
                   Rooms.get(RoomsI).get(RoomsJ).getPotion(i).getX(), 
                   Rooms.get(RoomsI).get(RoomsJ).getPotion(i).getY(), this);
        }
        
        for(int i = 0; i < Rooms.get(RoomsI).get(RoomsJ).PageSize(); i++)
        {
           g2d.drawImage(Rooms.get(RoomsI).get(RoomsJ).getPage(i).getSprite(), 
                   Rooms.get(RoomsI).get(RoomsJ).getPage(i).getX(), 
                   Rooms.get(RoomsI).get(RoomsJ).getPage(i).getY(), this);
        }
        
        for(int i = 0; i < Rooms.get(RoomsI).get(RoomsJ).RuneSize(); i++)
        {
           g2d.drawImage(Rooms.get(RoomsI).get(RoomsJ).getRune(i).getSprite(), 
                   Rooms.get(RoomsI).get(RoomsJ).getRune(i).getX(), 
                   Rooms.get(RoomsI).get(RoomsJ).getRune(i).getY(), this);
        }
        
        for(int i = 0; i < Rooms.get(RoomsI).get(RoomsJ).PlayerProjectileSize(); i++)
        {
           g2d.drawImage(Rooms.get(RoomsI).get(RoomsJ).getPlayerProjectile(i).getSprite(), 
                   Rooms.get(RoomsI).get(RoomsJ).getPlayerProjectile(i).getX(), 
                   Rooms.get(RoomsI).get(RoomsJ).getPlayerProjectile(i).getY(), this);
        }
        
        for(int i = 0; i < Rooms.get(RoomsI).get(RoomsJ).EnemySize(); i++)
        {
           g2d.drawImage(Rooms.get(RoomsI).get(RoomsJ).getEnemy(i).getSprite(), 
                   Rooms.get(RoomsI).get(RoomsJ).getEnemy(i).getX(), 
                   Rooms.get(RoomsI).get(RoomsJ).getEnemy(i).getY(), this);
        }
        
        for(int i = 0; i < Rooms.get(RoomsI).get(RoomsJ).EnemyProjectileSize(); i++)
        {
           g2d.drawImage(Rooms.get(RoomsI).get(RoomsJ).getEnemyProjectile(i).getSprite(), 
                   Rooms.get(RoomsI).get(RoomsJ).getEnemyProjectile(i).getX(), 
                   Rooms.get(RoomsI).get(RoomsJ).getEnemyProjectile(i).getY(), this);
        }
        
        g2d.drawImage(Player.getSprite(), Player.getX(), Player.getY(), this);
        
        gtemp.drawImage(bufImg, 0, 0, this);
        gtemp.dispose();
    }
    
    public static void main(String[] args) {
        PlayGame game = new PlayGame();
        game.init();
        //game.musicThreadLoop();
        game.mainMenu();
    }
}
