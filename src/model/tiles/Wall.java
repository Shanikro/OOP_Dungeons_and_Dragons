package model.tiles;

import model.tiles.units.Unit;
import model.tiles.units.enemies.Enemy;
import model.tiles.units.players.Player;
import utils.callbacks.MessageCallback;

public class Wall extends Tile {
    public static final char WALL_TILE = '#';

    public Wall() {
        super(WALL_TILE);
    }

    @Override
    public MessageCallback visit(Empty empty) {
        return null;
    }

    @Override
    public MessageCallback visit(Wall wall) {
        return null;
    }

    @Override
    public MessageCallback visit(Player player) {
        return null;
    }

    @Override
    public MessageCallback visit(Enemy enemy) {
        return null;
    }

    @Override
    public MessageCallback accept(Tile tile) {
        return tile.visit(this);
    }
}
