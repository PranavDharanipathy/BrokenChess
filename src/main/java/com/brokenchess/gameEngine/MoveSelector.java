package com.brokenchess.gameEngine;

public class MoveSelector {

    private volatile Integer[] pendingClick = null;
    
    private Integer[] lastClickedSquare;

    public void notifyClick(int row, int col) {
        pendingClick = new Integer[] {row, col};
    }

    public Integer[] getLastClickedSquare() {
        return lastClickedSquare;
    }

    public void update() {

        Integer[] click = pendingClick;
        if (click != null) {
            lastClickedSquare = click;
            pendingClick = null;
        }
    }
}
