package model.tiles.units.enemies;

import model.tiles.units.players.Player;
import utils.Position;

public class Monster extends Enemy {

    private int visionRange;

    public Monster(char tile, String name, int hitPoints, int attack, int defense, int experienceValue, int visionRange) {
        super(tile, name, hitPoints, attack, defense, experienceValue);
        this.visionRange = visionRange;
    }

    public void takeTurn(Player player) {

        if (this.getPosition().range(player.getPosition()) < visionRange) {
            int dx = this.getPosition().getX() - player.getPosition().getX();
            int dy = this.getPosition().getY() - player.getPosition().getY();

            if (Math.abs(dx) > Math.abs(dy)) {
                if (dx > 0) {
                    moveLeft();
                } else {
                    moveRight();
                }
            } else {
                if (dy > 0) {
                    moveUp();
                } else {
                    moveDown();
                }
            }
        }
        else {
            // Random movement or stay in place
           randomMovement();
        }
    }

    private void moveLeft() { this.getPosition().setX(getPosition().getX()-1);}

    private void moveRight() { this.getPosition().setX(getPosition().getX()+1);}

    private void moveUp() { this.getPosition().setY(getPosition().getY()-1);}

    private void moveDown() { this.getPosition().setY(getPosition().getY()+1);}

    private void randomMovement() {
        int move = this.generator.generate(5);

        switch (move) {
            case 0:
                moveLeft();
                break;
            case 1:
                moveRight();
                break;
            case 2:
                moveUp();
                break;
            case 3:
                moveDown();
                break;
            case 4:
                // Stay in place
                break;
        }
    }
}

