package SourceFiles;

import SourceFiles.GameLogic.GameEvents;
import SourceFiles.GameObjects.StationaryObjects.GearObjects.Gear;
import SourceFiles.GameLogic.KeyControl;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.MouseWheelEvent;
import java.awt.MouseInfo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import javax.swing.*;

public class PlayGame extends JPanel implements KeyListener{
    /*
    PlayGame is the main controller for the game. Loads and initializes all 
    images/objects used, creates the window used for the game, and controls the 
    main game loop.
    */
    
    private BufferedImage bufImg;
    private Graphics2D g2d;
    private Image  TempleFloor,  NullSpellIcon, CurrentSpellIcon, ChilledImg, 
            FrozenImg, HealthBar, HealthBarBackground;
    
    //private final int ScreenWidth = 1024, ScreenHeight = 960;
    //private final int ScreenWidth = 540, ScreenHeight = 720;
    private final int ScreenWidth = 1280, ScreenHeight = 960;
    private final int FPS = 60;
    
    private boolean Paused, InGame;
    private JFrame GameWindow;
    private JPanel PauseMenu, MainMenu;
    private GameEvents PlayerKeyEvent;
    private GameInstance Game;
    private boolean PauseTutorial;
    
    private float OpaqueValue;
    private boolean OpaqueLower, OpaqueRaise;
    
    private JButton testButton;
    
    private void startGame()
    {
        /*
        startGame is called when game first opens, it creates the GameWindow used
        */
        GameWindow = new JFrame();
        GameWindow.addWindowListener(new WindowAdapter(){});
        GameWindow.add(this);
        GameWindow.setTitle("Rogue Game");
        GameWindow.setSize(ScreenWidth + 6, ScreenHeight + 35);
        GameWindow.setResizable(false);
        GameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GameWindow.setVisible(true);
        GameWindow.getContentPane().setFocusable(true);
        GameWindow.getContentPane().addKeyListener(this);
        GameWindow.setBackground(Color.black);
        this.Paused = false;
        this.InGame = false;
        PauseTutorial = true;
        
        //GameWindow.setExtendedState(JFrame.MAXIMIZED_BOTH);
        //GameWindow.setUndecorated(true);
        
    }
    
    private void resourcesInit()
    {
        try {
            this.TempleFloor = ImageIO.read(new File("Resources" + File.separator + "Floor.png"));
            this.NullSpellIcon = ImageIO.read(new File("Resources" + File.separator + "NullIcon.png"));
            this.CurrentSpellIcon = ImageIO.read(new File("Resources" + File.separator + "CurrentSpellIcon.png"));
            this.ChilledImg = ImageIO.read(new File("Resources" + File.separator + "chilled.png"));
            this.FrozenImg = ImageIO.read(new File("Resources" + File.separator + "frozen.png"));
            this.HealthBar = ImageIO.read(new File("Resources" + File.separator + "HealthBar.png"));
            this.HealthBarBackground = ImageIO.read(new File("Resources" + File.separator + "HealthBarBackground.png"));
        }catch (Exception e) {
            System.out.print(e.getStackTrace() + " Error loading resources in PlayGsme \n");
        }
    }
    
    private void newGameInit()
    {
        this.Paused = false;
        this.InGame = true;
        
        this.OpaqueValue = 0f;
        this.OpaqueRaise = true;
        this.OpaqueLower = false;
        
        Game = new GameInstance(ScreenWidth, ScreenHeight);
        
        PlayerKeyEvent = new GameEvents();
        PlayerKeyEvent.addObserver(Game.getPlayer());
        KeyControl playerKeys = new KeyControl(PlayerKeyEvent);
        
        GameWindow.getContentPane().requestFocusInWindow();
        GameWindow.getContentPane().addKeyListener(playerKeys);
        GameWindow.addMouseListener(new MouseAdapter() { 
            public void mousePressed(MouseEvent me) { 
                if(me.getButton() == MouseEvent.BUTTON1)
                    Game.getPlayer().fire();
            } 
            public void mouseReleased(MouseEvent me) { 
                if(me.getButton() == MouseEvent.BUTTON1)
                    Game.getPlayer().stopFire();
            } 
        }); 
        GameWindow.addMouseWheelListener(new MouseWheelListener() {
            public void mouseWheelMoved(MouseWheelEvent e){
                if(e.getWheelRotation() < 0)
                {
                    Game.getPlayer().scrollUp();
                }
                else if(e.getWheelRotation() > 0)
                {
                    Game.getPlayer().scrollDown();
                }
            }
        });
    }
    
    private void mainMenuInit()
    {
        
    }
    
    private void pauseMenuInit()
    {
        /*
        PauseMenu is used whenever the pause game button is pressed. Currently
        being worked on, will display a map, equipped gear, and talent tree
        */
        PauseMenu = new JPanel();
        PauseMenu.setVisible(false);
        PauseMenu.setLayout(null);
        
        JButton testbut = new JButton(new ImageIcon(NullSpellIcon));
        //testbut.setIcon(new ImageIcon(tempchar));
        //testbut.setRolloverIcon(null);
        //testbut.setDisabledIcon(null);
        //testbut.setPressedIcon(icon);
        //testbut.setRolloverSelectedIcon(icon);
        testbut.setBorder(BorderFactory.createEmptyBorder());
        testbut.setContentAreaFilled(false);
        testbut.setBounds(100,100, this.NullSpellIcon.getWidth(null), NullSpellIcon.getHeight(null));
        testbut.setToolTipText("test");
        PauseMenu.add(testbut);
        
        JTextArea testtext = new JTextArea();
        testtext.setBounds(300, 100, 200, 600);
        testtext.setText("imma test! a really realy really really really really long test");
        testtext.setLineWrap(true);
        testtext.setFont(new Font("Serif", Font.ITALIC, 16));
        testtext.setBorder(BorderFactory.createEmptyBorder());
        testtext.setFocusable(false);
        testtext.setOpaque(false);
        PauseMenu.add(testtext);
        
        testbut.addMouseListener(new MouseAdapter() {
        public void mouseEntered(MouseEvent evt) {
                testtext.setText("we in");
            }

            public void mouseExited(MouseEvent evt) {
                testtext.setText("we out");
            }
        });
        
        testbut.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e){  
            System.out.println("pushed the button");
        }
        });
        
        
        JLabel testlab = new JLabel(new ImageIcon(this.TempleFloor));
        testlab.setBounds(0, 0, TempleFloor.getWidth(null), TempleFloor.getHeight(null));
        testlab.setVisible(true);
        PauseMenu.add(testlab);
        
        PauseMenu.setBounds(200, 200, 200, 200);
        
        GameWindow.add(PauseMenu);
        GameWindow.setVisible(true);
    }
    
    private void pauseGame()
    {
        this.Paused = true;
        this.setVisible(false);
        this.PauseMenu.setVisible(true);
    }
    
    private void unPauseGame()
    {
        this.Paused = false;
        this.PauseMenu.setVisible(false);
        this.setVisible(true);
    }
    
    //ovverides used for pause game key
    @Override
    public void keyPressed(KeyEvent e) {}
    
    @Override
    public void keyTyped(KeyEvent e) {}
    
    @Override
    public void keyReleased(KeyEvent e) 
    {
        int key = e.getKeyCode();
        
        if(key == KeyEvent.VK_P || key == KeyEvent.VK_ESCAPE)
        {
            if(!Paused && InGame)
            {
                this.Paused = true;
            }
            else if (InGame)
            {
                unPauseGame();
            }
        }
    }
    
 
    private void timerLoop()
    {
        /*
        timerLoop is the main game loop that repeats to update the game. It aims 
        to update all objects in 1/60 second and tells the thread to sleep for
        any remaining time aftre all updates are complete.
        */
        
        long currTime;
        //target time hoping to aim for each loop
        long targetTime = 1000000000 / FPS;
        

        while ((Game.getPlayer().isAlive())) {
            currTime = System.nanoTime();
            
            if(Game.getTransition())
            {
                OpaqueLower = true;
            }
            
            if(!OpaqueLower && !OpaqueRaise)
            {
                updateGame();
            }
            repaint();
            
            //While the game is paused, leave the game loop in loop that sleeps
            //and continously checks if the game is puased until the game resumes.
            if(this.Paused)
            {
                this.PauseTutorial = false;
                pauseGame();
                while(Paused)
                {
                    try{
                        Thread.sleep(100);
                    } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
            
            //sleep for any remaining time
            if ((currTime - System.nanoTime() + targetTime) > 0) {
                try {
                    Thread.sleep((currTime - System.nanoTime() + targetTime) / 1000000);
                } catch (InterruptedException ex) {
                    System.out.println(ex.getStackTrace() +  "Error sleeping in timerLoop");
                }
            }
        }
    }
    
    private void updateGame()
    {
        //scale ammount is used to adjust the mouse angle for scaled window sizes
        double scale;

        if(ScreenHeight < ScreenWidth)
        {
            scale = (double)ScreenWidth/Game.getGameWith();
        }
        else if(ScreenHeight > ScreenWidth)
        {
            scale = (double)ScreenHeight/Game.getGameHeight();
        }
        else
        {
            scale = 1;
        }

        double mouseX = (MouseInfo.getPointerInfo().getLocation().x - (int)GameWindow.getLocation().getX() - 4) / scale + Game.screenShiftX();
        double mouseY = (MouseInfo.getPointerInfo().getLocation().y - (int)GameWindow.getLocation().getY() - 32) / scale + Game.screenShiftY();

        Game.updateGame((int)mouseX, (int)mouseY);
    }
    

    private void mainMenu()
    {
        /*
        Initializes the main game menu. Still needs implementation
        */
    }
    

    @Override
    public void paintComponent(Graphics g) {
        /*
        The paintComponent override paints all of the game objects onto the game
        window. Since they are painted ontop of previous objects, they are printed
        in a specific order to achieve the correct layering.
        */
        if(!InGame)
            return;
        
        if (bufImg == null) {
            bufImg = (BufferedImage) createImage(Game.getGameWith(), Game.getGameHeight());
        }
        Graphics2D gtemp = (Graphics2D) g;
        gtemp.setBackground(Color.black);
        g2d = bufImg.createGraphics();
        super.paintComponent(gtemp);
        
        if(OpaqueRaise)
        {
            this.OpaqueValue += 0.05f;
            
            if(OpaqueValue < 1f)
            {
                g2d.fillRect(0, 0, 1280, 1280);
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, this.OpaqueValue));
            }
            else
            {
                OpaqueRaise = false;
            }
        }
        if(OpaqueLower)
        {
            this.OpaqueValue -= 0.05f;
            
            if(OpaqueValue < 0f)
            {
                OpaqueLower = false;
                OpaqueRaise = true;
                g2d.fillRect(0, 0, 1280, 1280);
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0f));
                Game.changeRoom();
            }
            else
            {
                g2d.fillRect(0, 0, 1280, 1280);
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, this.OpaqueValue));
            }
        }
        
        
        for(int i = 0; i < 8; i++)
        {
            for(int j = 0; j < 8; j++)
            {
                g2d.drawImage(TempleFloor, i * TempleFloor.getWidth(null) + 128, j * TempleFloor.getHeight(null) + 128, this);
            }
        }
        
        g2d.drawImage(Game.getPlayer().getShadow(), Game.getPlayer().getShadowX(), Game.getPlayer().getY() + 120, this);
        
        for(int i = 0; i < Game.getRoom().EnemySize(); i++)
        {
           g2d.drawImage(Game.getRoom().getEnemy(i).getShadow(), 
                   Game.getRoom().getEnemy(i).getShadowX(), 
                   Game.getRoom().getEnemy(i).getShadowY(), this);
        }
        
        for(int i = 0; i < Game.getRoom().PlayerProjectileSize(); i++)
        {
            paintRotatedImg(Game.getRoom().getPlayerProjectile(i).getShadow(), 
                   Game.getRoom().getPlayerProjectile(i).getAngle(),
                   Game.getRoom().getPlayerProjectile(i).getX(), 
                   Game.getRoom().getPlayerProjectile(i).getY() + Game.getShadowHeight());
        }
        
        for(int i = 0; i < Game.getRoom().EnemyProjectileSize(); i++)
        {
            paintRotatedImg(Game.getRoom().getEnemyProjectile(i).getShadow(), 
                   Game.getRoom().getEnemyProjectile(i).getAngle(),
                   Game.getRoom().getEnemyProjectile(i).getX(), 
                   Game.getRoom().getEnemyProjectile(i).getY() + Game.getShadowHeight());
        }
        
        //paint order: player > enemy> projectile > itmes > wall
        //lowest prio is first
        
        for(int i = 0; i < Game.getRoom().BarrelSize(); i++)
        {
           g2d.drawImage(Game.getRoom().getBarrel(i).getSprite(), 
                   Game.getRoom().getBarrel(i).getX(), 
                   Game.getRoom().getBarrel(i).getY(), this);
        }
        
        for(int i = 0; i < Game.getRoom().SpikeTrapSize(); i++)
        {
            g2d.drawImage(Game.getRoom().getSpikeTrap(i).getSprite(), 
                    Game.getRoom().getSpikeTrap(i).getX(), 
                    Game.getRoom().getSpikeTrap(i).getY(), this);
        }
        
        for(int i = 0; i < Game.getRoom().EnemyAoeSize(); i++)
        {
            g2d.drawImage(Game.getRoom().getEnemyAoe(i).getSprite(),
                    Game.getRoom().getEnemyAoe(i).getX(), 
                    Game.getRoom().getEnemyAoe(i).getY(), this);
        }
        
        for(int i = 0; i < Game.getRoom().PlayerAoeSize(); i++)
        {
            g2d.drawImage(Game.getRoom().getPlayerAoe(i).getSprite(), 
                    Game.getRoom().getPlayerAoe(i).getX(), 
                    Game.getRoom().getPlayerAoe(i).getY(), this);
        }
        
        for(int i = 0; i < Game.getWalls().length; i++)
        {
            g2d.drawImage(Game.getWalls()[i].getSprite(), Game.getWalls()[i].getX(), Game.getWalls()[i].getY(), this);
        }
        
        for(int i = 0; i < Game.getRoom().DoorSize(); i++)
        {
           g2d.drawImage(Game.getRoom().getDoor(i).getSprite(), 
                   Game.getRoom().getDoor(i).getX(), 
                   Game.getRoom().getDoor(i).getY(), this);
        }
        
        
        for(int i = 0; i < Game.getRoom().WallSize(); i++)
        {
           g2d.drawImage(Game.getRoom().getWall(i).getSprite(), 
                   Game.getRoom().getWall(i).getX(), 
                   Game.getRoom().getWall(i).getY(), this);
        }
        
        for(int i = 0; i < Game.getRoom().AnimatedWallSize(); i++)
        {
           g2d.drawImage(Game.getRoom().getAnimatedWall(i).getSprite(), 
                   Game.getRoom().getAnimatedWall(i).getX(), 
                   Game.getRoom().getAnimatedWall(i).getY(), this);
        }
        
        for(int i = 0; i < Game.getRoom().CoinSize(); i++)
        {
           g2d.drawImage(Game.getRoom().getCoin(i).getSprite(), 
                   Game.getRoom().getCoin(i).getX(),
                   Game.getRoom().getCoin(i).getY(), this);
        }
        
        for(int i = 0; i < Game.getRoom().PotionSize(); i++)
        {
           g2d.drawImage(Game.getRoom().getPotion(i).getSprite(), 
                   Game.getRoom().getPotion(i).getX(), 
                   Game.getRoom().getPotion(i).getY(), this);
        }
        
        for(int i = 0; i < Game.getRoom().PotionSize(); i++)
        {
           g2d.drawImage(Game.getRoom().getPotion(i).getSprite(),
                   Game.getRoom().getPotion(i).getX(), 
                   Game.getRoom().getPotion(i).getY(), this);
        }
        
        for(int i = 0; i < Game.getRoom().PageSize(); i++)
        {
           g2d.drawImage(Game.getRoom().getPage(i).getSprite(), 
                   Game.getRoom().getPage(i).getX(), 
                   Game.getRoom().getPage(i).getY(), this);
        }
        
        for(int i = 0; i < Game.getRoom().RuneSize(); i++)
        {
           g2d.drawImage(Game.getRoom().getRune(i).getSprite(), 
                   Game.getRoom().getRune(i).getX(), 
                   Game.getRoom().getRune(i).getY(), this);
        }
        
        for(int i = 0; i < Game.getRoom().RingSize(); i++)
        {
           g2d.drawImage(Game.getRoom().getRing(i).getSprite(), 
                   Game.getRoom().getRing(i).getX(), 
                   Game.getRoom().getRing(i).getY(), this);
        }
        
        for(int i = 0; i < Game.getRoom().NeckSize(); i++)
        {
           g2d.drawImage(Game.getRoom().getNeck(i).getSprite(), 
                   Game.getRoom().getNeck(i).getX(), 
                   Game.getRoom().getNeck(i).getY(), this);
        }
        
        for(int i = 0; i < Game.getRoom().TomeSize(); i++)
        {
           g2d.drawImage(Game.getRoom().getTome(i).getSprite(), 
                   Game.getRoom().getTome(i).getX(), 
                   Game.getRoom().getTome(i).getY(), this);
        }
        
        for(int i = 0; i < Game.getRoom().BootsSize(); i++)
        {
           g2d.drawImage(Game.getRoom().getBoots(i).getSprite(), 
                   Game.getRoom().getBoots(i).getX(), 
                   Game.getRoom().getBoots(i).getY(), this);
        }
        
        for(int i = 0; i < Game.getRoom().EnemySize(); i++)
        {
           g2d.drawImage(Game.getRoom().getEnemy(i).getSprite(), 
                   Game.getRoom().getEnemy(i).getX(), 
                   Game.getRoom().getEnemy(i).getY(), this);
           if(Game.getRoom().getEnemy(i).isChilled())
           {
               g2d.drawImage(this.ChilledImg, 
                       Game.getRoom().getEnemy(i).getCenterX() - this.ChilledImg.getWidth(null)/2,
                       Game.getRoom().getEnemy(i).getCenterY() - this.ChilledImg.getHeight(null)/2,
                       this);
           }
           if(Game.getRoom().getEnemy(i).isBurning())
           {
               g2d.drawImage(this.Game.getBurningAnimation().getSprite(), 
                       Game.getRoom().getEnemy(i).getCenterX() - this.Game.getBurningAnimation().getSprite().getWidth(null)/2,
                       Game.getRoom().getEnemy(i).getCenterY() - this.Game.getBurningAnimation().getSprite().getHeight(null)/2,
                       this);
           }
           if(Game.getRoom().getEnemy(i).isFrozen())
           {
               g2d.drawImage(this.FrozenImg, 
                       Game.getRoom().getEnemy(i).getCenterX() - this.FrozenImg.getWidth(null)/2,
                       Game.getRoom().getEnemy(i).getCenterY() - this.FrozenImg.getHeight(null)/2 - 6,
                       this);
           }
        }
        
        for(int i = 0; i < Game.getRoom().PlayerProjectileSize(); i++)
        {
            if(Game.getRoom().getPlayerProjectile(i).getY() < this.Game.getPlayer().getY() + 45)
                paintRotatedImg(Game.getRoom().getPlayerProjectile(i).getSprite(), 
                       Game.getRoom().getPlayerProjectile(i).getAngle(),
                       Game.getRoom().getPlayerProjectile(i).getX(), 
                       Game.getRoom().getPlayerProjectile(i).getY());
        }
        
        g2d.drawImage(Game.getPlayer().getSprite(), Game.getPlayer().getX(), Game.getPlayer().getImageY(), this);
        
        for(int i = 0; i < Game.getRoom().PlayerProjectileSize(); i++)
        {
            if(Game.getRoom().getPlayerProjectile(i).getY() >= this.Game.getPlayer().getY() + 4)
                paintRotatedImg(Game.getRoom().getPlayerProjectile(i).getSprite(), 
                       Game.getRoom().getPlayerProjectile(i).getAngle(),
                       Game.getRoom().getPlayerProjectile(i).getX(), 
                       Game.getRoom().getPlayerProjectile(i).getY());
        }
        
        for(int i = 0; i < Game.getRoom().EnemyProjectileSize(); i++)
        {
            paintRotatedImg(Game.getRoom().getEnemyProjectile(i).getSprite(), 
                   Game.getRoom().getEnemyProjectile(i).getAngle(),
                   Game.getRoom().getEnemyProjectile(i).getX(), 
                   Game.getRoom().getEnemyProjectile(i).getY());
        }
        
        for(int i = 0; i < Game.getRoom().AnimationSize(); i++)
        {
            paintRotatedImg(Game.getRoom().getAnimation(i).getSprite(), 
                   Game.getRoom().getAnimation(i).getAngle(),
                   Game.getRoom().getAnimation(i).getX(), 
                   Game.getRoom().getAnimation(i).getY());
        }
        
        
        //draw overlay now
        int yShift = Game.screenShiftY();
        int xShift = Game.screenShiftX();
        double scale;
        if(ScreenHeight < ScreenWidth)
        {
            scale = (double)ScreenWidth/Game.getGameWith();
        }
        else if(ScreenHeight > ScreenWidth)
        {
            scale = (double)ScreenHeight/Game.getGameHeight();
        }
        else
        {
            scale = 1;
        }
        double scaledHeight = ScreenHeight / scale;
        double scaledWidth = ScreenWidth / scale;
        
        for(int i = 0; i < 4; i ++)
        {
            if(Game.getPlayer().getSpell(i) == null)
            {
                g2d.drawImage(this.NullSpellIcon, 54 + xShift + 100 * i, (int)scaledHeight - 110 + yShift, this);
            }
            else
            {
                g2d.drawImage(Game.getPlayer().getSpell(i).getIcon(), 54 + xShift + 100 * i, (int)scaledHeight - 110 + yShift, this);
                if(i == Game.getPlayer().getCurrentSpellNumber())
                {
                    g2d.drawImage(this.CurrentSpellIcon, 52 + xShift + 100 * i, (int)scaledHeight - 112 + yShift, this);
                }
            }
        }
        
        
        paintStrings();
        
        for(int i = 0; i < 4; i ++)
        {
            if(Game.getPlayer().getSpell(i) == null)
            {
                g2d.drawImage(this.NullSpellIcon, 54 + xShift + 100 * i, (int)scaledHeight - 110 + yShift, this);
            }
            else
            {
                g2d.drawImage(Game.getPlayer().getSpell(i).getIcon(), 54 + xShift + 100 * i, (int)scaledHeight - 110 + yShift, this);
                if(i == Game.getPlayer().getCurrentSpellNumber())
                {
                    g2d.drawImage(this.CurrentSpellIcon, 52 + xShift + 100 * i, (int)scaledHeight - 112 + yShift, this);
                }
            }
        }
        
        g2d.setColor(Color.WHITE);
        Font spellNameFont = (new Font("Arial Black", Font.PLAIN, 16));
        Font spellCoolDownFont = (new Font("Arial Black", Font.PLAIN, 32));
        FontMetrics metrics = g2d.getFontMetrics(spellNameFont);
        
        for(int i = 0; i < 4; i ++)
        {
            if(Game.getPlayer().getSpell(i) != null)
            {
                g2d.setFont(spellNameFont);
                metrics = g2d.getFontMetrics(spellNameFont);
                g2d.drawString(Game.getPlayer().getSpell(i).getSpellName(), 
                        88 + xShift +100 * i - (metrics.stringWidth(Game.getPlayer().getSpell(i).getSpellName()))/2, (int)scaledHeight - 120 + yShift);
                if(Game.getPlayer().getSpell(i).getCoolDown(FPS) > 0)
                {
                    g2d.setFont(spellCoolDownFont);
                    metrics = g2d.getFontMetrics(spellCoolDownFont);
                    g2d.drawString(Integer.toString(Game.getPlayer().getSpell(i).getCoolDown(FPS)), 
                            88 + xShift + 100 * i - metrics.stringWidth(Integer.toString(Game.getPlayer().getSpell(i).getCoolDown(FPS)))/2, (int)scaledHeight - 65 + yShift);
                }
            }
            g2d.setFont(spellNameFont);
            metrics = g2d.getFontMetrics(spellNameFont);
            g2d.drawString(Integer.toString(i+1), 
                    88 + xShift + 100 * i - (metrics.stringWidth(Integer.toString(i+1)))/2, (int)scaledHeight - 20 + yShift);
        }
        
        if(PauseTutorial && Game.getPlayer().getSkillPoints() > 0)
        {
            Font pauseTutorialFont = (new Font("Arial Black", Font.PLAIN, 10));
            g2d.setColor(Color.black);
            metrics = g2d.getFontMetrics(pauseTutorialFont);
            String pauseString = "Press [Esc], [P], or [M] to spend skill points.";
            g2d.drawString(pauseString, (int)scaledWidth + xShift - 400, 30 + yShift);
        }
        
        int hpWidth = 211 * Game.getPlayer().getCurrentHealth() / Game.getPlayer().getMaxHealth();
        int xpWidth = 130 * Game.getPlayer().getExperience() / Game.getPlayer().getNextLevel();
        
        g2d.drawImage(this.HealthBarBackground, 40 + xShift, 40 + yShift, this);
        g2d.setColor(new Color(0, 245, 0));
        g2d.fillRect(112 + xShift, 60 + yShift, hpWidth, 1);
        g2d.setColor(new Color(0, 220, 0));
        g2d.fillRect(112 + xShift, 61 + yShift, hpWidth, 2);
        g2d.setColor(new Color(0, 200, 0));
        g2d.fillRect(112 + xShift, 63 + yShift, hpWidth, 5);
        g2d.setColor(new Color(0, 180, 0));
        g2d.fillRect(112 + xShift, 68 + yShift, hpWidth, 2);
        g2d.setColor(new Color(0, 160, 0));
        g2d.fillRect(112 + xShift, 70 + yShift, hpWidth, 2);
        g2d.setColor(new Color(0, 100, 0));
        g2d.fillRect(112 + xShift, 72 + yShift, hpWidth, 1);
        g2d.setColor(new Color(90, 0, 135));
        g2d.fillRect(115 + xShift, 80 + yShift, xpWidth, 5);
        
        Color frostColor = new Color(0, 100, 225);
        Color flameColor = new Color(250, 0, 0);
        Color voidColor = new Color(90, 0, 135);
        
        if(Game.getPlayer().getSpell(Game.getPlayer().getCurrentSpellNumber()).isIce())
        {
            g2d.setColor(frostColor);
            this.drawLeftOrb();
            if(Game.getPlayer().getSpell(Game.getPlayer().getCurrentSpellNumber()).isFire())
            {
                g2d.setColor(flameColor);
            }
            else if(Game.getPlayer().getSpell(Game.getPlayer().getCurrentSpellNumber()).isVoid())
            {
                g2d.setColor(voidColor);
            }
            this.drawRightOrb();
        }
        else if(Game.getPlayer().getSpell(Game.getPlayer().getCurrentSpellNumber()).isFire())
        {
            g2d.setColor(flameColor);
            this.drawLeftOrb();
            if(Game.getPlayer().getSpell(Game.getPlayer().getCurrentSpellNumber()).isVoid())
            {
                g2d.setColor(voidColor);
            }
            this.drawRightOrb();
        }
        else
        {
            g2d.setColor(voidColor);
            this.drawLeftOrb();
            this.drawRightOrb();
        }
        
        g2d.drawImage(this.HealthBar, 40 + xShift, 40 + yShift, this);
        
        BufferedImage shiftImg = bufImg.getSubimage(xShift, yShift, (int)scaledWidth, (int)scaledHeight);
        gtemp.scale(scale, scale);
        gtemp.drawImage(shiftImg, 0, 0, this);
        
        gtemp.dispose();
    }
    
    private void drawLeftOrb()
    {
        int yShift = Game.screenShiftY();
        int xShift = Game.screenShiftX();
        
        g2d.fillRect(78 + xShift, 46 + yShift, 18, 66);
        g2d.fillRect(96 + xShift, 52 + yShift, 10, 52);
        g2d.fillRect(106 + xShift, 56 + yShift, 4, 36);
    }
    
    private void drawRightOrb()
    {
        int yShift = Game.screenShiftY();
        int xShift = Game.screenShiftX();
        
        g2d.fillRect(60 + xShift, 46 + yShift, 18, 66);
        g2d.fillRect(50 + xShift, 52 + yShift, 10, 52);
        g2d.fillRect(46 + xShift, 56 + yShift, 4, 36);
    }
    
    private void paintStrings()
    {
        //Text for items
        Font itemNameFont = (new Font("Arial Black", Font.PLAIN, 14));
        g2d.setFont(itemNameFont);
        FontMetrics metrics = g2d.getFontMetrics(itemNameFont);
        
        for(int i = 0; i < this.Game.getRoom().RuneSize(); i++)
        {
            g2d.drawString(this.Game.getRoom().getRune(i).getRuneName(), 
                        this.Game.getRoom().getRune(i).getCenterX() - 
                                metrics.stringWidth(this.Game.getRoom().getRune(i).getRuneName())/2, 
                        this.Game.getRoom().getRune(i).getY() - 10);
        }
        
        for(int i = 0; i < this.Game.getRoom().PageSize(); i++)
        {
            g2d.drawString(this.Game.getRoom().getPage(i).getSpellName(), 
                        this.Game.getRoom().getPage(i).getCenterX() - 
                                metrics.stringWidth(this.Game.getRoom().getPage(i).getSpellName())/2, 
                        this.Game.getRoom().getPage(i).getY() - 10);
        }
        
        Color purple = new Color(100, 0, 150);
        Color blue = new Color(0, 0, 250);
        Color green =  new Color(0, 255, 50);
        Color red = new Color(200, 0, 0);
        Color orange = new Color(230, 80, 0);
        Color trash = new Color(200, 200, 255);
                
        for(int i = 0; i < this.Game.getRoom().RingSize(); i++)
        {
            this.paintGearInfo(Game.getRoom().getRing(i), Game.getPlayer().getRing());
        }
        
        for(int i = 0; i < this.Game.getRoom().NeckSize(); i++)
        {
            this.paintGearInfo(Game.getRoom().getNeck(i), Game.getPlayer().getNeck());
        }
        
        for(int i = 0; i < this.Game.getRoom().BootsSize(); i++)
        {
            this.paintGearInfo(Game.getRoom().getBoots(i), Game.getPlayer().getBoots());
        }
        
        for(int i = 0; i < this.Game.getRoom().TomeSize(); i++)
        {
            this.paintGearInfo(Game.getRoom().getTome(i), Game.getPlayer().getTome());
        }
    }
    
    private void paintGearInfo(Gear gear, Gear oldGear)
    {
        Color purple = new Color(100, 0, 150);
        Color blue = new Color(0, 0, 250);
        Color orange = new Color(230, 80, 0);
        Color trash = new Color(200, 200, 255);
        
        Font itemNameFont = (new Font("Arial Black", Font.PLAIN, 12));
        g2d.setFont(itemNameFont);
        FontMetrics metrics = g2d.getFontMetrics(itemNameFont);

        int rarityDisplacement = 10;
        String statValue;

        int darkVal, flameVal, frostVal, vitVal, intVal, speedVal;

        if(oldGear == null)
        {
            darkVal = gear.getDark();
            flameVal = gear.getFlame();
            frostVal = gear.getFrost();
            vitVal = gear.getVitality();
            intVal = gear.getIntellect();
            speedVal = gear.getMoveSpeed();
        }
        else
        {
            darkVal =  gear.getDark() - oldGear.getDark();
            flameVal = gear.getFlame() - oldGear.getFlame();
            frostVal = gear.getFrost() - oldGear.getFrost();
            vitVal = gear.getVitality() - oldGear.getVitality();
            intVal = gear.getIntellect() - oldGear.getIntellect();
            speedVal = gear.getMoveSpeed() - oldGear.getMoveSpeed();
        }

        if(speedVal != 0)
        {
            this.printStatInfo(speedVal, rarityDisplacement, "Move Speed", gear);
            rarityDisplacement += 15;
        }

        if(darkVal != 0)
        {
            this.printStatInfo(darkVal, rarityDisplacement, "Void", gear);
            rarityDisplacement += 15;
        }
        if(frostVal != 0)
        {
            this.printStatInfo(frostVal, rarityDisplacement, "Frost", gear);
            rarityDisplacement += 15;
        }
        if(flameVal != 0)
        {
            this.printStatInfo(flameVal, rarityDisplacement, "Flame", gear);
            rarityDisplacement += 15;
        }
        if(vitVal != 0)
        {
            this.printStatInfo(vitVal, rarityDisplacement, "Vitality", gear);
            rarityDisplacement += 15;
        }
        if(intVal != 0)
        {
            this.printStatInfo(intVal, rarityDisplacement, "Intellect", gear);
            rarityDisplacement += 15;
        }

        if( gear.getRarity() == 2)
        {
            g2d.setColor(blue);
        }
        else if( gear.getRarity() == 3)
        {
            g2d.setColor(purple);
        }
        else if( gear.getRarity() == 4)
        {
            g2d.setColor(orange);
        }
        else
        {
            g2d.setColor(trash);
        }

        itemNameFont = (new Font("Arial Black", Font.PLAIN, 14));
        g2d.setFont(itemNameFont);
        metrics = g2d.getFontMetrics(itemNameFont);

        g2d.drawString( gear.getItemName(), gear.getCenterX() - 
                metrics.stringWidth( gear.getItemName())/2, 
                gear.getY() - rarityDisplacement);
    }
    
    public void printStatInfo(int statVal, int rarityDisplacement, String statName,
            Gear gear)
    {
        Color green =  new Color(0, 255, 50);
        Color red = new Color(200, 0, 0);
        
        Font itemNameFont = (new Font("Arial Black", Font.PLAIN, 12));
        g2d.setFont(itemNameFont);
        FontMetrics metrics = g2d.getFontMetrics(itemNameFont);
        
        if(statVal != 0)
        {
            String statString = "";
            
            if(statVal > 0)
            {
                g2d.setColor(green);
                statString = "+";
            }
            else
            {
                g2d.setColor(red);
                statString = "-";
            }

            statString += Integer.toString(Math.abs(statVal)) + " " + statName;
            g2d.drawString(statString, gear.getCenterX() - metrics.stringWidth(statString)/2,
                    gear.getY() - rarityDisplacement);
        }
    }
        
    private BufferedImage bufferedImageConverter(Image img) {
        /*
        used to convert images into buffered images
        */
        BufferedImage bimg = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = bimg.createGraphics();
        g.drawImage(img, 0, 0, null);
        g.dispose();

        return bimg;
    }

    private void paintRotatedImg(Image img, double angle, int x, int y) {
        double rAngle = Math.toRadians(angle);
        int h = (int) (img.getWidth(null) * Math.abs(Math.sin(rAngle)) + img.getHeight(null) * Math.abs(Math.cos(rAngle)));
        int w = (int) (img.getHeight(null) * Math.abs(Math.sin(rAngle)) + img.getWidth(null) * Math.abs(Math.cos(rAngle)));

        BufferedImage bimgTemp = bufferedImageConverter(img);

        AffineTransform old = g2d.getTransform();

        AffineTransform at = AffineTransform.getRotateInstance(rAngle, x + img.getWidth(this) / 2, y + img.getHeight(this) / 2);
        g2d.setTransform(at);
        g2d.drawImage(bimgTemp, x, y, null);
        g2d.setTransform(old);
    }
    
    
    
    public static void main(String[] args) {
        PlayGame game = new PlayGame();
        //game.startGame();
        game.resourcesInit();
        game.startGame();
        game.pauseMenuInit();
        game.mainMenuInit();
        game.newGameInit();
        //game.musicThreadLoop();
        //game.mainMenu();
        game.timerLoop();
    }
}