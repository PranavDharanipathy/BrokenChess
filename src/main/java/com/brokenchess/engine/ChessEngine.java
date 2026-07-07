package com.brokenchess.engine;

public class ChessEngine {

    private final String[] boardState;
    private ChessBot bot;

    public ChessEngine() {
        this.boardState = new String[] {
                "br", "bn", "bb", "bq", "bk", "bb", "bn", "br",
                "bp", "bp", "bp", "bp", "bp", "bp", "bp", "bp",
                "_", "_", "_", "_", "_", "_", "_", "_",
                "_", "_", "_", "_", "_", "_", "_", "_",
                "_", "_", "_", "_", "_", "_", "_", "_",
                "_", "_", "_", "_", "_", "_", "_", "_",
                "wp", "wp", "wp", "wp", "wp", "wp", "wp", "wp",
                "wr", "wn", "wb", "wq", "wk", "wb", "wn", "wr"
        };
    }

    public void setBot(ChessBot bot) {
        this.bot = bot;
    }

    public String[] getBoardState() {
        return boardState.clone();
    }

    public void makeMove(int[] fromTo) {
        if (fromTo == null || fromTo.length != 2) {
            return;
        }

        int fromIndex = fromTo[0];
        int toIndex = fromTo[1];

        if (fromIndex < 0 || fromIndex >= 64 || toIndex < 0 || toIndex >= 64) {
            return;
        }

        String piece = boardState[fromIndex];
        boardState[fromIndex] = "_";
        boardState[toIndex] = piece;
    }

    public void update() {
        
    }
}
