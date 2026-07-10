package com.brokenchess.gameEngine;

public class ChessEngine {

    private String[][] boardState;

    private final MoveSelector moveSelector;

    private ChessBot bot;

    public ChessEngine() {

        boardState = new String[][] {
                new String[]{"br", "bn", "bb", "bq", "bk", "bb", "bn", "br"},
                new String[]{"bp", "bp", "bp", "bp", "bp", "bp", "bp", "bp"},
                new String[]{"_", "_", "_", "_", "_", "_", "_", "_"},
                new String[]{"_", "_", "_", "_", "_", "_", "_", "_"},
                new String[]{"_", "_", "_", "_", "_", "_", "_", "_"},
                new String[]{"_", "_", "_", "_", "_", "_", "_", "_"},
                new String[]{"wp", "wp", "wp", "wp", "wp", "wp", "wp", "wp"},
                new String[]{"wr", "wn", "wb", "wq", "wk", "wb", "wn", "wr"}
        };
        
        moveSelector = new MoveSelector();
    }

    public void setBot(ChessBot bot) {
        this.bot = bot;
    }

    public MoveSelector getMoveSelector() {
        return moveSelector;
    }

    public String[][] getBoardState() {
        return boardState.clone();
    }

    public void makeMove(int[][] move) {

        if (move == null || move.length != 2) {
            return;
        }

        final int[] fromIndex = move[0];
        final int[] toIndex = move[1];

        String piece = boardState[fromIndex[0]][fromIndex[1]];
        boardState[fromIndex[0]][fromIndex[1]] = "_";
        boardState[toIndex[0]][toIndex[1]] = piece;
    }

    public void update() {
        moveSelector.update();
    }
}
