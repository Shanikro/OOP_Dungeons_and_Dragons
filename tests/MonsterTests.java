import model.tiles.units.enemies.*;
import model.tiles.units.players.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import utils.Position;

public class MonsterTests {

    private Monster monster;
    private Player player;

    @Before
    public void setUp() {
        // Assuming you have a concrete subclass of Player, like Warrior.
        player = new Warrior("PlayerName", 100, 20, 10, 3); // Replace with your actual subclass
        monster = new Monster('M', "Goblin", 50, 10, 5, 40, 3); // Creating a Monster instance
        monster.initialize(new Position(5, 5)); // Setting monster's initial position
        player.initialize(new Position(8, 5)); // Setting player's initial position
    }

    @Test
    public void testInitialMonsterState() {
        Assert.assertEquals(50, monster.getHealth().getCurrent()); // Assuming getHealth() returns a Health object
        Assert.assertEquals(10, monster.getAttack());
        Assert.assertEquals(5, monster.getDefense());
        Assert.assertEquals(40, monster.getExperienceValue());
    }


    @Test
    public void testTakeTurn_PlayerDead() {
        // Simulate player death
        player.getHealth().setCurrent(0);

        Position oldPosition = monster.getPosition();
        Position newPosition = monster.takeTurn(player);

        // Monster should stay in place since the player is dead
        Assert.assertEquals(oldPosition, newPosition);
    }


    @Test
    public void testDescribe() {
        String description = monster.describe();
        String expectedDescription = String.format("""
                Goblin\t\t\tHealth: 50/50\t\t\tAttack: 10\t\t\tDefense: 5\t\t\tExperience: 40
                \t\t\tVisionRange: 3
                """);

        Assert.assertEquals(expectedDescription.trim(), description.trim());
    }
}

