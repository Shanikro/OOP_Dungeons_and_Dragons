package control.initializers;

import model.tiles.Empty;
import model.tiles.Tile;
import model.tiles.Wall;
import model.tiles.units.enemies.Enemy;
import model.tiles.units.enemies.Monster;
import model.tiles.units.players.Mage;
import model.tiles.units.players.Player;
import model.tiles.units.players.Warrior;
import model.tiles.units.players.Rogue;
import utils.Position;
import utils.callbacks.DeathCallback;
import utils.callbacks.MessageCallback;
import utils.generators.Generator;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class TileFactory {
    private Player p;
    private static final List<Supplier<Player>> playerTypes = Arrays.asList(
            () -> new Warrior("Jon Snow", 30, 4, 300, 3),
            () -> new Warrior("The Hound", 20, 6, 400, 5),
            () -> new Mage("Melisandre", 100, 2, 1,300,30,15,5,6),
            () -> new Mage("Thoros of Myr", 250, 25, 4,150,20,20,3,4),
            () -> new Rogue("Arya Stark", 150, 40, 2, 20),
            () -> new Rogue("Bronn", 250, 35, 3, 50)
    );

    private static final Map<Character, Supplier<Enemy>> enemyTypes = Map.of(
            's', () -> new Monster('s', "Lannister Solider", 80, 8, 3, 25,3),
            'k', () -> new Monster('q', "Enemy2", 15, 3, 3, 1),
            'q', () -> new Monster('T', "Enemy3", 20, 2, 4, 2)
    );
    public TileFactory(){
    }

    public Player producePlayer(int playerID){
        Supplier<Player> supp = playerTypes.get(playerID-1);
        this.p = supp.get();
        return this.p;
    }

    public Player producePlayer(){
        return this.p;
    }

    public Enemy produceEnemy(char tile, Position p, DeathCallback c, Generator g, MessageCallback m){
        Enemy e = enemyTypes.get(tile).get();
        e.initialize(p, g, c, m);
        return e;
    }

    public Tile produceEmpty(Position p){
        return new Empty().initialize(p);
    }

    public Tile produceWall(Position p){
        return new Wall().initialize(p);
    }
}
