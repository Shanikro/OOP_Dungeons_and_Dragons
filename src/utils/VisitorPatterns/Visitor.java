package utils.VisitorPatterns;
import model.tiles.*;
import model.tiles.units.enemies.*;
import model.tiles.units.players.*;
import utils.callbacks.MessageCallback;

public interface Visitor {
    MessageCallback visit (Wall wall);
    MessageCallback visit (Player player);
    MessageCallback visit (Enemy enemy);
    MessageCallback visit (Empty empty);
}
