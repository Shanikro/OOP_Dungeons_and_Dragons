package model.tiles.units.players;

import model.game.Board;
import model.tiles.units.enemies.Enemy;
import utils.callbacks.MessageCallback;

import java.util.List;

public class Rogue extends Player{
    private final int MAX_ENERGY = 100;
    private final int ADDITIONAL_ATTACK = 3;
    private final int ABILITY_RANGE = 2;
    private final int TICK_ENERGY = 10;
    private int cost;
    private int currEnergy;

    public Rogue(String name, int health, int attack, int defense, int cost){
        super(name, health, attack, defense);
        this.cost = cost;
        this.currEnergy = MAX_ENERGY;
    }

    @Override
    public MessageCallback levelUp()
    {
        //Before level up
        int attackB = getAttack();
        int defenceB = getDefense();
        int healthB = health.getCurrent();

        super.levelUp();
        this.currEnergy = MAX_ENERGY;
        this.attack += ADDITIONAL_ATTACK * this.level;;

        //Calculate diff
        int attackDiff = getAttack() - attackB;
        int defenceDiff = getDefense() - defenceB;
        int healthDiff = health.getCurrent() - healthB;

        return (s) -> String.format("%s reached level %d: +%d Health, +%d Attack, +%d Defence +%d\n"
                , getName(), getLevel(), healthDiff, attackDiff, defenceDiff);
    }

    //game tick
    public void gameTick(){
        this.currEnergy = Math.min(this.currEnergy + this.TICK_ENERGY, this.MAX_ENERGY);
    }

    @Override
    //use special ability
    public MessageCallback useSA(List<Enemy> enemies) {
        StringBuilder output = new StringBuilder();

        if (currEnergy < cost) {
            output.append(getName()).append(String.format(" tried to cast Fan Of Knives, but there was'nt enougt energy: %s.\n", currEnergy));
            return (s) -> printer.print(output.toString());
        }

        this.currEnergy -= this.cost;

        output.append(getName()).append(" cast Fan Of Knives.\n");

        if (enemies.size() > 0) {
            for (Enemy enemy : enemies) {
                int damage = this.attack();
                int actualDamage = Math.max(0, damage - enemy.defend());
                enemy.getHealth().setCurrent(enemy.getHealth().getCurrent() - actualDamage);
                }
            }

        else {
            output.append(String.format("There is no enemy within %s range: %d.\n", getName(), ABILITY_RANGE));
        }

        MessageCallback callback = (s) -> printer.print(output.toString());
        callback.send("");
        return callback;
    }

    @Override
    public String describe()
    {
        return String.format("""
                        %s\t\t\tHealth: %d/%d\t\t\tAttackPoints: %d\t\t\tDefensePoints: %d
                        \t\t\tLevel: %d\t\t\tExperience: %d/%d\t\t\tCost: %d\t\t\tCurrentEnergy: %d
                        """,
                name, health.getCurrent(), health.getCapacity(), getAttack(), getDefense(), getLevel(), getExperience(),
                levelRequirement(), cost, currEnergy);

    }

    @Override
    public int getSARange(){
        return ABILITY_RANGE;
    }

}
