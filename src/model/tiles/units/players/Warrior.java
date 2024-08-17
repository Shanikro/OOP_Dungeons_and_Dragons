package model.tiles.units.players;

import model.game.Board;
import utils.Position;
import model.tiles.Tile;
import utils.Health;
import model.tiles.units.enemies.Enemy;
import model.tiles.units.players.Player;
import utils.callbacks.MessageCallback;

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

    public Warrior(String name , Board board, int attackPoints, int defensePoints, int health, int abilityCooldown)
    {
        super(name,board,health,attackPoints,defensePoints);

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
        this.remainingCooldown =0;
        this.getHealth().setCurrent(getHealth().getCurrent() + (WARRIOR_ADDITIONAL_HEAITH * this.level));
        this.attack += WARRIOR_ADDITIONAL_ATTACK * this.level;
        this.defense += WARRIOR_ADDITIONAL_DEFENSE * this.level;

        //Calculate diff
        int attackDiff = getAttack() - attackB;
        int defenceDiff = getDefense() - defenceB;
        int healthDiff = health.getCurrent() - healthB;

        return () -> String.format("%s reached level %d: +%d Health, +%d Attack, +%d Defence +%d\n"
                , getName(), getLevel(), healthDiff, attackDiff, defenceDiff);
    }
    //game tick
    public void gameTick(){
        this.remainingCooldown = Math.max(remainingCooldown--,0);
    }

    @Override
    public MessageCallback useSA() {
        StringBuilder output = new StringBuilder();

        if (remainingCooldown > 0){
             output.append(getName()).append(String.format("tried to cast Avenger's Shield, but there is a cooldown: %s.\n", remainingCooldown));
             return ()-> printer.print(output.toString());
         }
         this.remainingCooldown = this.abilityCooldown;
         this.getHealth().setCurrent(Math.min(this.getHealth().getCurrent() + 10 * this.defense, this.getHealth().getCapacity()));
        output.append(getName()).append(" cast Avenger's Shield\n");

         List<Enemy> enemies = board.enemiesInRange(3);
        
         if (!enemies.isEmpty()) {
             int index = random.nextInt(enemies.size());
             Enemy target = enemies.get(index);
             int damage = (int) (0.1 * this.getHealth().getCapacity());
             int actualDamage = Math.max(0, damage - target.defend());
             target.getHealth().setCurrent(target.getHealth().getCurrent() - actualDamage);
             // System.out.println("Enemy " + target.getName() + " took " + actualDamage + " damage.");
         }
        return ()-> printer.print(output.toString());
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
}


