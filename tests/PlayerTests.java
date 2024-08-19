import model.tiles.units.enemies.*;
import model.tiles.units.players.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PlayerTests {

    private Player player;
    private Enemy enemy;

    @Before
    public void setUp() {
        // Assuming you have a concrete subclass of Player, like Warrior, and a concrete subclass of Enemy, like Monster.
        player = new Warrior("PlayerName", 100, 20, 10, 3); // Replace with your actual subclass
        enemy = new Monster('E', "Goblin", 50, 10, 5, 40, 2); // Replace with your actual subclass
    }

    @Test
    public void testInitialPlayerState() {
        Assert.assertEquals(1, player.getLevel());
        Assert.assertEquals(0, player.getExperience());
        Assert.assertEquals(10, player.getHealth().getCurrent()); // Assuming getHealth() returns a Health object
        Assert.assertEquals(100, player.getAttack());
        Assert.assertEquals(20, player.getDefense());
    }

    @Test
    public void testAddExperience() {
        player.addExperience(10);
        Assert.assertEquals(1, player.getLevel()); // Level should not  increase with 10 exp
        Assert.assertEquals(10, player.getExperience());

        player.addExperience(50);
        Assert.assertEquals(2, player.getLevel()); // Level should increase now
        Assert.assertEquals(10, player.getExperience()); // Should be experience after level up
    }

    @Test
    public void testLevelUp() {
        player.addExperience(50);
        Assert.assertEquals(2, player.getLevel());
        Assert.assertEquals(0, player.getExperience()); // Experience should reset after level up
        Assert.assertEquals(40, player.getHealth().getCurrent()); // 100 + 10 * level
        Assert.assertEquals(112, player.getAttack()); // 20 + 4 * level
        Assert.assertEquals(24, player.getDefense()); // 10 + 1 * level
    }

    @Test
    public void testPlayerVsEnemyBattle() {
        player.visit(enemy);
        // Assuming your battle logic reduces health
        Assert.assertTrue(player.getHealth().getCurrent() <= 100);
        Assert.assertTrue(enemy.getHealth().getCurrent() <= 50);

        if (!enemy.isAlive()) {
            Assert.assertTrue(player.getExperience() > 0);
        }
    }

    @Test
    public void testOnDeath() {
        player.getHealth().setCurrent(0); // Simulate player death
        player.onDeath();
        Assert.assertEquals('X', player.toString().charAt(0)); // Tile should be 'X' after death
    }
}
