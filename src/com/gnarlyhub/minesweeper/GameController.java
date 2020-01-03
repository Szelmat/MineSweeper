package com.gnarlyhub.minesweeper;

public class GameController {

    static MineSweeper game;

    public static void main(String[] args) {
        game = new MineSweeper();
    }

    static void startOver(int y) {
        if(y == 8) {
            game.startBeginner();
        } else if(y == 16) {
            game.startIntermediate();
        } else {
            game.startAdvanced();
        }
    }

}
