package model.tiles.units.players;

import utils.Position;
import model.tiles.Tile;
import utils.Health;
import model.tiles.units.enemies.Enemy;
import model.tiles.units.players.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Warrior extends Player {

    private final int WARRIOR_ADDITIONAL_HEAITH = 5;
    private final int WARRIOR_ADDITIONAL_ATTACK = 2;
    private final int WARRIOR_ADDITIONAL_DEFENSE = 1;

    private int abilityCooldown;
    private int remainingCooldown;
    private Random random;

    public Warrior(String name ,  int attackPoints, int defensePoints, int health, int abilityCooldown)
    {
        super(name,health,attackPoints,defensePoints);

        this.abilityCooldown = abilityCooldown;
        this.remainingCooldown = 0;
        this.random = new Random();
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


     public void useSA(List<Enemy> enemies) {
         this.remainingCooldown = this.abilityCooldown;
         this.getHealth().setCurrent(Math.min(this.getHealth().getCurrent() + 10 * this.defense, this.getHealth().getCapacity()));

         List<Enemy> enemiesInRange = new ArrayList<>();
         for (Enemy enemy : enemies) {
             if (this.position.range(enemy.getPosition()) < 3) {
                 enemiesInRange.add(enemy);
             }
         }
         if (!enemiesInRange.isEmpty()) {
             int index = random.nextInt(enemiesInRange.size());
             Enemy target = enemiesInRange.get(index);
             int damage = (int) (0.1 * this.getHealth().getCapacity());
             int actualDamage = Math.max(0, damage - target.defend());
             target.getHealth().setCurrent(target.getHealth().getCurrent() - actualDamage);
             // System.out.println("Enemy " + target.getName() + " took " + actualDamage + " damage.");
         }
     }
}


