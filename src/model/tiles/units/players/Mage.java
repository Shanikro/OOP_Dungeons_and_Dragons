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

    @Override
    public MessageCallback levelUp(){

        //Before level up
        int attackB = getAttack();
        int defenceB = getDefense();
        int healthB = health.getCurrent();
        int manaB = manaCap;
        int spellB = spellPower;

        super.levelUp();
        this.manaCap += MAGE_ADDITIONAL_MANA_CAP * this.level;
        this.manaCurr = Math.min( this.manaCurr + (manaCap/4), manaCap);
        this.spellPower += WARRIOR_ADDITIONAL_SPELL_POWER * this.level;

        //Calculate diff
        int attackDiff = getAttack() - attackB;
        int defenceDiff = getDefense() - defenceB;
        int healthDiff = health.getCurrent() - healthB;
        int manaDiff =  manaCap - manaB;
        int spellDiff = spellPower - spellB;

        return () -> String.format("%s reached level %d: +%d Health, +%d Attack, +%d Defence +%d maximum mana, +%d spell power\n"
                , getName(), getLevel(), healthDiff, attackDiff, defenceDiff, manaDiff, spellDiff);
    }

    //game tick
    public void gameTick(){
        this.manaCurr = Math.min( this.manaCap, this.manaCurr + this.level);
    }

    @Override
    //use special ability
    public MessageCallback useSA(){
        StringBuilder output = new StringBuilder();

        if (manaCurr < manaCost){
            output.append(getName()).append(String.format("tried to cast Blizzard, but there was'nt enougt mana: %s.\n", manaCurr));
            return ()-> printer.print(output.toString());
        }

        this.manaCurr -= this.manaCost ;
        int hits = 0;
        List<Enemy> enemies = board.enemiesInRange(abilityRange);

        output.append(getName()).append(" cast Blizzard\n");

        while (hits < hitsCount && hasLivingEnemyInRange(enemies)) {
            Enemy enemy = selectRandomEnemyInRange(enemies);
            if (enemy != null) {
                int damage = this.spellPower;
                int actualDamage = Math.max(0, damage - enemy.defend());
                enemy.getHealth().setCurrent(enemy.getHealth().getCurrent() - actualDamage);
                hits++;
            }
            else{
                output.append(String.format("There is no enemy within %s range: %d.\n", getName(), abilityRange));
            }
        }

        return ()-> printer.print(output.toString());
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

    @Override
    public String describe()
    {
        return String.format("""
                        %s\t\t\tHealth: %d/%d\t\t\tAttackPoints: %d\t\t\tDefensePoints: %d\t\t\tLevel: %d
                        \t\t\tExperience: %d/%d\t\t\tManaPool: %d\t\t\tCurrentMana: %d\t\t\tManaCost: %d
                        \t\t\tSpellPower: %d\t\t\tHitsCount: %d\t\t\tAbilityRange: %d
                        """,
                name, health.getCurrent(), getAttack(), getDefense(), getLevel(), getExperience(), manaCap, manaCurr, manaCost, spellPower, hitsCount, abilityRange);

    }

}
