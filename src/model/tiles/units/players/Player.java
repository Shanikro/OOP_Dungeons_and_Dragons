package model.tiles.units.players;

import model.game.Board;
import model.tiles.Tile;
import model.tiles.units.Unit;
import model.tiles.units.enemies.Enemy;
import utils.callbacks.MessageCallback;

import java.util.List;

public abstract class Player extends Unit {
    public static final char PLAYER_TILE = '@';
    protected static final int LEVEL_REQUIREMENT = 50;
    protected static final int HEALTH_GAIN = 10;
    protected static final int ATTACK_GAIN = 4;
    protected static final int DEFENSE_GAIN = 1;

    protected int level;
    protected int experience;

    public Player(String name, int hitPoints, int attack, int defense) {
        super(PLAYER_TILE, name, hitPoints, attack, defense);
        this.level = 1;
        this.experience = 0;
    }

    public void addExperience(int experienceValue){
        this.experience += experienceValue;
        while (experience >= levelRequirement()) {
            levelUp();
        }
    }

    public MessageCallback levelUp(){
        this.experience -= levelRequirement();
        this.level++;
        int healthGain = healthGain();
        int attackGain = attackGain();
        int defenseGain = defenseGain();
        health.increaseMax(healthGain);
        health.heal();
        attack += attackGain;
        defense += defenseGain;

        return (s) -> {};
    }

    protected int levelRequirement(){
        return LEVEL_REQUIREMENT * level;
    }

    protected int healthGain(){
        return HEALTH_GAIN * level;
    }

    protected int attackGain(){
        return ATTACK_GAIN * level;
    }

    protected int defenseGain(){
        return DEFENSE_GAIN * level;
    }

    @Override
    public MessageCallback accept(Tile tile) {
        return tile.visit(this);
    }

    @Override
    public MessageCallback visit(Player p){
        return (s)->{};
    }

    @Override
    public MessageCallback visit(Enemy e) {
        StringBuilder output = new StringBuilder();

        //Battle
        String battleString = battle(e);
        output.append(battleString);

        //Enemy dead
        if (!e.isAlive()) {
            addExperience(e.getExperienceValue());
            output.append(String.format("%s died. %s gained %d experience\n", e.getName(), getName(), e.getExperienceValue()));
            e.swapPosition(this);
        }

        MessageCallback callback = (s) -> printer.print(output.toString());
        callback.send("");
        return callback;
    }

    @Override
    public void onDeath() {
        if(!isAlive())
            tile='X';
    }

    public int getLevel() {return level;}

    public int getExperience() {return experience;}

    public abstract void gameTick();

    public abstract MessageCallback useSA(List<Enemy> enemies);

    public abstract int getSARange();

    @Override
    public String toString(){
        return "" + PLAYER_TILE;
    }


}
