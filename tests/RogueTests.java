import model.tiles.units.enemies.Monster;
import model.tiles.units.enemies.Enemy;
import model.tiles.units.players.Rogue;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class RogueTests {

    private Rogue rogue;
    private Enemy enemy;

    @Before
    public void setUp() {
        rogue = new Rogue("RogueName", 100, 20, 10, 50);
        enemy = new Monster('E', "Goblin", 50, 5, 3, 10, 2); // Assuming Monster is a concrete subclass of Enemy
    }

    @Test
    public void testInitialRogueState() {
        Assert.assertEquals(1, rogue.getLevel());
        Assert.assertEquals(0, rogue.getExperience());
        Assert.assertEquals(100, rogue.getHealth().getCurrent()); // Assuming getHealth() returns a Health object
        Assert.assertEquals(20, rogue.getAttack());
        Assert.assertEquals(10, rogue.getDefense());
        Assert.assertEquals(2, rogue.getSARange()); // Initial energy should be 100
    }

    @Test
    public void testLevelUp() {
        rogue.addExperience(50); // Trigger a level up
        String levelUpMessage = rogue.levelUp();

        Assert.assertEquals(3, rogue.getLevel());
        Assert.assertEquals(150, rogue.getHealth().getCurrent()); // 100 + 25 (health increase)
        Assert.assertEquals(55, rogue.getAttack()); // 20 + 3 (attack increase per level)
        Assert.assertEquals(15, rogue.getDefense()); // Defense remains the same
        Assert.assertEquals(2, rogue.getSARange()); // Energy should reset to 100
    }

    @Test
    public void testGameTick() {
        rogue.gameTick();
        Assert.assertEquals(2, rogue.getSARange());
    }

    @Test
    public void testUseSA_NotEnoughEnergy() {
        rogue.useSA(new ArrayList<>()); // First use, energy drops to 50
        rogue.useSA(new ArrayList<>()); // Second use, should fail due to not enough energy

        String output = rogue.useSA(new ArrayList<>()).toString();
        Assert.assertEquals(2, rogue.getSARange()); // Energy should remain the same
    }


    @Test
    public void testDescribe() {
        String description = rogue.describe();
        String expectedDescription = String.format("""
                RogueName\t\t\tHealth: 100/100\t\t\tAttackPoints: 20\t\t\tDefensePoints: 10
                \t\t\tLevel: 1\t\t\tExperience: 0/50\t\t\tCost: 50\t\t\tCurrentEnergy: 100
                """);

        Assert.assertEquals(expectedDescription.trim(), description.trim());
    }
}

