package model.tiles;

import model.tiles.units.Unit;
import model.tiles.units.enemies.Enemy;
import model.tiles.units.players.*;
import utils.callbacks.MessageCallback;

public class Empty extends Tile {
    public static final char EMPTY_TILE = '.';

    public Empty() {
        super(EMPTY_TILE);
    }

    @Override
    public MessageCallback visit(Empty empty) {
        return ()->{};
    }

    @Override
    public MessageCallback visit(Wall wall) {
        return ()->{};
    }

    @Override
    public MessageCallback visit(Player player) {
        return ()->{};
    }

    @Override
    public MessageCallback visit(Enemy enemy) {
        return ()->{};
    }

    @Override
    public MessageCallback accept(Tile tile) {
       return tile.visit(this);
    }
}
