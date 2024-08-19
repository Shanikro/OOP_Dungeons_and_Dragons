package model.tiles;

import utils.Position;
import utils.VisitorPatterns.Visited;
import utils.VisitorPatterns.Visitor;
import utils.printer.*;

public abstract class Tile implements Visitor , Visited {
    protected char tile;
    protected Position position;
    protected static final Printer printer = PrinterC.getInstance();

    public Tile(char tile){
        this.tile = tile;
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

    public Position getPosition() {
        return position;
    }

    @Override
    public abstract String toString();
}
