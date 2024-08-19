package control;

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
import utils.generators.Generator;
import utils.printer.Printer;
import utils.printer.PrinterC;

import java.util.*;
import java.util.function.Supplier;

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
        playerTypes.put(1, () -> new Warrior("Jon Snow", 30, 4, 300, 3));
        playerTypes.put(2, () -> new Warrior("The Hound", 20, 6, 400, 5));
        playerTypes.put(3, () -> new Mage("Melisandre", 100, 5, 1, 300, 30, 15, 5, 6));
        playerTypes.put(4, () -> new Mage("Thoros of Myr", 250, 25, 4, 150, 20, 20, 3, 4));
        playerTypes.put(5, () -> new Rogue("Arya Stark", 150, 40, 2, 20));
        playerTypes.put(6, () -> new Rogue("Bronn", 250, 35, 3, 50));

        // Monsters
        enemiesTypes = new HashMap<>();
        enemiesTypes.put('s', () -> new Monster('s', "Lannister Solider", 80, 8, 3, 25, 3));
        enemiesTypes.put('k', () -> new Monster('k', "Lannister Knight", 200, 14, 8, 50, 4));
        enemiesTypes.put('q', () -> new Monster('q', "Queen’s Guard", 400, 20, 15, 100, 5));
        enemiesTypes.put('z', () -> new Monster('z', "Wright", 600, 30, 15, 100, 3));
        enemiesTypes.put('b', () -> new Monster('b', "Bear-Wright", 1000, 75, 30, 250, 4));
        enemiesTypes.put('g', () -> new Monster('g', "Giant-Wright", 1500, 100, 40, 500, 5));
        enemiesTypes.put('w', () -> new Monster('w', "White Walker", 2000, 150, 50, 1000, 6));
        enemiesTypes.put('M', () -> new Monster('M', "The Mountain", 1000, 60, 25, 500, 6));
        enemiesTypes.put('C', () -> new Monster('C', "Queen Cersei", 100, 10, 10, 1000, 1));
        enemiesTypes.put('K', () -> new Monster('K', "Night’s King", 5000, 300, 150, 5000, 8));
        // Traps
        enemiesTypes.put('B', () -> new Trap('B', "Bonus Trap", 1, 1, 1, 250, 1, 5));
        enemiesTypes.put('Q', () -> new Trap('Q', "Queen’s Trap", 250, 50, 10, 100, 3, 7));
        enemiesTypes.put('D', () -> new Trap('D', "Death Trap", 500, 100, 20, 250, 1, 10));

    }

    public Player producePlayer(int playerID) {
        Supplier<Player> supp = playerTypes.get(playerID);
        this.p = supp.get();
        return this.p;
    }

    public Player producePlayer(Position p,Generator g) {
        this.p.initialize(p,g);
        return this.p;
    }

    public Enemy produceEnemy(char tile, Position p, Generator g) {
        Enemy e = enemiesTypes.get(tile).get();
        e.initialize(p,g);
        return e;
    }

    public Tile produceEmpty(Position p) {
        return new Empty().initialize(p);
    }

    public Tile produceWall(Position p) {
        return new Wall().initialize(p);
    }


    public void printPlayers() { // Call back for the start of the game
        StringBuilder output = new StringBuilder();

        int i = 1;
        for (Supplier<Player> playerSupplier : playerTypes.values()) {
            output.append(i).append("- ");
            output.append(playerSupplier.get().describe()).append(" \n");
            i++;
        }
            printer.print(output.toString());
    }

    public Player getPlayer() {
        return this.p;
    }
}
