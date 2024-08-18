import model.game.Game;
import model.tiles.units.Unit;
import model.tiles.units.players.Player;
import utils.Position;
import utils.callbacks.MessageCallback;
import utils.generators.FixedGenerator;
import view.CLI;
import view.View;

public class Main {
    public static void main(String[] args) {
        View cliView = new CLI();
        MessageCallback callback = cliView.getCallback();

        String levelDirPath;

        if(args.length > 0){
            levelDirPath = args[0];
            Game game = new Game(levelDirPath, callback);
            game.start();
        }
        System.out.println("Hello world!");
    }
}