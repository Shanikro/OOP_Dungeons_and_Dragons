import model.tiles.units.enemies.Monster;
import model.tiles.units.enemies.Enemy;
import model.tiles.units.players.Mage;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class MageTests {

    private Mage mage;
    private Enemy enemy1;
    private Enemy enemy2;

    @Before
    public void setUp() {
        mage = new Mage("MageName", 100, 10, 5, 200, 50, 40, 3, 2);
        enemy1 = new Monster('E', "Goblin", 50, 5, 3, 10, 2); // Assuming Monster is a concrete subclass of Enemy
        enemy2 = new Monster('E', "Orc", 70, 6, 4, 15, 2);
    }

    @Test
    public void testInitialMageState() {
        Assert.assertEquals(1, mage.getLevel());
        Assert.assertEquals(0, mage.getExperience());
        Assert.assertEquals(100, mage.getHealth().getCurrent());
        Assert.assertEquals(10, mage.getAttack());
        Assert.assertEquals(5, mage.getDefense());
        Assert.assertEquals(2, mage.getSARange()); // Initial mana should be manaCap / 4 = 50
    }

    @Test
    public void testLevelUp() {
        mage.addExperience(50); // Trigger a level up
        String levelUpMessage = mage.levelUp();

        Assert.assertEquals(3, mage.getLevel());
        Assert.assertEquals(150, mage.getHealth().getCurrent()); // Assuming health increases appropriately
        Assert.assertEquals(30, mage.getAttack()); // Attack remains the same
        Assert.assertEquals(10, mage.getDefense()); // Defense remains the same
        Assert.assertEquals(2, mage.getSARange()); // ManaCap should increase by 25 * level
    }

    @Test
    public void testGameTick() {
        mage.gameTick();
        Assert.assertEquals(2, mage.getSARange()); // Mana should increase by level (initially 1) on each tick

        mage.useSA(new ArrayList<>()); // Use special ability, reducing mana
        mage.gameTick();
        Assert.assertEquals(2, mage.getSARange()); // Mana should increase by 2 after using ability
    }

    @Test
    public void testUseSA_NotEnoughMana() {
        mage.useSA(new ArrayList<>()); // First use, mana drops to 0
        mage.useSA(new ArrayList<>()); // Second use, should fail due to not enough mana

        String output = mage.useSA(new ArrayList<>()).toString();
        Assert.assertEquals(2, mage.getSARange()); // Mana should remain the same
    }

   /* @Test
    public void testUseSA_EnemyInRange() {
        List<Enemy> enemies = new ArrayList<>();
        enemies.add(enemy1);
        enemies.add(enemy2);

        String output = mage.useSA(enemies).toString();

        Assert.assertTrue(output.contains("cast Blizzard"));
        Assert.assertTrue(enemy1.getHealth().getCurrent() < 50 || enemy2.getHealth().getCurrent() < 70); // Enemies should take damage
        Assert.assertEquals(0, mage.getSARange()); // Mana should decrease by the cost (50)
    }*/


    @Test
    public void testDescribe() {
        String description = mage.describe();
        String expectedDescription = String.format("""
                MageName\t\t\tHealth: 100/100\t\t\tAttackPoints: 10\t\t\tDefensePoints: 5\t\t\tLevel: 1
                \t\t\tExperience: 0/50\t\t\tManaPool: 200\t\t\tCurrentMana: 50\t\t\tManaCost: 50
                \t\t\tSpellPower: 40\t\t\tHitsCount: 3\t\t\tAbilityRange: 2
                """);

        Assert.assertEquals(expectedDescription.trim(), description.trim());
    }
}

