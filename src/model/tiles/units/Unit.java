package model.tiles.units;

import model.game.Board;
import model.tiles.Empty;
import model.tiles.Tile;
import model.tiles.Wall;
import model.tiles.units.enemies.Enemy;
import model.tiles.units.players.*;
import utils.Health;
import utils.Position;
import utils.callbacks.MessageCallback;
import utils.generators.Generator;

public abstract class Unit extends Tile {
    protected String name;
    protected Health health;
    protected int attack;
    protected int defense;

    protected Generator generator;

    public Unit(char tile, String name, int hitPoints, int attack, int defense) {
        super(tile);
        this.name = name;
        this.health = new Health(hitPoints);
        this.attack = attack;
        this.defense = defense;
    }

    public void initialize(Position p, Generator generator){
        super.initialize(p);
        this.generator = generator;
    }

    public int attack(){
        return generator.generate(attack);
    }

    public int defend(){
        return generator.generate(defense);
    }

    public boolean isAlive(){
        return health.getCurrent() > 0;
    }

    public String battle(Unit other) {
        StringBuilder output = new StringBuilder();

        //Starting a Battle
        output.append(String.format("%s engaged in combat with %s\n%s%s",
                getName(), other.getName(),
                describe(), other.describe()));

        //Attack
        int attack = this.attack();
        output.append(String.format("%s rolled %d attack points\n", getName(), attack));

        //Defence
        int defence = other.defend();
        output.append(String.format("%s rolled %d defence points\n", other.getName(), defence));

        //Damage
        int damageTaken = attack - defence;
        if (damageTaken > 0) {
            int damageDealt = other.getHealth().takeDamage(damageTaken);
            output.append(String.format("%s dealt %d damage to %s\n", getName(), damageDealt, other.getName()));
        } else {
            output.append(String.format("%s's attack was fully defended by %s\n", getName(), other.getName()));
        }

        return output.toString();
    }

    @Override
    public MessageCallback visit(Empty empty)
    {
        swapPosition(empty);
        return (s)->{};
    }

    @Override
    public MessageCallback visit(Wall wall)
    {
        return (s)->{};
    }

    public Health getHealth(){ return health;}

    public String getName(){ return name;}

    public int getAttack() {return attack;}

    public int getDefense() {return defense;}

    public abstract void onDeath();

    public abstract String describe();




}
