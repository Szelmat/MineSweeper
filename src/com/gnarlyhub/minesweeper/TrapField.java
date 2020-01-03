package com.gnarlyhub.minesweeper;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class TrapField extends JPanel {

    Field[][] fields;
    int[] traps;
    Random rnd = new Random();
    int x, y, t;
    static int currX, currY, currT;
    static boolean[][] opened;
    static int remFields; // The remaining (unopened) fields

    public TrapField(int x, int y, int t) {
        currX = this.x = x;
        currY = this.y = y;
        currT = this.t = t;
        remFields = x * y;
        fields = new Field[x][y];
        opened = new boolean[x][y];

        // Raffleing the mines
        traps = new int[t];
        int curr;
        for(int i = 0; i < traps.length; i++) {
            curr = rnd.nextInt(x * y);

            if(i == 0)
                traps[i] = curr;

            for(int j = 0; j < i; j++) {
                if(traps[j] == curr) {
                    i--;
                    break;
                } else {
                    traps[i] = curr;
                }
            }
        }

        setLayout(new GridLayout(x, y));
        boolean currentFeeder;
        for (int i = 0; i < x; i++) {
            for(int j = 0; j < y; j++) {
                currentFeeder = false;
                for(int k = 0; k < traps.length; k++) {
                    if((i * y + j) == traps[k])
                        currentFeeder = true;
                    fields[i][j] = new Field(i, j, currentFeeder);
                }
                add(fields[i][j]);
            }
        }

        for(int i = 0; i < x; i++) {
            for(int j = 0; j < y; j++) {
                trapsInRange(fields[i][j]);
            }
        }
    }

    // Collecting adjacent fields
    public Field[] getRange(Field f) {
        Field[] range = new Field[8];

        if(f.askX() > 0) {
            if(f.askY() > 0) { range[0] = fields[f.askX() - 1][f.askY() - 1]; }
            range[1] = fields[f.askX() - 1][f.askY()];
            if(f.askY() < (y - 1)) range[2] = fields[f.askX() - 1][f.askY() + 1];
        }
        if(f.askY() > 0) { range[3] = fields[f.askX()][f.askY() - 1]; }
        if(f.askY() < (y - 1)) range[4] = fields[f.askX()][f.askY() + 1];
        if(f.askX() < (x - 1)) {
            if(f.askY() > 0) { range[5] = fields[f.askX() + 1][f.askY() - 1]; }
            range[6] = fields[f.askX() + 1][f.askY()];
            if(f.askY() < (y - 1)) range[7] = fields[f.askX() + 1][f.askY() + 1];
        }
        f.setRange(range);
        return range;
    }

    int trapsInRange(Field f) {
        int sum = 0;
        Field[] currRange = getRange(f);
        for(int i = 0; i < 8; i++) {
            if(currRange[i] != null && currRange[i].isTrap()) {
                sum++;
            }
        }
        f.setTrapsInRange(sum);
        return sum;
    }

    // If only the traps haven't been opened,
    // the player won the game.
    public static boolean isGameOver() {
        return remFields == currT;
    }

}
