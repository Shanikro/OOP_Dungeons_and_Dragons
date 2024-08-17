package model.tiles;

import model.game.Board;
import model.tiles.units.Unit;
import utils.Position;
import utils.VisitorPatterns.Visited;
import utils.VisitorPatterns.Visitor;
import utils.printer.*;

public abstract class Tile implements Visitor , Visited {
    protected char tile;
    protected Position position;
    protected Board board;
    protected static final Printer printer = PrinterC.getInstance();

    public Tile(char tile,Board board){
        this.tile = tile;
        this.board = board;
    }

    public Tile initialize(Position p){
        this.position = p;
        return this;
    }

    public void swapPosition(Tile t) {
        Position temp = t.position;
        t.position = this.position;
        this.position = temp;
    }

    @Override
    public String toString() {
        return String.valueOf(tile);
    }


    public Position getPosition() {
        return position;
    }
}
