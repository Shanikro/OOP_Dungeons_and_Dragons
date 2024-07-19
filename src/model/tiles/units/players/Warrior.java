package model.tiles.units.players;

import utils.Position;
import model.tiles.Tile;
import utils.Health;
import model.tiles.units.enemies.Enemy;
import model.tiles.units.players.Player;


public class Warrior extends Player {

    private final int WARRIOR_ADDITIONAL_HEAITH = 5;
    private final int WARRIOR_ADDITIONAL_ATTACK = 2;
    private final int WARRIOR_ADDITIONAL_DEFENSE = 1;

    private int abilityCooldown;
    private int remainingCooldown;

    public Warrior(String name ,  int attackPoints, int defensePoints, int health, int abilityCooldown)
    {
        super(name,health,attackPoints,defensePoints);

        this.abilityCooldown = abilityCooldown;
        this.remainingCooldown = 0;
    }

    public void levelUp()
    {
        super.levelUp();
        this.remainingCooldown =0;
        this.getHealth().setCurrent(getHealth().getCurrent() + (WARRIOR_ADDITIONAL_HEAITH * this.level));
        this.attack += WARRIOR_ADDITIONAL_ATTACK * this.level;
        this.defense += WARRIOR_ADDITIONAL_DEFENSE * this.level;
    }
    //game tick
    public void gameTick(){
        this.remainingCooldown--;
    }


     public void useSA(){
        this.remainingCooldown = this.abilityCooldown;
        this.getHealth().setCurrent(Math.min(this.getHealth().getCurrent() + 10  * this.defense, this.getHealth().getCapacity()));
        // randomly hit enemy
     }

}
