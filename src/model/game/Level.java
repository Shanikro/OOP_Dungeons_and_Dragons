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


    public void act(String input)
    {
        switch (input)
        {
            case "w" -> {
                Tile swap = board.getTile(factory.getPlayer().getPosition().up());
                swap.accept(factory.getPlayer());
                //swap position at board
                board.setTile(factory.getPlayer(),factory.getPlayer().getPosition());
                board.setTile(swap,swap.getPosition());
            }

            case "s" -> {
                Tile swap = board.getTile(factory.getPlayer().getPosition().down());
                swap.accept(factory.getPlayer());
                //swap position at board
                board.setTile(factory.getPlayer(),factory.getPlayer().getPosition());
                board.setTile(swap,swap.getPosition());
            }
            case "a" -> {
                Tile swap = board.getTile(factory.getPlayer().getPosition().left());
                swap.accept(factory.getPlayer());
                //swap position at board
                board.setTile(factory.getPlayer(), factory.getPlayer().getPosition());
                board.setTile(swap, swap.getPosition());
            }
            case "d" -> {
                Tile swap = board.getTile(factory.getPlayer().getPosition().right());
                swap.accept(factory.getPlayer());
                //swap position at board
                board.setTile(factory.getPlayer(),factory.getPlayer().getPosition());
                board.setTile(swap,swap.getPosition());
            }

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
        act(action);//TODO- צריך שבswap postion זה ישנה גם בלוח עצמו ולא רק בשדות
        enemyGameTick();
    }

    public void enemyGameTick(){
        List<Enemy> toRemove = new ArrayList<>();
        for (Enemy e : enemies) {
            if (e.isAlive()) {
                Position p = e.takeTurn(factory.getPlayer());
                Tile t = board.getTile(p);
                t.accept(e); //TODO

                //swap position at board
                board.setTile(e,e.getPosition());
                board.setTile(t,t.getPosition());

            } else {
                toRemove.add(e);
            }
        }

        for (Enemy e : toRemove){
            removeEnemy(e);
        }
    }

    public boolean isOver() {
        return enemies.isEmpty();
    }

    public Board getBoard() {
        return board;
    }
}
