package model.game;

import control.TileFactory;
import model.tiles.units.enemies.Enemy;
import model.tiles.units.players.Player;
import utils.callbacks.MessageCallback;
import utils.*;

import java.util.List;

public class Level {
    private Board board;
    private Player player;
    private List<Enemy> enemies;
    private final TileFactory factory = TileFactory.getInstance();
    private MessageCallback msg;

    public MessageCallback action(String input)
    {
        MessageCallback action;
        switch (input)
        {
            case "w" -> action = board.getTile(player.getPosition().getX()- 1,player.getPosition().getY()).accept(player);
            case "s" -> action = board.getTile(player.getPosition().getX()+1,player.getPosition().getY()).accept(player);
            case "a" -> action = board.getTile(player.getPosition().getX(),player.getPosition().getY()-1).accept(player);
            case "d" -> action = board.getTile(player.getPosition().getX(), player.getPosition().getY() + 1).accept(player);
            case "e" -> action = player.useSA(board.enemiesInRange(player.getSARange())); //Special Ability

            default -> action = () -> {};
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
