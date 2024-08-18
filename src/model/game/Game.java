package model.game;

import control.LevelInitializer;
import control.TileFactory;
import model.tiles.units.players.Player;
import utils.callbacks.MessageCallback;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Game {

    private MessageCallback msg;
    private Player player;
    private Level currentLevel;
    private String directoryPath;
    private int levelsAmount;
    private final TileFactory factory = TileFactory.getInstance();

    public Game(String directoryPath,MessageCallback callback){
        this.msg = callback ;
        this.directoryPath = directoryPath;

        this.levelsAmount = getLevelNumber(directoryPath);

        Scanner scanner = new Scanner(System.in);
        msg.send("Select player:\n");
        factory.printPlayers();

        int playerChosen = scanner.nextInt();
        while(playerChosen < 1 || playerChosen > 6) {
            msg.send("Select player:\n");
            factory.printPlayers();
            playerChosen = scanner.nextInt();
        }

            setPlayer(factory.producePlayer(playerChosen));
            msg.send("\nYou have selected: " + player.getName() + "\n");

        }


    public void start(){
        Scanner scanner = new Scanner(System.in);
        int levelNumber = 1;
        this.currentLevel = new Level(levelNumber,msg,new LevelInitializer(directoryPath));

        while (!currentLevel.gameOver() && levelNumber < levelsAmount) {

            while (!currentLevel.gameOver() && !currentLevel.isOver()) {
                msg.send(currentLevel.getBoard().toString());
                String action = scanner.nextLine();
                currentLevel.gameTick(action);
            }

            levelNumber++;
        }

        msg.send("\n GAME OVER!!!");
    }

    private int getLevelNumber(String directoryPath) {
        Path path = Paths.get(directoryPath);
        int fileCount = 0;

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(path)) {
            for (Path entry : stream) {
                if (Files.isRegularFile(entry)) {
                    fileCount++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileCount;
    }

    private void setPlayer(Player player) {
        this.player = player;
    }
}

