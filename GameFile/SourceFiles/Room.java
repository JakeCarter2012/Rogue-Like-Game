package SourceFiles;

import java.util.ArrayList;

public class Room {
    private ArrayList<AreaOfEffect> PlayerAoes;
    private ArrayList<AreaOfEffect> EnemyAoes;
    private ArrayList<Coin> Coins;
    private ArrayList<MovingEnemy> Enemies;
    private ArrayList<Page> Pages;
    private ArrayList<Rune> Runes;
    private ArrayList<Potion> Potions;
    private ArrayList<Projectile> PlayerProjectiles;
    private ArrayList<Projectile> EnemyProjectiles;
    private ArrayList<SpikeTrap> SpikeTraps;
    private ArrayList<StationaryObject> Walls;
    private ArrayList<StationaryObject> Barrels;
    private int NorthDoor, SouthDoor, LeftDoor, RightDoor;
    private boolean Shop, RuneRoom, BossRoom;
    
    
    //need up down left right doors
    //doors be collidable?
    //need to remove projectiles/aoes on room exit
    //alg for connection rooms
    //room types for shops/multiple rune offerings?
    //need to make sure thaat the added room isnt boxed in, if it is next room wont be created
    public Room(boolean shopRoom, boolean runeRoom, boolean bossRoom)
    {
        this.PlayerAoes = new ArrayList<AreaOfEffect>();
        this.EnemyAoes = new ArrayList<AreaOfEffect>();
        this.Coins = new ArrayList<Coin>();
        this.Enemies = new ArrayList<MovingEnemy>();
        this.Pages = new ArrayList<Page>();
        this.Runes = new ArrayList<Rune>();
        this.Potions = new ArrayList<Potion>();
        this.PlayerProjectiles = new ArrayList<Projectile>();
        this.EnemyProjectiles = new ArrayList<Projectile>();
        this.SpikeTraps = new ArrayList<SpikeTrap>();
        this.Walls = new ArrayList<StationaryObject>();
        this.Barrels = new ArrayList<StationaryObject>();
        
        this.NorthDoor = this.SouthDoor = this.LeftDoor = this.RightDoor = -1;
        
        this.Shop = shopRoom;
        this.RuneRoom = runeRoom;
        this.BossRoom = bossRoom;
    }
    
    public void addNorthDoor(int i)
    {
        this.NorthDoor = i;
    }
    
    public void addSouthDoor(int i)
    {
        this.SouthDoor = i;
    }
    
    public void addLeftDoor(int i)
    {
        this.LeftDoor = i;
    }
    
    public void addRightDoor(int i)
    {
        this.RightDoor = i;
    }
    
    public int getNorthDoor()
    {
        return this.NorthDoor;
    }
    
    public int getSouthDoor()
    {
        return this.SouthDoor;
    }
    
    public int getLeftDoor()
    {
        return this.LeftDoor;
    }
    
    public int getRightDoor()
    {
        return this.RightDoor;
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
    
    public void addPlayerAoe(AreaOfEffect aoe)
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
    
    public void addPlayerProjectile(Projectile p)
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
    
    public void addWall(StationaryObject wall)
    {
        this.Walls.add(wall);
    }
    
    public void addBarrel(StationaryObject barrel)
    {
        this.Barrels.add(barrel);
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
    
    public AreaOfEffect getPlayerAoe(int i)
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
    
    public Projectile getPlayerProjectile(int i)
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
    
    public StationaryObject getWall(int i)
    {
        return this.Walls.get(i);
    }
    
    public StationaryObject getBarrel(int i)
    {
        return this.Barrels.get(i);
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
    
    public int getPageSize()
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
}