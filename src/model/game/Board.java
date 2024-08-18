package model.game;

import control.initializers.TileFactory;
import model.tiles.Tile;
import model.tiles.units.enemies.Enemy;
import model.tiles.units.players.Player;
import utils.Position;
import utils.callbacks.MessageCallback;
import utils.printer.Printer;
import utils.printer.PrinterC;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Board {
    private Map<Position, Tile> board;
    private Player player;
    private List<Enemy> enemies;
    private final int width;
    private final TileFactory factory = TileFactory.getInstance();
    private final Printer printer = PrinterC.getInstance();

    public Board(List<Tile> tiles, Player p, List<Enemy> enemies, int width){
        this.player = p;
        this.enemies = enemies;
        this.width = width;
        this.board = new TreeMap<>();
        for(Tile t : tiles){
            board.put(t.getPosition(), t);
        }
    }

    public Tile getTile(int x, int y){
        return board.get(new Position(x, y));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(Map.Entry<Position, Tile> entry : board.entrySet()){
            sb.append(entry.getValue().toString());
            if(entry.getKey().getX() == width-1){
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    public List<Enemy> enemiesInRange(int abilityRange) {
        List<Enemy> enemyList = new ArrayList<>();
        for(Enemy e: enemies){
            if(player.getPosition().range(e.getPosition()) < abilityRange)
                enemyList.add(e);
        }
        return enemyList;
    }

    public void removeEnemy(Enemy enemy) {
        enemies.remove(enemy);
        Position p = enemy.getPosition();
        board.put(p,factory.produceEmpty(p));

    }

}
