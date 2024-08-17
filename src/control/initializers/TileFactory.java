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
import utils.Supplier;
import utils.callbacks.MessageCallback;
import utils.generators.Generator;
import utils.printer.Printer;
import utils.printer.PrinterC;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.*;

public class TileFactory {
    //Singleton
    private static TileFactory instance = null;

    private Player p;
    private Map<Integer, Supplier<Player>> playerTypes;
    private Map<Character, Supplier<Enemy>> enemiesTypes;

    private static final Printer printer = PrinterC.getInstance();

    public static TileFactory getInstance() {
        if (instance == null) {
            instance = new TileFactory();
        }
        return instance;
    }

    private TileFactory() {

        //Players
        playerTypes = new TreeMap<>();
        playerTypes.put(1, (tile,board) -> new Warrior("Jon Snow", board, 30, 4, 300, 3));
        playerTypes.put(2, (tile,board) -> new Warrior("The Hound", board, 20, 6, 400, 5));
        playerTypes.put(3, (tile,board) -> new Mage("Melisandre", board, 100, 2, 1, 300, 30, 15, 5, 6));
        playerTypes.put(4, (tile,board) -> new Mage("Thoros of Myr", board, 250, 25, 4, 150, 20, 20, 3, 4));
        playerTypes.put(5, (tile,board) -> new Rogue("Arya Stark", board, 150, 40, 2, 20));
        playerTypes.put(6, (tile,board) -> new Rogue("Bronn", board, 250, 35, 3, 50));

        // Monsters
        enemiesTypes = new HashMap<>();
        enemiesTypes.put('s', (tile,board) -> new Monster('s', board, "Lannister Solider", 80, 8, 3, 25, 3));
        enemiesTypes.put('k', (tile,board) -> new Monster('k', board, "Lannister Knight", 200, 14, 8, 4, 50));
        enemiesTypes.put('q', (tile,board) -> new Monster('q', board,"Queen’s Guard", 400, 20, 15, 5, 100));
        enemiesTypes.put('z', (tile,board) -> new Monster('z', board,"Wright", 600, 30, 15, 3, 100));
        enemiesTypes.put('b', (tile,board) -> new Monster('b', board,"Bear-Wright", 1000, 75, 30, 4, 250));
        enemiesTypes.put('g', (tile,board) -> new Monster('g', board,"Giant-Wright", 1500, 100, 40, 5, 500));
        enemiesTypes.put('w', (tile,board) -> new Monster('w', board,"White Walker", 2000, 150, 50, 6, 1000));
        enemiesTypes.put('M', (tile,board) -> new Monster('M', board,"The Mountain", 1000, 60, 25, 6, 500));
        enemiesTypes.put('C', (tile,board) -> new Monster('C', board,"Queen Cersei", 100, 10, 10, 1, 1000));
        enemiesTypes.put('K', (tile,board) -> new Monster('K', board,"Night’s King", 5000, 300, 150, 8, 5000));
        // Traps
        enemiesTypes.put('B', (tile,board) -> new Trap('B', board,"Bonus Trap", 1, 1, 1, 250, 1, 5));
        enemiesTypes.put('Q', (tile,board) -> new Trap('Q', board,"Queen’s Trap", 250, 50, 10, 100, 3, 7));
        enemiesTypes.put('D', (tile,board) -> new Trap('D', board,"Death Trap", 500, 100, 20, 250, 1, 10));

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
        Enemy e = enemiesTypes.get(tile).get();
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
