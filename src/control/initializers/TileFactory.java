package control.initializers;

import model.game.Board;
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
        playerTypes.put(1, (tile, board, position) -> new Warrior("Jon Snow", board, 30, 4, 300, 3));
        playerTypes.put(2, (tile, board, position) -> new Warrior("The Hound", board, 20, 6, 400, 5));
        playerTypes.put(3, (tile, board, position) -> new Mage("Melisandre", board, 100, 2, 1, 300, 30, 15, 5, 6));
        playerTypes.put(4, (tile, board, position) -> new Mage("Thoros of Myr", board, 250, 25, 4, 150, 20, 20, 3, 4));
        playerTypes.put(5, (tile, board, position) -> new Rogue("Arya Stark", board, 150, 40, 2, 20));
        playerTypes.put(6, (tile, board, position) -> new Rogue("Bronn", board, 250, 35, 3, 50));

        // Monsters
        enemiesTypes = new HashMap<>();
        enemiesTypes.put('s', (tile, board, position) -> new Monster('s', board, "Lannister Solider", 80, 8, 3, 25, 3));
        enemiesTypes.put('k', (tile, board, position) -> new Monster('k', board, "Lannister Knight", 200, 14, 8, 4, 50));
        enemiesTypes.put('q', (tile, board, position) -> new Monster('q', board, "Queen’s Guard", 400, 20, 15, 5, 100));
        enemiesTypes.put('z', (tile, board, position) -> new Monster('z', board, "Wright", 600, 30, 15, 3, 100));
        enemiesTypes.put('b', (tile, board, position) -> new Monster('b', board, "Bear-Wright", 1000, 75, 30, 4, 250));
        enemiesTypes.put('g', (tile, board, position) -> new Monster('g', board, "Giant-Wright", 1500, 100, 40, 5, 500));
        enemiesTypes.put('w', (tile, board, position) -> new Monster('w', board, "White Walker", 2000, 150, 50, 6, 1000));
        enemiesTypes.put('M', (tile, board, position) -> new Monster('M', board, "The Mountain", 1000, 60, 25, 6, 500));
        enemiesTypes.put('C', (tile, board, position) -> new Monster('C', board, "Queen Cersei", 100, 10, 10, 1, 1000));
        enemiesTypes.put('K', (tile, board, position) -> new Monster('K', board, "Night’s King", 5000, 300, 150, 8, 5000));
        // Traps
        enemiesTypes.put('B', (tile, board, position) -> new Trap('B', board, "Bonus Trap", 1, 1, 1, 250, 1, 5));
        enemiesTypes.put('Q', (tile, board, position) -> new Trap('Q', board, "Queen’s Trap", 250, 50, 10, 100, 3, 7));
        enemiesTypes.put('D', (tile, board, position) -> new Trap('D', board, "Death Trap", 500, 100, 20, 250, 1, 10));

    }

    public Player producePlayer(int playerID, Board b, Position p) {
        Supplier<Player> supp = playerTypes.get(playerID - 1);
        this.p = supp.get('@', b, this.p.getPosition());
        return this.p;
    }

    public Player producePlayer() {
        return this.p;
    }

    public Enemy produceEnemy(char tile, Position p, Board b, Generator g, MessageCallback m) {
        Enemy e = enemiesTypes.get(tile).get(tile, b, p);
        e.initialize(p, g, m);
        return e;
    }

    public Tile produceEmpty(Position p) {
        return new Empty().initialize(p);
    }

    public Tile produceWall(Position p) {
        return new Wall().initialize(p);
    }


    public MessageCallback printPlayers() { // Call back for the start of the game
        return () -> {
            String output = """
            Select player:
            %1$s Health: %2$s         Attack: %3$s              Defense: %4$s              Level: %5$s                Experience: %6$s                Cooldown: %7$s
            %8$s Health: %9$s         Attack: %10$s             Defense: %11$s             Level: %12$s               Experience: %13$s               Cooldown: %14$s
            %15$s Health: %16$s        Attack: %17$s             Defense: %18$s             Level: %19$s               Experience: %20$s               Mana: %21$s             Spell Power: %22$s
            %23$s Health: %24$s        Attack: %25$s             Defense: %26$s             Level: %27$s               Experience: %28$s               Mana: %29$s             Spell Power: %30$s
            %31$s Health: %32$s        Attack: %33$s             Defense: %34$s             Level: %35$s               Experience: %36$s               Energy: %37$s
            %38$s Health: %39$s        Attack: %40$s             Defense: %41$s             Level: %42$s               Experience: %43$s               Energy: %44$s
            %45$s Health: %46$s        Attack: %47$s             Defense: %48$s             Level: %49$s               Experience: %50$s               Arrows: %51$s           Range: %52$s
            """.formatted(
                    "1. Jon Snow", "300/300", "30", "4", "1", "0/50", "0/3",
                    "2. The Hound", "400/400", "20", "6", "1", "0/50", "0/5",
                    "3. Melisandre", "100/100", "5", "1", "1", "0/50", "75/300", "15",
                    "4. Thoros of Myr", "250/250", "25", "4", "1", "0/50", "37/150", "20",
                    "5. Arya Stark", "150/150", "40", "2", "1", "0/50", "100/100",
                    "6. Bronn", "250/250", "35", "3", "1", "0/50", "100/100"
            );
            printer.print(output);
        };
    }

}
