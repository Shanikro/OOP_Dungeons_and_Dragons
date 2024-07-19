package control.initializers;

import model.tiles.Empty;
import model.tiles.Tile;
import model.tiles.Wall;
import model.tiles.units.enemies.Enemy;
import model.tiles.units.enemies.Monster;
import model.tiles.units.enemies.Trap;
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
            'k', () -> new Monster('k', "Lannister Knight", 200, 14, 8, 4, 50),
            'q', () -> new Monster('q', "Queen’s Guard", 400, 20 , 15, 5, 100),
            'z', () -> new Monster('z', "Wright", 600, 30 , 15, 3, 100),
            'b', () -> new Monster('b', "Bear-Wright", 1000, 75 , 30, 4, 250),
            'g', () -> new Monster('g', "Giant-Wright", 1500, 100 , 40, 5, 500),
            'w', () -> new Monster('w', "White Walker", 2000, 150 , 50, 6, 1000),
            'M', () -> new Monster('M', "The Mountain", 1000, 60 , 25, 6, 500),
            'C', () -> new Monster('C', "Queen Cersei", 100, 10 , 10, 1, 1000),
            'K', () -> new Monster('K', "Night’s King", 5000, 300 , 150, 8, 5000),
            'K', () -> new Trap('B', "Bonus Trap", 1, 1 , 1, 250,1,5),
            'Q', () -> new Trap('Q', "Queen’s Trap", 250, 50 , 10, 100,3,7),
            'D', () -> new Trap('D', "Death Trap", 500, 100 , 20, 250,1,10)

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
