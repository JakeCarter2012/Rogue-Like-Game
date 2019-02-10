package SourceFiles;

import SourceFiles.GameLogic.CollisionDetector;
import SourceFiles.GameObjects.Animations.Animation;
import SourceFiles.GameObjects.MovingObjects.Enemies.DartGoblin;
import SourceFiles.GameObjects.MovingObjects.Enemies.MovingEnemy;
import SourceFiles.GameObjects.MovingObjects.Enemies.SpearGoblin;
import SourceFiles.GameObjects.MovingObjects.Player.ProjectileSpell;
import SourceFiles.GameObjects.MovingObjects.Player.Spell;
import SourceFiles.GameObjects.MovingObjects.Player.WizardPlayer;
import SourceFiles.GameObjects.StationaryObjects.Door;
import SourceFiles.GameObjects.StationaryObjects.GearObjects.Boots;
import SourceFiles.GameObjects.StationaryObjects.GearObjects.Neck;
import SourceFiles.GameObjects.StationaryObjects.GearObjects.Ring;
import SourceFiles.GameObjects.StationaryObjects.GearObjects.Tome;
import SourceFiles.GameObjects.StationaryObjects.Wall;
import SourceFiles.Room.Room;
import java.awt.Image;
import java.io.File;
import java.util.Random;
import javafx.util.Pair;
import java.util.ArrayList;
import javax.imageio.ImageIO;


public class GameInstance {
    
    //Array of arrays used to store Rooms on a grid
    private Room[][] Rooms;
    private int RoomsI, RoomsJ;
    private int ScreenWidth, ScreenHeight;
    private final int GameWidth = 1280, GameHeight = 1280;
    private final int ShadowHeight = 60;
    private boolean levelFinished;
    private WizardPlayer Player;
    private int Money, FloorLevel;
    private boolean Transition;
    private int RoomChangeI, RoomChangeJ;
    
    private Image IceShardsImg, IceShardsIcon, FireBallImg, FireBallIcon, VoidWaveImg,
            VoidWaveIcon, IceShardsShadow,
            FireBallShadow, SmallProjectileShadow, VoidWaveShadow;
    private Image[] IceShardsBreak, VoidWaveEnd, FireBallEnd, BurningImgs;
    private Spell FireBall, IceShards, VoidWave, WildFire, Blizzard, BlackHole, Meteor, Comet, FrostFlame;//FrostFlame:: Blue Fire
    private Animation Burning;
    
    private Image TopWallLeftImg, TopWallRightImg, TopWallMidImg,
            LeftWallTopImg, LeftWallBottomImg, LeftWallMidImg,
            RightWallTopImg, RightWallBottomImg, RightWallMidImg,
            BottomWallLeftImg, BottomWallRightImg, BottomWallMidImg;
    private Wall[] Walls;
    private Wall TopWallMid, LeftWallMid, BottomWallMid, RightWallMid;
    private Image[] TopDoorImgs, LeftDoorImgs, BottomDoorImgs, RightDoorImgs;
    private Door TopDoor, LeftDoor, BottomDoor, RightDoor;
    
    private Image WizRightForwardAttack, WizRightForward, WizRightBackAttack,
            WizRightAttack, WizRight, WizLeftForwardAttack, WizLeftForward,
            WizLeftBackwardAttack, WizLeftAttack, WizLeft, WizForwardAttack, 
            WizForward, WizBackAttack, WizBack;
    private Image[] PlayerShadow;
    
    private Image SmallProjectileGreen, GoblinShadow;
    private Image[] SmallGreenProjectileEnd;
    private Image[] SpearGoblinRight, SpearGoblinLeft, DartGoblinLeft, DartGoblinRight,
            DartGoblinLeftAttack, DartGoblinRightAttack;
    
    private Image[] RubyRing, AmethystRing, SaphireRing, EmeraldRing, RubyNeck, 
            SaphireNeck, EmeraldNeck, AmethystNeck;
    private Image FlameTome, FrostTome, VoidTome, FlameBoots, FrostBoots, VoidBoots;
    
    public GameInstance(int screenWidth, int screenHeight)
    {
        this.ScreenWidth = screenWidth;
        this.ScreenHeight = screenHeight;
        
        this.resourcesInit();
        this.newGameInit();
        this.floorRandomizer();
    }
    
    private void resourcesInit()
    {
        /*
        Initializes all images/ constants, such as walls, used in the game.
        Images are initialized in the beginning and declared globally;
        instead of reading an image every time an object is created, images are 
        loaded only once to increase performance.
        */
        try{
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
            
            WizRightForwardAttack = ImageIO.read(new File("Resources" + File.separator + "WizRightForwardAttack.png"));
            WizRightForward = ImageIO.read(new File("Resources" + File.separator + "WizRightForward.png"));
            WizRightBackAttack = ImageIO.read(new File("Resources" + File.separator + "WizRightBackAttack.png"));
            WizRightAttack = ImageIO.read(new File("Resources" + File.separator + "WizRightAttack.png"));
            WizRight = ImageIO.read(new File("Resources" + File.separator + "WizRight.png"));
            WizLeftForwardAttack = ImageIO.read(new File("Resources" + File.separator + "WizLeftForwardAttack.png"));
            WizLeftForward = ImageIO.read(new File("Resources" + File.separator + "WizLeftForward.png"));
            WizLeftBackwardAttack = ImageIO.read(new File("Resources" + File.separator + "WizLeftBackAttack.png"));
            WizLeftAttack = ImageIO.read(new File("Resources" + File.separator + "WizLeftAttack.png"));
            WizLeft = ImageIO.read(new File("Resources" + File.separator + "WizLeft.png"));
            WizForwardAttack = ImageIO.read(new File("Resources" + File.separator + "WizForwardAttack.png"));
            WizForward = ImageIO.read(new File("Resources" + File.separator + "WizForward.png"));
            WizBackAttack = ImageIO.read(new File("Resources" + File.separator + "WizBackAttack.png"));
            WizBack = ImageIO.read(new File("Resources" + File.separator + "WizBack.png"));
            
            PlayerShadow = new Image[5];
            PlayerShadow[0] = ImageIO.read(new File("Resources" + File.separator + "PlayerShadow1.png"));
            PlayerShadow[1] = ImageIO.read(new File("Resources" + File.separator + "PlayerShadow2.png"));
            PlayerShadow[2] = ImageIO.read(new File("Resources" + File.separator + "PlayerShadow3.png"));
            PlayerShadow[3] = ImageIO.read(new File("Resources" + File.separator + "PlayerShadow4.png"));
            PlayerShadow[4] = ImageIO.read(new File("Resources" + File.separator + "PlayerShadow5.png"));
            
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
            
            GoblinShadow = ImageIO.read(new File("Resources" + File.separator + "GoblinShadow.png"));
            
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
            System.out.print(e.getStackTrace() + " Error loading game resources \n");
        }
        
        //Now constant spells/objects that never change are created
        IceShards = new ProjectileSpell("Ice Shards", 5,10, 30, false, true, false, 30, IceShardsImg, IceShardsIcon, IceShardsShadow, IceShardsBreak);
        FireBall = new ProjectileSpell("Fire Ball", 5,10, 30, true, false, false, 30, FireBallImg, FireBallIcon, FireBallShadow, FireBallEnd);
        VoidWave = new ProjectileSpell("Void Wave", 1,10, 119, false, false, true, 0, VoidWaveImg, VoidWaveIcon, VoidWaveShadow, VoidWaveEnd);
        
        Burning = new Animation(0, 0, 0, BurningImgs, 3, true);
        
        Walls = new Wall[8];
        Walls[0] = new Wall(0, 0, 579, 128 - ShadowHeight, TopWallLeftImg);
        Walls[1] = new Wall(700, 0, 580, 128 - ShadowHeight, TopWallRightImg);
        Walls[2] = new Wall(0, 0, 128, 528, LeftWallTopImg);
        Walls[3] = new Wall(0, 721, 128, 559, LeftWallBottomImg);
        Walls[4] = new Wall(0, 1152, 580, 128, BottomWallLeftImg);
        Walls[5] = new Wall(701, 1152, 579, 128, BottomWallRightImg);
        Walls[6] = new Wall(1152, 0, 128, 528, RightWallTopImg);
        Walls[7] = new Wall(1152, 720, 128, 560, RightWallBottomImg);
        
        TopDoor = new Door(579, 0, 121, 128 - ShadowHeight, TopDoorImgs);
        LeftDoor = new Door(0, 528, 128, 193, LeftDoorImgs);
        BottomDoor = new Door(580, 1152, 121, 128, BottomDoorImgs);
        RightDoor = new Door(1152, 528, 128, 192, RightDoorImgs);
        
        TopWallMid = new Wall(579, 0, 121, 60, TopWallMidImg);
        LeftWallMid = new Wall(0, 528, 128, 193, LeftWallMidImg);
        BottomWallMid = new Wall(580, 1152, 121, 128, BottomWallMidImg);
        RightWallMid = new Wall(1152, 528, 128, 192, RightWallMidImg);
    }
    
    public void newGameInit()
    {
        /*
        newGameInit is used for whenevr a new game is started. It sets all
        game values to their starting values and creates the player's character.
        */
        
        this.Money = 0;
        this.FloorLevel = 1;
        this.Transition = false;
        this.RoomChangeI = this.RoomChangeJ = 0;
        this.Player = new WizardPlayer(609, 576, WizRightForwardAttack, 
                WizRightForward, WizRightBackAttack, WizRightAttack, WizRight, 
                WizLeftForwardAttack, WizLeftForward, WizLeftBackwardAttack, 
                WizLeftAttack, WizLeft, WizForwardAttack, WizForward, WizBackAttack, 
                WizBack, PlayerShadow);
        
        this.Player.addNewSpell(IceShards);
        this.Player.addNewSpell(FireBall);
        this.Player.addNewSpell(VoidWave);
    }
    
    private void floorRandomizer()
    {
        Rooms = new Room[5][5];
        this.RoomsI = 2;
        this.RoomsJ = 2;
        this.Rooms[RoomsI][RoomsJ] = new Room();
        
        ArrayList<Pair<Integer, Integer>> createdRooms = new ArrayList<Pair<Integer, Integer>>();
        createdRooms.add(new Pair(RoomsI, RoomsJ));
        
        Random rnd = new Random();
        
        if(rnd.nextInt(2) == 0)
        {
            Rooms[RoomsI - 1][RoomsJ] = new Room();
            createdRooms.add(new Pair(RoomsI - 1, RoomsJ));
        }
        else
        {
            Rooms[RoomsI][RoomsJ - 1] = new Room();
            createdRooms.add(new Pair(RoomsI, RoomsJ - 1));
        }
        
        int totalRooms = this.FloorLevel + 5;
        int selectedRoom, selectedI, selectedJ, selectedDoor;
        
        while(createdRooms.size() < totalRooms)
        {
            selectedRoom = rnd.nextInt(createdRooms.size());
            selectedI = createdRooms.get(selectedRoom).getKey();
            selectedJ = createdRooms.get(selectedRoom).getValue();
            selectedDoor = rnd.nextInt(4);
            
            if(selectedDoor == 0 && selectedI != 0)//top
            {
                if(Rooms[selectedI - 1][selectedJ] == null)
                {
                    Rooms[selectedI - 1][selectedJ] = new Room();
                    createdRooms.add(new Pair(selectedI - 1, selectedJ));
                }
            }
            else if(selectedDoor == 1 && selectedI != 4)//bottom
            {
                if(Rooms[selectedI + 1][selectedJ] == null)
                {
                    Rooms[selectedI + 1][selectedJ] = new Room();
                    createdRooms.add(new Pair(selectedI + 1, selectedJ));
                }
            }
            else if(selectedDoor == 2 && selectedJ != 0)//left
            {
                if(Rooms[selectedI][selectedJ - 1] == null)
                {
                    Rooms[selectedI][selectedJ - 1] = new Room();
                    createdRooms.add(new Pair(selectedI, selectedJ - 1));
                }
            }
            else if(selectedDoor == 3 && selectedJ != 4)//right
            {
                if(Rooms[selectedI][selectedJ + 1] == null)
                {
                    Rooms[selectedI][selectedJ + 1] = new Room();
                    createdRooms.add(new Pair(selectedI, selectedJ + 1));
                }
            }
        }
        
        this.setBossRoom();
        this.fillRooms();
    }
    
    private void fillRooms()
    {
        Random rnd = new Random();
        boolean lootRoom = false, shopRoom = false;
        int remainingRooms;
        if(this.FloorLevel == 10)
        {
            remainingRooms = 13;
        }
        else
        {
            remainingRooms = this.FloorLevel + 3;
        }
        
        for(int i = 0; i < 5; i++)
        {
            for(int j = 0; j < 5; j++)
            {
                if(Rooms[i][j] != null)
                {
                    if(i == 2 && j == 2)
                    {
                        this.startingRoom();
                    }
                    else if(Rooms[i][j].isBossRoom())
                    {
                        this.addBoss(i, j);
                    }
                    /*
                    else if(!lootRoom)
                    {
                        if(rnd.nextInt(remainingRooms) == 0)
                        {
                            
                        }
                        else if(!shopRoom && remainingRooms == 2)
                        {
                            
                        }
                        else
                        {
                            this.addRandomEnemies(i, j);
                        }
                        remainingRooms--;
                    }
                    else if(!shopRoom)
                    {
                        if(rnd.nextInt(remainingRooms) == 0)
                        {
                            
                        }
                        else
                        {
                            this.addRandomEnemies(i, j);
                        }
                        remainingRooms--;
                    }
                    */
                    else
                    {
                        this.addRandomEnemies(i, j);
                        remainingRooms--;
                    }
                    
                    addDoors(i, j);
                }
            }
        }
    }
    
    private void addBoss(int i, int j)
    {
        //Enemies in boss room are temporary until I create assets for a boss
        Rooms[i][j].addEnemy(new SpearGoblin(500, 400, 1, this.SpearGoblinLeft,
                this.SpearGoblinRight, GoblinShadow));
        Rooms[i][j].addEnemy(new SpearGoblin(700, 400, 1, this.SpearGoblinLeft,
                this.SpearGoblinRight, GoblinShadow));
        Rooms[i][j].addEnemy(new DartGoblin(400, 200, 1, this.DartGoblinLeft, 
                this.DartGoblinRight, this.DartGoblinLeftAttack, 
                this.DartGoblinRightAttack, GoblinShadow, this.SmallProjectileGreen, 
                this.SmallProjectileShadow, this.SmallGreenProjectileEnd));
        Rooms[i][j].addEnemy(new DartGoblin(800, 200, 1, this.DartGoblinLeft, 
                this.DartGoblinRight, this.DartGoblinLeftAttack, 
                this.DartGoblinRightAttack, GoblinShadow, this.SmallProjectileGreen, 
                this.SmallProjectileShadow, this.SmallGreenProjectileEnd));
    }
    
    private void addRandomEnemies(int i, int j)
    {
        Random rnd = new Random();
        int randomRoom = rnd.nextInt(3);
        if(randomRoom == 0)
        {
            this.enemyRoom1(i, j);
        }
        else if(randomRoom == 1)
        {
            this.enemyRoom2(i, j);
        }
        else
        {
            this.enemyRoom3(i, j);
        }
    }
    
    private void enemyRoom1(int i, int j)
    {
        Rooms[i][j].addEnemy(new SpearGoblin(400, 400, 1, this.SpearGoblinLeft,
                this.SpearGoblinRight, GoblinShadow));
        Rooms[i][j].addEnemy(new SpearGoblin(800, 800, 1, this.SpearGoblinLeft,
                this.SpearGoblinRight, GoblinShadow));
        Rooms[i][j].addEnemy(new DartGoblin(600, 600, 1, this.DartGoblinLeft, 
                this.DartGoblinRight, this.DartGoblinLeftAttack, 
                this.DartGoblinRightAttack, GoblinShadow, this.SmallProjectileGreen, 
                this.SmallProjectileShadow, this.SmallGreenProjectileEnd));
    }
    
    private void enemyRoom2(int i, int j)
    {
        Rooms[i][j].addEnemy(new SpearGoblin(600, 600, 1, this.SpearGoblinLeft,
                this.SpearGoblinRight, GoblinShadow));
        Rooms[i][j].addEnemy(new DartGoblin(400, 400, 1, this.DartGoblinLeft, 
                this.DartGoblinRight, this.DartGoblinLeftAttack, 
                this.DartGoblinRightAttack, GoblinShadow, this.SmallProjectileGreen, 
                this.SmallProjectileShadow, this.SmallGreenProjectileEnd));
        Rooms[i][j].addEnemy(new DartGoblin(800, 400, 1, this.DartGoblinLeft, 
                this.DartGoblinRight, this.DartGoblinLeftAttack, 
                this.DartGoblinRightAttack, GoblinShadow, this.SmallProjectileGreen, 
                this.SmallProjectileShadow, this.SmallGreenProjectileEnd));
    }
    
    private void enemyRoom3(int i, int j)
    {
        Rooms[i][j].addEnemy(new DartGoblin(400, 400, 1, this.DartGoblinLeft, 
                this.DartGoblinRight, this.DartGoblinLeftAttack, 
                this.DartGoblinRightAttack, GoblinShadow, this.SmallProjectileGreen, 
                this.SmallProjectileShadow, this.SmallGreenProjectileEnd));
        Rooms[i][j].addEnemy(new DartGoblin(400, 800, 1, this.DartGoblinLeft, 
                this.DartGoblinRight, this.DartGoblinLeftAttack, 
                this.DartGoblinRightAttack, GoblinShadow, this.SmallProjectileGreen, 
                this.SmallProjectileShadow, this.SmallGreenProjectileEnd));
        Rooms[i][j].addEnemy(new DartGoblin(800, 400, 1, this.DartGoblinLeft, 
                this.DartGoblinRight, this.DartGoblinLeftAttack, 
                this.DartGoblinRightAttack, GoblinShadow, this.SmallProjectileGreen, 
                this.SmallProjectileShadow, this.SmallGreenProjectileEnd));
        Rooms[i][j].addEnemy(new DartGoblin(800, 800, 1, this.DartGoblinLeft, 
                this.DartGoblinRight, this.DartGoblinLeftAttack, 
                this.DartGoblinRightAttack, GoblinShadow, this.SmallProjectileGreen, 
                this.SmallProjectileShadow, this.SmallGreenProjectileEnd));
    }
    
    private void addDoors(int i, int j)
    {
        //top doorway
        if(Rooms[i][j].isBossRoom())
        {
            Rooms[i][j].addDoor(TopDoor);
        }
        else if(i == 0)
        {
            Rooms[i][j].addWall(TopWallMid);
        }
        else if(Rooms[i - 1][j] != null)
        {
            Rooms[i][j].addDoor(TopDoor);
        }
        else
        {
            Rooms[i][j].addWall(TopWallMid);
        }

        //bottom doorway
        if(i == 4)
        {
            Rooms[i][j].addWall(BottomWallMid);
        }
        else if(Rooms[i + 1][j] != null)
        {
            Rooms[i][j].addDoor(BottomDoor);
        }
        else
        {
            Rooms[i][j].addWall(BottomWallMid);
        }

        //right doorway
        if(j == 4)
        {
            Rooms[i][j].addWall(RightWallMid);
        }
        else if(Rooms[i][j + 1] != null)
        {
            Rooms[i][j].addDoor(RightDoor);
        }
        else
        {
            Rooms[i][j].addWall(RightWallMid);
        }

        //left doorway
        if(j == 0)
        {
            Rooms[i][j].addWall(LeftWallMid);
        }
        else if(Rooms[i][j - 1] != null)
        {
            Rooms[i][j].addDoor(LeftDoor);
        }
        else
        {
            Rooms[i][j].addWall(LeftWallMid);
        }
    }
    
    private void setBossRoom()
    {
        //random instead, first room where above room is null/doesn't exist?
        for(int i = 0; i < 5; i ++)
        {
            for(int j = 0; j < 5; j++)
            {
                if(Rooms[i][j] != null)
                {
                    Rooms[i][j].setBossRoom();
                    return;
                }
            }
        }
    }
    
    private void startingRoom()
    {
        addRandomGear(250, 400);
        addRandomGear(400, 250);
        addRandomGear(800, 250);
        addRandomGear(950, 400);
    }
   
    public WizardPlayer getPlayer()
    {
        return this.Player;
    }
    
    public int getGameWith()
    {
        return this.GameWidth;
    }
    
    public int getGameHeight()
    {
        return this.GameHeight;
    }
    
    public Room getRoom()
    {
        return this.Rooms[RoomsI][RoomsJ];
    }
    
    public Wall[] getWalls()
    {
        return this.Walls;
    }
    
    public Animation getBurningAnimation()
    {
        return this.Burning;
    }
    
    public int getShadowHeight()
    {
        return this.ShadowHeight;
    }
    
    public boolean getTransition()
    {
        if(Transition)
        {
            Transition = false;
            return true;
        }
        else
        {
            return false;
        }
    }
    
    public void changeRoom()
    {
        if(RoomChangeI == -1)
        {
            if(Rooms[RoomsI][RoomsJ].isBossRoom())
            {
                this.FloorLevel++;
                floorRandomizer();
                Player.setX(609);
                Player.setY(576);
                Player.faceDown();
            }
            else
            {
                RoomsI--;
                Player.setX(609);
                Player.setY(976);
                Player.faceUp();
            }
        }
        else if(RoomChangeJ == -1)
        {
            RoomsJ--;
            Player.setX(1038);
            Player.setY(576);
            Player.faceLeft();
        }
        else if(RoomChangeI == 1)
        {
            RoomsI++;
            Player.setX(609);
            Player.setY(this.ShadowHeight + 20);
            Player.faceDown();
        }
        else if(RoomChangeJ == 1)
        {
            RoomsJ++;
            Player.setX(178);
            Player.setY(576);
            Player.faceRight();
        }
        
        this.RoomChangeI = 0;
        this.RoomChangeJ = 0;
        
        if(Rooms[RoomsI][RoomsJ].EnemySize() > 0)
        {
            Rooms[RoomsI][RoomsJ].lockDoors();
        }
    }
    
    public void updatePlayer(int mouseX, int mouseY)
    {
        /*
        updatePlayer is used to check for collisions with GameObjects. Then it 
        calls the player's update function, and checks if the player has any 
        projectiles or aoe's ready to be created, and adds the to the current Room.
        */
        CollisionDetector col = new CollisionDetector();
        
        for(int i = 0; i < Walls.length; i++)
        {
            col.playerCollision(Player, Walls[i]);
        }
        
        for(int i = 0; i < Rooms[RoomsI][RoomsJ].DoorSize(); i++)
        {
            if(col.normalCollision(Player, Rooms[RoomsI][RoomsJ].getDoor(i)))
            {
                if(Rooms[RoomsI][RoomsJ].getDoor(i).isLocked())
                {
                    if(!Player.getHorizontalCollision())
                    {
                        if(col.horizontalSlideCollision(Player, Rooms[RoomsI][RoomsJ].getDoor(i)))
                        {
                            Player.setHorizontalCollision();
                        }
                    }
                    
                    if(!Player.getVerticalCollision())
                    {
                        if(col.verticalSlideCollision(Player, Rooms[RoomsI][RoomsJ].getDoor(i)))
                        {
                            Player.setVerticalCollision();
                        }
                    }
                }
                else if(!Player.getGeneralCollision())
                {
                    if(Rooms[RoomsI][RoomsJ].getDoor(i).getY() == 0)
                    {
                        this.Transition = true;
                        this.RoomChangeI = -1;
                        Rooms[RoomsI][RoomsJ].leaveRoom();
                    }
                    else if(Rooms[RoomsI][RoomsJ].getDoor(i).getX() == 0)
                    {
                        this.RoomChangeJ = - 1;
                        this.Transition = true;
                        Rooms[RoomsI][RoomsJ].leaveRoom();
                    }
                    else if(Rooms[RoomsI][RoomsJ].getDoor(i).getY() == 1152)
                    {
                        this.RoomChangeI = 1;
                        this.Transition = true;
                        Rooms[RoomsI][RoomsJ].leaveRoom();
                    }
                    else if(Rooms[RoomsI][RoomsJ].getDoor(i).getX() == 1152)
                    {
                        this.RoomChangeJ = 1;
                        this.Transition = true;
                        Rooms[RoomsI][RoomsJ].leaveRoom();
                    }
                }
                    
                Player.setGeneralCollision();
            }
        }
        
        for(int i = 0; i < Rooms[RoomsI][RoomsJ].WallSize(); i++)
        {
            col.playerCollision(Player ,Rooms[RoomsI][RoomsJ].getWall(i));
        }
        
        for(int i = 0; i < Rooms[RoomsI][RoomsJ].BarrelSize(); i++)
        {
            col.playerCollision(Player ,Rooms[RoomsI][RoomsJ].getBarrel(i));
        }
        
        for(int i = 0; i < Rooms[RoomsI][RoomsJ].EnemySize(); i++)
        {
            col.playerCollision(Player ,Rooms[RoomsI][RoomsJ].getEnemy(i));
        }
        
        //now update the player's position
        Player.updatePlayer(mouseX, mouseY);
        //now test for collisions with gear
        for(int i = 0; i < Rooms[RoomsI][RoomsJ].RingSize(); i++)
        {
            if(col.standingCollision(Player, Rooms[RoomsI][RoomsJ].getRing(i)))
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
            if(col.standingCollision(Player, Rooms[RoomsI][RoomsJ].getNeck(i)))
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
            if(col.standingCollision(Player, Rooms[RoomsI][RoomsJ].getBoots(i)))
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
            if(col.standingCollision(Player, Rooms[RoomsI][RoomsJ].getTome(i)))
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
        
        for(int i = 0; i < Rooms[RoomsI][RoomsJ].EnemySize(); i++)
        {
            if(Rooms[RoomsI][RoomsJ].getEnemy(i).isDead())
            {
                Player.addExperience(Rooms[RoomsI][RoomsJ].getEnemy(i).getExperience());
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
                col.EnemyCollision(Rooms[RoomsI][RoomsJ].getEnemy(i), Walls[j], 
                        Player.getCenterX(), Player.getCenterY());
            }
            
            for(int j = 0; j < Rooms[RoomsI][RoomsJ].DoorSize(); j++)
            {
                col.EnemyCollision(Rooms[RoomsI][RoomsJ].getEnemy(i), 
                        Rooms[RoomsI][RoomsJ].getDoor(j), Player.getCenterX(), 
                        Player.getCenterY());
            }
            
            for(int j = 0; j < Rooms[RoomsI][RoomsJ].WallSize(); j++)
            {
                col.EnemyCollision(Rooms[RoomsI][RoomsJ].getEnemy(i), 
                        Rooms[RoomsI][RoomsJ].getWall(j), Player.getCenterX(), 
                        Player.getCenterY());
            }

            for(int j = 0; j < Rooms[RoomsI][RoomsJ].BarrelSize(); j++)
            {
                col.EnemyCollision(Rooms[RoomsI][RoomsJ].getEnemy(i), 
                        Rooms[RoomsI][RoomsJ].getBarrel(j), Player.getCenterX(), 
                        Player.getCenterY());
            }
            
            for(int j = 0; j < Rooms[RoomsI][RoomsJ].EnemySize(); j++)
            {
                if(i != j)
                {
                    col.EnemyCollision(Rooms[RoomsI][RoomsJ].getEnemy(i), 
                            Rooms[RoomsI][RoomsJ].getEnemy(j), Player.getCenterX(), 
                            Player.getCenterY());
                }
            }
            
            if(col.normalCollision(Rooms[RoomsI][RoomsJ].getEnemy(i), Player))
            {
                Player.takeDamage(Rooms[RoomsI][RoomsJ].getEnemy(i).getBumpDamage());
                Rooms[RoomsI][RoomsJ].getEnemy(i).setPlayerCollsion();
            }
            
            Rooms[RoomsI][RoomsJ].getEnemy(i).updateMovingEnemy(Player.getCenterX(), 
                    Player.getCenterY());
            
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
    
    public void updateGame(int mouseX, int mouseY)
    {
        updatePlayer(mouseX, mouseY);
        updateEnemies();
        updatePlayerProjectiles();
        updateEnemyProjectiles();
        
        Rooms[RoomsI][RoomsJ].updateRoom();
        Burning.updateAnimation();
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
    
}
