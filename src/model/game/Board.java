package model.game;

import control.TileFactory;
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
    private List<Enemy> enemies;
    private final int width;
    private final TileFactory factory = TileFactory.getInstance();
    

    public Board(List<Tile> tiles, List<Enemy> enemies, int width){
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

    public Tile getTile(Position p){
        return board.get(p);
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
            if(factory.getPlayer().getPosition().range(e.getPosition()) < abilityRange)
                enemyList.add(e);
        }
        return enemyList;
    }

    public void setTile(Tile tile, Position position){
        board.put(position,tile);
    }


}
