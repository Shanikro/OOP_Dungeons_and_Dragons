package control.initializers;

import utils.Position;
import utils.generators.FixedGenerator;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class LevelInitializer {
    private File directory;
    private File currentFile;
    private Iterator<File> directoryIterator;
    private Scanner fileReader;

    private int playerID;
    private final TileFactory tileFactory = TileFactory.getInstance();


    public LevelInitializer(int playerID){
        this.playerID = playerID;
    }

    public void initLevel(String levelPath){
        List<String> lines;
        try {
            lines = Files.readAllLines(Paths.get(levelPath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        int row = 0;
        for(String line : lines){
            int col = 0;
            for(char c : line.toCharArray()){
                Position position = new Position(col, row);
                switch(c) {
                    case '.':
                        tileFactory.produceEmpty(position);
                        break;
                    case '#':
                        tileFactory.produceWall(position);
                        break;
                    case '@':
                        tileFactory.producePlayer(playerID,);
                        break;
                    default:
                        tileFactory.produceEnemy(c, position, b, new FixedGenerator(),);
                        break;
                }
                col++;
            }
            row++;
        }
        }
    }
}
