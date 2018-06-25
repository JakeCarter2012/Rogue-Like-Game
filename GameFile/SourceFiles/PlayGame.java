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
            if(col.normalCollision(Player, Rooms.get(RoomsI).get(RoomsJ).getWall(i)))
            {
                
            }
        }
        //now update the player's position
        Player.updatePlayer(generalCol, horizontalCol, verticalCol);
        
        //now test for remaining collisions based on where the player ens up standing
        
        //now test for collisions for enemies
        
        //now test for collisions for projectiles
        
        //now update traps.
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
        
    }
    
    public static void main(String[] args) {
        PlayGame game = new PlayGame();
        game.init();
        //game.musicThreadLoop();
        game.mainMenu();
    }
}
