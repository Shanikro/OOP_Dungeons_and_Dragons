package model.game;

import control.TileFactory;
import utils.callbacks.MessageCallback;

public class Game {

    private Level currentLevel;
    private String directoryPath;
    private final TileFactory factory = TileFactory.getInstance();
    private MessageCallback msg;


}
