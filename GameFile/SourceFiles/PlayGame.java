package SourceFiles;

import SourceFiles.Room.Room;
import SourceFiles.GameObjects.MovingObjects.Player.Spell;
import SourceFiles.GameObjects.MovingObjects.Player.ProjectileSpell;
import SourceFiles.GameObjects.MovingObjects.Player.WizardPlayer;
import SourceFiles.GameLogic.GameEvents;
import SourceFiles.GameObjects.MovingObjects.Enemies.MovingEnemy;
import SourceFiles.GameObjects.MovingObjects.Enemies.SpearGoblin;
import SourceFiles.GameObjects.MovingObjects.Enemies.DartGoblin;
import SourceFiles.GameObjects.StationaryObjects.StationaryObject;
import SourceFiles.GameObjects.StationaryObjects.Wall;
import SourceFiles.GameObjects.StationaryObjects.Door;
import SourceFiles.GameObjects.Animations.Animation;
import SourceFiles.GameObjects.StationaryObjects.GearObjects.Boots;
import SourceFiles.GameObjects.StationaryObjects.GearObjects.Ring;
import SourceFiles.GameObjects.StationaryObjects.GearObjects.Neck;
import SourceFiles.GameObjects.StationaryObjects.GearObjects.Tome;
import SourceFiles.GameLogic.KeyControl;
import SourceFiles.GameLogic.CollisionDetector;
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
import java.util.Random;
import javax.swing.*;

public class PlayGame extends JPanel implements KeyListener{
    /*
    PlayGame is the main controller for the game. Loads and initializes all 
    images/objects used, creates the window used for the game, and controls the 
    main game loop.
    */
    
    //Array of arrays used to store Rooms on a grid
    private Room[][] Rooms;
    private int RoomsI, RoomsJ;
    private BufferedImage bufImg;
    private Graphics2D g2d;
    private Image ForestFloor, TempleFloor, tempchar, NullSpellIcon, CurrentSpellIcon, 
            IceShardsImg, IceShardsIcon, FireBallImg, FireBallIcon, VoidWaveImg,
            VoidWaveIcon, TestSpellImg, ChilledImg, FrozenImg, IceShardsShadow,
            FireBallShadow, SmallProjectileShadow, VoidWaveShadow, testenemy;
    private Image[] IceShardsBreak, VoidWaveEnd, FireBallEnd, BurningImgs;
    private Image TopWallLeftImg, TopWallRightImg, TopWallMidImg,
            LeftWallTopImg, LeftWallBottomImg, LeftWallMidImg,
            RightWallTopImg, RightWallBottomImg, RightWallMidImg,
            BottomWallLeftImg, BottomWallRightImg, BottomWallMidImg;
    //private final int ScreenWidth = 1024, ScreenHeight = 960;
    //private final int ScreenWidth = 1280, ScreenHeight = 720;
    private final int ScreenWidth = 1280, ScreenHeight = 960;
    private final int GameWidth = 1280, GameHeight = 1280;
    private final int FPS = 60;
    private final int ShadowHeight = 60;
    private boolean levelFinished;
    private WizardPlayer Player;
    private int Money;
    private boolean Paused, InGame;
    private JFrame GameWindow;
    private JPanel PauseMenu, MainMenu;
    private GameEvents PlayerKeyEvent;
    private Spell FireBall, IceShards, VoidWave, WildFire, Blizzard, BlackHole, Meteor, Comet, FrostFlame;//FrostFlame:: Blue Fire
    private Wall[] Walls;
    private Door TopDoor, LeftDoor, BottomDoor, RightDoor;
    private Wall TopWallMid, LeftWallMid, BottomWallMid, RightWallMid;
    private Animation Burning;
    
    private Image[] WizRightForwardAttack, WizRightForward, WizRightBackAttack,
            WizRightAttack, WizRight, WizLeftForwardAttack, WizLeftForward,
            WizLeftBackwardAttack, WizLeftAttack, WizLeft, WizForwardAttack, 
            WizForward, WizBackAttack, WizBack;
    
    private Image[] SpearGoblinRight, SpearGoblinLeft, DartGoblinLeft, DartGoblinRight,
            DartGoblinLeftAttack, DartGoblinRightAttack;
    private Image[] TopDoorImgs, LeftDoorImgs, BottomDoorImgs, RightDoorImgs;
    
    private Image SmallProjectileGreen;
    private Image[] SmallGreenProjectileEnd;
    private Image[] RubyRing, AmethystRing, SaphireRing, EmeraldRing, RubyNeck, 
            SaphireNeck, EmeraldNeck, AmethystNeck;
    private Image FlameTome, FrostTome, VoidTome, FlameBoots, FrostBoots, VoidBoots;
    
    private JButton testButton;
    
    public void startGame()
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
        this.Paused = false;
        this.InGame = false;
        
        //GameWindow.setExtendedState(JFrame.MAXIMIZED_BOTH);
        //GameWindow.setUndecorated(true);
        
    }
    
    public void mainMenuInit()
    {
        
    }
    
    public void pauseMenuInit()
    {
        /*
        PauseMenu is used whenever the pause game button is pressed. Currently
        being worked on, will display a map, equipped gear, and talent tree
        */
        PauseMenu = new JPanel();
        PauseMenu.setVisible(false);
        PauseMenu.setLayout(null);
        
        JButton testbut = new JButton(new ImageIcon(tempchar));
        //testbut.setIcon(new ImageIcon(tempchar));
        //testbut.setRolloverIcon(null);
        //testbut.setDisabledIcon(null);
        //testbut.setPressedIcon(icon);
        //testbut.setRolloverSelectedIcon(icon);
        testbut.setBorder(BorderFactory.createEmptyBorder());
        testbut.setContentAreaFilled(false);
        testbut.setBounds(100,100, this.tempchar.getWidth(null), tempchar.getHeight(null));
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
        
        
        JLabel testlab = new JLabel(new ImageIcon(this.ForestFloor));
        testlab.setBounds(0, 0, ForestFloor.getWidth(null), ForestFloor.getHeight(null));
        testlab.setVisible(true);
        PauseMenu.add(testlab);
        
        PauseMenu.setBounds(200, 200, 200, 200);
        
        GameWindow.add(PauseMenu);
        GameWindow.setVisible(true);
    }
    
    public void pauseGame()
    {
        this.Paused = true;
        this.setVisible(false);
        this.PauseMenu.setVisible(true);
    }
    
    public void unPauseGame()
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
    
    public void resourcesInit()
    {
        /*
        Initializes all images/ constants, such as walls, used in the game.
        Images are initialized in the beginning and declared globally;
        instead of reading an image every time an object is created, images are 
        loaded only once to increase performance.
        */
        try{
            this.ForestFloor = ImageIO.read(new File("Resources" + File.separator + "ForestBackground.png"));
            this.tempchar = ImageIO.read(new File("Resources" + File.separator + "char.png"));
            this.NullSpellIcon = ImageIO.read(new File("Resources" + File.separator + "NullIcon.png"));
            this.CurrentSpellIcon = ImageIO.read(new File("Resources" + File.separator + "CurrentSpellIcon.png"));
            this.testenemy = ImageIO.read(new File("Resources" + File.separator + "testenemy.png"));
            
            this.TestSpellImg = ImageIO.read(new File("Resources" + File.separator + "TestSpell.png"));
            
            this.IceShardsImg = ImageIO.read(new File("Resources" + File.separator + "IceShardsImg.png"));
            this.IceShardsIcon = ImageIO.read(new File("Resources" + File.separator + "IceShardsIcon.png"));
            this.FireBallImg = ImageIO.read(new File("Resources" + File.separator + "FireBallImg.png"));
            this.FireBallIcon = ImageIO.read(new File("Resources" + File.separator + "FireBallIcon.png"));
            this.VoidWaveImg = ImageIO.read(new File("Resources" + File.separator + "VoidWaveImg.png"));
            this.VoidWaveIcon = ImageIO.read(new File("Resources" + File.separator + "VoidWaveIcon.png"));
            
            IceShardsShadow = ImageIO.read(new File("Resources" + File.separator + "IceShardsShadow.png"));
            FireBallShadow = ImageIO.read(new File("Resources" + File.separator + "FireBallShadow.png"));
            VoidWaveShadow = ImageIO.read(new File("Resources" + File.separator + "VoidWaveShadow.png"));
            SmallProjectileShadow = ImageIO.read(new File("Resources" + File.separator + "SmallProjectileShadow.png"));
            
            WizRightForwardAttack = new Image[3];
            WizRightForwardAttack[0] = ImageIO.read(new File("Resources" + File.separator + "WizRightForwardAttack1.png"));
            WizRightForwardAttack[1] = ImageIO.read(new File("Resources" + File.separator + "WizRightForwardAttack2.png"));
            WizRightForwardAttack[2] = ImageIO.read(new File("Resources" + File.separator + "WizRightForwardAttack3.png"));
            
            WizRightForward = new Image[3];
            WizRightForward[0] = ImageIO.read(new File("Resources" + File.separator + "WizRightForward1.png"));
            WizRightForward[1] = ImageIO.read(new File("Resources" + File.separator + "WizRightForward2.png"));
            WizRightForward[2] = ImageIO.read(new File("Resources" + File.separator + "WizRightForward3.png"));
            
            WizRightBackAttack = new Image[3];
            WizRightBackAttack[0] = ImageIO.read(new File("Resources" + File.separator + "WizRightBackAttack1.png"));
            WizRightBackAttack[1] = ImageIO.read(new File("Resources" + File.separator + "WizRightBackAttack2.png"));
            WizRightBackAttack[2] = ImageIO.read(new File("Resources" + File.separator + "WizRightBackAttack3.png"));
            
            WizRightAttack = new Image[3];
            WizRightAttack[0] = ImageIO.read(new File("Resources" + File.separator + "WizRightAttack1.png"));
            WizRightAttack[1] = ImageIO.read(new File("Resources" + File.separator + "WizRightAttack2.png"));
            WizRightAttack[2] = ImageIO.read(new File("Resources" + File.separator + "WizRightAttack3.png"));
            
            WizRight = new Image[3];
            WizRight[0] = ImageIO.read(new File("Resources" + File.separator + "WizRight1.png"));
            WizRight[1] = ImageIO.read(new File("Resources" + File.separator + "WizRight2.png"));
            WizRight[2] = ImageIO.read(new File("Resources" + File.separator + "WizRight3.png"));
            
            WizLeftForwardAttack = new Image[3];
            WizLeftForwardAttack[0] = ImageIO.read(new File("Resources" + File.separator + "WizLeftForwardAttack1.png"));
            WizLeftForwardAttack[1] = ImageIO.read(new File("Resources" + File.separator + "WizLeftForwardAttack2.png"));
            WizLeftForwardAttack[2] = ImageIO.read(new File("Resources" + File.separator + "WizLeftForwardAttack3.png"));
            
            WizLeftForward = new Image[3];
            WizLeftForward[0] = ImageIO.read(new File("Resources" + File.separator + "WizLeftForward1.png"));
            WizLeftForward[1] = ImageIO.read(new File("Resources" + File.separator + "WizLeftForward2.png"));
            WizLeftForward[2] = ImageIO.read(new File("Resources" + File.separator + "WizLeftForward3.png"));
            
            WizLeftBackwardAttack = new Image[3];
            WizLeftBackwardAttack[0] = ImageIO.read(new File("Resources" + File.separator + "WizLeftBackAttack1.png"));
            WizLeftBackwardAttack[1] = ImageIO.read(new File("Resources" + File.separator + "WizLeftBackAttack2.png"));
            WizLeftBackwardAttack[2] = ImageIO.read(new File("Resources" + File.separator + "WizLeftBackAttack3.png"));
            
            WizLeftAttack = new Image[3];
            WizLeftAttack[0] = ImageIO.read(new File("Resources" + File.separator + "WizLeftAttack1.png"));
            WizLeftAttack[1] = ImageIO.read(new File("Resources" + File.separator + "WizLeftAttack2.png"));
            WizLeftAttack[2] = ImageIO.read(new File("Resources" + File.separator + "WizLeftAttack3.png"));
            
            WizLeft = new Image[3];
            WizLeft[0] = ImageIO.read(new File("Resources" + File.separator + "WizLeft1.png"));
            WizLeft[1] = ImageIO.read(new File("Resources" + File.separator + "WizLeft2.png"));
            WizLeft[2] = ImageIO.read(new File("Resources" + File.separator + "WizLeft3.png"));
            
            WizForwardAttack = new Image[3];
            WizForwardAttack[0] = ImageIO.read(new File("Resources" + File.separator + "WizForwardAttack1.png"));
            WizForwardAttack[1] = ImageIO.read(new File("Resources" + File.separator + "WizForwardAttack2.png"));
            WizForwardAttack[2] = ImageIO.read(new File("Resources" + File.separator + "WizForwardAttack3.png"));
            
            WizForward = new Image[3];
            WizForward[0] = ImageIO.read(new File("Resources" + File.separator + "WizForward1.png"));
            WizForward[1] = ImageIO.read(new File("Resources" + File.separator + "WizForward2.png"));
            WizForward[2] = ImageIO.read(new File("Resources" + File.separator + "WizForward3.png"));
            
            WizBackAttack = new Image[3];
            WizBackAttack[0] = ImageIO.read(new File("Resources" + File.separator + "WizBackAttack1.png"));
            WizBackAttack[1] = ImageIO.read(new File("Resources" + File.separator + "WizBackAttack2.png"));
            WizBackAttack[2] = ImageIO.read(new File("Resources" + File.separator + "WizBackAttack3.png"));
            
            WizBack = new Image[3];           
            WizBack[0] = ImageIO.read(new File("Resources" + File.separator + "WizBack1.png"));
            WizBack[1] = ImageIO.read(new File("Resources" + File.separator + "WizBack2.png"));
            WizBack[2] = ImageIO.read(new File("Resources" + File.separator + "WizBack3.png"));
            
            SpearGoblinRight = new Image[6];           
            SpearGoblinRight[0] = ImageIO.read(new File("Resources" + File.separator + "SpearGoblinRight1.png"));
            SpearGoblinRight[1] = ImageIO.read(new File("Resources" + File.separator + "SpearGoblinRight2.png"));
            SpearGoblinRight[2] = ImageIO.read(new File("Resources" + File.separator + "SpearGoblinRight3.png"));
            SpearGoblinRight[3] = ImageIO.read(new File("Resources" + File.separator + "SpearGoblinRight4.png"));
            SpearGoblinRight[4] = ImageIO.read(new File("Resources" + File.separator + "SpearGoblinRight5.png"));
            SpearGoblinRight[5] = ImageIO.read(new File("Resources" + File.separator + "SpearGoblinRight6.png"));
            
            SpearGoblinLeft = new Image[6];           
            SpearGoblinLeft[0] = ImageIO.read(new File("Resources" + File.separator + "SpearGoblinLeft1.png"));
            SpearGoblinLeft[1] = ImageIO.read(new File("Resources" + File.separator + "SpearGoblinLeft2.png"));
            SpearGoblinLeft[2] = ImageIO.read(new File("Resources" + File.separator + "SpearGoblinLeft3.png"));
            SpearGoblinLeft[3] = ImageIO.read(new File("Resources" + File.separator + "SpearGoblinLeft4.png"));
            SpearGoblinLeft[4] = ImageIO.read(new File("Resources" + File.separator + "SpearGoblinLeft5.png"));
            SpearGoblinLeft[5] = ImageIO.read(new File("Resources" + File.separator + "SpearGoblinLeft6.png"));
            
            DartGoblinLeft = new Image[6];           
            DartGoblinLeft[0] = ImageIO.read(new File("Resources" + File.separator + "DartGoblinLeft1.png"));
            DartGoblinLeft[1] = ImageIO.read(new File("Resources" + File.separator + "DartGoblinLeft2.png"));
            DartGoblinLeft[2] = ImageIO.read(new File("Resources" + File.separator + "DartGoblinLeft3.png"));
            DartGoblinLeft[3] = ImageIO.read(new File("Resources" + File.separator + "DartGoblinLeft4.png"));
            DartGoblinLeft[4] = ImageIO.read(new File("Resources" + File.separator + "DartGoblinLeft5.png"));
            DartGoblinLeft[5] = ImageIO.read(new File("Resources" + File.separator + "DartGoblinLeft6.png"));
            
            DartGoblinRight = new Image[6];           
            DartGoblinRight[0] = ImageIO.read(new File("Resources" + File.separator + "DartGoblinRight1.png"));
            DartGoblinRight[1] = ImageIO.read(new File("Resources" + File.separator + "DartGoblinRight2.png"));
            DartGoblinRight[2] = ImageIO.read(new File("Resources" + File.separator + "DartGoblinRight3.png"));
            DartGoblinRight[3] = ImageIO.read(new File("Resources" + File.separator + "DartGoblinRight4.png"));
            DartGoblinRight[4] = ImageIO.read(new File("Resources" + File.separator + "DartGoblinRight5.png"));
            DartGoblinRight[5] = ImageIO.read(new File("Resources" + File.separator + "DartGoblinRight6.png"));
            
            DartGoblinLeftAttack = new Image[2];           
            DartGoblinLeftAttack[0] = ImageIO.read(new File("Resources" + File.separator + "DartGoblinLeftAttack1.png"));
            DartGoblinLeftAttack[1] = ImageIO.read(new File("Resources" + File.separator + "DartGoblinLeftAttack2.png"));
            
            DartGoblinRightAttack = new Image[2];           
            DartGoblinRightAttack[0] = ImageIO.read(new File("Resources" + File.separator + "DartGoblinRightAttack1.png"));
            DartGoblinRightAttack[1] = ImageIO.read(new File("Resources" + File.separator + "DartGoblinRightAttack2.png"));
            
            SmallProjectileGreen = ImageIO.read(new File("Resources" + File.separator + "SmallProjectileGreen.png"));
            
            ChilledImg = ImageIO.read(new File("Resources" + File.separator + "chilled.png"));
            FrozenImg = ImageIO.read(new File("Resources" + File.separator + "frozen.png"));
            
            TopWallRightImg = ImageIO.read(new File("Resources" + File.separator + "TopWallRight.png"));
            TopWallMidImg = ImageIO.read(new File("Resources" + File.separator + "TopWallMiddle.png"));
            TopWallLeftImg = ImageIO.read(new File("Resources" + File.separator + "TopWallLeft.png"));
            BottomWallRightImg = ImageIO.read(new File("Resources" + File.separator + "BottomWallRight.png"));
            BottomWallMidImg = ImageIO.read(new File("Resources" + File.separator + "BottomWallMiddle.png"));
            BottomWallLeftImg = ImageIO.read(new File("Resources" + File.separator + "BottomWallLeft.png"));
            LeftWallTopImg = ImageIO.read(new File("Resources" + File.separator + "LeftWallTop.png"));
            LeftWallMidImg = ImageIO.read(new File("Resources" + File.separator + "LeftWallMiddle.png"));
            LeftWallBottomImg = ImageIO.read(new File("Resources" + File.separator + "LeftWallBottom.png"));
            RightWallTopImg = ImageIO.read(new File("Resources" + File.separator + "RightWallTop.png"));
            RightWallMidImg = ImageIO.read(new File("Resources" + File.separator + "RightWallMiddle.png"));
            RightWallBottomImg = ImageIO.read(new File("Resources" + File.separator + "RightWallBottom.png"));
            
            TopDoorImgs = new Image[7];
            TopDoorImgs[0] = ImageIO.read(new File("Resources" + File.separator + "TopDoorOpen.png"));
            TopDoorImgs[1] = ImageIO.read(new File("Resources" + File.separator + "TopDoorClosing1.png"));
            TopDoorImgs[2] = ImageIO.read(new File("Resources" + File.separator + "TopDoorClosing2.png"));
            TopDoorImgs[3] = ImageIO.read(new File("Resources" + File.separator + "TopDoorClosing3.png"));
            TopDoorImgs[4] = ImageIO.read(new File("Resources" + File.separator + "TopDoorClosing4.png"));
            TopDoorImgs[5] = ImageIO.read(new File("Resources" + File.separator + "TopDoorClosing5.png"));
            TopDoorImgs[6] = ImageIO.read(new File("Resources" + File.separator + "TopDoorClosed.png"));
            
            LeftDoorImgs = new Image[7];
            LeftDoorImgs[0] = ImageIO.read(new File("Resources" + File.separator + "LeftDoorOpen.png"));
            LeftDoorImgs[1] = ImageIO.read(new File("Resources" + File.separator + "LeftDoorClosing1.png"));
            LeftDoorImgs[2] = ImageIO.read(new File("Resources" + File.separator + "LeftDoorClosing2.png"));
            LeftDoorImgs[3] = ImageIO.read(new File("Resources" + File.separator + "LeftDoorClosing3.png"));
            LeftDoorImgs[4] = ImageIO.read(new File("Resources" + File.separator + "LeftDoorClosing4.png"));
            LeftDoorImgs[5] = ImageIO.read(new File("Resources" + File.separator + "LeftDoorClosing5.png"));
            LeftDoorImgs[6] = ImageIO.read(new File("Resources" + File.separator + "LeftDoorClosed.png"));
            
            RightDoorImgs = new Image[7];
            RightDoorImgs[0] = ImageIO.read(new File("Resources" + File.separator + "RightDoorOpen.png"));
            RightDoorImgs[1] = ImageIO.read(new File("Resources" + File.separator + "RightDoorClosing1.png"));
            RightDoorImgs[2] = ImageIO.read(new File("Resources" + File.separator + "RightDoorClosing2.png"));
            RightDoorImgs[3] = ImageIO.read(new File("Resources" + File.separator + "RightDoorClosing3.png"));
            RightDoorImgs[4] = ImageIO.read(new File("Resources" + File.separator + "RightDoorClosing4.png"));
            RightDoorImgs[5] = ImageIO.read(new File("Resources" + File.separator + "RightDoorClosing5.png"));
            RightDoorImgs[6] = ImageIO.read(new File("Resources" + File.separator + "RightDoorClosed.png"));
            
            BottomDoorImgs = new Image[7];
            BottomDoorImgs[0] = ImageIO.read(new File("Resources" + File.separator + "BottomDoorOpen.png"));
            BottomDoorImgs[1] = ImageIO.read(new File("Resources" + File.separator + "BottomDoorClosing1.png"));
            BottomDoorImgs[2] = ImageIO.read(new File("Resources" + File.separator + "BottomDoorClosing2.png"));
            BottomDoorImgs[3] = ImageIO.read(new File("Resources" + File.separator + "BottomDoorClosing3.png"));
            BottomDoorImgs[4] = ImageIO.read(new File("Resources" + File.separator + "BottomDoorClosing4.png"));
            BottomDoorImgs[5] = ImageIO.read(new File("Resources" + File.separator + "BottomDoorClosing5.png"));
            BottomDoorImgs[6] = ImageIO.read(new File("Resources" + File.separator + "BottomDoorClosed.png"));
            
            TempleFloor = ImageIO.read(new File("Resources" + File.separator + "Floor.png"));
            
            SmallGreenProjectileEnd = new Image[4];
            SmallGreenProjectileEnd[0] = ImageIO.read(new File("Resources" + File.separator + "SmallProjectileGreenEnd1.png"));
            SmallGreenProjectileEnd[1] = ImageIO.read(new File("Resources" + File.separator + "SmallProjectileGreenEnd2.png"));
            SmallGreenProjectileEnd[2] = ImageIO.read(new File("Resources" + File.separator + "SmallProjectileGreenEnd3.png"));
            SmallGreenProjectileEnd[3] = ImageIO.read(new File("Resources" + File.separator + "SmallProjectileGreenEnd4.png"));
            
            IceShardsBreak = new Image[4];
            IceShardsBreak[0] = ImageIO.read(new File("Resources" + File.separator + "IceShardsBreak1.png"));
            IceShardsBreak[1] = ImageIO.read(new File("Resources" + File.separator + "IceShardsBreak2.png"));
            IceShardsBreak[2] = ImageIO.read(new File("Resources" + File.separator + "IceShardsBreak3.png"));
            IceShardsBreak[3] = ImageIO.read(new File("Resources" + File.separator + "IceShardsBreak4.png"));
            
            VoidWaveEnd = new Image[4];
            VoidWaveEnd[0] = ImageIO.read(new File("Resources" + File.separator + "VoidWaveEnd1.png"));
            VoidWaveEnd[1] = ImageIO.read(new File("Resources" + File.separator + "VoidWaveEnd2.png"));
            VoidWaveEnd[2] = ImageIO.read(new File("Resources" + File.separator + "VoidWaveEnd3.png"));
            VoidWaveEnd[3] = ImageIO.read(new File("Resources" + File.separator + "VoidWaveEnd4.png"));
            
            FireBallEnd = new Image[6];
            FireBallEnd[0] = ImageIO.read(new File("Resources" + File.separator + "FireBallEnd1.png"));
            FireBallEnd[1] = ImageIO.read(new File("Resources" + File.separator + "FireBallEnd2.png"));
            FireBallEnd[2] = ImageIO.read(new File("Resources" + File.separator + "FireBallEnd3.png"));
            FireBallEnd[3] = ImageIO.read(new File("Resources" + File.separator + "FireBallEnd4.png"));
            FireBallEnd[4] = ImageIO.read(new File("Resources" + File.separator + "FireBallEnd5.png"));
            FireBallEnd[5] = ImageIO.read(new File("Resources" + File.separator + "FireBallEnd6.png"));
            
            BurningImgs = new Image[4];
            BurningImgs[0] = ImageIO.read(new File("Resources" + File.separator + "Burning1.png"));
            BurningImgs[1] = ImageIO.read(new File("Resources" + File.separator + "Burning2.png"));
            BurningImgs[2] = ImageIO.read(new File("Resources" + File.separator + "Burning3.png"));
            BurningImgs[3] = ImageIO.read(new File("Resources" + File.separator + "Burning4.png"));
            
            RubyRing = new Image[2];
            RubyRing[0] = ImageIO.read(new File("Resources" + File.separator + "RubyRing1.png"));
            RubyRing[1] = ImageIO.read(new File("Resources" + File.separator + "RubyRing2.png"));
            
            RubyNeck = new Image[2];
            RubyNeck[0] = ImageIO.read(new File("Resources" + File.separator + "RubyNeck1.png"));
            RubyNeck[1] = ImageIO.read(new File("Resources" + File.separator + "RubyNeck2.png"));
            
            SaphireRing = new Image[2];
            SaphireRing[0] = ImageIO.read(new File("Resources" + File.separator + "SaphireRing1.png"));
            SaphireRing[1] = ImageIO.read(new File("Resources" + File.separator + "SaphireRing2.png"));
            
            SaphireNeck = new Image[2];
            SaphireNeck[0] = ImageIO.read(new File("Resources" + File.separator + "SaphireNeck1.png"));
            SaphireNeck[1] = ImageIO.read(new File("Resources" + File.separator + "SaphireNeck2.png"));
            
            EmeraldRing = new Image[2];
            EmeraldRing[0] = ImageIO.read(new File("Resources" + File.separator + "EmeraldRing1.png"));
            EmeraldRing[1] = ImageIO.read(new File("Resources" + File.separator + "EmeraldRing2.png"));
            
            EmeraldNeck = new Image[2];
            EmeraldNeck[0] = ImageIO.read(new File("Resources" + File.separator + "EmeraldNeck1.png"));
            EmeraldNeck[1] = ImageIO.read(new File("Resources" + File.separator + "EmeraldNeck2.png"));
            
            AmethystRing = new Image[2];
            AmethystRing[0] = ImageIO.read(new File("Resources" + File.separator + "AmethystRing1.png"));
            AmethystRing[1] = ImageIO.read(new File("Resources" + File.separator + "AmethystRing2.png"));
            
            AmethystNeck = new Image[2];
            AmethystNeck[0] = ImageIO.read(new File("Resources" + File.separator + "AmethystNeck1.png"));
            AmethystNeck[1] = ImageIO.read(new File("Resources" + File.separator + "AmethystNeck2.png"));
            
            FlameTome = ImageIO.read(new File("Resources" + File.separator + "FlameTome.png"));
            FrostTome = ImageIO.read(new File("Resources" + File.separator + "FrostTome.png"));
            VoidTome = ImageIO.read(new File("Resources" + File.separator + "VoidTome.png"));

            FlameBoots = ImageIO.read(new File("Resources" + File.separator + "FlameBoots.png"));
            FrostBoots = ImageIO.read(new File("Resources" + File.separator + "FrostBoots.png"));
            VoidBoots = ImageIO.read(new File("Resources" + File.separator + "VoidBoots.png"));
        } catch (Exception e) {
            System.out.print(e.getStackTrace() + " Error loading resources \n");
        }
        
        //Now constant spells/objects that never change are created
        IceShards = new ProjectileSpell("Ice Shards", 5,10, 30, false, true, false, 30, IceShardsImg, IceShardsIcon, IceShardsShadow, IceShardsBreak);
        FireBall = new ProjectileSpell("Fire Ball", 5,10, 30, true, false, false, 30, FireBallImg, FireBallIcon, FireBallShadow, FireBallEnd);
        VoidWave = new ProjectileSpell("Void Wave", 1,10, 59, false, false, true, 0, VoidWaveImg, VoidWaveIcon, VoidWaveShadow, VoidWaveEnd);
        
        Burning = new Animation(0, 0, 0, BurningImgs, 3, true);
        
        Walls = new Wall[8];
        Walls[0] = new Wall(0, 0, 579, 128 - ShadowHeight, TopWallLeftImg);
        Walls[1] = new Wall(700, 0, 580, 128 - ShadowHeight, TopWallRightImg);
        Walls[2] = new Wall(0, 0, 128, 580, LeftWallTopImg);
        Walls[3] = new Wall(0, 701, 128, 579, LeftWallBottomImg);
        Walls[4] = new Wall(0, 1152, 580, 128, BottomWallLeftImg);
        Walls[5] = new Wall(701, 1152, 579, 128, BottomWallRightImg);
        Walls[6] = new Wall(1152, 0, 128, 579, RightWallTopImg);
        Walls[7] = new Wall(1152, 700, 128, 580, RightWallBottomImg);
        
        TopDoor = new Door(579, 0, 121, 128 - ShadowHeight, TopDoorImgs);
        LeftDoor = new Door(0, 580, 128,121, LeftDoorImgs);
        BottomDoor = new Door(580, 1152, 121, 128, BottomDoorImgs);
        RightDoor = new Door(1152, 579, 128, 121, RightDoorImgs);
        
        TopWallMid = new Wall(579, 0, 121, 60, TopWallMidImg);
        LeftWallMid = new Wall(0, 580, 128,121, LeftWallMidImg);
        BottomWallMid = new Wall(580, 1152, 121, 128, BottomWallMidImg);
        RightWallMid = new Wall(1152, 579, 128, 121, RightWallMidImg);
    }
    
    public void newGameInit()
    {
        /*
        newGameInit is used for whenevr a new game is started. It resets all
        game values to their starting values and creates the player's character.
        */
        this.Paused = false;
        this.InGame = true;
        this.Money = 0;
        this.Player = new WizardPlayer(200, 200, 0, this.GameWidth, 0, this.GameHeight, 
                WizRightForwardAttack, WizRightForward, WizRightBackAttack,
                WizRightAttack, WizRight, WizLeftForwardAttack, WizLeftForward,
                WizLeftBackwardAttack, WizLeftAttack, WizLeft, WizForwardAttack, 
                WizForward, WizBackAttack, WizBack);
        
        
        this.Player.addNewSpell(IceShards);
        this.Player.addNewSpell(FireBall);
        this.Player.addNewSpell(VoidWave);
        //this.Player.addNewSpell(new ProjectileSpell("Test Spell", 5,10, 30, false, false, false, 0, TestSpellImg,this.NullSpellIcon,this.SmallProjectileShadow, null));
        
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
        GameWindow.addMouseWheelListener(new MouseWheelListener() {
            public void mouseWheelMoved(MouseWheelEvent e){
                if(e.getWheelRotation() < 0)
                {
                    Player.scrollUp();
                }
                else if(e.getWheelRotation() > 0)
                {
                    Player.scrollDown();
                }
            }
        });
    }
    
    public void testLevelInit()
    {
        //Temporary test level to test new objects as they are created
        Rooms = new Room[5][5];
        this.RoomsI = 0;
        this.RoomsJ = 0;
        this.Rooms[RoomsI][RoomsJ] = new Room();
        
        this.Rooms[RoomsI][RoomsJ].addDoor(TopDoor);
        this.Rooms[RoomsI][RoomsJ].addDoor(LeftDoor);
        this.Rooms[RoomsI][RoomsJ].addWall(RightWallMid);
        this.Rooms[RoomsI][RoomsJ].addWall(BottomWallMid);
       
        SpearGoblin gobo = new SpearGoblin(700, 700, 0, this.GameWidth, 0, 
                this.GameHeight, 1, this.SpearGoblinLeft, this.SpearGoblinRight);
        DartGoblin gobo2 = new DartGoblin(900, 900, 0, this.GameWidth, 0, 
                this.GameHeight, 1, this.DartGoblinLeft, this.DartGoblinRight,
                this.DartGoblinLeftAttack, this.DartGoblinRightAttack, 
                this.SmallProjectileGreen, this.SmallProjectileShadow,
                this.SmallGreenProjectileEnd);
        
        Rooms[RoomsI][RoomsJ].lockDoors();
        Rooms[RoomsI][RoomsJ].addEnemy(gobo);
        Rooms[RoomsI][RoomsJ].addEnemy(gobo2);
        
        addRandomGear(400, 200);
        addRandomGear(600, 200);
        addRandomGear(800, 200);
    }
    
    public void timerLoop()
    {
        /*
        timerLoop is the main game loop that repeats to update the game. It aims 
        to update all objects in 1/60 second and tells the thread to sleep for
        any remaining time aftre all updates are complete.
        */
        
        long currTime;
        //target time hoping to aim for each loop
        long targetTime = 1000000000 / FPS;
        

        while ((Player.isAlive()) && !levelFinished) {
            currTime = System.nanoTime();

            updateGame();
            repaint();
            
            //While the game is paused, leave the game loop in loop that sleeps
            //and continously checks if the game is puased until the game resumes.
            if(this.Paused)
            {
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
                } catch (InterruptedException ex) {}
            }
        }
    }
    
    public void updatePlayer()
    {
        /*
        updatePlayer is used to check for collisions with GameObjects. Then it 
        calls the player's update function, and checks if the player has any 
        projectiles or aoe's ready to be created, and adds the to the current Room.
        */
        CollisionDetector col = new CollisionDetector();
        
        boolean generalCol = false;
        boolean verticalCol = false;
        boolean horizontalCol = false;
        
        for(int i = 0; i < Walls.length; i++)
        {
            if(verticalCol && horizontalCol)
            {
                break;
            }
            
            if(col.normalCollision(Player, Walls[i]))
            {
                generalCol = true;
                
                if(col.horizontalCollision(Player, Walls[i]))
                {
                    horizontalCol = true;
                }
                
                if(col.verticalCollision(Player, Walls[i]))
                {
                    verticalCol = true;
                }
            }
        }
        
        for(int i = 0; i < Rooms[RoomsI][RoomsJ].DoorSize(); i++)
        {
            if(verticalCol && horizontalCol)
            {
                break;
            }
            
            if(col.normalCollision(Player, Rooms[RoomsI][RoomsJ].getDoor(i)))
            {
                generalCol = true;
                
                if(Rooms[RoomsI][RoomsJ].getDoor(i).isLocked())
                {
                    if(col.horizontalCollision(Player, Rooms[RoomsI][RoomsJ].getDoor(i)))
                    {
                        horizontalCol = true;
                    }

                    if(col.verticalCollision(Player, Rooms[RoomsI][RoomsJ].getDoor(i)))
                    {
                        verticalCol = true;
                    }
                }
                else
                {
                    if(Rooms[RoomsI][RoomsJ].getDoor(i).getY() == 0)
                    {
                        if(RoomsI == 0 || Rooms[RoomsI][RoomsJ] == null)
                        {
                            //handle next level creation
                        }
                        else
                        {
                            //RoomsI--;
                        }
                    }
                    else if(Rooms[RoomsI][RoomsJ].getDoor(i).getX() == 0)
                    {
                        //RoomsJ--;
                    }
                    else if(Rooms[RoomsI][RoomsJ].getDoor(i).getY() == 1152)
                    {
                        //RoomsI++;
                    }
                    else if(Rooms[RoomsI][RoomsJ].getDoor(i).getX() == 1152)
                    {
                        //RoomsJ++;
                    }
                    
                    if(Rooms[RoomsI][RoomsJ].EnemySize() > 0)
                    {
                        Rooms[RoomsI][RoomsJ].lockDoors();
                    }
                }
            }
        }
        
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
        
        //now test for collisions with gear
        for(int i = 0; i < Rooms[RoomsI][RoomsJ].RingSize(); i++)
        {
            if(col.normalCollision(Player, Rooms[RoomsI][RoomsJ].getRing(i)))
            {
                if(!Rooms[RoomsI][RoomsJ].getRing(i).wasDropped())
                {
                    if(Player.getRing() == null)
                    {
                        Player.equipRing(Rooms[RoomsI][RoomsJ].getRing(i));
                    }
                    else
                    {
                        Player.getRing().setX(Rooms[RoomsI][RoomsJ].getRing(i).getX());
                        Player.getRing().setY(Rooms[RoomsI][RoomsJ].getRing(i).getY());
                        Player.getRing().setDropped(true);
                        Rooms[RoomsI][RoomsJ].addRing(Player.getRing());
                        Player.equipRing(Rooms[RoomsI][RoomsJ].getRing(i));
                    }
                    Rooms[RoomsI][RoomsJ].removeRing(i);
                }
            }
            else
            {
                Rooms[RoomsI][RoomsJ].getRing(i).setDropped(false);
            }
        }
        
        for(int i = 0; i < Rooms[RoomsI][RoomsJ].NeckSize(); i++)
        {
            if(col.normalCollision(Player, Rooms[RoomsI][RoomsJ].getNeck(i)))
            {
                if(!Rooms[RoomsI][RoomsJ].getNeck(i).wasDropped())
                {
                    if(Player.getNeck() == null)
                    {
                        Player.equipNeck(Rooms[RoomsI][RoomsJ].getNeck(i));
                    }
                    else
                    {
                        Player.getNeck().setX(Rooms[RoomsI][RoomsJ].getNeck(i).getX());
                        Player.getNeck().setY(Rooms[RoomsI][RoomsJ].getNeck(i).getY());
                        Player.getNeck().setDropped(true);
                        Rooms[RoomsI][RoomsJ].addNeck(Player.getNeck());
                        Player.equipNeck(Rooms[RoomsI][RoomsJ].getNeck(i));
                    }
                    Rooms[RoomsI][RoomsJ].removeNeck(i);
                }
            }
            else
            {
                Rooms[RoomsI][RoomsJ].getNeck(i).setDropped(false);
            }
        }
        
        for(int i = 0; i < Rooms[RoomsI][RoomsJ].BootsSize(); i++)
        {
            if(col.normalCollision(Player, Rooms[RoomsI][RoomsJ].getBoots(i)))
            {
                if(!Rooms[RoomsI][RoomsJ].getBoots(i).wasDropped())
                {
                    if(Player.getBoots() == null)
                    {
                        Player.equipBoots(Rooms[RoomsI][RoomsJ].getBoots(i));
                    }
                    else
                    {
                        Player.getBoots().setX(Rooms[RoomsI][RoomsJ].getBoots(i).getX());
                        Player.getBoots().setY(Rooms[RoomsI][RoomsJ].getBoots(i).getY());
                        Player.getBoots().setDropped(true);
                        Rooms[RoomsI][RoomsJ].addBoots(Player.getBoots());
                        Player.equipBoots(Rooms[RoomsI][RoomsJ].getBoots(i));
                    }
                    Rooms[RoomsI][RoomsJ].removeBoots(i);
                }
            }
            else
            {
                Rooms[RoomsI][RoomsJ].getBoots(i).setDropped(false);
            }
        }
        
        for(int i = 0; i < Rooms[RoomsI][RoomsJ].TomeSize(); i++)
        {
            if(col.normalCollision(Player, Rooms[RoomsI][RoomsJ].getTome(i)))
            {
                if(!Rooms[RoomsI][RoomsJ].getTome(i).wasDropped())
                {
                    if(Player.getTome() == null)
                    {
                        Player.equipTome(Rooms[RoomsI][RoomsJ].getTome(i));
                    }
                    else
                    {
                        Player.getTome().setX(Rooms[RoomsI][RoomsJ].getTome(i).getX());
                        Player.getTome().setY(Rooms[RoomsI][RoomsJ].getTome(i).getY());
                        Player.getTome().setDropped(true);
                        Rooms[RoomsI][RoomsJ].addTome(Player.getTome());
                        Player.equipTome(Rooms[RoomsI][RoomsJ].getTome(i));
                    }
                    Rooms[RoomsI][RoomsJ].removeTome(i);
                }
            }
            else
            {
                Rooms[RoomsI][RoomsJ].getTome(i).setDropped(false);
            }
        }
        
        //scale ammount is used to adjust the mouse angle for scaled window sizes
        double scale;
        
        if(ScreenHeight < ScreenWidth)
        {
            scale = (double)ScreenWidth/GameWidth;
        }
        else if(ScreenHeight > ScreenWidth)
        {
            scale = (double)ScreenHeight/GameHeight;
        }
        else
        {
            scale = 1;
        }
        
        double mouseX = (MouseInfo.getPointerInfo().getLocation().x - (int)GameWindow.getLocation().getX() - 4) / scale + screenShiftX();
        double mouseY = (MouseInfo.getPointerInfo().getLocation().y - (int)GameWindow.getLocation().getY() - 32) / scale + screenShiftY();
        
        //now update the player's position
        Player.updatePlayer((int)mouseX, (int)mouseY, generalCol, horizontalCol, verticalCol);
        
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
            
            for(int i = 0; i < Rooms[RoomsI][RoomsJ].BarrelSize(); i++)
            {
                if(col.standingCollision(Rooms[RoomsI][RoomsJ].getBarrel(i), 
                       Rooms[RoomsI][RoomsJ].getPlayerAoe(Rooms[RoomsI][RoomsJ].PlayerAoeSize() - 1)))
                {
                    Rooms[RoomsI][RoomsJ].removeBarrel(i);
                    i--;
                }
            }
        }
    }
    
    public void updateEnemies()
    {
        /*
        updateEnemies is used to check for collisions with GameObjects. Then it 
        calls the enemy's update function, and checks if the enemy has any 
        projectiles or aoe's ready to be created, and adds the to the current Room.
        */
        CollisionDetector col = new CollisionDetector();
        
        boolean generalCol = false;
        boolean verticalCol = false;
        boolean horizontalCol = false;
        for(int i = 0; i < Rooms[RoomsI][RoomsJ].EnemySize(); i++)
        {
            generalCol = false;
            verticalCol = false;
            horizontalCol = false;
            
            if(Rooms[RoomsI][RoomsJ].getEnemy(i).isDead())
            {
                Rooms[RoomsI][RoomsJ].removeEnemy(i);
                if(Rooms[RoomsI][RoomsJ].EnemySize() == 0)
                {
                    Rooms[RoomsI][RoomsJ].unlockDoors();
                }
                i--;
                continue;
            }
            
            for(int j = 0; j < Walls.length; j++)
            {
                if(verticalCol && horizontalCol)
                {
                    break;
                }

                if(col.normalCollision(Rooms[RoomsI][RoomsJ].getEnemy(i), 
                        Walls[j]))
                {
                    generalCol = true;

                    if(col.horizontalCollision(Rooms[RoomsI][RoomsJ].getEnemy(i), 
                            Walls[j]))
                    {
                        horizontalCol = true;
                    }

                    if(col.verticalCollision(Rooms[RoomsI][RoomsJ].getEnemy(i), 
                            Walls[j]))
                    {
                        verticalCol = true;
                    }
                }
            }
            
            for(int j = 0; j < Rooms[RoomsI][RoomsJ].DoorSize(); j++)
            {
                if(verticalCol && horizontalCol)
                {
                    break;
                }

                if(col.normalCollision(Rooms[RoomsI][RoomsJ].getEnemy(i), 
                        Rooms[RoomsI][RoomsJ].getDoor(j)))
                {
                    generalCol = true;

                    if(col.horizontalCollision(Rooms[RoomsI][RoomsJ].getEnemy(i), 
                            Rooms[RoomsI][RoomsJ].getDoor(j)))
                    {
                        horizontalCol = true;
                    }

                    if(col.verticalCollision(Rooms[RoomsI][RoomsJ].getEnemy(i), 
                            Rooms[RoomsI][RoomsJ].getDoor(j)))
                    {
                        verticalCol = true;
                    }
                }
            }
            
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
                
                if(i == j)
                {
                    continue;
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
                
                Player.takeDamage(Rooms[RoomsI][RoomsJ].getEnemy(i).getBumpDamage());

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
                    if(Rooms[RoomsI][RoomsJ].getPlayerAoe(j).isFire() && Rooms[RoomsI][RoomsJ].getPlayerAoe(j).isIce())
                        Rooms[RoomsI][RoomsJ].getEnemy(i).frostBurn(Rooms[RoomsI][RoomsJ].getPlayerAoe(j).getElementChance(),
                                Player.getBurnDamage(), Player.getBurnTime(), Player.getFreezeTime());
                    else if(Rooms[RoomsI][RoomsJ].getPlayerAoe(j).isFire())
                        Rooms[RoomsI][RoomsJ].getEnemy(i).ignite(Rooms[RoomsI][RoomsJ].getPlayerAoe(j).getElementChance(),
                                Player.getBurnDamage(), Player.getBurnTime());
                    else if(Rooms[RoomsI][RoomsJ].getPlayerAoe(j).isIce())
                        Rooms[RoomsI][RoomsJ].getEnemy(i).freeze(Rooms[RoomsI][RoomsJ].getPlayerAoe(j).getElementChance(),
                                Player.getFreezeTime());
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
            
            if(Rooms[RoomsI][RoomsJ].getEnemy(i).isSummonReady())
            {
                MovingEnemy newSummon = Rooms[RoomsI][RoomsJ].getEnemy(i).getSummon();
                boolean freeSpace = true;
                
                freeSpace = col.standingCollision(Player, newSummon);
                
                for(int j = 0; j < Rooms[RoomsI][RoomsJ].BarrelSize(); j++)
                {
                    if(!freeSpace)
                        break;
                    freeSpace = col.standingCollision(Rooms[RoomsI][RoomsJ].getBarrel(j), newSummon);
                }
                
                for(int j = 0; j < Rooms[RoomsI][RoomsJ].EnemySize(); j++)
                {
                    if(!freeSpace)
                        break;
                    freeSpace = col.standingCollision(Rooms[RoomsI][RoomsJ].getEnemy(j), newSummon);
                }
                
                for(int j = 0; j < Rooms[RoomsI][RoomsJ].WallSize(); j++)
                {
                    if(!freeSpace)
                        break;
                    freeSpace = col.standingCollision(Rooms[RoomsI][RoomsJ].getWall(j), newSummon);
                }
                
                //Not i++; don't want new enemies to be updated the frame they are spawned
                //(may screw up summons on next frame)
                if(freeSpace){
                    Rooms[RoomsI][RoomsJ].addEnemy(newSummon);
                }
            }
        }
    }
    
    public void updatePlayerProjectiles()
    {
        /*
        updatePlayerProjectiles is used to check for collisions with GameObjects
        */
        CollisionDetector col = new CollisionDetector();
        boolean generalCol = false;
        
        for(int i = 0; i < Rooms[RoomsI][RoomsJ].PlayerProjectileSize(); i++)
        {
            generalCol = false;
            for(int j = 0; j < Rooms[RoomsI][RoomsJ].EnemySize(); j++)
            {
                if(col.normalCollision(Rooms[RoomsI][RoomsJ].getPlayerProjectile(i), Rooms[RoomsI][RoomsJ].getEnemy(j)))
                {
                    Rooms[RoomsI][RoomsJ].getEnemy(j).takeDamage(Rooms[RoomsI][RoomsJ].getPlayerProjectile(i).getDamage());
                    if(Rooms[RoomsI][RoomsJ].getPlayerProjectile(i).isFire() && 
                            Rooms[RoomsI][RoomsJ].getPlayerProjectile(i).isIce())
                    {
                        Rooms[RoomsI][RoomsJ].getEnemy(i).frostBurn(Rooms[RoomsI][RoomsJ].getPlayerProjectile(j).getElementChance(),
                                Player.getBurnDamage(), Player.getBurnTime(), Player.getFreezeTime());
                    }
                    else if(Rooms[RoomsI][RoomsJ].getPlayerProjectile(i).isFire())
                    {
                        Rooms[RoomsI][RoomsJ].getEnemy(j).ignite(Rooms[RoomsI][RoomsJ].getPlayerProjectile(i).getElementChance(),
                                Player.getBurnDamage(), Player.getBurnTime());
                    }
                    else if(Rooms[RoomsI][RoomsJ].getPlayerProjectile(i).isIce())
                    {
                        Rooms[RoomsI][RoomsJ].getEnemy(j).freeze(Rooms[RoomsI][RoomsJ].getPlayerProjectile(i).getElementChance(),
                                Player.getFreezeTime());
                    }
                    
                    if(!Rooms[RoomsI][RoomsJ].getPlayerProjectile(i).isVoid())
                    {
                        if(Rooms[RoomsI][RoomsJ].getPlayerProjectile(i).getEndAnimation() != null)
                        {
                            Rooms[RoomsI][RoomsJ].addAnimation(Rooms[RoomsI][RoomsJ].getPlayerProjectile(i).getEndAnimation());
                        }
                        Rooms[RoomsI][RoomsJ].removePlayerProjectile(i);
                        i--;
                        generalCol = true;
                        break;
                    }
                }
            }
            if(!generalCol)
            {
                for(int j = 0; j < Walls.length; j++)
                {
                    if(col.normalCollision(Rooms[RoomsI][RoomsJ].getPlayerProjectile(i), Walls[j]))
                    {
                        if(Rooms[RoomsI][RoomsJ].getPlayerProjectile(i).getEndAnimation() != null)
                        {
                            Rooms[RoomsI][RoomsJ].addAnimation(Rooms[RoomsI][RoomsJ].getPlayerProjectile(i).getEndAnimation());
                        }
                        Rooms[RoomsI][RoomsJ].removePlayerProjectile(i);
                        i--;
                        generalCol = true;
                        break;
                    }
                }
            }
            if(!generalCol)
            {
                for(int j = 0; j < Rooms[RoomsI][RoomsJ].DoorSize(); j++)
                {
                    if(col.normalCollision(Rooms[RoomsI][RoomsJ].getPlayerProjectile(i), Rooms[RoomsI][RoomsJ].getDoor(j)))
                    {
                        if(Rooms[RoomsI][RoomsJ].getPlayerProjectile(i).getEndAnimation() != null)
                        {
                            Rooms[RoomsI][RoomsJ].addAnimation(Rooms[RoomsI][RoomsJ].getPlayerProjectile(i).getEndAnimation());
                        }
                        Rooms[RoomsI][RoomsJ].removePlayerProjectile(i);
                        i--;
                        generalCol = true;
                        break;
                    }
                }
            }
            if(!generalCol)
            {
                for(int j = 0; j < Rooms[RoomsI][RoomsJ].WallSize(); j++)
                {
                    if(col.normalCollision(Rooms[RoomsI][RoomsJ].getPlayerProjectile(i), Rooms[RoomsI][RoomsJ].getWall(j)))
                    {
                        if(Rooms[RoomsI][RoomsJ].getPlayerProjectile(i).getEndAnimation() != null)
                        {
                            Rooms[RoomsI][RoomsJ].addAnimation(Rooms[RoomsI][RoomsJ].getPlayerProjectile(i).getEndAnimation());
                        }
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
                        if(!Rooms[RoomsI][RoomsJ].getPlayerProjectile(i).isVoid())
                        {
                            if(Rooms[RoomsI][RoomsJ].getPlayerProjectile(i).getEndAnimation() != null)
                            {
                                Rooms[RoomsI][RoomsJ].addAnimation(Rooms[RoomsI][RoomsJ].getPlayerProjectile(i).getEndAnimation());
                            }
                            Rooms[RoomsI][RoomsJ].removePlayerProjectile(i);
                            i--;
                            generalCol = true;
                            break;
                        }
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
    }
    
    public void updateEnemyProjectiles()
    {
        CollisionDetector col = new CollisionDetector();
        
        boolean generalCol = false;
        
        for(int i = 0; i < Rooms[RoomsI][RoomsJ].EnemyProjectileSize(); i++)
        {
            generalCol = false;
            if(col.normalCollision(Rooms[RoomsI][RoomsJ].getEnemyProjectile(i), Player))
            {
                Player.takeDamage(Rooms[RoomsI][RoomsJ].getEnemyProjectile(i).getDamage());
                if(Rooms[RoomsI][RoomsJ].getEnemyProjectile(i).getEndAnimation() != null)
                {
                    Rooms[RoomsI][RoomsJ].addAnimation(Rooms[RoomsI][RoomsJ].getEnemyProjectile(i).getEndAnimation());
                }
                Rooms[RoomsI][RoomsJ].removeEnemyProjectile(i);
                i--;
                generalCol = true;
                        break;
            }
            if(!generalCol)
            {
                for(int j = 0; j < Walls.length; j++)
                {
                    if(col.normalCollision(Rooms[RoomsI][RoomsJ].getEnemyProjectile(i), Walls[j]))
                    {
                        if(Rooms[RoomsI][RoomsJ].getEnemyProjectile(i).getEndAnimation() != null)
                        {
                            Rooms[RoomsI][RoomsJ].addAnimation(Rooms[RoomsI][RoomsJ].getEnemyProjectile(i).getEndAnimation());
                        }
                        Rooms[RoomsI][RoomsJ].removeEnemyProjectile(i);
                        i--;
                        generalCol = true;
                        break;
                    }
                }
            }
            if(!generalCol)
            {
                for(int j = 0; j < Rooms[RoomsI][RoomsJ].DoorSize(); j++)
                {
                    if(col.normalCollision(Rooms[RoomsI][RoomsJ].getEnemyProjectile(i), Rooms[RoomsI][RoomsJ].getDoor(j)))
                    {
                        if(Rooms[RoomsI][RoomsJ].getEnemyProjectile(i).getEndAnimation() != null)
                        {
                            Rooms[RoomsI][RoomsJ].addAnimation(Rooms[RoomsI][RoomsJ].getEnemyProjectile(i).getEndAnimation());
                        }
                        Rooms[RoomsI][RoomsJ].removeEnemyProjectile(i);
                        i--;
                        generalCol = true;
                        break;
                    }
                }
            }
            if(!generalCol)
            {
                for(int j = 0; j < Rooms[RoomsI][RoomsJ].WallSize(); j++)
                {
                    if(col.normalCollision(Rooms[RoomsI][RoomsJ].getEnemyProjectile(i), Rooms[RoomsI][RoomsJ].getWall(j)))
                    {
                        if(Rooms[RoomsI][RoomsJ].getEnemyProjectile(i).getEndAnimation() != null)
                        {
                            Rooms[RoomsI][RoomsJ].addAnimation(Rooms[RoomsI][RoomsJ].getEnemyProjectile(i).getEndAnimation());
                        }
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
                        if(Rooms[RoomsI][RoomsJ].getEnemyProjectile(i).getEndAnimation() != null)
                        {
                            Rooms[RoomsI][RoomsJ].addAnimation(Rooms[RoomsI][RoomsJ].getEnemyProjectile(i).getEndAnimation());
                        }
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
    }
    
    public void addRandomGear(int x, int y)
    {
        Random rnd = new Random();
        
        int gearType = rnd.nextInt(4);
        
        if(gearType == 0)
        {
            addRandomRing(x, y);
        }
        else if(gearType == 1)
        {
            addRandomNeck(x, y);
        }
        else if(gearType == 2)
        {
            addRandomBoot(x, y);
        }
        else
        {
            addRandomTome(x, y);
        }
    }
    
    public void addRandomRing(int x, int y)
    {
        Random rnd = new Random();
        
        int statType = rnd.nextInt(4);
        
        if(statType == 0)
        {
            Rooms[RoomsI][RoomsJ].addRing(new Ring(x, y, this.EmeraldRing[rnd.nextInt(2)],
                    Player.getLevel(), true, false, false, false));
        }
        else if(statType == 1)
        {
            Rooms[RoomsI][RoomsJ].addRing(new Ring(x, y, this.RubyRing[rnd.nextInt(2)],
                    Player.getLevel(), false, true, false, false));
        }
        else if(statType == 2)
        {
            Rooms[RoomsI][RoomsJ].addRing(new Ring(x, y, this.SaphireRing[rnd.nextInt(2)],
                    Player.getLevel(), false, false, true, false));
        }
        else
        {
            Rooms[RoomsI][RoomsJ].addRing(new Ring(x, y, this.AmethystRing[rnd.nextInt(2)],
                    Player.getLevel(), false, false, false, true));
        }
    }
    
    public void addRandomNeck(int x, int y)
    {
        Random rnd = new Random();
        
        int statType = rnd.nextInt(4);
        
        if(statType == 0)
        {
            Rooms[RoomsI][RoomsJ].addNeck(new Neck(x, y, this.EmeraldNeck[rnd.nextInt(2)],
                    Player.getLevel(), true, false, false, false));
        }
        else if(statType == 1)
        {
            Rooms[RoomsI][RoomsJ].addNeck(new Neck(x, y, this.RubyNeck[rnd.nextInt(2)],
                    Player.getLevel(), false, true, false, false));
        }
        else if(statType == 2)
        {
            Rooms[RoomsI][RoomsJ].addNeck(new Neck(x, y, this.SaphireNeck[rnd.nextInt(2)],
                    Player.getLevel(), false, false, true, false));
        }
        else
        {
            Rooms[RoomsI][RoomsJ].addNeck(new Neck(x, y, this.AmethystNeck[rnd.nextInt(2)],
                    Player.getLevel(), false, false, false, true));
        }
    }
    
    public void addRandomBoot(int x, int y)
    {
        Random rnd = new Random();
        
        int statType = rnd.nextInt(3);
        
        if(statType == 0)
        {
            Rooms[RoomsI][RoomsJ].addBoots(new Boots(x, y, VoidBoots,
                    Player.getLevel(), false, false, true));
        }
        else if(statType == 1)
        {
            Rooms[RoomsI][RoomsJ].addBoots(new Boots(x, y, FlameBoots,
                    Player.getLevel(), true, false, false));
        }
        else
        {
            Rooms[RoomsI][RoomsJ].addBoots(new Boots(x, y, FrostBoots,
                    Player.getLevel(), false, true, false));
        }
    }
    
    public void addRandomTome(int x, int y)
    {
        Random rnd = new Random();
        
        int statType = rnd.nextInt(3);
        
        if(statType == 0)
        {
            Rooms[RoomsI][RoomsJ].addTome(new Tome(x, y, VoidTome,
                    Player.getLevel(), false, false, true));
        }
        else if(statType == 1)
        {
            Rooms[RoomsI][RoomsJ].addTome(new Tome(x, y, FlameTome,
                    Player.getLevel(), true, false, false));
        }
        else
        {
            Rooms[RoomsI][RoomsJ].addTome(new Tome(x, y, FrostTome,
                    Player.getLevel(), false, true, false));
        }
    }
    
    public void updateGame()
    {
        updatePlayer();
        updateEnemies();
        updatePlayerProjectiles();
        updateEnemyProjectiles();
        
        Rooms[RoomsI][RoomsJ].updateRoom();
        Burning.updateAnimation();
    }
    
    public void mainMenu()
    {
        /*
        Initializes the main game menu. Still needs implementation
        */
    }
    
    public int screenShiftY()
    {
        /*
        screenShift determines the amount the camera needs to be shifted for
        painting/updating based on the size and scale of the game's window;
        if the height is larger than the width, then the y value won't be shifted.
        */
        if(ScreenHeight >= ScreenWidth)
        {
            return 0;
        }
        
        int yShift;
        
        double scale = (double)ScreenWidth/GameWidth;
        double scaledHeight = ScreenHeight / scale;
        
        if(Player.getCenterY() < scaledHeight/2)
        {
            yShift = 0;
        }
        else if(Player.getCenterY() > GameHeight - scaledHeight/2)
        {
            yShift = GameHeight - (int)scaledHeight;            
        }
        else
        {
            yShift = Player.getCenterY() - (int)scaledHeight/2;
        }
        
        return yShift;
    }
    
    public int screenShiftX()
    {
        /*
        screenShift determines the amount the camera needs to be shifted for
        painting/updating based on the size and scale of the game's window;
        if the width is larger than the height, then the x value won't be shifted.
        */
        if(ScreenWidth >= ScreenHeight)
        {
            return 0;
        }
        
        int xShift;
        
        double scale = (double)ScreenHeight/GameHeight;
        double scaledWidth = ScreenWidth / scale;
        
        if(Player.getCenterX() < scaledWidth/2)
        {
            xShift = 0;
        }
        else if(Player.getCenterX() > GameWidth - scaledWidth/2)
        {
            xShift = GameWidth - (int)scaledWidth;            
        }
        else
        {
            xShift = Player.getCenterX() - (int)scaledWidth/2;
        }
        
        return xShift;
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
            bufImg = (BufferedImage) createImage(GameWidth, GameHeight);
        }
        Graphics2D gtemp = (Graphics2D) g;
        g2d = bufImg.createGraphics();
        super.paintComponent(gtemp);
        
        for(int i = 0; i < 8; i++)
        {
            for(int j = 0; j < 8; j++)
            {
                g2d.drawImage(TempleFloor, i * TempleFloor.getWidth(null) + 128, j * TempleFloor.getHeight(null) + 128, this);
            }
        }
        
        for(int i = 0; i < Rooms[RoomsI][RoomsJ].PlayerProjectileSize(); i++)
        {
            paintRotatedImg(Rooms[RoomsI][RoomsJ].getPlayerProjectile(i).getShadow(), 
                   Rooms[RoomsI][RoomsJ].getPlayerProjectile(i).getAngle(),
                   Rooms[RoomsI][RoomsJ].getPlayerProjectile(i).getX(), 
                   Rooms[RoomsI][RoomsJ].getPlayerProjectile(i).getY() + ShadowHeight);
        }
        
        for(int i = 0; i < Rooms[RoomsI][RoomsJ].EnemyProjectileSize(); i++)
        {
            paintRotatedImg(Rooms[RoomsI][RoomsJ].getEnemyProjectile(i).getShadow(), 
                   Rooms[RoomsI][RoomsJ].getEnemyProjectile(i).getAngle(),
                   Rooms[RoomsI][RoomsJ].getEnemyProjectile(i).getX(), 
                   Rooms[RoomsI][RoomsJ].getEnemyProjectile(i).getY() + ShadowHeight);
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
        
        for(int i = 0; i < Walls.length; i++)
        {
            g2d.drawImage(Walls[i].getSprite(), Walls[i].getX(), Walls[i].getY(), this);
        }
        
        for(int i = 0; i < Rooms[RoomsI][RoomsJ].DoorSize(); i++)
        {
           g2d.drawImage(Rooms[RoomsI][RoomsJ].getDoor(i).getSprite(), 
                   Rooms[RoomsI][RoomsJ].getDoor(i).getX(), 
                   Rooms[RoomsI][RoomsJ].getDoor(i).getY(), this);
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
        
        for(int i = 0; i < Rooms[RoomsI][RoomsJ].RingSize(); i++)
        {
           g2d.drawImage(Rooms[RoomsI][RoomsJ].getRing(i).getSprite(), 
                   Rooms[RoomsI][RoomsJ].getRing(i).getX(), 
                   Rooms[RoomsI][RoomsJ].getRing(i).getY(), this);
        }
        
        for(int i = 0; i < Rooms[RoomsI][RoomsJ].NeckSize(); i++)
        {
           g2d.drawImage(Rooms[RoomsI][RoomsJ].getNeck(i).getSprite(), 
                   Rooms[RoomsI][RoomsJ].getNeck(i).getX(), 
                   Rooms[RoomsI][RoomsJ].getNeck(i).getY(), this);
        }
        
        for(int i = 0; i < Rooms[RoomsI][RoomsJ].TomeSize(); i++)
        {
           g2d.drawImage(Rooms[RoomsI][RoomsJ].getTome(i).getSprite(), 
                   Rooms[RoomsI][RoomsJ].getTome(i).getX(), 
                   Rooms[RoomsI][RoomsJ].getTome(i).getY(), this);
        }
        
        for(int i = 0; i < Rooms[RoomsI][RoomsJ].BootsSize(); i++)
        {
           g2d.drawImage(Rooms[RoomsI][RoomsJ].getBoots(i).getSprite(), 
                   Rooms[RoomsI][RoomsJ].getBoots(i).getX(), 
                   Rooms[RoomsI][RoomsJ].getBoots(i).getY(), this);
        }
        
        for(int i = 0; i < Rooms[RoomsI][RoomsJ].EnemySize(); i++)
        {
           g2d.drawImage(Rooms[RoomsI][RoomsJ].getEnemy(i).getSprite(), 
                   Rooms[RoomsI][RoomsJ].getEnemy(i).getX(), 
                   Rooms[RoomsI][RoomsJ].getEnemy(i).getY(), this);
           if(Rooms[RoomsI][RoomsJ].getEnemy(i).isChilled())
           {
               g2d.drawImage(this.ChilledImg, 
                       Rooms[RoomsI][RoomsJ].getEnemy(i).getCenterX() - this.ChilledImg.getWidth(null)/2,
                       Rooms[RoomsI][RoomsJ].getEnemy(i).getCenterY() - this.ChilledImg.getHeight(null)/2,
                       this);
           }
           if(Rooms[RoomsI][RoomsJ].getEnemy(i).isBurning())
           {
               g2d.drawImage(this.Burning.getSprite(), 
                       Rooms[RoomsI][RoomsJ].getEnemy(i).getCenterX() - this.Burning.getSprite().getWidth(null)/2,
                       Rooms[RoomsI][RoomsJ].getEnemy(i).getCenterY() - this.Burning.getSprite().getHeight(null)/2,
                       this);
           }
           if(Rooms[RoomsI][RoomsJ].getEnemy(i).isFrozen())
           {
               g2d.drawImage(this.FrozenImg, 
                       Rooms[RoomsI][RoomsJ].getEnemy(i).getCenterX() - this.FrozenImg.getWidth(null)/2,
                       Rooms[RoomsI][RoomsJ].getEnemy(i).getCenterY() - this.FrozenImg.getHeight(null)/2 - 6,
                       this);
           }
        }
        
        for(int i = 0; i < Rooms[RoomsI][RoomsJ].PlayerProjectileSize(); i++)
        {
            if(Rooms[RoomsI][RoomsJ].getPlayerProjectile(i).getY() < this.Player.getY() + 45)
                paintRotatedImg(Rooms[RoomsI][RoomsJ].getPlayerProjectile(i).getSprite(), 
                       Rooms[RoomsI][RoomsJ].getPlayerProjectile(i).getAngle(),
                       Rooms[RoomsI][RoomsJ].getPlayerProjectile(i).getX(), 
                       Rooms[RoomsI][RoomsJ].getPlayerProjectile(i).getY());
        }
        
        if(Player.getSprite() != null);
            g2d.drawImage(Player.getSprite(), Player.getX(), Player.getY(), this);
        
        for(int i = 0; i < Rooms[RoomsI][RoomsJ].PlayerProjectileSize(); i++)
        {
            if(Rooms[RoomsI][RoomsJ].getPlayerProjectile(i).getY() >= this.Player.getY() + 4)
                paintRotatedImg(Rooms[RoomsI][RoomsJ].getPlayerProjectile(i).getSprite(), 
                       Rooms[RoomsI][RoomsJ].getPlayerProjectile(i).getAngle(),
                       Rooms[RoomsI][RoomsJ].getPlayerProjectile(i).getX(), 
                       Rooms[RoomsI][RoomsJ].getPlayerProjectile(i).getY());
        }
        
        for(int i = 0; i < Rooms[RoomsI][RoomsJ].EnemyProjectileSize(); i++)
        {
            paintRotatedImg(Rooms[RoomsI][RoomsJ].getEnemyProjectile(i).getSprite(), 
                   Rooms[RoomsI][RoomsJ].getEnemyProjectile(i).getAngle(),
                   Rooms[RoomsI][RoomsJ].getEnemyProjectile(i).getX(), 
                   Rooms[RoomsI][RoomsJ].getEnemyProjectile(i).getY());
        }
        
        for(int i = 0; i < Rooms[RoomsI][RoomsJ].AnimationSize(); i++)
        {
            paintRotatedImg(Rooms[RoomsI][RoomsJ].getAnimation(i).getSprite(), 
                   Rooms[RoomsI][RoomsJ].getAnimation(i).getAngle(),
                   Rooms[RoomsI][RoomsJ].getAnimation(i).getX(), 
                   Rooms[RoomsI][RoomsJ].getAnimation(i).getY());
        }
        
        
        //draw overlay now
        int yShift = screenShiftY();
        int xShift = screenShiftX();
        double scale;
        if(ScreenHeight < ScreenWidth)
        {
            scale = (double)ScreenWidth/GameWidth;
        }
        else if(ScreenHeight > ScreenWidth)
        {
            scale = (double)ScreenHeight/GameHeight;
        }
        else
        {
            scale = 1;
        }
        double scaledHeight = ScreenHeight / scale;
        double scaledWidth = ScreenWidth / scale;
        
        for(int i = 0; i < 4; i ++)
        {
            if(Player.getSpell(i) == null)
            {
                g2d.drawImage(this.NullSpellIcon, 54 + (int)xShift + 100 * i, (int)scaledHeight - 110 + yShift, this);
            }
            else
            {
                g2d.drawImage(Player.getSpell(i).getIcon(), 54 + (int)xShift + 100 * i, (int)scaledHeight - 110 + yShift, this);
                if(i == Player.getCurrentSpellNumber())
                {
                    g2d.drawImage(this.CurrentSpellIcon, 52 + (int)xShift + 100 * i, (int)scaledHeight - 112 + yShift, this);
                }
            }
        }
        
        BufferedImage shiftImg = bufImg.getSubimage(xShift, yShift, (int)scaledWidth, (int)scaledHeight);
        gtemp.scale(scale, scale);
        gtemp.drawImage(shiftImg, 0, 0, this);
        
        //Text for items
        gtemp.setColor(Color.WHITE);
        Font spellNameFont = (new Font("Arial Black", Font.PLAIN, 16));
        Font spellCoolDownFont = (new Font("Arial Black", Font.PLAIN, 32));
        FontMetrics metrics = gtemp.getFontMetrics(spellNameFont);
        
        for(int i = 0; i < 4; i ++)
        {
            if(Player.getSpell(i) != null)
            {
                gtemp.setFont(spellNameFont);
                metrics = gtemp.getFontMetrics(spellNameFont);
                gtemp.drawString(Player.getSpell(i).getSpellName(), 
                        88 + 100 * i - (metrics.stringWidth(Player.getSpell(i).getSpellName()))/2, (int)scaledHeight - 120);
                if(Player.getSpell(i).getCoolDown(FPS) > 0)
                {
                    gtemp.setFont(spellCoolDownFont);
                    metrics = gtemp.getFontMetrics(spellCoolDownFont);
                    gtemp.drawString(Integer.toString(Player.getSpell(i).getCoolDown(FPS)), 
                            88 + 100 * i - metrics.stringWidth(Integer.toString(Player.getSpell(i).getCoolDown(FPS)))/2, (int)scaledHeight - 65);
                }
            }
            gtemp.setFont(spellNameFont);
            metrics = gtemp.getFontMetrics(spellNameFont);
            gtemp.drawString(Integer.toString(i+1), 
                    88 + 100 * i - (metrics.stringWidth(Integer.toString(i+1)))/2, (int)scaledHeight - 20);
        }
        
        Font itemNameFont = (new Font("Arial Black", Font.PLAIN, 14));
        gtemp.setFont(itemNameFont);
        metrics = gtemp.getFontMetrics(itemNameFont);
        
        for(int i = 0; i < this.Rooms[RoomsI][RoomsJ].RuneSize(); i++)
        {
            gtemp.drawString(this.Rooms[RoomsI][RoomsJ].getRune(i).getRuneName(), 
                        this.Rooms[RoomsI][RoomsJ].getRune(i).getCenterX() - 
                                metrics.stringWidth(this.Rooms[RoomsI][RoomsJ].getRune(i).getRuneName())/2 + xShift, 
                        this.Rooms[RoomsI][RoomsJ].getRune(i).getY() - 10 - yShift);
        }
        
        for(int i = 0; i < this.Rooms[RoomsI][RoomsJ].PageSize(); i++)
        {
            gtemp.drawString(this.Rooms[RoomsI][RoomsJ].getPage(i).getSpellName(), 
                        this.Rooms[RoomsI][RoomsJ].getPage(i).getCenterX() - 
                                metrics.stringWidth(this.Rooms[RoomsI][RoomsJ].getPage(i).getSpellName())/2 + xShift, 
                        this.Rooms[RoomsI][RoomsJ].getPage(i).getY() - 10 - yShift);
        }
        
        Color purple = new Color(100, 0, 150);
        Color blue = new Color(0, 0, 250);
        Color green =  new Color(0, 255, 50);
        Color red = new Color(200, 0, 0);
        Color orange = new Color(255, 100, 0);
        Color trash = new Color(200, 200, 255);
                
        for(int i = 0; i < this.Rooms[RoomsI][RoomsJ].RingSize(); i++)
        {
            itemNameFont = (new Font("Arial Black", Font.PLAIN, 12));
            gtemp.setFont(itemNameFont);
            metrics = gtemp.getFontMetrics(itemNameFont);
        
            int rarityDisplacement = 10;
            String statValue;
            
            int darkVal, flameVal, frostVal, vitVal, intVal;
            
            if(Player.getRing() == null)
            {
                darkVal = this.Rooms[RoomsI][RoomsJ].getRing(i).getDark();
                flameVal = this.Rooms[RoomsI][RoomsJ].getRing(i).getFlame();
                frostVal = this.Rooms[RoomsI][RoomsJ].getRing(i).getFrost();
                vitVal = this.Rooms[RoomsI][RoomsJ].getRing(i).getVitality();
                intVal = this.Rooms[RoomsI][RoomsJ].getRing(i).getIntellect();
            }
            else
            {
                darkVal = this.Rooms[RoomsI][RoomsJ].getRing(i).getDark() -
                        Player.getRing().getDark();
                flameVal = this.Rooms[RoomsI][RoomsJ].getRing(i).getFlame() -
                        Player.getRing().getFlame();
                frostVal = this.Rooms[RoomsI][RoomsJ].getRing(i).getFrost() -
                        Player.getRing().getFrost();
                vitVal = this.Rooms[RoomsI][RoomsJ].getRing(i).getVitality() -
                        Player.getRing().getVitality();
                intVal = this.Rooms[RoomsI][RoomsJ].getRing(i).getIntellect() -
                        Player.getRing().getIntellect();
            }
            
            if(darkVal != 0)
            {
                if(darkVal > 0)
                {
                    gtemp.setColor(green);
                    statValue = "+";
                }
                else
                {
                    gtemp.setColor(red);
                    statValue = "-";
                }
                
                statValue += Integer.toString(Math.abs(darkVal)) + " Void";
                gtemp.drawString(statValue, this.Rooms[RoomsI][RoomsJ].getRing(i).getCenterX() - 
                                metrics.stringWidth(statValue)/2 + xShift, 
                        this.Rooms[RoomsI][RoomsJ].getRing(i).getY() - rarityDisplacement - yShift);
                rarityDisplacement += 15;
            }
            if(frostVal != 0)
            {
                if(frostVal > 0)
                {
                    gtemp.setColor(green);
                    statValue = "+";
                }
                else
                {
                    gtemp.setColor(red);
                    statValue = "-";
                }
                
                statValue += Integer.toString(Math.abs(frostVal)) + " Frost";
                gtemp.drawString(statValue, this.Rooms[RoomsI][RoomsJ].getRing(i).getCenterX() - 
                                metrics.stringWidth(statValue)/2 + xShift, 
                        this.Rooms[RoomsI][RoomsJ].getRing(i).getY() - rarityDisplacement - yShift);
                rarityDisplacement += 15;
            }
            if(flameVal != 0)
            {
                if(flameVal > 0)
                {
                    gtemp.setColor(green);
                    statValue = "+";
                }
                else
                {
                    gtemp.setColor(red);
                    statValue = "-";
                }
                
                statValue += Integer.toString(Math.abs(flameVal)) + " Flame";
                gtemp.drawString(statValue, this.Rooms[RoomsI][RoomsJ].getRing(i).getCenterX() - 
                                metrics.stringWidth(statValue)/2 + xShift, 
                        this.Rooms[RoomsI][RoomsJ].getRing(i).getY() - rarityDisplacement - yShift);
                rarityDisplacement += 15;
            }
            if(vitVal != 0)
            {
                if(vitVal > 0)
                {
                    gtemp.setColor(green);
                    statValue = "+";
                }
                else
                {
                    gtemp.setColor(red);
                    statValue = "-";
                }
                
                statValue += Integer.toString(Math.abs(vitVal)) + " Vitality";
                gtemp.drawString(statValue, this.Rooms[RoomsI][RoomsJ].getRing(i).getCenterX() - 
                                metrics.stringWidth(statValue)/2 + xShift, 
                        this.Rooms[RoomsI][RoomsJ].getRing(i).getY() - rarityDisplacement - yShift);
                rarityDisplacement += 15;
            }
            if(intVal != 0)
            {
                if(intVal > 0)
                {
                    gtemp.setColor(green);
                    statValue = "+";
                }
                else
                {
                    gtemp.setColor(red);
                    statValue = "-";
                }
                
                statValue += Integer.toString(Math.abs(intVal)) + " Intellect";
                gtemp.drawString(statValue, this.Rooms[RoomsI][RoomsJ].getRing(i).getCenterX() - 
                                metrics.stringWidth(statValue)/2 + xShift, 
                        this.Rooms[RoomsI][RoomsJ].getRing(i).getY() - rarityDisplacement - yShift);
                rarityDisplacement += 15;
            }
            
            if(this.Rooms[RoomsI][RoomsJ].getRing(i).getRarity() == 2)
            {
                gtemp.setColor(blue);
            }
            else if(this.Rooms[RoomsI][RoomsJ].getRing(i).getRarity() == 3)
            {
                gtemp.setColor(purple);
            }
            else
            {
                gtemp.setColor(trash);
            }
            
            itemNameFont = (new Font("Arial Black", Font.PLAIN, 14));
            gtemp.setFont(itemNameFont);
            metrics = gtemp.getFontMetrics(itemNameFont);
            
            gtemp.drawString(this.Rooms[RoomsI][RoomsJ].getRing(i).getItemName(), 
                        this.Rooms[RoomsI][RoomsJ].getRing(i).getCenterX() - 
                                metrics.stringWidth(this.Rooms[RoomsI][RoomsJ].getRing(i).getItemName())/2 + xShift, 
                        this.Rooms[RoomsI][RoomsJ].getRing(i).getY() - rarityDisplacement - yShift);
        }
        
        for(int i = 0; i < this.Rooms[RoomsI][RoomsJ].NeckSize(); i++)
        {
            itemNameFont = (new Font("Arial Black", Font.PLAIN, 12));
            gtemp.setFont(itemNameFont);
            metrics = gtemp.getFontMetrics(itemNameFont);
        
            int rarityDisplacement = 10;
            String statValue;
            
            int darkVal, flameVal, frostVal, vitVal, intVal;
            
            if(Player.getNeck() == null)
            {
                darkVal = this.Rooms[RoomsI][RoomsJ].getNeck(i).getDark();
                flameVal = this.Rooms[RoomsI][RoomsJ].getNeck(i).getFlame();
                frostVal = this.Rooms[RoomsI][RoomsJ].getNeck(i).getFrost();
                vitVal = this.Rooms[RoomsI][RoomsJ].getNeck(i).getVitality();
                intVal = this.Rooms[RoomsI][RoomsJ].getNeck(i).getIntellect();
            }
            else
            {
                darkVal = this.Rooms[RoomsI][RoomsJ].getNeck(i).getDark() -
                        Player.getNeck().getDark();
                flameVal = this.Rooms[RoomsI][RoomsJ].getNeck(i).getFlame() -
                        Player.getNeck().getFlame();
                frostVal = this.Rooms[RoomsI][RoomsJ].getNeck(i).getFrost() -
                        Player.getNeck().getFrost();
                vitVal = this.Rooms[RoomsI][RoomsJ].getNeck(i).getVitality() -
                        Player.getNeck().getVitality();
                intVal = this.Rooms[RoomsI][RoomsJ].getNeck(i).getIntellect() -
                        Player.getNeck().getIntellect();
            }
            
            if(darkVal != 0)
            {
                if(darkVal > 0)
                {
                    gtemp.setColor(green);
                    statValue = "+";
                }
                else
                {
                    gtemp.setColor(red);
                    statValue = "-";
                }
                
                statValue += Integer.toString(Math.abs(darkVal)) + " Void";
                gtemp.drawString(statValue, this.Rooms[RoomsI][RoomsJ].getNeck(i).getCenterX() - 
                                metrics.stringWidth(statValue)/2 + xShift, 
                        this.Rooms[RoomsI][RoomsJ].getNeck(i).getY() - rarityDisplacement - yShift);
                rarityDisplacement += 15;
            }
            if(frostVal != 0)
            {
                if(frostVal > 0)
                {
                    gtemp.setColor(green);
                    statValue = "+";
                }
                else
                {
                    gtemp.setColor(red);
                    statValue = "-";
                }
                
                statValue += Integer.toString(Math.abs(frostVal)) + " Frost";
                gtemp.drawString(statValue, this.Rooms[RoomsI][RoomsJ].getNeck(i).getCenterX() - 
                                metrics.stringWidth(statValue)/2 + xShift, 
                        this.Rooms[RoomsI][RoomsJ].getNeck(i).getY() - rarityDisplacement - yShift);
                rarityDisplacement += 15;
            }
            if(flameVal != 0)
            {
                if(flameVal > 0)
                {
                    gtemp.setColor(green);
                    statValue = "+";
                }
                else
                {
                    gtemp.setColor(red);
                    statValue = "-";
                }
                
                statValue += Integer.toString(Math.abs(flameVal)) + " Flame";
                gtemp.drawString(statValue, this.Rooms[RoomsI][RoomsJ].getNeck(i).getCenterX() - 
                                metrics.stringWidth(statValue)/2 + xShift, 
                        this.Rooms[RoomsI][RoomsJ].getNeck(i).getY() - rarityDisplacement - yShift);
                rarityDisplacement += 15;
            }
            if(vitVal != 0)
            {
                if(vitVal > 0)
                {
                    gtemp.setColor(green);
                    statValue = "+";
                }
                else
                {
                    gtemp.setColor(red);
                    statValue = "-";
                }
                
                statValue += Integer.toString(Math.abs(vitVal)) + " Vitality";
                gtemp.drawString(statValue, this.Rooms[RoomsI][RoomsJ].getNeck(i).getCenterX() - 
                                metrics.stringWidth(statValue)/2 + xShift, 
                        this.Rooms[RoomsI][RoomsJ].getNeck(i).getY() - rarityDisplacement - yShift);
                rarityDisplacement += 15;
            }
            if(intVal != 0)
            {
                if(intVal > 0)
                {
                    gtemp.setColor(green);
                    statValue = "+";
                }
                else
                {
                    gtemp.setColor(red);
                    statValue = "-";
                }
                
                statValue += Integer.toString(Math.abs(intVal)) + " Intellect";
                gtemp.drawString(statValue, this.Rooms[RoomsI][RoomsJ].getNeck(i).getCenterX() - 
                                metrics.stringWidth(statValue)/2 + xShift, 
                        this.Rooms[RoomsI][RoomsJ].getNeck(i).getY() - rarityDisplacement - yShift);
                rarityDisplacement += 15;
            }
            
            if(this.Rooms[RoomsI][RoomsJ].getNeck(i).getRarity() == 2)
            {
                gtemp.setColor(blue);
            }
            else if(this.Rooms[RoomsI][RoomsJ].getNeck(i).getRarity() == 3)
            {
                gtemp.setColor(purple);
            }
            else
            {
                gtemp.setColor(trash);
            }
            
            itemNameFont = (new Font("Arial Black", Font.PLAIN, 14));
            gtemp.setFont(itemNameFont);
            metrics = gtemp.getFontMetrics(itemNameFont);
            
            gtemp.drawString(this.Rooms[RoomsI][RoomsJ].getNeck(i).getItemName(), 
                        this.Rooms[RoomsI][RoomsJ].getNeck(i).getCenterX() - 
                                metrics.stringWidth(this.Rooms[RoomsI][RoomsJ].getNeck(i).getItemName())/2 + xShift, 
                        this.Rooms[RoomsI][RoomsJ].getNeck(i).getY() - rarityDisplacement - yShift);
        }
        
        for(int i = 0; i < this.Rooms[RoomsI][RoomsJ].BootsSize(); i++)
        {
            itemNameFont = (new Font("Arial Black", Font.PLAIN, 12));
            gtemp.setFont(itemNameFont);
            metrics = gtemp.getFontMetrics(itemNameFont);
        
            int rarityDisplacement = 10;
            String statValue;
            
            int darkVal, flameVal, frostVal, vitVal, intVal;
            
            if(Player.getBoots() == null)
            {
                darkVal = this.Rooms[RoomsI][RoomsJ].getBoots(i).getDark();
                flameVal = this.Rooms[RoomsI][RoomsJ].getBoots(i).getFlame();
                frostVal = this.Rooms[RoomsI][RoomsJ].getBoots(i).getFrost();
                vitVal = this.Rooms[RoomsI][RoomsJ].getBoots(i).getVitality();
                intVal = this.Rooms[RoomsI][RoomsJ].getBoots(i).getIntellect();
            }
            else
            {
                darkVal = this.Rooms[RoomsI][RoomsJ].getBoots(i).getDark() -
                        Player.getBoots().getDark();
                flameVal = this.Rooms[RoomsI][RoomsJ].getBoots(i).getFlame() -
                        Player.getBoots().getFlame();
                frostVal = this.Rooms[RoomsI][RoomsJ].getBoots(i).getFrost() -
                        Player.getBoots().getFrost();
                vitVal = this.Rooms[RoomsI][RoomsJ].getBoots(i).getVitality() -
                        Player.getBoots().getVitality();
                intVal = this.Rooms[RoomsI][RoomsJ].getBoots(i).getIntellect() -
                        Player.getBoots().getIntellect();
            }
            
            if(darkVal != 0)
            {
                if(darkVal > 0)
                {
                    gtemp.setColor(green);
                    statValue = "+";
                }
                else
                {
                    gtemp.setColor(red);
                    statValue = "-";
                }
                
                statValue += Integer.toString(Math.abs(darkVal)) + " Void";
                gtemp.drawString(statValue, this.Rooms[RoomsI][RoomsJ].getBoots(i).getCenterX() - 
                                metrics.stringWidth(statValue)/2 + xShift, 
                        this.Rooms[RoomsI][RoomsJ].getBoots(i).getY() - rarityDisplacement - yShift);
                rarityDisplacement += 15;
            }
            if(frostVal != 0)
            {
                if(frostVal > 0)
                {
                    gtemp.setColor(green);
                    statValue = "+";
                }
                else
                {
                    gtemp.setColor(red);
                    statValue = "-";
                }
                
                statValue += Integer.toString(Math.abs(frostVal)) + " Frost";
                gtemp.drawString(statValue, this.Rooms[RoomsI][RoomsJ].getBoots(i).getCenterX() - 
                                metrics.stringWidth(statValue)/2 + xShift, 
                        this.Rooms[RoomsI][RoomsJ].getBoots(i).getY() - rarityDisplacement - yShift);
                rarityDisplacement += 15;
            }
            if(flameVal != 0)
            {
                if(flameVal > 0)
                {
                    gtemp.setColor(green);
                    statValue = "+";
                }
                else
                {
                    gtemp.setColor(red);
                    statValue = "-";
                }
                
                statValue += Integer.toString(Math.abs(flameVal)) + " Flame";
                gtemp.drawString(statValue, this.Rooms[RoomsI][RoomsJ].getBoots(i).getCenterX() - 
                                metrics.stringWidth(statValue)/2 + xShift, 
                        this.Rooms[RoomsI][RoomsJ].getBoots(i).getY() - rarityDisplacement - yShift);
                rarityDisplacement += 15;
            }
            if(vitVal != 0)
            {
                if(vitVal > 0)
                {
                    gtemp.setColor(green);
                    statValue = "+";
                }
                else
                {
                    gtemp.setColor(red);
                    statValue = "-";
                }
                
                statValue += Integer.toString(Math.abs(vitVal)) + " Vitality";
                gtemp.drawString(statValue, this.Rooms[RoomsI][RoomsJ].getBoots(i).getCenterX() - 
                                metrics.stringWidth(statValue)/2 + xShift, 
                        this.Rooms[RoomsI][RoomsJ].getBoots(i).getY() - rarityDisplacement - yShift);
                rarityDisplacement += 15;
            }
            if(intVal != 0)
            {
                if(intVal > 0)
                {
                    gtemp.setColor(green);
                    statValue = "+";
                }
                else
                {
                    gtemp.setColor(red);
                    statValue = "-";
                }
                
                statValue += Integer.toString(Math.abs(intVal)) + " Intellect";
                gtemp.drawString(statValue, this.Rooms[RoomsI][RoomsJ].getBoots(i).getCenterX() - 
                                metrics.stringWidth(statValue)/2 + xShift, 
                        this.Rooms[RoomsI][RoomsJ].getBoots(i).getY() - rarityDisplacement - yShift);
                rarityDisplacement += 15;
            }
            
            if(this.Rooms[RoomsI][RoomsJ].getBoots(i).getRarity() == 2)
            {
                gtemp.setColor(blue);
            }
            else if(this.Rooms[RoomsI][RoomsJ].getBoots(i).getRarity() == 3)
            {
                gtemp.setColor(orange);
            }
            else
            {
                gtemp.setColor(trash);
            }
            
            itemNameFont = (new Font("Arial Black", Font.PLAIN, 14));
            gtemp.setFont(itemNameFont);
            metrics = gtemp.getFontMetrics(itemNameFont);
            
            gtemp.drawString(this.Rooms[RoomsI][RoomsJ].getBoots(i).getItemName(), 
                        this.Rooms[RoomsI][RoomsJ].getBoots(i).getCenterX() - 
                                metrics.stringWidth(this.Rooms[RoomsI][RoomsJ].getBoots(i).getItemName())/2 + xShift, 
                        this.Rooms[RoomsI][RoomsJ].getBoots(i).getY() - rarityDisplacement - yShift);
        }
        
        for(int i = 0; i < this.Rooms[RoomsI][RoomsJ].TomeSize(); i++)
        {
            itemNameFont = (new Font("Arial Black", Font.PLAIN, 12));
            gtemp.setFont(itemNameFont);
            metrics = gtemp.getFontMetrics(itemNameFont);
        
            int rarityDisplacement = 10;
            String statValue;
            
            int darkVal, flameVal, frostVal, vitVal, intVal;
            
            if(Player.getTome() == null)
            {
                darkVal = this.Rooms[RoomsI][RoomsJ].getTome(i).getDark();
                flameVal = this.Rooms[RoomsI][RoomsJ].getTome(i).getFlame();
                frostVal = this.Rooms[RoomsI][RoomsJ].getTome(i).getFrost();
                vitVal = this.Rooms[RoomsI][RoomsJ].getTome(i).getVitality();
                intVal = this.Rooms[RoomsI][RoomsJ].getTome(i).getIntellect();
            }
            else
            {
                darkVal = this.Rooms[RoomsI][RoomsJ].getTome(i).getDark() -
                        Player.getTome().getDark();
                flameVal = this.Rooms[RoomsI][RoomsJ].getTome(i).getFlame() -
                        Player.getTome().getFlame();
                frostVal = this.Rooms[RoomsI][RoomsJ].getTome(i).getFrost() -
                        Player.getTome().getFrost();
                vitVal = this.Rooms[RoomsI][RoomsJ].getTome(i).getVitality() -
                        Player.getTome().getVitality();
                intVal = this.Rooms[RoomsI][RoomsJ].getTome(i).getIntellect() -
                        Player.getTome().getIntellect();
            }
            
            if(darkVal != 0)
            {
                if(darkVal > 0)
                {
                    gtemp.setColor(green);
                    statValue = "+";
                }
                else
                {
                    gtemp.setColor(red);
                    statValue = "-";
                }
                
                statValue += Integer.toString(Math.abs(darkVal)) + " Void";
                gtemp.drawString(statValue, this.Rooms[RoomsI][RoomsJ].getTome(i).getCenterX() - 
                                metrics.stringWidth(statValue)/2 + xShift, 
                        this.Rooms[RoomsI][RoomsJ].getTome(i).getY() - rarityDisplacement - yShift);
                rarityDisplacement += 15;
            }
            if(frostVal != 0)
            {
                if(frostVal > 0)
                {
                    gtemp.setColor(green);
                    statValue = "+";
                }
                else
                {
                    gtemp.setColor(red);
                    statValue = "-";
                }
                
                statValue += Integer.toString(Math.abs(frostVal)) + " Frost";
                gtemp.drawString(statValue, this.Rooms[RoomsI][RoomsJ].getTome(i).getCenterX() - 
                                metrics.stringWidth(statValue)/2 + xShift, 
                        this.Rooms[RoomsI][RoomsJ].getTome(i).getY() - rarityDisplacement - yShift);
                rarityDisplacement += 15;
            }
            if(flameVal != 0)
            {
                if(flameVal > 0)
                {
                    gtemp.setColor(green);
                    statValue = "+";
                }
                else
                {
                    gtemp.setColor(red);
                    statValue = "-";
                }
                
                statValue += Integer.toString(Math.abs(flameVal)) + " Flame";
                gtemp.drawString(statValue, this.Rooms[RoomsI][RoomsJ].getTome(i).getCenterX() - 
                                metrics.stringWidth(statValue)/2 + xShift, 
                        this.Rooms[RoomsI][RoomsJ].getTome(i).getY() - rarityDisplacement - yShift);
                rarityDisplacement += 15;
            }
            if(vitVal != 0)
            {
                if(vitVal > 0)
                {
                    gtemp.setColor(green);
                    statValue = "+";
                }
                else
                {
                    gtemp.setColor(red);
                    statValue = "-";
                }
                
                statValue += Integer.toString(Math.abs(vitVal)) + " Vitality";
                gtemp.drawString(statValue, this.Rooms[RoomsI][RoomsJ].getTome(i).getCenterX() - 
                                metrics.stringWidth(statValue)/2 + xShift, 
                        this.Rooms[RoomsI][RoomsJ].getTome(i).getY() - rarityDisplacement - yShift);
                rarityDisplacement += 15;
            }
            if(intVal != 0)
            {
                if(intVal > 0)
                {
                    gtemp.setColor(green);
                    statValue = "+";
                }
                else
                {
                    gtemp.setColor(red);
                    statValue = "-";
                }
                
                statValue += Integer.toString(Math.abs(intVal)) + " Intellect";
                gtemp.drawString(statValue, this.Rooms[RoomsI][RoomsJ].getTome(i).getCenterX() - 
                                metrics.stringWidth(statValue)/2 + xShift, 
                        this.Rooms[RoomsI][RoomsJ].getTome(i).getY() - rarityDisplacement - yShift);
                rarityDisplacement += 15;
            }
            
            if(this.Rooms[RoomsI][RoomsJ].getTome(i).getRarity() == 2)
            {
                gtemp.setColor(blue);
            }
            else if(this.Rooms[RoomsI][RoomsJ].getTome(i).getRarity() == 3)
            {
                gtemp.setColor(purple);
            }
            else
            {
                gtemp.setColor(trash);
            }
            
            itemNameFont = (new Font("Arial Black", Font.PLAIN, 14));
            gtemp.setFont(itemNameFont);
            metrics = gtemp.getFontMetrics(itemNameFont);
            
            gtemp.drawString(this.Rooms[RoomsI][RoomsJ].getTome(i).getItemName(), 
                        this.Rooms[RoomsI][RoomsJ].getTome(i).getCenterX() - 
                                metrics.stringWidth(this.Rooms[RoomsI][RoomsJ].getTome(i).getItemName())/2 + xShift, 
                        this.Rooms[RoomsI][RoomsJ].getTome(i).getY() - rarityDisplacement - yShift);
        }
        
        gtemp.dispose();
    }
    
    public BufferedImage bufferedImageConverter(Image img) {
        /*
        used to convert images into buffered images
        */
        BufferedImage bimg = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = bimg.createGraphics();
        g.drawImage(img, 0, 0, null);
        g.dispose();

        return bimg;
    }

    public void paintRotatedImg(Image img, double angle, int x, int y) {
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
        game.testLevelInit();
        //game.musicThreadLoop();
        //game.mainMenu();
        game.timerLoop();
    }
}
