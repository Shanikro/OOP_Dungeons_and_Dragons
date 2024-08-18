package model.game;

import control.LevelInitializer;
import control.TileFactory;
import model.tiles.Tile;
import model.tiles.units.enemies.Enemy;
import utils.callbacks.MessageCallback;
import utils.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Level {

    private LevelInitializer levelBuilder;
    private MessageCallback msg;
    private Board board;
    private List<Enemy> enemies;
    private final TileFactory factory = TileFactory.getInstance();


    public Level(int currentLevel,MessageCallback callback,LevelInitializer levelBuilder) {

        this.levelBuilder = levelBuilder;
        this.msg = callback;

        List<Tile> tileList = levelBuilder.initLevel(currentLevel);
        this.enemies = levelBuilder.getEnemeis();

        board = new Board(tileList,enemies,levelBuilder.getWidth());

    }


    public void action(String input)
    {
        switch (input)
        {
            case "w" -> board.getTile(factory.getPlayer().getPosition().getX()- 1,factory.getPlayer().getPosition().getY()).accept(factory.getPlayer());
            case "s" -> board.getTile(factory.getPlayer().getPosition().getX()+1,factory.getPlayer().getPosition().getY()).accept(factory.getPlayer());
            case "a" -> board.getTile(factory.getPlayer().getPosition().getX(),factory.getPlayer().getPosition().getY()-1).accept(factory.getPlayer());
            case "d" -> board.getTile(factory.getPlayer().getPosition().getX(), factory.getPlayer().getPosition().getY() + 1).accept(factory.getPlayer());
            case "e" -> factory.getPlayer().useSA(board.enemiesInRange(factory.getPlayer().getSARange())); //Special Ability

            default -> {}
        }
    }

    public void removeEnemy(Enemy enemy) {
        enemies.remove(enemy);
        Position p = enemy.getPosition();
        board.setTile(factory.produceEmpty(p),p);
    }

    public void gameTick(String action){
        //TODO
    }

    public boolean gameOver() {
        //TODO
        return false;
    }

    public boolean isOver() {
        //TODO
        return false;
    }

    public Board getBoard() {
        return board;
    }
}
