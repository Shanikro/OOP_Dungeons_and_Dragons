package model.game;

import control.TileFactory;
import model.tiles.units.players.Player;
import utils.callbacks.MessageCallback;
import utils.printer.PrinterC;
import java.util.Scanner;
import control.TileFactory;
import utils.callbacks.MessageCallback;

public class Game {

    private MessageCallback msg;
    private Player player;
    private Level currentLevel;
    private String levelPath;
    private final TileFactory factory = TileFactory.getInstance();
    private final PrinterC printer = PrinterC.getInstance();


    public Game(String levelPath,MessageCallback callback){
        this.msg = callback ;
        this.currentLevel = new Level(levelPath,callback);
        this.levelPath = levelPath;

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



    /*public void start(){
        Scanner scanner = new Scanner(System.in);
        int levelNumber = 1;

        while (!currentLevel.gameOver() && currentLevel.hasLevel(directoryPath + "\\level" + levelNumber + ".txt")) {
            currentLevel.loadLevel(directoryPath + "\\level" + levelNumber + ".txt");

            while (!currentLevel.gameOver() && !currentLevel.isOver()) {
                EmptyRow();
                currentLevel.levelInfo();
                msg.send("Your turn - ");
                String userAction = scanner.nextLine();
                currentLevel.gameTick(userAction);
            }

            levelNumber++;
        }

        msg.send("\n GAME OVER!!!");
    }
    */
    private void setPlayer(Player player) {
        this.player = player;
    }
}

