import model.tiles.units.enemies.Monster;
import model.tiles.units.enemies.Enemy;
import model.tiles.units.players.Warrior;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import utils.callbacks.MessageCallback;

import java.util.ArrayList;
import java.util.List;

public class WarriorTests {

    private Warrior warrior;
    private Enemy enemy;

    @Before
    public void setUp() {
        warrior = new Warrior("WarriorName", 20, 10, 100, 3);
        enemy = new Monster('E', "Goblin", 50, 5, 3, 10, 2); // Assuming Monster is a concrete subclass of Enemy
    }

    @Test
    public void testInitialWarriorState() {
        Assert.assertEquals(1, warrior.getLevel());
        Assert.assertEquals(0, warrior.getExperience());
        Assert.assertEquals(100, warrior.getHealth().getCurrent()); // Assuming getHealth() returns a Health object
        Assert.assertEquals(20, warrior.getAttack());
        Assert.assertEquals(10, warrior.getDefense());
    }

    @Test
    public void testLevelUp() {
        warrior.addExperience(50); // Trigger a level up
        String levelUpMessage = warrior.levelUp();

        Assert.assertEquals(3, warrior.getLevel());
        Assert.assertEquals(165, warrior.getHealth().getCurrent()); // 100 + 25 (5 * 2 level)
        Assert.assertEquals(50, warrior.getAttack()); // 20 + 4 (2 * 2 level)
        Assert.assertEquals(20, warrior.getDefense()); // 10 + 2 (1 * 2 level)
    }

    @Test
    public void testGameTick() {

        Assert.assertEquals(3, warrior.getSARange()); // Cooldown should still be 0

        warrior.useSA(new ArrayList<>()); // Use special ability, triggering cooldown
        warrior.gameTick();
        Assert.assertEquals(3, warrior.getSARange());
    }



    @Test
    public void testDescribe() {
        String description = warrior.describe();
        String expectedDescription = String.format("""
                WarriorName\t\t\tHealth: 100/100\t\t\tAttackPoints: 20\t\t\tDefensePoints: 10\t\t\tLevel: 1
                \t\t\tExperience: 0/50\t\t\tCooldown: 0/3
                """);

        Assert.assertEquals(expectedDescription.trim(), description.trim());
    }
}
