package SourceFiles;

import SourceFiles.GameLogic.GameEvents;
import SourceFiles.GameObjects.StationaryObjects.GearObjects.Gear;
import SourceFiles.GameLogic.KeyControl;
import SourceFiles.GameObjects.MovingObjects.Player.Spell;
import java.awt.AlphaComposite;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
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
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import javax.swing.*;
import javax.swing.JComboBox;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;

public class PlayGame extends JPanel implements KeyListener{
    /*
    PlayGame is the main controller for the game. Loads and initializes all 
    images/objects used, creates the window used for the game, and controls the 
    main game loop.
    */
    
    private BufferedImage bufImg, GameImg;
    private Graphics2D g2d;
    private Image  TempleFloor,  NullSpellIcon, CurrentSpellIcon, ChilledImg, 
            FrozenImg, HealthBar, HealthBarBackground, PauseMenuImg, TalentMenuImg,
            SettingsMenuImg, HoverIcon, CloseTabImg, PlayerTabImg, TalentTabImg, 
            SettingsTabImg, HoverTabImg, PlayerHead;
    
    //private final int ScreenWidth = 1024, ScreenHeight = 768;
    //private final int ScreenWidth = screenSize;
    //private final int ScreenWidth = 1920, ScreenHeight = 1080;
    private int ScreenWidth,ScreenHeight;
    private String ScreenSize;
    private final int FPS = 60;
    
    private boolean Paused, InGame;
    private JFrame GameWindow;
    private JPanel PauseMenu, MainMenu, TalentMenu, SettingsMenu;
    private JPanel MenuCards;
    private CardLayout MenuLayout;
    private GameEvents PlayerKeyEvent;
    private GameInstance Game;
    private boolean PauseTutorial;
    
    private JButton NeckBtn, RingBtn, TomeBtn, BootsBtn, Spell1Btn,
            Spell2Btn, Spell3Btn, Spell4Btn;
    private JTextPane GearText, LeftStatText, RightStatText,
            SpellText, FloorText;
    private JLabel HoverLabel, MapLabel, PlayerTabHoverLabel, TalentTabHoverLabel,
            SettingsTabHoverLabel, BackgroundLabel;
    
    private int MissingHealth;
    private float OpaqueValue;
    private boolean OpaqueLower, OpaqueRaise;
    
    private void startGame()
    {
        /*
        startGame is called when game first opens
        */
        MenuLayout = new CardLayout();
        MenuCards = new JPanel(MenuLayout);
        MenuCards.setBackground(Color.black);
        MenuCards.setVisible(false);
        MenuCards.setOpaque(true);
        
        try{
            BufferedReader settingsReader = new BufferedReader(new FileReader("SaveFiles" + File.separator + "Settings.txt"));
            try{
                ScreenSize = settingsReader.readLine();
            }catch(IOException e){
                System.out.println("Error reading Settings file: " + e);
                badSettingsFile();
                ScreenSize = "1280 x 800";
            }
            try{
                settingsReader.close();
            }catch(IOException e){
                System.out.println("Error closing Settings file: " + e);
            }
        }catch(FileNotFoundException notFound){
            System.out.println("Settings file not found: " + notFound);
            ScreenSize = "1280 x 800";
        }
        
        parseScreenSize();
    }
    
    private void initGameWindow()
    {
        GameWindow.addWindowListener(new WindowAdapter(){});
        GameWindow.add(this);
        GameWindow.setTitle("Rogue Game");
        GameWindow.setResizable(false);
        GameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //GameWindow.setContentPane(this);
        GameWindow.getContentPane().setFocusable(true);
        GameWindow.getContentPane().addKeyListener(this);
        GameWindow.setBackground(Color.black);
        GameWindow.setVisible(true);
        
        GameWindow.add(MenuCards);
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
            
            this.PauseMenuImg = ImageIO.read(new File("Resources" + File.separator + "PauseMenu.png"));
            this.HoverIcon = ImageIO.read(new File("Resources" + File.separator + "HoverIcon.png"));
            this.TalentMenuImg = ImageIO.read(new File("Resources" + File.separator + "TalentTree.png"));
            this.SettingsMenuImg = ImageIO.read(new File("Resources" + File.separator + "PauseBookEmpty.png"));
            this.CloseTabImg = ImageIO.read(new File("Resources" + File.separator + "CloseTab.png"));
            this.PlayerTabImg = ImageIO.read(new File("Resources" + File.separator + "CharacterTab.png"));
            this.TalentTabImg = ImageIO.read(new File("Resources" + File.separator + "TalentTab.png"));
            this.SettingsTabImg = ImageIO.read(new File("Resources" + File.separator + "SettingsTab.png"));
            this.HoverTabImg = ImageIO.read(new File("Resources" + File.separator + "TabHover.png"));
            this.PlayerHead = ImageIO.read(new File("Resources" + File.separator + "PlayerHead.png"));
        }catch (Exception e) {
            System.out.print(e.getStackTrace() + " Error loading resources in PlayGsme \n");
        }
    }
    
    private void newGameInit()
    {
        this.Paused = false;
        this.InGame = true;
        PauseTutorial = true;
        
        this.MissingHealth = 0;
        this.OpaqueValue = 0f;
        this.OpaqueRaise = true;
        this.OpaqueLower = false;
        
        NeckBtn = null;
        RingBtn = null;
        TomeBtn = null;
        BootsBtn = null;
        Spell1Btn = null;
        Spell2Btn = null;
        Spell3Btn = null;
        Spell4Btn = null;
        
        Game = new GameInstance(ScreenWidth, ScreenHeight);
        
        PlayerKeyEvent = new GameEvents();
        
        GameWindowAddListeners();
    }
    
    private void GameWindowAddListeners()
    {
        
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
    
    private void resizeScreen(String screenSize)
    {
        writeSettingsFile(screenSize);
        ScreenSize = screenSize;
        parseScreenSize();
        Game.adjustScreenSize(ScreenWidth, ScreenHeight);
        this.setSize(ScreenWidth, ScreenHeight);
        initializeMenus();
        updateMenuComponents();
        GameWindowAddListeners();
    }
    
    private void parseScreenSize()
    {
        if (GameWindow != null)
        {
            GameWindow.dispose();
        }
        GameWindow = new JFrame();
        
        if(ScreenSize.equals("Fullscreen Borderless"))
        {
            GameWindow.setSize(Toolkit.getDefaultToolkit().getScreenSize());
            GameWindow.setExtendedState(JFrame.MAXIMIZED_BOTH);
            GameWindow.setUndecorated(true);
            initGameWindow();
            ScreenWidth = (int) GameWindow.getSize().getWidth();
            ScreenHeight = (int) GameWindow.getSize().getHeight();
        }
        else
        {
            String[] sizes = ScreenSize.split(" x ");
            try{
                try{
                    ScreenWidth = Integer.parseInt(sizes[0]);
                    ScreenHeight = Integer.parseInt(sizes[1]) - 35;
                    GameWindow.setSize(ScreenWidth + 6, ScreenHeight + 35);
                    initGameWindow();
                }catch(NumberFormatException badNumbers){
                    System.out.println("Improper screen size settings format: " + badNumbers);
                    ScreenWidth = 1280;
                    ScreenHeight = 800 - 35;
                    GameWindow.setSize(ScreenWidth + 6, ScreenHeight + 35);
                    initGameWindow();
                    badSettingsFile();
                }
            }catch(ArrayIndexOutOfBoundsException outOfBounds){
                System.out.println("Improper screen size settings format: " + outOfBounds);
                ScreenWidth = 1280;
                ScreenHeight = 800 - 35;
                GameWindow.setSize(ScreenWidth + 6, ScreenHeight + 35);
                initGameWindow();
                badSettingsFile();
            }
        }
    }
    
    private void writeSettingsFile(String settings)
    {
        List<String> settingsList = Arrays.asList(settings);
        Path settingsFile = Paths.get("SaveFiles" + File.separator + "Settings.txt");
        try{
            Files.write(settingsFile, settingsList, Charset.forName("UTF-8"));
        }catch(IOException e){
            System.out.println("Error writing Settings file: " + e);
        }
    }
    
    private void badSettingsFile()
    {
        /*
        badSettingsFile is used when the settings file is corrupt to 
        reset it. Note that currently the screen size is currently the only
        setting, this will be used for other settings as well in the future.
        */
        writeSettingsFile("1280 x 800");
    }
    
    private void initializeMenus()
    {
        BackgroundLabel = new JLabel();
        BackgroundLabel.setBounds(0, 0, ScreenWidth, ScreenHeight);
        BackgroundLabel.setIcon(null);
        BackgroundLabel.setBackground(Color.black);
        
        bufImg = (BufferedImage) createImage(1280, 1280);
        GameImg = (BufferedImage) createImage(ScreenWidth, ScreenHeight);
        
        pauseMenuInit();
        talentMenuInit();
        settingsMenuInit();
        Spell1Btn = Spell2Btn = Spell3Btn = Spell4Btn = null;
        mainMenuInit();
        
        SettingsMenu.add(BackgroundLabel);
        
        this.MenuLayout.show(MenuCards, "SettingsMenu");
    }
    
    private void mainMenuInit()
    {
        
    }
    
    private JButton createCloseTab(JLabel tabHoverLabel)
    {
        int xOffSet = (ScreenWidth - PauseMenuImg.getWidth(null)) / 2;
        int yOffSet = (ScreenHeight - PauseMenuImg.getHeight(null)) / 2;
        
        JButton closeTab = tabButtonInit(xOffSet + 992, yOffSet + 50, CloseTabImg, tabHoverLabel);
        
        closeTab.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){  
                unPauseGame();
            }
        });
        
        return closeTab;
    }
    
    private JButton createPlayerTab(JLabel tabHoverLabel)
    {
        int xOffSet = (ScreenWidth - PauseMenuImg.getWidth(null)) / 2;
        int yOffSet = (ScreenHeight - PauseMenuImg.getHeight(null)) / 2;
        
        JButton playerTab = tabButtonInit(xOffSet + 992, yOffSet + 130, PlayerTabImg, tabHoverLabel);
        
        playerTab.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                PauseMenu.add(BackgroundLabel);
                PauseMenu.setComponentZOrder(BackgroundLabel, PauseMenu.getComponentCount() - 1);
                MenuLayout.show(MenuCards, "PauseMenu");
            }
        });
        
        return playerTab;
    }
    
    private JButton createSettingsTab(JLabel tabHoverLabel)
    {
        int xOffSet = (ScreenWidth - PauseMenuImg.getWidth(null)) / 2;
        int yOffSet = (ScreenHeight - PauseMenuImg.getHeight(null)) / 2;
        JButton settingsTab = tabButtonInit(xOffSet + 992, yOffSet + 230, SettingsTabImg, tabHoverLabel);
        
        settingsTab.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){  
                SettingsMenu.add(BackgroundLabel);
                SettingsMenu.setComponentZOrder(BackgroundLabel, SettingsMenu.getComponentCount() - 1);
                MenuLayout.show(MenuCards, "SettingsMenu");
            }
        });
        
        return settingsTab;
    }
    
    private JButton createTalentTab(JLabel tabHoverLabel)
    {
        int xOffSet = (ScreenWidth - PauseMenuImg.getWidth(null)) / 2;
        int yOffSet = (ScreenHeight - PauseMenuImg.getHeight(null)) / 2;
        
        JButton talentTab = tabButtonInit(xOffSet + 992, yOffSet + 180, TalentTabImg, tabHoverLabel);
        
        talentTab.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){  
                TalentMenu.add(BackgroundLabel);
                TalentMenu.setComponentZOrder(BackgroundLabel, TalentMenu.getComponentCount() - 1);
                MenuLayout.show(MenuCards, "TalentMenu");
            }
        });
        
        return talentTab;
    }
    
    private JButton tabButtonInit(int x, int y, Image btnImg, JLabel tabHoverLabel)
    {
        JButton btn = new JButton(new ImageIcon(btnImg));
        btn.setBorder(BorderFactory.createEmptyBorder());
        btn.setContentAreaFilled(false);
        btn.setBounds(x, y, btnImg.getWidth(null), btnImg.getHeight(null));
        btn.setFocusable(false);
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                tabHoverLabel.setBounds(x, y - 2, HoverTabImg.getWidth(null), HoverTabImg.getHeight(null));
                tabHoverLabel.setVisible(true);
                tabHoverLabel.getParent().setComponentZOrder(tabHoverLabel, 2);
            }

            public void mouseExited(MouseEvent evt) {
                tabHoverLabel.setVisible(false);
            }
        });
        return btn;
    }
    
    private void pauseMenuInit()
    {
        /*
        PauseMenu is used whenever the pause game button is pressed. Currently
        being worked on, will display a map, equipped gear, and talent tree
        */
        PauseMenu = new JPanel();
        PauseMenu.setVisible(true);
        PauseMenu.setLayout(null);
        PauseMenu.setBackground(Color.black);
        
        int xOffSet = (ScreenWidth - PauseMenuImg.getWidth(null)) / 2;
        int yOffSet = (ScreenHeight - PauseMenuImg.getHeight(null)) / 2;
        
        FloorText = new JTextPane();
        FloorText.setBounds(69 + xOffSet, 35 + yOffSet, 374, 64);
        FloorText.setFont(new Font("Palatino Linotype", Font.PLAIN, 20));
        FloorText.setFocusable(false);
        FloorText.setOpaque(false);
        FloorText.setText("");
        Style style = FloorText.addStyle("Title", null);
        StyleConstants.setForeground(style, Color.BLACK);
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        FloorText.getStyledDocument().setParagraphAttributes(0, FloorText.getStyledDocument().getLength(), center, false);
        PauseMenu.add(FloorText);
        
        LeftStatText = new JTextPane();
        LeftStatText.setBounds(589 + xOffSet, 46 + yOffSet, 174, 320);
        LeftStatText.setFont(new Font("Palatino Linotype", Font.PLAIN, 17));
        LeftStatText.setFocusable(false);
        LeftStatText.setOpaque(false);
        LeftStatText.setText("");
        LeftStatText.addStyle("Style", null);
        StyleConstants.setForeground(style, Color.BLACK);
        PauseMenu.add(LeftStatText);
        
        RightStatText = new JTextPane();
        RightStatText.setBounds(773 + xOffSet, 46 + yOffSet, 169, 320);
        RightStatText.setFont(new Font("Palatino Linotype", Font.PLAIN, 17));
        RightStatText.setFocusable(false);
        RightStatText.setOpaque(false);
        RightStatText.setText("");
        RightStatText.addStyle("Style", null);
        StyleConstants.setForeground(style, Color.BLACK);
        PauseMenu.add(RightStatText);
        
        JTextPane runeText = new JTextPane();
        runeText.setBounds(69 + xOffSet, 625 + yOffSet, 374, 64);
        runeText.setFont(new Font("Palatino Linotype", Font.PLAIN, 20));
        runeText.setFocusable(false);
        runeText.setOpaque(false);
        runeText.addStyle("Title", null);
        runeText.getStyledDocument().setParagraphAttributes(0, runeText.getStyledDocument().getLength(), center, false);
        try {
           runeText.getStyledDocument().insertString(
                    runeText.getStyledDocument().getLength(), "Runes", style);
        }catch(BadLocationException e){}
        PauseMenu.add(runeText);
        
        JTextPane gearTitle = new JTextPane();
        gearTitle.setBounds(587 + xOffSet, 394 + yOffSet, 186, 64);
        gearTitle.setFont(new Font("Palatino Linotype", Font.PLAIN, 20));
        gearTitle.setFocusable(false);
        gearTitle.setOpaque(false);
        gearTitle.addStyle("Title", null);
        gearTitle.getStyledDocument().setParagraphAttributes(0, gearTitle.getStyledDocument().getLength(), center, false);
        try {
           gearTitle.getStyledDocument().insertString(
                    gearTitle.getStyledDocument().getLength(), "Equipment", style);
        }catch(BadLocationException e){}
        PauseMenu.add(gearTitle);
        
        GearText = new JTextPane();
        GearText.setBounds(803 + xOffSet, 393 + yOffSet, 146, 275);
        GearText.setFont(new Font("Palatino Linotype", Font.PLAIN, 16));
        GearText.setFocusable(false);
        GearText.setOpaque(false);
        GearText.setText("");
        GearText.addStyle("Style", null);
        PauseMenu.add(GearText);
        
        JTextPane spellTitle = new JTextPane();
        spellTitle.setBounds(69 + xOffSet, 494 + yOffSet, 374, 64);
        spellTitle.setFont(new Font("Palatino Linotype", Font.PLAIN, 20));
        spellTitle.setFocusable(false);
        spellTitle.setOpaque(false);
        spellTitle.addStyle("Title", null);
        spellTitle.getStyledDocument().setParagraphAttributes(0, spellTitle.getStyledDocument().getLength(), center, false);
        try {
           spellTitle.getStyledDocument().insertString(
                    spellTitle.getStyledDocument().getLength(), "Spells", style);
        }catch(BadLocationException e){}
        PauseMenu.add(spellTitle);
        
        SpellText = new JTextPane();
        SpellText.setBounds(75 + xOffSet, 368 + yOffSet, 361, 124);
        SpellText.setFont(new Font("Palatino Linotype", Font.PLAIN, 16));
        SpellText.setFocusable(false);
        SpellText.setOpaque(false);
        SpellText.setText("");
        SpellText.addStyle("Style", null);
        PauseMenu.add(SpellText);
        
        JLabel pauseLabel = new JLabel(new ImageIcon(this.PauseMenuImg));
        pauseLabel.setBounds(xOffSet, yOffSet, PauseMenuImg.getWidth(null), PauseMenuImg.getHeight(null));
        pauseLabel.setVisible(true);
        PauseMenu.add(pauseLabel);
        PauseMenu.setOpaque(false);
        
        HoverLabel = new JLabel(new ImageIcon(this.CurrentSpellIcon));
        HoverLabel.setVisible(false);
        PauseMenu.add(HoverLabel);
        
        MapLabel = new JLabel();
        MapLabel.setVisible(true);
        MapLabel.setBounds(85 + xOffSet, 91 + yOffSet, 341, 241);
        MapLabel.setOpaque(true);
        PauseMenu.add(MapLabel);
        PauseMenu.setComponentZOrder(MapLabel, 2);
        
        this.PlayerTabHoverLabel = new JLabel(new ImageIcon(HoverTabImg));
        
        JButton closeTab = createCloseTab(PlayerTabHoverLabel);
        JButton playerTab = createPlayerTab(PlayerTabHoverLabel);
        JButton talentTab = createTalentTab(PlayerTabHoverLabel);
        JButton settingsTab = createSettingsTab(PlayerTabHoverLabel);
        
        PauseMenu.add(closeTab);
        PauseMenu.setComponentZOrder(closeTab, 2);
        
        PauseMenu.add(playerTab);
        PauseMenu.setComponentZOrder(playerTab, 2);
        
        PauseMenu.add(talentTab);
        PauseMenu.setComponentZOrder(talentTab, 2);
        
        PauseMenu.add(settingsTab);
        PauseMenu.setComponentZOrder(settingsTab, 2);
        
        PauseMenu.add(PlayerTabHoverLabel);
        PauseMenu.setComponentZOrder(PlayerTabHoverLabel, 2);
        
        MenuCards.add(PauseMenu, "PauseMenu");
    }
    
    private void talentMenuInit()
    {
        TalentMenu = new JPanel();
        TalentMenu.setVisible(true);
        TalentMenu.setLayout(null);
        TalentMenu.setBackground(Color.black);
        
        int xOffSet = (ScreenWidth - TalentMenuImg.getWidth(null)) / 2;
        int yOffSet = (ScreenHeight - TalentMenuImg.getHeight(null)) / 2;
        
        JLabel talentLabel = new JLabel(new ImageIcon(this.TalentMenuImg));
        talentLabel.setBounds(xOffSet, yOffSet, TalentMenuImg.getWidth(null), TalentMenuImg.getHeight(null));
        talentLabel.setVisible(true);
        TalentMenu.add(talentLabel);
        TalentMenu.setOpaque(true);
        
        this.TalentTabHoverLabel = new JLabel(new ImageIcon(HoverTabImg));
        
        JButton closeTab = createCloseTab(TalentTabHoverLabel);
        JButton playerTab = createPlayerTab(TalentTabHoverLabel);
        JButton talentTab = createTalentTab(TalentTabHoverLabel);
        JButton settingsTab = createSettingsTab(TalentTabHoverLabel);
        
        TalentMenu.add(closeTab);
        TalentMenu.setComponentZOrder(closeTab, 0);
        
        TalentMenu.add(playerTab);
        TalentMenu.setComponentZOrder(playerTab, 0);
        
        TalentMenu.add(talentTab);
        TalentMenu.setComponentZOrder(talentTab, 0);
        
        TalentMenu.add(settingsTab);
        TalentMenu.setComponentZOrder(settingsTab, 0);
        
        TalentMenu.add(TalentTabHoverLabel);
        TalentMenu.setComponentZOrder(TalentTabHoverLabel, 0);
        
        this.MenuCards.add(TalentMenu, "TalentMenu");
    }
    
    private void settingsMenuInit()
    {
        SettingsMenu = new JPanel();
        SettingsMenu.setVisible(true);
        SettingsMenu.setLayout(null);
        SettingsMenu.setBackground(Color.black);
        
        int xOffSet = (ScreenWidth - SettingsMenuImg.getWidth(null)) / 2;
        int yOffSet = (ScreenHeight - SettingsMenuImg.getHeight(null)) / 2;
        
        JLabel settingsLabel = new JLabel(new ImageIcon(this.SettingsMenuImg));
        settingsLabel.setBounds(xOffSet, yOffSet, SettingsMenuImg.getWidth(null), SettingsMenuImg.getHeight(null));
        settingsLabel.setVisible(true);
        SettingsMenu.add(settingsLabel);
        SettingsMenu.setOpaque(true);
        
        JTextPane controlsText = new JTextPane();
        controlsText.setBounds(589 + xOffSet, 46 + yOffSet, 361, 620);
        controlsText.setFont(new Font("Palatino Linotype", Font.PLAIN, 20));
        controlsText.setFocusable(false);
        controlsText.setOpaque(false);
        controlsText.setText("");
        Style style = controlsText.addStyle("Style", null);
        StyleConstants.setForeground(style, Color.BLACK);
        SimpleAttributeSet align = new SimpleAttributeSet();
        StyleConstants.setAlignment(align, StyleConstants.ALIGN_LEFT);
        controlsText.getStyledDocument().setParagraphAttributes(0, controlsText.getStyledDocument().getLength(), align, false);
        SettingsMenu.add(controlsText);
        SettingsMenu.setComponentZOrder(controlsText, 0);
        try {
            controlsText.getStyledDocument().insertString(
                    controlsText.getStyledDocument().getLength(), "Controls\n", style);
        }catch(BadLocationException e){}
        
        controlsText.setFont(new Font("Palatino Linotype", Font.PLAIN, 17));
        StyleConstants.setAlignment(align, StyleConstants.ALIGN_CENTER);
        controlsText.getStyledDocument().setParagraphAttributes(0, controlsText.getStyledDocument().getLength(), align, false);
        String controlsString = "";
        
        controlsString += "Spell One:\t[1]\n";
        controlsString += "Spell Two:\t[2]\n";
        controlsString += "Spell Three:\t[3]\n";
        controlsString += "Spell Four:\t[4]\n";
        controlsString += "Previous Spell:\tMouse Wheel Up\n";
        controlsString += "Next Spell:\tMouse Wheel Down\n";
        controlsString += "Move Up:\t[W] / Up Arrow Key\n";
        controlsString += "Move Left:\t[A] / Left Arrow Key\n";
        controlsString += "Move Down:\t[S] / Down Arrow Key\n";
        controlsString += "Move Right:\t[D] / Right Arrow Key\n";
        controlsString += "Pause Game:\t[P] / [M] / [Esc]\n";
        try {
            controlsText.getStyledDocument().insertString(
                    controlsText.getStyledDocument().getLength(), controlsString, style);
        }catch(BadLocationException e){}
        
        this.SettingsTabHoverLabel = new JLabel(new ImageIcon(HoverTabImg));
        
        JTextPane settingsText = new JTextPane();
        settingsText.setBounds(75 + xOffSet, 46 + yOffSet, 361,  620);
        settingsText.setFont(new Font("Palatino Linotype", Font.PLAIN, 17));
        settingsText.setFocusable(false);
        settingsText.setOpaque(false);
        settingsText.setText("");
        style = settingsText.addStyle("Style", null);
        StyleConstants.setForeground(style, Color.BLACK);
        StyleConstants.setAlignment(align, StyleConstants.ALIGN_LEFT);
        settingsText.getStyledDocument().setParagraphAttributes(0, settingsText.getStyledDocument().getLength(), align, false);
        SettingsMenu.add(settingsText);
        SettingsMenu.setComponentZOrder(settingsText, 0);
        try {
            settingsText.getStyledDocument().insertString(
                    settingsText.getStyledDocument().getLength(), "Settings\n", style);
        }catch(BadLocationException e){}
        StyleConstants.setAlignment(align, StyleConstants.ALIGN_CENTER);
        settingsText.getStyledDocument().setParagraphAttributes(0, settingsText.getStyledDocument().getLength(), align, false);
        try {
            settingsText.getStyledDocument().insertString(
                    settingsText.getStyledDocument().getLength(), "Screen Size:\n", style);
        }catch(BadLocationException e){}
        
        initializeScreenSizeSettings();
        
        JButton exitButton = new JButton("Quit to Desktop");
        exitButton.setBounds(78 + xOffSet, 114 + yOffSet, 125, 20);
        exitButton.setBackground(new Color(176, 126, 64));
        exitButton.setBorder(BorderFactory.createLineBorder(new Color(124, 82, 34)));
        exitButton.setFont(new Font("Palatino Linotype", Font.PLAIN, 15));
        exitButton.setForeground(Color.black);
        exitButton.setFocusable(false);
        exitButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                System.exit(0);
            }
        });
        UIManager.put("Button.select", new Color(200, 143, 80));
        SettingsMenu.add(exitButton);
        SettingsMenu.setComponentZOrder(exitButton, 0);
        
        JButton closeTab = createCloseTab(SettingsTabHoverLabel);
        JButton playerTab = createPlayerTab(SettingsTabHoverLabel);
        JButton talentTab = createTalentTab(SettingsTabHoverLabel);
        JButton settingsTab = createSettingsTab(SettingsTabHoverLabel);
        
        SettingsMenu.add(closeTab);
        SettingsMenu.setComponentZOrder(closeTab, 0);
        
        SettingsMenu.add(playerTab);
        SettingsMenu.setComponentZOrder(playerTab, 0);
        
        SettingsMenu.add(talentTab);
        SettingsMenu.setComponentZOrder(talentTab, 0);
        
        SettingsMenu.add(settingsTab);
        SettingsMenu.setComponentZOrder(settingsTab, 0);
        
        SettingsMenu.add(SettingsTabHoverLabel);
        SettingsMenu.setComponentZOrder(SettingsTabHoverLabel, 0);
        
        MenuCards.add(SettingsMenu, "SettingsMenu");
    }
    
    private void initializeScreenSizeSettings()
    {
        int xOffSet = (ScreenWidth - SettingsMenuImg.getWidth(null)) / 2;
        int yOffSet = (ScreenHeight - SettingsMenuImg.getHeight(null)) / 2;
        
        JComboBox screenSizes = new JComboBox();
        screenSizes.setBounds(224 + xOffSet, 80 + yOffSet, 170, 20);
        screenSizes.setUI(new BasicComboBoxUI(){
            @Override
            protected JButton createArrowButton() {
                JButton arrowBut = new BasicArrowButton(BasicArrowButton.SOUTH, 
                        new Color(176, 126, 64), new Color(200, 143, 80), 
                        new Color(124, 82, 34), new Color(200, 143, 80));
                arrowBut.setBorder(BorderFactory.createLineBorder(new Color(176, 126, 64)));
                arrowBut.setForeground(Color.black);
                return arrowBut;
            }
        });
        UIManager.put("ComboBox.background", new Color(200, 143, 80));
        UIManager.put("Button.shadow", new Color(200, 143, 80));
        screenSizes.setBackground(new Color(176, 126, 64));
        screenSizes.setForeground(Color.BLACK);
        screenSizes.setBorder(BorderFactory.createLineBorder(new Color(124, 82, 34)));
        screenSizes.setFocusable(false);
        screenSizes.setFont(new Font("Palatino Linotype", Font.PLAIN, 15));
        screenSizes.addItem("Fullscreen Borderless");
        screenSizes.addItem("1024 x 768");
        screenSizes.addItem("1280 x 800");
        screenSizes.addItem("1280 x 1024");
        screenSizes.addItem("1366 x 768");
        screenSizes.addItem("1440 x 900");
        screenSizes.addItem("1536 x 864");
        screenSizes.addItem("1920 x 1080");
        screenSizes.setSelectedItem(this.ScreenSize);
        screenSizes.getEditor().getEditorComponent().setBackground(Color.yellow);
        screenSizes.setRenderer(new DefaultListCellRenderer(){
            @Override
            public Component getListCellRendererComponent(JList list, Object value,
                    int index, boolean isSelected, boolean cellHasFocus) 
            {
                JLabel sizeOption = (JLabel)super.getListCellRendererComponent(list, value, 
                        index, isSelected, cellHasFocus);
                
                if (isSelected) 
                {
                    sizeOption.setBackground(new Color(200, 143, 80));
                    sizeOption.setForeground(Color.black);
                }
                else
                {
                    sizeOption.setBackground(new Color(176, 126, 64));
                    sizeOption.setForeground(Color.black);
                }
                return sizeOption;
            }
        });
        
        screenSizes.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(!((String)screenSizes.getSelectedItem()).equals(ScreenSize))
                {
                    resizeScreen((String)screenSizes.getSelectedItem());
                }
            }
        });
        
        SettingsMenu.add(screenSizes);
        SettingsMenu.setComponentZOrder(screenSizes, 0);
    }
    
    private void updateMenuComponents()
    {
        int xOffSet = (ScreenWidth - PauseMenuImg.getWidth(null)) / 2;
        int yOffSet = (ScreenHeight - PauseMenuImg.getHeight(null)) / 2;
        
        createMap();
        
        Style style = FloorText.getStyle("Title");
        
        printPausePlayerStats();
        
        FloorText.setText("");
        try {
            FloorText.getStyledDocument().insertString(
                    FloorText.getStyledDocument().getLength(), "Floor " + Game.getFloorLevel(), style);
        }catch(BadLocationException e){}
        
        if(Game.getPlayer().getNeck() != null)
        {
            if(NeckBtn != null)
            {
                PauseMenu.remove(NeckBtn);
            }
            NeckBtn = createGearButton(Game.getPlayer().getNeck(), xOffSet + 587, yOffSet + 443);
            PauseMenu.add(NeckBtn);
            PauseMenu.setComponentZOrder(NeckBtn, 1);
        }
        if(Game.getPlayer().getRing() != null)
        {
            if(RingBtn != null)
            {
                PauseMenu.remove(RingBtn);
            }
            RingBtn = createGearButton(Game.getPlayer().getRing(), xOffSet + 587, yOffSet + 558);
            PauseMenu.add(RingBtn);
            PauseMenu.setComponentZOrder(RingBtn, 1);
        }
        if(Game.getPlayer().getBoots() != null)
        {
            if(BootsBtn != null)
            {
                PauseMenu.remove(BootsBtn);
            }
            BootsBtn = createGearButton(Game.getPlayer().getBoots(), xOffSet + 703, yOffSet + 558);
            PauseMenu.add(BootsBtn);
            PauseMenu.setComponentZOrder(BootsBtn, 1);
        }
        if(Game.getPlayer().getTome() != null)
        {
            if(TomeBtn != null)
            {
                PauseMenu.remove(TomeBtn);
            }
            TomeBtn = createGearButton(Game.getPlayer().getTome(), xOffSet + 703, yOffSet + 443);
            PauseMenu.add(TomeBtn);
            PauseMenu.setComponentZOrder(TomeBtn, 1);
        }
        
        if(Game.getPlayer().getSpell(0) != null)
        {
            if(Spell1Btn == null)
            {
                Spell1Btn = createSpellButton(0, xOffSet + 90, yOffSet + 537);
                PauseMenu.add(Spell1Btn);
                PauseMenu.setComponentZOrder(Spell1Btn, 1);
            }
            else
            {
                Spell1Btn.setIcon(new ImageIcon(Game.getPlayer().getSpell(0).getIcon()));
            }
        }
        if(Game.getPlayer().getSpell(1) != null)
        {
            if(Spell2Btn == null)
            {
                Spell2Btn = createSpellButton(1, xOffSet + 178, yOffSet + 537);
                PauseMenu.add(Spell2Btn);
                PauseMenu.setComponentZOrder(Spell2Btn, 1);
            }
            else
            {
                Spell2Btn.setIcon(new ImageIcon(Game.getPlayer().getSpell(1).getIcon()));
            }
        }
        if(Game.getPlayer().getSpell(2) != null)
        {
            if(Spell3Btn == null)
            {
                Spell3Btn = createSpellButton(2, xOffSet + 266, yOffSet + 537);
                PauseMenu.add(Spell3Btn);
                PauseMenu.setComponentZOrder(Spell3Btn, 1);
            }
            else
            {
                Spell3Btn.setIcon(new ImageIcon(Game.getPlayer().getSpell(2).getIcon()));
            }
        }
        if(Game.getPlayer().getSpell(3) != null)
        {
            if(Spell4Btn == null)
            {
                Spell4Btn = createSpellButton(3, xOffSet + 352, yOffSet + 537);
                PauseMenu.add(Spell4Btn);
                PauseMenu.setComponentZOrder(Spell4Btn, 1);
            }
            else
            {
                Spell4Btn.setIcon(new ImageIcon(Game.getPlayer().getSpell(3).getIcon()));
            }
        }
    }
    
    private void pauseGame()
    {
        this.Paused = true;
        
        this.updateMenuComponents();
        
        BackgroundLabel.setIcon(new ImageIcon(GameImg));
        
        if(Game.getPlayer().getSkillPoints() > 0)
        {
            TalentMenu.add(BackgroundLabel);
            TalentMenu.setComponentZOrder(BackgroundLabel, TalentMenu.getComponentCount() - 1);
            this.MenuLayout.show(MenuCards, "TalentMenu");
        }
        else
        {
            PauseMenu.add(BackgroundLabel);
            PauseMenu.setComponentZOrder(BackgroundLabel, PauseMenu.getComponentCount() - 1);
            this.MenuLayout.show(MenuCards, "PauseMenu");
        }
        
        this.MenuCards.setVisible(true);
    }
    
    private void unPauseGame()
    {
        this.Paused = false;
        this.PlayerTabHoverLabel.setVisible(false);
        this.SettingsTabHoverLabel.setVisible(false);
        this.TalentTabHoverLabel.setVisible(false);        
        this.MenuCards.setVisible(false);
    }
    
    private void createMap()
    {
        BufferedImage mapBufImg =(BufferedImage)createImage(341, 241);
        Graphics2D gtemp = mapBufImg.createGraphics();
        
        Color brownInk = new Color(124, 82, 34);
        Color crimson = new Color(150, 0, 0);
        Color gold = new Color(255, 150, 0);
        
        gtemp.setBackground(new Color(221, 162, 93));
        gtemp.clearRect(0, 0, 341, 241);
        
        for(int i = 0; i < 5; i++)
        {
            for(int j = 0; j < 5; j++)
            {
                if(Game.getRoom(i, j) != null)
                {
                    if(Game.getRoom(i, j).isBossRoom())
                    {
                        gtemp.setColor(crimson);
                    }
                    else if(Game.getRoom(i, j).isShop() && Game.getRoom(i, j).wasVisited())
                    {
                        gtemp.setColor(gold);
                    }
                    else
                    {
                        gtemp.setColor(brownInk);
                    }
                    
                    if(Game.getRoom(i, j).wasVisited())
                    {
                        gtemp.fillRect(j * 70, i * 50, 60, 40);
                        if(Game.getRoomsI() == i && Game.getRoomsJ() == j)
                        {
                            gtemp.drawImage(PlayerHead, j * 70 + 30 - (PlayerHead.getWidth(null)/2), 
                                    i * 50 + 20 - (PlayerHead.getHeight(null)/2), null);
                        }
                    }
                    else
                    {
                        if(i > 0 )
                        {
                            if(Game.getRoom(i - 1, j) != null)
                            {
                                if(Game.getRoom(i - 1, j).wasVisited())
                                {
                                    gtemp.drawRect(j * 70, i * 50, 60, 40);
                                    gtemp.drawRect(j * 70 + 1, i * 50 + 1, 58, 38);
                                }
                            }
                        }
                        
                        if(i < 4 )
                        {
                            if(Game.getRoom(i + 1, j) != null)
                            {
                                if(Game.getRoom(i + 1, j).wasVisited())
                                {
                                    gtemp.drawRect(j * 70, i * 50, 60, 40);
                                    gtemp.drawRect(j * 70 + 1, i * 50 + 1, 58, 38);
                                }
                            }
                        }
                        
                        if(j > 0 )
                        {
                            if(Game.getRoom(i, j - 1) != null)
                            {
                                if(Game.getRoom(i, j - 1).wasVisited())
                                {
                                    gtemp.drawRect(j * 70, i * 50, 60, 40);
                                    gtemp.drawRect(j * 70 + 1, i * 50 + 1, 58, 38);
                                }
                            }
                        }
                        
                        if(j < 4 )
                        {
                            if(Game.getRoom(i, j + 1) != null)
                            {
                                if(Game.getRoom(i, j + 1).wasVisited())
                                {
                                    gtemp.drawRect(j * 70, i * 50, 60, 40);
                                    gtemp.drawRect(j * 70 + 1, i * 50 + 1, 58, 38);
                                }
                            }
                        }
                    }
                }
            }
        }
        
        MapLabel.setIcon(new ImageIcon(mapBufImg));
        
        gtemp.dispose();
    }
    
    private JButton createGearButton(Gear gear, int x, int y)
    {
        JButton btn = new JButton(new ImageIcon(gear.getSprite()));
        btn.setBorder(BorderFactory.createEmptyBorder());
        btn.setContentAreaFilled(false);
        btn.setBounds(x, y, 67, 67);
        btn.setFocusable(false);
        btn.addMouseListener(new MouseAdapter() {
        public void mouseEntered(MouseEvent evt) {
                HoverLabel.setBounds(x - 2, y - 2, 73, 73);
                HoverLabel.setVisible(true);
                PauseMenu.setComponentZOrder(HoverLabel, 2);
                printPauseGearText(gear);
            }

            public void mouseExited(MouseEvent evt) {
                GearText.setText("");
                HoverLabel.setVisible(false);
            }
        });
        return btn;
    }
    
    private JButton createSpellButton(int spellNumber, int x, int y)
    {
        JButton btn = new JButton(new ImageIcon(Game.getPlayer().getSpell(spellNumber).getIcon()));
        btn.setBorder(BorderFactory.createEmptyBorder());
        btn.setContentAreaFilled(false);
        btn.setBounds(x, y, 68, 68);
        btn.setFocusable(false);
        btn.addMouseListener(new MouseAdapter() {
        public void mouseEntered(MouseEvent evt) {
                HoverLabel.setBounds(x - 2, y - 2, 73, 73);
                HoverLabel.setVisible(true);
                PauseMenu.setComponentZOrder(HoverLabel, 2);
                printPauseSpellText(spellNumber);
            }

            public void mouseExited(MouseEvent evt) {
                SpellText.setText("");
                HoverLabel.setVisible(false);
            }
        });

        return btn;
    }
    
    private void printPausePlayerStats()
    {
        int xOffSet = (ScreenWidth - PauseMenuImg.getWidth(null)) / 2;
        int yOffSet = (ScreenHeight - PauseMenuImg.getHeight(null)) / 2;
        
        LeftStatText.setText("");
        RightStatText.setText("");
        
        Style style = LeftStatText.getStyle("Style");
        StyleConstants.setForeground(style, Color.BLACK);
        
        String statInfo =  "";
        
        statInfo += "Level " + Game.getPlayer().getLevel() + "\n";
        statInfo += "Health: " + Game.getPlayer().getCurrentHealth() + "/" + Game.getPlayer().getMaxHealth() + "\n";
        statInfo += "Experience: " + Game.getPlayer().getExperience() + "/" + Game.getPlayer().getNextLevel() + "\n";
        statInfo += "Skill points: " + Game.getPlayer().getSkillPoints() + "\n";
        statInfo += "Gold: " + Game.getGold() + "\n\n";
        statInfo += "Vitality: " + Game.getPlayer().getVitality() + "\n";
        statInfo += "+" + Game.getPlayer().getBonusHealth() + " Health" + "\n\n";
        statInfo += "Intellect: " + Game.getPlayer().getIntellect() + "\n";
        statInfo += "+" + Game.getPlayer().getBonusSpellDamage() + "% Spell Damage\n\n";
        statInfo += "MoveSpeed: " + Game.getPlayer().getMoveSpeed();
        try {
            LeftStatText.getStyledDocument().insertString(
                    LeftStatText.getStyledDocument().getLength(), statInfo, style);
        }catch(BadLocationException e){}
        
        style = RightStatText.getStyle("Style");
        StyleConstants.setForeground(style, Color.BLACK);
        
        statInfo =  "";
        statInfo += "Flame: " + Game.getPlayer().getFlame()  + "\n";
        statInfo += "+" + Game.getPlayer().getBonusFlameDamage() + "% Fire Damage" + "\n";
        statInfo += Game.getPlayer().getBurnChance() + "% Burn Chance" + "\n";
        statInfo += Game.getPlayer().getBurnDamage() + " Burn Damage" + "\n\n";
        statInfo += "Frost: " + Game.getPlayer().getFrost()  + "\n";
        statInfo += "+" + Game.getPlayer().getBonusFrostDamage() + "% Ice Damage" + "\n";
        statInfo += Game.getPlayer().getChillChance() + "% Chill Chance" + "\n\n";
        statInfo += "Void: " + Game.getPlayer().getVoid()  + "\n";
        statInfo += "+" + Game.getPlayer().getBonusVoidDamage() + "% Void Damage" + "\n";
        
        try {
            RightStatText.getStyledDocument().insertString(
                    RightStatText.getStyledDocument().getLength(), statInfo, style);
        }catch(BadLocationException e){}
    }
    
    private void printPauseSpellText(int spellNumber)
    {
        Color purple = new Color(100, 0, 150);
        Color blue = new Color(0, 0, 250);
        Color orange = new Color(230, 80, 0);
        
        Style style = GearText.getStyle("Style");
        
        Spell spell = Game.getPlayer().getSpell(spellNumber);
        
        if(spell.getRarity() == 2)
        {
            StyleConstants.setForeground(style, blue);
        }
        else if(spell.getRarity() == 3)
        {
            StyleConstants.setForeground(style, purple);
        }
        else if(spell.getRarity() == 4)
        {
            StyleConstants.setForeground(style, orange);
        }
        
        String spellInfo = spell.getSpellName() + "\n";
        
        try {
            SpellText.getStyledDocument().insertString(
                    SpellText.getStyledDocument().getLength(), spellInfo, style);
        }catch(BadLocationException e){}
        
        StyleConstants.setForeground(style, Color.BLACK);
        
        spellInfo = "Cooldown: ";
        if(spell.getResetTime() < 60)
        {
            spellInfo += "Instant\n";
        }
        else
        {
            spellInfo += Integer.toString(1 + spell.getResetTime()/60) + " seconds\n";
        }
        
        try {
            SpellText.getStyledDocument().insertString(
                    SpellText.getStyledDocument().getLength(), spellInfo, style);
        }catch(BadLocationException e){}
        
        spellInfo += Integer.toString(spell.getResetTime()/60) + " seconds\n";
        
        spellInfo = Game.getPlayer().getSpellDescription(spellNumber) + "\n";
        
        try {
            SpellText.getStyledDocument().insertString(
                    SpellText.getStyledDocument().getLength(), spellInfo, style);
        }catch(BadLocationException e){}
    }
    
    private void printPauseGearText(Gear gear)
    {
        Color purple = new Color(100, 0, 150);
        Color blue = new Color(0, 0, 250);
        Color orange = new Color(230, 80, 0);
        
        Style style = GearText.getStyle("Style");
        
        if(gear.getRarity() == 2)
        {
            StyleConstants.setForeground(style, blue);
        }
        else if(gear.getRarity() == 3)
        {
            StyleConstants.setForeground(style, purple);
        }
        else if(gear.getRarity() == 4)
        {
            StyleConstants.setForeground(style, orange);
        }
        else
        {
            StyleConstants.setForeground(style, Color.WHITE);
        }
        
        String gearInfo = gear.getItemName() + "\n";
        
        try {
            GearText.getStyledDocument().insertString(
                    GearText.getStyledDocument().getLength(), gearInfo, style);
        }catch(BadLocationException e){}
        
        StyleConstants.setForeground(style, Color.BLACK);
        
        gearInfo = "Level " + Integer.toString(gear.getLevel()) + "\n";
            try {
            GearText.getStyledDocument().insertString(
                    GearText.getStyledDocument().getLength(), gearInfo, style);
        }catch(BadLocationException e){}
        
        if(gear.getIntellect() > 0)
        {
            gearInfo = "+" + Integer.toString(gear.getIntellect()) + " Intellect\n";
                try {
                GearText.getStyledDocument().insertString(
                        GearText.getStyledDocument().getLength(), gearInfo, style);
            }catch(BadLocationException e){}
        }
        if(gear.getVitality() > 0)
        {
            gearInfo = "+" + Integer.toString(gear.getVitality()) + " Vitality\n";
                try {
                GearText.getStyledDocument().insertString(
                        GearText.getStyledDocument().getLength(), gearInfo, style);
            }catch(BadLocationException e){}
        }
        if(gear.getFlame() > 0)
        {
            gearInfo = "+" + Integer.toString(gear.getFlame()) + " Flame\n";
                try {
                GearText.getStyledDocument().insertString(
                        GearText.getStyledDocument().getLength(), gearInfo, style);
            }catch(BadLocationException e){}
        }
        if(gear.getFrost() > 0)
        {
            gearInfo = "+" + Integer.toString(gear.getFrost()) + " Frost\n";
                try {
                GearText.getStyledDocument().insertString(
                        GearText.getStyledDocument().getLength(), gearInfo, style);
            }catch(BadLocationException e){}
        }
        if(gear.getDark() > 0)
        {
            gearInfo = "+" + Integer.toString(gear.getDark()) + " Void\n";
                try {
                GearText.getStyledDocument().insertString(
                        GearText.getStyledDocument().getLength(), gearInfo, style);
            }catch(BadLocationException e){}
        }
        if(gear.getMoveSpeed() > 0)
        {
            gearInfo = "+" + Integer.toString(gear.getMoveSpeed()) + " Move Speed\n";
                try {
                GearText.getStyledDocument().insertString(
                        GearText.getStyledDocument().getLength(), gearInfo, style);
            }catch(BadLocationException e){}
        }
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
        
        if(key == KeyEvent.VK_P || key == KeyEvent.VK_ESCAPE || key == KeyEvent.VK_M)
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
    
    @Override
    public void paintComponent(Graphics g) {
        /*
        The paintComponent override paints all of the game objects onto the game
        window. Since they are painted ontop of previous objects, they are printed
        in a specific order to achieve the correct layering.
        */
        if(!InGame || Paused)
            return;
        
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
        Font spellNameFont = (new Font("Arial Bold", Font.BOLD, 18));
        Font spellCoolDownFont = (new Font("Arial Bold", Font.BOLD, 32));
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
            Font pauseTutorialFont = (new Font("Palatino Linotype", Font.BOLD, 10));
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
        
        if(MissingHealth > Game.getPlayer().getCurrentHealth())
        {
            if(MissingHealth > Game.getPlayer().getMaxHealth())
            {
                MissingHealth = Game.getPlayer().getMaxHealth();
            }
            
            int missingWidth = 211 * (MissingHealth - Game.getPlayer().getCurrentHealth()) 
                    / Game.getPlayer().getMaxHealth();
            g2d.setColor(new Color(200, 0, 0));
            g2d.fillRect(112 + xShift + hpWidth + 1, 60 + yShift, missingWidth, 12);
            MissingHealth -= 2;
        }
        else
        {
            MissingHealth = Game.getPlayer().getCurrentHealth();
        }
        
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
        
        BufferedImage gameImg = bufImg.getSubimage(xShift, yShift, (int)scaledWidth, (int)scaledHeight);
        
        Graphics2D scaledGraphics = (Graphics2D) g;
        scaledGraphics = GameImg.createGraphics();
        scaledGraphics.scale(scale, scale);
        scaledGraphics.drawImage(bufImg, -xShift, -yShift, null);
        
        gtemp.drawImage(GameImg, 0, 0, this);
        
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
        Font itemNameFont = (new Font("Palatino Linotype", Font.BOLD, 14));
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
            rarityDisplacement += 18;
        }

        if(darkVal != 0)
        {
            this.printStatInfo(darkVal, rarityDisplacement, "Void", gear);
            rarityDisplacement += 18;
        }
        if(frostVal != 0)
        {
            this.printStatInfo(frostVal, rarityDisplacement, "Frost", gear);
            rarityDisplacement += 18;
        }
        if(flameVal != 0)
        {
            this.printStatInfo(flameVal, rarityDisplacement, "Flame", gear);
            rarityDisplacement += 18;
        }
        if(vitVal != 0)
        {
            this.printStatInfo(vitVal, rarityDisplacement, "Vitality", gear);
            rarityDisplacement += 18;
        }
        if(intVal != 0)
        {
            this.printStatInfo(intVal, rarityDisplacement, "Intellect", gear);
            rarityDisplacement += 18;
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
            g2d.setColor(Color.WHITE);
        }

        Font itemNameFont = (new Font("Palatino Linotype", Font.BOLD, 18));
        g2d.setFont(itemNameFont);
        FontMetrics metrics = g2d.getFontMetrics(itemNameFont);

        g2d.drawString( gear.getItemName(), gear.getCenterX() - 
                metrics.stringWidth( gear.getItemName())/2, 
                gear.getY() - rarityDisplacement);
    }
    
    public void printStatInfo(int statVal, int rarityDisplacement, String statName,
            Gear gear)
    {
        Color green =  new Color(0, 255, 50);
        Color red = new Color(200, 0, 0);
        
        Font itemNameFont = (new Font("Arial Bold", Font.BOLD, 14));
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
        game.resourcesInit();
        game.startGame();
        game.initializeMenus();
        game.newGameInit();
        game.GameWindow.setVisible(true);
        //game.musicThreadLoop();
        game.timerLoop();
    }
}