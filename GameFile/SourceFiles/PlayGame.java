package SourceFiles;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.JPanel;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.MouseInfo;

public class PlayGame extends JPanel {
    //private ArrayList<ArrayList<Room>> Rooms;
    private Room[][] Rooms;
    private int RoomsI, RoomsJ;
    private BufferedImage bufImg;
    private Graphics2D g2d;
    private Image ForestFloor, tempchar;
    private final int ScreenWidth = 1280, ScreenHeight = 960;
    private final int FPS = 60;
    private boolean levelFinished;
    private WizardPlayer Player;
    private int Money;
    private JFrame GameWindow;
    private GameEvents PlayerKeyEvent;
    
    private Image[] WizRightForwardAttack, WizRightForward, WizRightBackAttack,
            WizRightAttack, WizRight, WizLeftForwardAttack, WizLeftForward,
            WizLeftBackwardAttack, WizLeftAttack, WizLeft, WizForwardAttack, 
            WizForward, WizBackAttack, WizBack;
    
    public void resourcesInit()
    {
        
        try{
            this.ForestFloor = ImageIO.read(new File("Resources/ForestBackground.png"));
            this.tempchar = ImageIO.read(new File("Resources/char.png"));
            
            WizRightForwardAttack = new Image[3];
            WizRightForwardAttack[0] = ImageIO.read(new File("Resources/WizRightForwardAttack1.png"));
            WizRightForwardAttack[1] = ImageIO.read(new File("Resources/WizRightForwardAttack2.png"));
            WizRightForwardAttack[2] = ImageIO.read(new File("Resources/WizRightForwardAttack3.png"));
            
            WizRightForward = new Image[3];
            WizRightForward[0] = ImageIO.read(new File("Resources/WizRightForward1.png"));
            WizRightForward[1] = ImageIO.read(new File("Resources/WizRightForward2.png"));
            WizRightForward[2] = ImageIO.read(new File("Resources/WizRightForward3.png"));
            
            WizRightBackAttack = new Image[3];
            WizRightBackAttack[0] = ImageIO.read(new File("Resources/WizRightBackAttack1.png"));
            WizRightBackAttack[1] = ImageIO.read(new File("Resources/WizRightBackAttack2.png"));
            WizRightBackAttack[2] = ImageIO.read(new File("Resources/WizRightBackAttack3.png"));
            
            WizRightAttack = new Image[3];
            WizRightAttack[0] = ImageIO.read(new File("Resources/WizRightAttack1.png"));
            WizRightAttack[1] = ImageIO.read(new File("Resources/WizRightAttack2.png"));
            WizRightAttack[2] = ImageIO.read(new File("Resources/WizRightAttack3.png"));
            
            WizRight = new Image[3];
            WizRight[0] = ImageIO.read(new File("Resources/WizRight1.png"));
            WizRight[1] = ImageIO.read(new File("Resources/WizRight2.png"));
            WizRight[2] = ImageIO.read(new File("Resources/WizRight3.png"));
            
            WizLeftForwardAttack = new Image[3];
            WizLeftForwardAttack[0] = ImageIO.read(new File("Resources/WizLeftForwardAttack1.png"));
            WizLeftForwardAttack[1] = ImageIO.read(new File("Resources/WizLeftForwardAttack2.png"));
            WizLeftForwardAttack[2] = ImageIO.read(new File("Resources/WizLeftForwardAttack3.png"));
            
            WizLeftForward = new Image[3];
            WizLeftForward[0] = ImageIO.read(new File("Resources/WizLeftForward1.png"));
            WizLeftForward[1] = ImageIO.read(new File("Resources/WizLeftForward2.png"));
            WizLeftForward[2] = ImageIO.read(new File("Resources/WizLeftForward3.png"));
            
            WizLeftBackwardAttack = new Image[3];
            WizLeftBackwardAttack[0] = ImageIO.read(new File("Resources/WizLeftBackAttack1.png"));
            WizLeftBackwardAttack[1] = ImageIO.read(new File("Resources/WizLeftBackAttack2.png"));
            WizLeftBackwardAttack[2] = ImageIO.read(new File("Resources/WizLeftBackAttack3.png"));
            
            WizLeftAttack = new Image[3];
            WizLeftAttack[0] = ImageIO.read(new File("Resources/WizLeftAttack1.png"));
            WizLeftAttack[1] = ImageIO.read(new File("Resources/WizLeftAttack2.png"));
            WizLeftAttack[2] = ImageIO.read(new File("Resources/WizLeftAttack3.png"));
            
            WizLeft = new Image[3];
            WizLeft[0] = ImageIO.read(new File("Resources/WizLeft1.png"));
            WizLeft[1] = ImageIO.read(new File("Resources/WizLeft2.png"));
            WizLeft[2] = ImageIO.read(new File("Resources/WizLeft3.png"));
            
            WizForwardAttack = new Image[3];
            WizForwardAttack[0] = ImageIO.read(new File("Resources/WizForwardAttack1.png"));
            WizForwardAttack[1] = ImageIO.read(new File("Resources/WizForwardAttack2.png"));
            WizForwardAttack[2] = ImageIO.read(new File("Resources/WizForwardAttack3.png"));
            
            WizForward = new Image[3];
            WizForward[0] = ImageIO.read(new File("Resources/WizForward1.png"));
            WizForward[1] = ImageIO.read(new File("Resources/WizForward2.png"));
            WizForward[2] = ImageIO.read(new File("Resources/WizForward3.png"));
            
            WizBackAttack = new Image[3];
            WizBackAttack[0] = ImageIO.read(new File("Resources/WizBackAttack1.png"));
            WizBackAttack[1] = ImageIO.read(new File("Resources/WizBackAttack2.png"));
            WizBackAttack[2] = ImageIO.read(new File("Resources/WizBackAttack3.png"));
            
            WizBack = new Image[3];           
            WizBack[0] = ImageIO.read(new File("Resources/WizBack1.png"));
            WizBack[1] = ImageIO.read(new File("Resources/WizBack2.png"));
            WizBack[2] = ImageIO.read(new File("Resources/WizBack3.png"));
            
        } catch (Exception e) {
            System.out.print(e.getStackTrace() + " Error loading resources");
        }
        
        //create the window we use
        GameWindow = new JFrame();
        GameWindow.addWindowListener(new WindowAdapter(){});
        GameWindow.add(this);
        GameWindow.setTitle("Rogue Game");
        GameWindow.setSize(ScreenWidth + 6, ScreenHeight + 35);
        GameWindow.setResizable(false);
        GameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GameWindow.setVisible(true);
        GameWindow.getContentPane().setFocusable(true);
    }
    
    public void newGameInit()
    {
        this.Money = 0;
        this.Player = new WizardPlayer(0, 0, 0, this.ScreenWidth, 0, this.ScreenHeight, 
                WizRightForwardAttack, WizRightForward, WizRightBackAttack,
                WizRightAttack, WizRight, WizLeftForwardAttack, WizLeftForward,
                WizLeftBackwardAttack, WizLeftAttack, WizLeft, WizForwardAttack, 
                WizForward, WizBackAttack, WizBack);
        
        this.Player.addNewSpell(new ProjectileSpell(5,10, 30, tempchar,tempchar));
        
        
        PlayerKeyEvent = new GameEvents();
        PlayerKeyEvent.addObserver(Player);
        KeyControl playerKeys = new KeyControl(PlayerKeyEvent);
        
        GameWindow.getContentPane().requestFocusInWindow();
        GameWindow.getContentPane().addKeyListener(playerKeys);
        GameWindow.addMouseListener(new MouseAdapter() { 
            public void mousePressed(MouseEvent me) { 
                if(me.getButton() == MouseEvent.BUTTON1)
                  Player.fire();
            } 
            public void mouseReleased(MouseEvent me) { 
                if(me.getButton() == MouseEvent.BUTTON1)
                  Player.stopFire();
            } 
        }); 
    }
    
    public void testLevelInit()
    {
        Rooms = new Room[5][5];
        this.RoomsI = 0;
        this.RoomsJ = 0;
        this.Rooms[0][0] = new Room();
        
        Rooms[RoomsI][RoomsJ].addWall(new StationaryObject(200, 200, this.tempchar));
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
        for(int i = 0; i < Rooms[RoomsI][RoomsJ].WallSize(); i++)
        {
            //if both collisions are already true, no need to waste resources and
            //continue checking
            if(verticalCol && horizontalCol)
            {
                break;
            }
            
            //test for normal collision first, and if there is a collision, then
            //test for vertical/horizontal collisions
            if(col.normalCollision(Player, Rooms[RoomsI][RoomsJ].getWall(i)))
            {
                generalCol = true;
                
                if(col.horizontalCollision(Player, Rooms[RoomsI][RoomsJ].getWall(i)))
                {
                    horizontalCol = true;
                }
                
                if(col.verticalCollision(Player, Rooms[RoomsI][RoomsJ].getWall(i)))
                {
                    verticalCol = true;
                }
            }
        }
        
        for(int i = 0; i < Rooms[RoomsI][RoomsJ].BarrelSize(); i++)
        {
            if(verticalCol && horizontalCol)
            {
                break;
            }
            
            if(col.normalCollision(Player, Rooms[RoomsI][RoomsJ].getBarrel(i)))
            {
                generalCol = true;
                
                if(col.horizontalCollision(Player, Rooms[RoomsI][RoomsJ].getBarrel(i)))
                {
                    horizontalCol = true;
                }
                
                if(col.verticalCollision(Player, Rooms[RoomsI][RoomsJ].getBarrel(i)))
                {
                    verticalCol = true;
                }
            }
        }
        
        for(int i = 0; i < Rooms[RoomsI][RoomsJ].EnemySize(); i++)
        {
            if(col.normalCollision(Player, Rooms[RoomsI][RoomsJ].getEnemy(i)))
            {
                generalCol = true;
                
                Player.takeDamage(Rooms[RoomsI][RoomsJ].getEnemy(i).getBumpDamage());
                
                if(col.horizontalCollision(Player, Rooms[RoomsI][RoomsJ].getEnemy(i)))
                {
                    horizontalCol = true;
                }
                
                if(col.verticalCollision(Player, Rooms[RoomsI][RoomsJ].getEnemy(i)))
                {
                    verticalCol = true;
                }
            }
        }
        
        //now update the player's position
        Player.updatePlayer(MouseInfo.getPointerInfo().getLocation().x - (int)GameWindow.getLocation().getX() - 4, 
                MouseInfo.getPointerInfo().getLocation().y - (int)GameWindow.getLocation().getY() - 32, 
                generalCol, horizontalCol, verticalCol);
        
        //now test for remaining collisions based on where the player ends up standing
        for(int i = 0; i < Rooms[RoomsI][RoomsJ].CoinSize(); i++)
        {
            if(col.standingCollision(Player, Rooms[RoomsI][RoomsJ].getCoin(i)))
            {
                Money += Rooms[RoomsI][RoomsJ].getCoin(i).getValue();
                Rooms[RoomsI][RoomsJ].removeCoin(i);
                i--;
            }
        }
        
        for(int i = 0; i < Rooms[RoomsI][RoomsJ].PotionSize(); i++)
        {
            if(col.standingCollision(Player, Rooms[RoomsI][RoomsJ].getPotion(i)))
            {
                Player.heal(Rooms[RoomsI][RoomsJ].getPotion(i).getHealAmount());
                Rooms[RoomsI][RoomsJ].removePotion(i);
                i--;
            }
        }
        
        for(int i = 0; i < Rooms[RoomsI][RoomsJ].EnemyAoeSize(); i++)
        {
            if(col.standingCollision(Player, Rooms[RoomsI][RoomsJ].getEnemyAoe(i)))
            {
                Player.takeDamage(Rooms[RoomsI][RoomsJ].getEnemyAoe(i).getDamage());
            }
        }
        
        for(int i = 0; i < Rooms[RoomsI][RoomsJ].SpikeTrapSize(); i++)
        {
            if(col.standingCollision(Player, Rooms[RoomsI][RoomsJ].getSpikeTrap(i)))
            {
                Player.takeDamage(Rooms[RoomsI][RoomsJ].getSpikeTrap(i).getDamage());
                //break after touching a trap; dont want to take double damage if
                //standing on two traps
                break;
            }
        }
        
        //now have player fire spell if ready
        if(Player.isProjectileReady())
        {
            Rooms[RoomsI][RoomsJ].addPlayerProjectile(Player.fireProjectile());
        }
        
        if(Player.isAoeReady())
        {
            Rooms[RoomsI][RoomsJ].addPlayerAoe(Player.fireAoe());
        }
        
        //now test for collisions for enemies 
        
        ////HAVE AOE COLLISIONS WITH BARRELS CHECK WHEN THEY'RE CREATED
        for(int i = 0; i < Rooms[RoomsI][RoomsJ].EnemySize(); i++)
        {
            generalCol = false;
            verticalCol = false;
            horizontalCol = false;
            
            for(int j = 0; j < Rooms[RoomsI][RoomsJ].WallSize(); j++)
            {
                if(verticalCol && horizontalCol)
                {
                    break;
                }

                if(col.normalCollision(Rooms[RoomsI][RoomsJ].getEnemy(i), 
                        Rooms[RoomsI][RoomsJ].getWall(j)))
                {
                    generalCol = true;

                    if(col.horizontalCollision(Rooms[RoomsI][RoomsJ].getEnemy(i), 
                            Rooms[RoomsI][RoomsJ].getWall(j)))
                    {
                        horizontalCol = true;
                    }

                    if(col.verticalCollision(Rooms[RoomsI][RoomsJ].getEnemy(i), 
                            Rooms[RoomsI][RoomsJ].getWall(j)))
                    {
                        verticalCol = true;
                    }
                }
            }

            for(int j = 0; j < Rooms[RoomsI][RoomsJ].BarrelSize(); j++)
            {
                if(verticalCol && horizontalCol)
                {
                    break;
                }

                if(col.normalCollision(Rooms[RoomsI][RoomsJ].getEnemy(i), 
                        Rooms[RoomsI][RoomsJ].getBarrel(j)))
                {
                    generalCol = true;

                    if(col.horizontalCollision(Rooms[RoomsI][RoomsJ].getEnemy(i), 
                            Rooms[RoomsI][RoomsJ].getBarrel(j)))
                    {
                        horizontalCol = true;
                    }

                    if(col.verticalCollision(Rooms[RoomsI][RoomsJ].getEnemy(i), 
                            Rooms[RoomsI][RoomsJ].getBarrel(j)))
                    {
                        verticalCol = true;
                    }
                }
            }
            
            for(int j = 0; j < Rooms[RoomsI][RoomsJ].EnemySize(); j++)
            {
                if(verticalCol && horizontalCol)
                {
                    break;
                }

                if(col.normalCollision(Rooms[RoomsI][RoomsJ].getEnemy(i), 
                        Rooms[RoomsI][RoomsJ].getEnemy(j)))
                {
                    generalCol = true;

                    if(col.horizontalCollision(Rooms[RoomsI][RoomsJ].getEnemy(i), 
                                Rooms[RoomsI][RoomsJ].getEnemy(j)))
                    {
                        horizontalCol = true;
                    }

                    if(col.verticalCollision(Rooms[RoomsI][RoomsJ].getEnemy(i), 
                            Rooms[RoomsI][RoomsJ].getEnemy(j)))
                    {
                        verticalCol = true;
                    }
                }
            }
            
            if(col.normalCollision(Rooms[RoomsI][RoomsJ].getEnemy(i), Player))
            {
                generalCol = true;

                if(col.horizontalCollision(Rooms[RoomsI][RoomsJ].getEnemy(i), Player))
                {
                    horizontalCol = true;
                }

                if(col.verticalCollision(Rooms[RoomsI][RoomsJ].getEnemy(i), Player))
                {
                    verticalCol = true;
                }
            }
            
            Rooms[RoomsI][RoomsJ].getEnemy(i).updateMovingEnemy(Player.getCenterX(), 
                    Player.getCenterY(), generalCol, horizontalCol, verticalCol);
            
            for(int j = 0; j < Rooms[RoomsI][RoomsJ].PlayerAoeSize(); j++)
            {
                if(col.standingCollision(Rooms[RoomsI][RoomsJ].getEnemy(i), 
                        Rooms[RoomsI][RoomsJ].getPlayerAoe(j)))
                {
                    Rooms[RoomsI][RoomsJ].getEnemy(i).takeDamage(Rooms[RoomsI][RoomsJ].getPlayerAoe(j).getDamage());
                }
            }
            
            for(int j = 0; j < Rooms[RoomsI][RoomsJ].SpikeTrapSize(); j++)
            {
                if(col.standingCollision(Rooms[RoomsI][RoomsJ].getEnemy(i), 
                        Rooms[RoomsI][RoomsJ].getSpikeTrap(i)))
                {
                    Rooms[RoomsI][RoomsJ].getEnemy(i).takeDamage(Rooms[RoomsI][RoomsJ].getSpikeTrap(i).getDamage());
                }
            }
            
            //fire for enemies
            if(Rooms[RoomsI][RoomsJ].getEnemy(i).isProjectileReady())
            {
                Rooms[RoomsI][RoomsJ].addEnemyProjectile(Rooms[RoomsI][RoomsJ].getEnemy(i).fireProjectile());
            }
            
            if(Rooms[RoomsI][RoomsJ].getEnemy(i).isAoeReady())
            {
                Rooms[RoomsI][RoomsJ].addEnemyAoe(Rooms[RoomsI][RoomsJ].getEnemy(i).fireAoe());
            }
        }
        
        //now test for collisions for player projectiles
        //also need to add elemental effects handling here?
        for(int i = 0; i < Rooms[RoomsI][RoomsJ].PlayerProjectileSize(); i++)
        {
            generalCol = false;
            for(int j = 0; j < Rooms[RoomsI][RoomsJ].EnemySize(); j++)
            {
                if(col.normalCollision(Rooms[RoomsI][RoomsJ].getPlayerProjectile(i), Rooms[RoomsI][RoomsJ].getEnemy(j)))
                {
                    Rooms[RoomsI][RoomsJ].getEnemy(j).takeDamage(Rooms[RoomsI][RoomsJ].getPlayerProjectile(i).getDamage());
                    Rooms[RoomsI][RoomsJ].removePlayerProjectile(i);
                    i--;
                    generalCol = true;
                    break;
                }
            }
            if(!generalCol)
            {
                for(int j = 0; j < Rooms[RoomsI][RoomsJ].WallSize(); j++)
                {
                    if(col.normalCollision(Rooms[RoomsI][RoomsJ].getPlayerProjectile(i), Rooms[RoomsI][RoomsJ].getWall(j)))
                    {
                        Rooms[RoomsI][RoomsJ].removePlayerProjectile(i);
                        i--;
                        generalCol = true;
                        break;
                    }
                }
            }
            if(!generalCol)
            {
                for(int j = 0; j < Rooms[RoomsI][RoomsJ].BarrelSize(); j++)
                {
                    if(col.normalCollision(Rooms[RoomsI][RoomsJ].getPlayerProjectile(i), Rooms[RoomsI][RoomsJ].getBarrel(j)))
                    {
                        Rooms[RoomsI][RoomsJ].removeBarrel(j);
                        j--;
                        Rooms[RoomsI][RoomsJ].removePlayerProjectile(i);
                        i--;
                        generalCol = true;
                        break;
                    }
                }
            }
            if(!generalCol)
            {
                Rooms[RoomsI][RoomsJ].getPlayerProjectile(i).updateProjectile();
                if(Rooms[RoomsI][RoomsJ].getPlayerProjectile(i).outOfBounds())
                    Rooms[RoomsI][RoomsJ].removePlayerProjectile(i);
            }
        }
        
        //enemy projectiles
        for(int i = 0; i < Rooms[RoomsI][RoomsJ].EnemyProjectileSize(); i++)
        {
            generalCol = false;
            if(col.normalCollision(Rooms[RoomsI][RoomsJ].getEnemyProjectile(i), Player))
            {
                Player.takeDamage(Rooms[RoomsI][RoomsJ].getEnemyProjectile(i).getDamage());
                Rooms[RoomsI][RoomsJ].removeEnemyProjectile(i);
                i--;
                generalCol = true;
                        break;
            }
            if(!generalCol)
            {
                for(int j = 0; j < Rooms[RoomsI][RoomsJ].WallSize(); j++)
                {
                    if(col.normalCollision(Rooms[RoomsI][RoomsJ].getEnemyProjectile(i), Rooms[RoomsI][RoomsJ].getWall(j)))
                    {
                        Rooms[RoomsI][RoomsJ].removeEnemyProjectile(i);
                        i--;
                        generalCol = true;
                        break;
                    }
                }
            }
            if(!generalCol)
            {
                for(int j = 0; j < Rooms[RoomsI][RoomsJ].BarrelSize(); j++)
                {
                    if(col.normalCollision(Rooms[RoomsI][RoomsJ].getEnemyProjectile(i), Rooms[RoomsI][RoomsJ].getBarrel(j)))
                    {
                        Rooms[RoomsI][RoomsJ].removeBarrel(j);
                        j--;
                        Rooms[RoomsI][RoomsJ].removeEnemyProjectile(i);
                        i--;
                        generalCol = true;
                        break;
                    }
                }
            }
            if(!generalCol)
            {
                Rooms[RoomsI][RoomsJ].getEnemyProjectile(i).updateProjectile();
                if(Rooms[RoomsI][RoomsJ].getEnemyProjectile(i).outOfBounds())
                    Rooms[RoomsI][RoomsJ].removeEnemyProjectile(i);
            }
        }
        
        //now update traps/aoes; timers for non-movers
        for(int i = 0; i < Rooms[RoomsI][RoomsJ].EnemyAoeSize(); i++)
        {
            Rooms[RoomsI][RoomsJ].getEnemyAoe(i).updateObject();
            if(Rooms[RoomsI][RoomsJ].getEnemyAoe(i).isDone())
            {
                Rooms[RoomsI][RoomsJ].removeEnemyAoe(i);
                i--;
            }
        }
        
        for(int i = 0; i < Rooms[RoomsI][RoomsJ].PlayerAoeSize(); i++)
        {
            Rooms[RoomsI][RoomsJ].getPlayerAoe(i).updateObject();
            if(Rooms[RoomsI][RoomsJ].getPlayerAoe(i).isDone())
            {
                Rooms[RoomsI][RoomsJ].removePlayerAoe(i);
                i--;
            }
        }
        
        for(int i = 0; i < Rooms[RoomsI][RoomsJ].SpikeTrapSize(); i++)
        {
            Rooms[RoomsI][RoomsJ].getSpikeTrap(i).updateObject();
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
        
        for(int i = 0; i < 5; i++)
        {
            for(int j = 0; j < 4; j++)
            {
                g2d.drawImage(ForestFloor, i * ForestFloor.getWidth(null), j * ForestFloor.getHeight(null), this);
            }
        }
        //paint order: player > enemy> projectile > itmes > wall
        //lowest prio is first
        
        for(int i = 0; i < Rooms[RoomsI][RoomsJ].BarrelSize(); i++)
        {
           g2d.drawImage(Rooms[RoomsI][RoomsJ].getBarrel(i).getSprite(), 
                   Rooms[RoomsI][RoomsJ].getBarrel(i).getX(), 
                   Rooms[RoomsI][RoomsJ].getBarrel(i).getY(), this);
        }
        
        for(int i = 0; i < Rooms[RoomsI][RoomsJ].SpikeTrapSize(); i++)
        {
            g2d.drawImage(Rooms[RoomsI][RoomsJ].getSpikeTrap(i).getSprite(), 
                    Rooms[RoomsI][RoomsJ].getSpikeTrap(i).getX(), 
                    Rooms[RoomsI][RoomsJ].getSpikeTrap(i).getY(), this);
        }
        
        for(int i = 0; i < Rooms[RoomsI][RoomsJ].EnemyAoeSize(); i++)
        {
            g2d.drawImage(Rooms[RoomsI][RoomsJ].getEnemyAoe(i).getSprite(),
                    Rooms[RoomsI][RoomsJ].getEnemyAoe(i).getX(), 
                    Rooms[RoomsI][RoomsJ].getEnemyAoe(i).getY(), this);
        }
        
        for(int i = 0; i < Rooms[RoomsI][RoomsJ].PlayerAoeSize(); i++)
        {
            g2d.drawImage(Rooms[RoomsI][RoomsJ].getPlayerAoe(i).getSprite(), 
                    Rooms[RoomsI][RoomsJ].getPlayerAoe(i).getX(), 
                    Rooms[RoomsI][RoomsJ].getPlayerAoe(i).getY(), this);
        }
        
        for(int i = 0; i < Rooms[RoomsI][RoomsJ].WallSize(); i++)
        {
           g2d.drawImage(Rooms[RoomsI][RoomsJ].getWall(i).getSprite(), 
                   Rooms[RoomsI][RoomsJ].getWall(i).getX(), 
                   Rooms[RoomsI][RoomsJ].getWall(i).getY(), this);
        }
        
        for(int i = 0; i < Rooms[RoomsI][RoomsJ].CoinSize(); i++)
        {
           g2d.drawImage(Rooms[RoomsI][RoomsJ].getCoin(i).getSprite(), 
                   Rooms[RoomsI][RoomsJ].getCoin(i).getX(),
                   Rooms[RoomsI][RoomsJ].getCoin(i).getY(), this);
        }
        
        for(int i = 0; i < Rooms[RoomsI][RoomsJ].PotionSize(); i++)
        {
           g2d.drawImage(Rooms[RoomsI][RoomsJ].getPotion(i).getSprite(), 
                   Rooms[RoomsI][RoomsJ].getPotion(i).getX(), 
                   Rooms[RoomsI][RoomsJ].getPotion(i).getY(), this);
        }
        
        for(int i = 0; i < Rooms[RoomsI][RoomsJ].PotionSize(); i++)
        {
           g2d.drawImage(Rooms[RoomsI][RoomsJ].getPotion(i).getSprite(),
                   Rooms[RoomsI][RoomsJ].getPotion(i).getX(), 
                   Rooms[RoomsI][RoomsJ].getPotion(i).getY(), this);
        }
        
        for(int i = 0; i < Rooms[RoomsI][RoomsJ].PageSize(); i++)
        {
           g2d.drawImage(Rooms[RoomsI][RoomsJ].getPage(i).getSprite(), 
                   Rooms[RoomsI][RoomsJ].getPage(i).getX(), 
                   Rooms[RoomsI][RoomsJ].getPage(i).getY(), this);
        }
        
        for(int i = 0; i < Rooms[RoomsI][RoomsJ].RuneSize(); i++)
        {
           g2d.drawImage(Rooms[RoomsI][RoomsJ].getRune(i).getSprite(), 
                   Rooms[RoomsI][RoomsJ].getRune(i).getX(), 
                   Rooms[RoomsI][RoomsJ].getRune(i).getY(), this);
        }
        
        for(int i = 0; i < Rooms[RoomsI][RoomsJ].EnemySize(); i++)
        {
           g2d.drawImage(Rooms[RoomsI][RoomsJ].getEnemy(i).getSprite(), 
                   Rooms[RoomsI][RoomsJ].getEnemy(i).getX(), 
                   Rooms[RoomsI][RoomsJ].getEnemy(i).getY(), this);
        }
        
        g2d.drawImage(Player.getSprite(), Player.getX(), Player.getY(), this);
        
        for(int i = 0; i < Rooms[RoomsI][RoomsJ].PlayerProjectileSize(); i++)
        {
           g2d.drawImage(Rooms[RoomsI][RoomsJ].getPlayerProjectile(i).getSprite(), 
                   Rooms[RoomsI][RoomsJ].getPlayerProjectile(i).getX(), 
                   Rooms[RoomsI][RoomsJ].getPlayerProjectile(i).getY(), this);
        }
        
        for(int i = 0; i < Rooms[RoomsI][RoomsJ].EnemyProjectileSize(); i++)
        {
           g2d.drawImage(Rooms[RoomsI][RoomsJ].getEnemyProjectile(i).getSprite(), 
                   Rooms[RoomsI][RoomsJ].getEnemyProjectile(i).getX(), 
                   Rooms[RoomsI][RoomsJ].getEnemyProjectile(i).getY(), this);
        }
        
        gtemp.drawImage(bufImg, 0, 0, this);
        gtemp.dispose();
    }
    
    public static void main(String[] args) {
        PlayGame game = new PlayGame();
        game.resourcesInit();
        game.newGameInit();
        game.testLevelInit();
        //game.musicThreadLoop();
        //game.mainMenu();
        game.timerLoop();
    }
}
