import model.tiles.units.enemies.*;
import model.tiles.units.players.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import utils.Position;

public class EnemyTests {
    private Enemy enemy;
    private Player player;

    @Before
    public void setUp() {
        // Assuming you have a concrete subclass of Enemy, like Monster, and a concrete subclass of Player, like Warrior.
        enemy = new Monster('E', "Goblin", 50, 10, 5, 40, 2); // Replace with your actual subclass
        player = new Warrior("PlayerName", 100, 20, 10, 3); // Replace with your actual subclass
    }

    @Test
    public void testInitialEnemyState() {
        Assert.assertEquals(40, enemy.getExperienceValue());
        Assert.assertEquals(50, enemy.getHealth().getCurrent()); // Assuming getHealth() returns a Health object
        Assert.assertEquals(10, enemy.getAttack());
        Assert.assertEquals(5, enemy.getDefense());
    }


    @Test
    public void testEnemyDeath() {
        enemy.getHealth().setCurrent(0); // Simulate enemy death
        enemy.onDeath();
        // No change in tile, but you may want to assert other death-related logic
        Assert.assertEquals('E', enemy.toString().charAt(0)); // Tile should remain as 'E'
    }

}
