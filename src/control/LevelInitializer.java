package control;

import utils.Position;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class LevelInitializer {

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
                        tileFactory.producePlayer(playerID);
                        break;
                    default:
                        tileFactory.produceEnemy(c, position);
                        break;
                }
                col++;
            }
            row++;
        }
        }
    }

