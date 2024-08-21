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

        View cliV = new CLI();
        MessageCallback callback = cliV.getCallback();

        if(args.length > 0){
            Game game = new Game(args[0],callback);
            game.start();
        }

        else{
            cliV.display("Level dir illegal!");
        }
    }
}