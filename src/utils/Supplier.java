package utils;

import model.game.Board;

public interface Supplier<T> {
    T get(char tile, Board board);
}