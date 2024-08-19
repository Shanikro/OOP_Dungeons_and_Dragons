import model.tiles.units.enemies.*;
import model.tiles.units.players.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import utils.Position;

public class TrapTests {

    private Trap trap;
    private Player player;

    @Before
    public void setUp() {
        // Assuming you have a concrete subclass of Player, like Warrior.
        player = new Warrior("PlayerName", 100, 20, 10, 3); // Replace with your actual subclass
        trap = new Trap('T', "Spike Trap", 30, 15, 5, 50, 3, 2); // Creating a Trap instance
        trap.initialize(new Position(5, 5)); // Setting trap's initial position
        player.initialize(new Position(7, 5)); // Setting player's initial position
    }

    @Test
    public void testInitialTrapState() {
        Assert.assertEquals(30, trap.getHealth().getCurrent()); // Assuming getHealth() returns a Health object
        Assert.assertEquals(15, trap.getAttack());
        Assert.assertEquals(5, trap.getDefense());
        Assert.assertEquals(50, trap.getExperienceValue());
        Assert.assertTrue(trap.toString().equals("T")); // Initial state should be visible
    }


    @Test
    public void testDescribe() {
        String description = trap.describe();
        String expectedDescription = String.format("""
                Spike Trap\t\t\tHealth: 30/30\t\t\tAttack: 15\t\t\tDefense: 5\t\t\tExperience: 50
                \t\t\tVisibilityTime: 3\t\t\tInVisibilityTime: 2
                """);

        Assert.assertEquals(expectedDescription.trim(), description.trim());
    }

    @Test
    public void testSwapPosition() {
        // Trap should not swap position with another tile
        Position originalPosition = trap.getPosition();
        trap.swapPosition(player);
        Position newPosition = trap.getPosition();

        Assert.assertEquals(originalPosition, newPosition); // Position should not change
    }
}
