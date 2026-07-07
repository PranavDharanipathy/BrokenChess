package com.brokenchess.engine;

@FunctionalInterface
public interface ChessBot {
    int[][] getMove(String[] boardState);
}
