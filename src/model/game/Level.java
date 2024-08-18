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
        this.enemies = levelBuilder.getEnemeis();

        List<Tile> tileList = levelBuilder.initLevel(currentLevel);

        board = new Board(tileList,enemies,);

    }


    public MessageCallback action(String input)
    {
        MessageCallback action;
        switch (input)
        {
            case "w" -> action = board.getTile(factory.getPlayer().getPosition().getX()- 1,factory.getPlayer().getPosition().getY()).accept(factory.getPlayer());
            case "s" -> action = board.getTile(factory.getPlayer().getPosition().getX()+1,factory.getPlayer().getPosition().getY()).accept(factory.getPlayer());
            case "a" -> action = board.getTile(factory.getPlayer().getPosition().getX(),factory.getPlayer().getPosition().getY()-1).accept(factory.getPlayer());
            case "d" -> action = board.getTile(factory.getPlayer().getPosition().getX(), factory.getPlayer().getPosition().getY() + 1).accept(factory.getPlayer());
            case "e" -> action = factory.getPlayer().useSA(board.enemiesInRange(factory.getPlayer().getSARange())); //Special Ability

            default -> action = (s) -> {};
        };
        gameTick();
        return action;
    }

    public void removeEnemy(Enemy enemy) {
        enemies.remove(enemy);
        Position p = enemy.getPosition();
        board.setTile(factory.produceEmpty(p),p);
    }

    public void gameTick(){
        //TODO
    }

}
