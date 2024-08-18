package model.tiles.units.enemies;

import model.game.Board;
import model.tiles.Tile;
import model.tiles.units.Unit;
import model.tiles.units.players.Player;
import utils.callbacks.MessageCallback;

public class Trap extends Enemy {
    private final int RANGE = 2;
    private int visibilityTime;
    private int invisibilityTime;
    private int ticksCount;
    private boolean visible;

    public Trap(char tile, String name, int hitPoints, int attack, int defense, int experienceValue, int visibilityTime, int invisibilityTime) {
        super(tile, name, hitPoints, attack, defense, experienceValue);
        this.visibilityTime = visibilityTime;
        this.invisibilityTime = invisibilityTime;
        this.ticksCount = 0;
        this.visible = true;
    }

    public MessageCallback takeTurn(Player player) {
        visible = ticksCount < visibilityTime;

        if (ticksCount == (visibilityTime + invisibilityTime)) {
            ticksCount = 0;
        } else {
            ticksCount++;
        }

        if (player.getPosition().range(this.getPosition()) < RANGE) {
            return this.visit(player);
        } else {
            return () -> {};
        }
    }

    @Override
    public String toString()
    {
        if(visible){
            return String.valueOf(tile);
        }
        else{
            return ".";
        }
    }

    @Override
    //Trap does not swap
    public void swapPosition(Tile t) {}

    @Override
    public String describe()
    {
        return String.format("""
                        %s\t\t\tHealth: %d/%d\t\t\tAttack: %d\t\t\tDefense: %d\t\t\tExperience: %d
                        \t\t\tVisibilityTime: %d\t\t\tInVisibilityTime: %d
                        """,
                name, health.getCurrent(), health.getCapacity(), getAttack(), getDefense(),
                experienceValue, visibilityTime, invisibilityTime);
    }


}
