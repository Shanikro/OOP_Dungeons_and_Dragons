package model.tiles.units.players;

import model.game.Board;
import model.tiles.units.enemies.Enemy;
import utils.callbacks.MessageCallback;

import java.util.List;
import java.util.Random;


public class Warrior extends Player {

    private final int WARRIOR_ADDITIONAL_HEAITH = 5;
    private final int WARRIOR_ADDITIONAL_ATTACK = 2;
    private final int WARRIOR_ADDITIONAL_DEFENSE = 1;
    private final int ABILITY_RANGE = 3;

    private int abilityCooldown;
    private int remainingCooldown;
    private Random random;

    public Warrior(String name, int attackPoints, int defensePoints, int health, int abilityCooldown)
    {
        super(name ,health,attackPoints,defensePoints);

        this.abilityCooldown = abilityCooldown;
        this.remainingCooldown = 0;
        this.random = new Random();
    }

    @Override
    public MessageCallback levelUp()
    {
        //Before level up
        int attackB = getAttack();
        int defenceB = getDefense();
        int healthB = health.getCurrent();

        super.levelUp();
        this.remainingCooldown = 0;
        this.getHealth().setCurrent(getHealth().getCurrent() + (WARRIOR_ADDITIONAL_HEAITH * this.level));
        this.attack += WARRIOR_ADDITIONAL_ATTACK * this.level;
        this.defense += WARRIOR_ADDITIONAL_DEFENSE * this.level;

        //Calculate diff
        int attackDiff = getAttack() - attackB;
        int defenceDiff = getDefense() - defenceB;
        int healthDiff = health.getCurrent() - healthB;

        return (s) -> String.format("%s reached level %d: +%d Health, +%d Attack, +%d Defence +%d\n"
                , getName(), getLevel(), healthDiff, attackDiff, defenceDiff);
    }
    //game tick
    public void gameTick(){
        this.remainingCooldown = Math.max(remainingCooldown--,0);
    }

    @Override
    public MessageCallback useSA(List<Enemy> enemies) {
        StringBuilder output = new StringBuilder();

        if (remainingCooldown > 0){
             output.append(getName()).append(String.format(" tried to cast Avenger's Shield, but there is a cooldown: %s.\n", remainingCooldown));
             return (s)-> printer.print(output.toString());
         }

        this.remainingCooldown = this.abilityCooldown;
        this.getHealth().setCurrent(Math.min(this.getHealth().getCurrent() + (10 * this.defense) , this.getHealth().getCapacity()));
        output.append(getName()).append(" cast Avenger's Shield\n");

         if (!enemies.isEmpty()) {
             int index = random.nextInt(enemies.size());
             Enemy enemy = enemies.get(index);
             int damage = this.getHealth().getCapacity() / 10;
             int actualDamage = Math.max(0, damage - enemy.defend());
             enemy.getHealth().setCurrent(enemy.getHealth().getCurrent() - actualDamage);
         }

         else {
             output.append(String.format("There is no enemy within %s range: %d.\n", getName(), ABILITY_RANGE));
         }

        return (s)-> printer.print(output.toString());
     }
    @Override
    public String describe()
    {
        return String.format("""
                        %s\t\t\tHealth: %d/%d\t\t\tAttackPoints: %d\t\t\tDefensePoints: %d\t\t\tLevel: %d
                        \t\t\tExperience: %d/%d\t\t\tCooldown: %d/%d
                        """,
                name, health.getCurrent(), health.getCapacity(), getAttack(), getDefense(), getLevel(), getExperience(), levelRequirement(), remainingCooldown, abilityCooldown);

    }

    @Override
    public int getSARange(){
        return ABILITY_RANGE;
    }

}


