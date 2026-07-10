package com.brokenchess.gameEngine;

@FunctionalInterface
public interface ChessBot {
    int[][] getMove(String[] boardState);
}
