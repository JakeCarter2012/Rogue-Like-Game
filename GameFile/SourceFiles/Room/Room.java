package SourceFiles.Room;

import SourceFiles.GameObjects.StationaryObjects.PlayerAoe;
import SourceFiles.GameObjects.MovingObjects.Projectiles.PlayerProjectile;
import SourceFiles.GameObjects.StationaryObjects.Rune;
import SourceFiles.GameObjects.StationaryObjects.Page;
import SourceFiles.GameObjects.MovingObjects.Enemies.MovingEnemy;
import SourceFiles.GameObjects.MovingObjects.Projectiles.Projectile;
import SourceFiles.GameObjects.StationaryObjects.AreaOfEffect;
import SourceFiles.GameObjects.StationaryObjects.Coin;
import SourceFiles.GameObjects.StationaryObjects.StationaryObject;
import SourceFiles.GameObjects.StationaryObjects.SpikeTrap;
import SourceFiles.GameObjects.StationaryObjects.Potion;
import SourceFiles.GameObjects.StationaryObjects.Door;
import SourceFiles.GameObjects.StationaryObjects.Wall;
import SourceFiles.GameObjects.Animations.Animation;
import java.util.ArrayList;

public class Room {
    private ArrayList<PlayerAoe> PlayerAoes;
    private ArrayList<AreaOfEffect> EnemyAoes;
    private ArrayList<Coin> Coins;
    private ArrayList<MovingEnemy> Enemies;
    private ArrayList<Page> Pages;
    private ArrayList<Rune> Runes;
    private ArrayList<Potion> Potions;
    private ArrayList<PlayerProjectile> PlayerProjectiles;
    private ArrayList<Projectile> EnemyProjectiles;
    private ArrayList<SpikeTrap> SpikeTraps;
    private ArrayList<Wall> Walls;
    private ArrayList<StationaryObject> Barrels;
    private ArrayList<Door> Doors;
    private ArrayList<Animation> Animations;
    private boolean Shop, RuneRoom, BossRoom;
    
    
    //need up down left right doors
    //doors be collidable?
    //need to remove projectiles/aoes on room exit
    //alg for connection rooms
    //room types for shops/multiple rune offerings?
    //need to make sure thaat the added room isnt boxed in, if it is next room wont be created
    //bossroom for healthbar, portal creation
    //doors as own object?
    public Room()
    {
        this.PlayerAoes = new ArrayList<PlayerAoe>();
        this.EnemyAoes = new ArrayList<AreaOfEffect>();
        this.Coins = new ArrayList<Coin>();
        this.Enemies = new ArrayList<MovingEnemy>();
        this.Pages = new ArrayList<Page>();
        this.Runes = new ArrayList<Rune>();
        this.Potions = new ArrayList<Potion>();
        this.PlayerProjectiles = new ArrayList<PlayerProjectile>();
        this.EnemyProjectiles = new ArrayList<Projectile>();
        this.SpikeTraps = new ArrayList<SpikeTrap>();
        this.Walls = new ArrayList<Wall>();
        this.Barrels = new ArrayList<StationaryObject>();
        this.Doors = new ArrayList<Door>();
        this.Animations = new ArrayList<Animation>();
        
        this.Shop = false;
        this.RuneRoom = false;
        this.BossRoom = false;
    }
    
    public void setShopRoom()
    {
        this.Shop = true;
    }
    
    public void setBossRoom()
    {
        this.BossRoom = true;
    }
    
    public void setRuneRoom()
    {
        this.RuneRoom = true;
    }
    
    public void addDoor(Door door)
    {
        this.Doors.add(door);
    }
    
    public Door getDoor(int i)
    {
        return this.Doors.get(i);
    }
    public boolean isShop()
    {
        return this.Shop;
    }
    
    public boolean isRuneRoom()
    {
        return this.RuneRoom;
    }
    
    public boolean isBossRoom()
    {
        return this.isBossRoom();
    }
    
    public void addPlayerAoe(PlayerAoe aoe)
    {
        this.PlayerAoes.add(aoe);
    }
    
    public void addEnemyAoe(AreaOfEffect aoe)
    {
        this.EnemyAoes.add(aoe);
    }
    
    public void addCoin(Coin c)
    {
        this.Coins.add(c);
    }
    
    public void addEnemy(MovingEnemy enemy)
    {
        this.Enemies.add(enemy);
    }
    
    public void addPage(Page p)
    {
        this.Pages.add(p);
    }
    
    public void addRune(Rune r)
    {
        this.Runes.add(r);
    }
    
    public void addPotion(Potion p)
    {
        this.Potions.add(p);
    }
    
    public void addPlayerProjectile(PlayerProjectile p)
    {
        this.PlayerProjectiles.add(p);
    }
    
    public void addEnemyProjectile(Projectile p)
    {
        this.EnemyProjectiles.add(p);
    }
    
    public void addSpikeTrap(SpikeTrap trap)
    {
        this.SpikeTraps.add(trap);
    }
    
    public void addWall(Wall wall)
    {
        this.Walls.add(wall);
    }
    
    public void addBarrel(StationaryObject barrel)
    {
        this.Barrels.add(barrel);
    }
    
    public void addAnimation(Animation animate)
    {
        this.Animations.add(animate);
    }
    
    public void removePlayerAoe(int i)
    {
        this.PlayerAoes.remove(i);
    }
    
    public void removeEnemyAoe(int i)
    {
        this.EnemyAoes.remove(i);
    }
    
    public void removeCoin(int i)
    {
        this.Coins.remove(i);
    }
    
    public void removeEnemy(int i)
    {
        this.Enemies.remove(i);
    }
    
    public void removePage(int i)
    {
        this.Pages.remove(i);
    }
    
    public void removeRune(int i)
    {
        this.Runes.remove(i);
    }
    
    public void removePotion(int i)
    {
        this.Potions.remove(i);
    }
    
    public void removePlayerProjectile(int i)
    {
        this.PlayerProjectiles.remove(i);
    }
    
    public void removeEnemyProjectile(int i)
    {
        this.EnemyProjectiles.remove(i);
    }
    
    public void removeSpikeTrap(int i)
    {
        this.SpikeTraps.remove(i);
    }
    
    public void removeWall(int i)
    {
        this.Walls.remove(i);
    }
    
    public void removeBarrel(int i)
    {
        this.Barrels.remove(i);
    }
    
    public void removeAnimation(int i)
    {
        this.Animations.remove(i);
    }
    
    public PlayerAoe getPlayerAoe(int i)
    {
        return this.PlayerAoes.get(i);
    }
    
    public AreaOfEffect getEnemyAoe(int i)
    {
        return this.EnemyAoes.get(i);
    }
    
    public Coin getCoin(int i)
    {
        return this.Coins.get(i);
    }
    
    public MovingEnemy getEnemy(int i)
    {
        return this.Enemies.get(i);
    }
    
    public Page getPage(int i)
    {
        return this.Pages.get(i);
    }
    
    public Rune getRune(int i)
    {
        return this.Runes.get(i);
    }
    
    public Potion getPotion(int i)
    {
        return this.Potions.get(i);
    }
    
    public PlayerProjectile getPlayerProjectile(int i)
    {
        return this.PlayerProjectiles.get(i);
    }
    
    public Projectile getEnemyProjectile(int i)
    {
        return this.EnemyProjectiles.get(i);
    }
    
    public SpikeTrap getSpikeTrap(int i)
    {
        return this.SpikeTraps.get(i);
    }
    
    public Wall getWall(int i)
    {
        return this.Walls.get(i);
    }
    
    public StationaryObject getBarrel(int i)
    {
        return this.Barrels.get(i);
    }
    
    public Animation getAnimation(int i)
    {
        return this.Animations.get(i);
    }
    
    public int PlayerAoeSize()
    {
        return this.PlayerAoes.size();
    }
    
    public int EnemyAoeSize()
    {
        return this.EnemyAoes.size();
    }
    
    public int CoinSize()
    {
        return this.Coins.size();
    }
    
    public int EnemySize()
    {
        return this.Enemies.size();
    }
    
    public int PageSize()
    {
        return this.Pages.size();
    }
    
    public int RuneSize()
    {
        return this.Runes.size();
    }
    
    public int PotionSize()
    {
        return this.Potions.size();
    }
    
    public int PlayerProjectileSize()
    {
        return this.PlayerProjectiles.size();
    }
    
    public int EnemyProjectileSize()
    {
        return this.EnemyProjectiles.size();
    }
    
    public int SpikeTrapSize()
    {
        return this.SpikeTraps.size();
    }
    
    public int WallSize()
    {
        return this.Walls.size();
    }
    
    public int BarrelSize()
    {
        return this.Barrels.size();
    }
    
    public int AnimationSize()
    {
        return this.Animations.size();
    }
    
    public int DoorSize()
    {
        return this.Doors.size();
    }
    
    public void unlockDoors()
    {
        for(int i = 0; i < Doors.size(); i++)
        {
            Doors.get(i).unlockDoor();
        }
    }
    
    public void lockDoors()
    {
        for(int i = 0; i < Doors.size(); i++)
        {
            Doors.get(i).lockDoor();
        }
    }
    
    public void updateRoom()
    {
        updatePlayerAoe();
        updateEnemyAoe();
        updateTraps();
        updateAnimations();
    }
    
    private void updatePlayerAoe()
    {
         
        for(int i = 0; i < PlayerAoeSize(); i++)
        {
            getPlayerAoe(i).updateObject();
            if(getPlayerAoe(i).isDone())
            {
                removePlayerAoe(i);
                i--;
            }
        }
    }
    
    private void updateEnemyAoe()
    {
        for(int i = 0; i < EnemyAoeSize(); i++)
        {
            getEnemyAoe(i).updateObject();
            if(getEnemyAoe(i).isDone())
            {
                removeEnemyAoe(i);
                i--;
            }
        }
    }
    
    private void updateTraps()
    {
        for(int i = 0; i < SpikeTrapSize(); i++)
        {
            getSpikeTrap(i).updateObject();
        }
        
        for(int i = 0; i < DoorSize(); i++)
        {
            getDoor(i).updateDoor();
        }
    }
    
    private void updateAnimations()
    {
        for(int i = 0; i < AnimationSize(); i++)
        {
            getAnimation(i).updateAnimation();
            if(getAnimation(i).isDone())
            {
                removeAnimation(i);
                i--;
            }
        }
    }
}