package model.tiles.units.enemies;

import model.game.Board;
import model.tiles.Tile;
import model.tiles.units.Unit;
import model.tiles.units.players.Player;
import utils.Position;
import utils.callbacks.MessageCallback;

public abstract class Enemy extends Unit {
    protected int experienceValue;

    public Enemy(char tile, String name, int hitPoints, int attack, int defense, int experienceValue) {
        super(tile, name, hitPoints, attack, defense);
        this.experienceValue = experienceValue;
    }

    public int getExperienceValue() {
        return experienceValue;
    }

    @Override
    public MessageCallback accept(Tile tile) {
        return tile.visit(this);
    }

    @Override
    public MessageCallback visit(Enemy enemy)
    {
        return (s)->{};
    }

    @Override
    public MessageCallback visit(Player p) {
        StringBuilder output = new StringBuilder();

        output.append(battle(p));

        if (!p.isAlive()) {
            p.onDeath();
            output.append(String.format("%s was killed by %s\nYou lost.\n", p.getName(), getName()));
        }

        return (s)->printer.print(output.toString());

    }

    @Override
    // Level responsibility
    public void onDeath() {}

    @Override
    public String toString(){
        return "" + tile;
    }

}
