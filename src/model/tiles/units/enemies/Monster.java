package model.tiles.units.enemies;

import model.game.Board;
import model.tiles.Tile;
import model.tiles.units.players.Player;
import utils.Position;
import utils.callbacks.MessageCallback;

public class Monster extends Enemy {

    private int visionRange;

    public Monster(char tile, String name, int hitPoints, int attack, int defense, int experienceValue, int visionRange) {
        super(tile, name, hitPoints, attack, defense, experienceValue);
        this.visionRange = visionRange;
    }

    public String monsterAction(Player player) {

        //The player is already dead
        if(!player.isAlive()) {
            return "";
        }

        //The player in range
        if (this.getPosition().range(player.getPosition()) < visionRange) {
            String swapWith ;
            int dx = this.getPosition().getX() - player.getPosition().getX();
            int dy = this.getPosition().getY() - player.getPosition().getY();

            if (Math.abs(dx) > Math.abs(dy)) {
                if (dx > 0) {
                    swapWith = left();
                } else {
                    swapWith = right();
                }
            } else {
                if (dy > 0) {
                    swapWith = up();
                } else {
                    swapWith = down();
                }
            }
            return swapWith;
        }

        // Random movement or stay in place
        else {
           return randomMovement();
        }
    }

    private String left() {
        return "d";
    }

    private String right() {
        return "a";
    }

    private String up() {
        return "w";
    }

    private String down() {
        return "s";
    }

    private String randomMovement() {
        String swapWith = null;
        int move = this.generator.generate(5);

        switch (move) {
            case 0:
                swapWith = left();
            case 1:
                swapWith = right();
            case 2:
                swapWith = up();
            case 3:
                swapWith = down();
            case 4:
                // Stay in place
        }
        if (swapWith != null){
            return swapWith;
        }
        return "";
    }

    @Override
    public String describe()
    {
        return String.format("""
                        %s\t\t\tHealth: %d/%d\t\t\tAttack: %d\t\t\tDefense: %d\t\t\tExperience: %d
                        \t\t\tVisionRange: %d
                        """,
                name, health.getCurrent(), health.getCapacity(), getAttack(), getDefense(), experienceValue ,visionRange);
    }
}

