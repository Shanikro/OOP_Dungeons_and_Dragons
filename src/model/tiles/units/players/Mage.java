package model.tiles.units.players;

import model.game.Board;
import model.tiles.units.enemies.Enemy;
import utils.callbacks.MessageCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Mage  extends Player{

    private final int MAGE_ADDITIONAL_MANA_CAP = 25;
    private final int WARRIOR_ADDITIONAL_SPELL_POWER = 10;
    private final int WARRIOR_ADDITIONAL_DEFENSE = 1;

    private int manaCap;
    private int manaCurr;
    private int manaCost;
    private int spellPower;
    private int hitsCount;
    private int abilityRange;
    private Random random;


    public Mage(String name, Board board, int health, int attack, int defense, int manaCap, int manaCost, int spellPower, int hitsCount, int abilityRange) {
        super(name, board, health, attack, defense);
        this.manaCap = manaCap;
        this.manaCurr = manaCap/4;
        this.manaCost = manaCost;
        this.spellPower = spellPower;
        this. hitsCount = hitsCount;
        this.abilityRange = abilityRange;
        this.random = new Random();
    }

    public void levelUp(){
        super.levelUp();
        this.manaCap += MAGE_ADDITIONAL_MANA_CAP * this.level;
        this.manaCurr = Math.min( this.manaCurr + (manaCap/4), manaCap);
        this.spellPower += WARRIOR_ADDITIONAL_SPELL_POWER * this.level;
    }

    //game tick
    public void gameTick(){
        this.manaCurr = Math.min( this.manaCap, this.manaCurr + this.level);
    }

    @Override
    //use special ability
    public MessageCallback useSA(List<Enemy> enemies){
        this.manaCurr -= this.manaCost ;
        int hits = 0;
        while (hits < hitsCount && hasLivingEnemyInRange(enemies)) {
            Enemy enemy = selectRandomEnemyInRange(enemies);
            if (enemy != null) {
                int damage = this.spellPower;
                int actualDamage = Math.max(0, damage - enemy.defend());
                enemy.getHealth().setCurrent(enemy.getHealth().getCurrent() - actualDamage);
               // System.out.println("Enemy " + enemy.getName() + " took " + actualDamage + " damage.");
                hits++;
            }
        }

    }
    private boolean hasLivingEnemyInRange(List<Enemy> enemies) {
        for (Enemy enemy : enemies) {
            if (enemy.getHealth().getCurrent() > 0 && this.position.range(enemy.getPosition()) < this.abilityRange) {
                return true;
            }
        }
        return false;
    }

    private Enemy selectRandomEnemyInRange(List<Enemy> enemies) {
        List<Enemy> enemiesInRange = new ArrayList<>();
        for (Enemy enemy : enemies) {
            if (enemy.getHealth().getCurrent() > 0 && this.position.range(enemy.getPosition()) < this.abilityRange) {
                enemiesInRange.add(enemy);
            }
        }

        if (enemiesInRange.isEmpty()) {
            return null;
        }

        int index = random.nextInt(enemiesInRange.size());
        return enemiesInRange.get(index);
    }
}
