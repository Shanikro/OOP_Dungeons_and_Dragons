package model.tiles.units.players;

import model.tiles.units.enemies.Enemy;

import java.util.List;

public class Rogue extends Player{
    private final int MAX_ENERGY = 100;
    private final int ADDITIONAL_ATTACK = 3;
    private int cost;
    private int currEnergy;

    public Rogue(String name, int health, int attack, int defense, int cost){
        super(name, health, attack, defense);
        this.cost = cost;
        this.currEnergy = MAX_ENERGY;
    }

    public void levelUp(){
        super.levelUp();
        this.currEnergy = MAX_ENERGY;
        this.attack += ADDITIONAL_ATTACK * this.level;
    }

    //game tick
    public void gameTick(){
        this.currEnergy = Math.min(this.currEnergy +10, MAX_ENERGY);
    }

    //use special ability
    public void useSA(List<Enemy> enemies){
        this.currEnergy -= this.cost;
        for(Enemy enemy : enemies){
            if(this.position.range(enemy.getPosition())<2){
                int damage = this.attack();
                int actualDamage = Math.max(0, damage - enemy.defend());
                enemy.getHealth().setCurrent(enemy.getHealth().getCurrent() - actualDamage);

            }
        }


    }
}
