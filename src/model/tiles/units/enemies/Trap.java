package model.tiles.units.enemies;

import model.tiles.units.Unit;
import model.tiles.units.players.Player;

public class Trap extends Enemy {
    private int visibilityTime;
    private int invisibilityTime;
    private int ticksCount;
    private boolean visible;

    public Trap(char tile, String name, int hitPoints, int attack, int defense, int experienceValue, int visionRange, int visibilityTime, int invisibilityTime) {
        super(tile, name, hitPoints, attack, defense, experienceValue);
        this.visibilityTime = visibilityTime;
        this.invisibilityTime = invisibilityTime;
        this.ticksCount = 0;
        this.visible = true;
    }

    public void takeTurn(Player player) {
        visible = ticksCount < visibilityTime;
        if (ticksCount == (visibilityTime + invisibilityTime)) {
            ticksCount = 0;
        }
        else {
            ticksCount++;
        }

        if (player.getPosition().range(this.getPosition())< 2) {
            battle(player);
        }
    }
    }
