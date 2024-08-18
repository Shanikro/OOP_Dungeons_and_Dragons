package utils.VisitorPatterns;

import model.tiles.Tile;
import utils.callbacks.MessageCallback;

public interface Visited {
    MessageCallback accept(Tile visitor);
}
