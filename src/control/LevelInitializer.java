package control;

import model.tiles.Tile;
import model.tiles.units.enemies.Enemy;
import utils.Position;
import utils.generators.RandomGenerator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class LevelInitializer {

    private final TileFactory tileFactory = TileFactory.getInstance();
    private String levelsPath;
    private List<Enemy> enemeis;
    private int width;


    public LevelInitializer(String levelsPath){
        this.levelsPath = levelsPath;
        this.enemeis = new ArrayList<>();

    }

    public List<Tile> initLevel(int levelNum){
        List<String> lines;
        try {
            String levelFilePath = levelsPath + "/level" + levelNum + ".txt";
            lines = Files.readAllLines(Paths.get(levelFilePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        List<Tile> boardTiles = new ArrayList<>();
        this.width = getWidth(lines);

        int row = 0;
        for(String line : lines){
            int col = 0;
            for(char c : line.toCharArray()){
                Position position = new Position(col, row);
                switch(c) {
                    case '.':
                        boardTiles.add(tileFactory.produceEmpty(position));
                        break;
                    case '#':
                        boardTiles.add(tileFactory.produceWall(position));
                        break;
                    case '@':
                        boardTiles.add(tileFactory.producePlayer(position,new RandomGenerator()));
                        break;
                    default:
                        Enemy enemy = tileFactory.produceEnemy(c,position,new RandomGenerator());
                        boardTiles.add(enemy);
                        enemeis.add(enemy);
                        break;
                }
                col++;
            }
            row++;
        }

        return boardTiles;
        }

    private int getWidth(List<String> lines) {
        return lines.get(0).length();
    }

    public List<Enemy> getEnemeis() {
        return this.enemeis;
    }

    public int getWidth(){
        return this.width;
    }

}

