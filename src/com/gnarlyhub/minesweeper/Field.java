package com.gnarlyhub.minesweeper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Field extends JButton implements MouseListener {

    // The available markings
    final static protected char[] markings = {' ', '\u2691', '?'};
    protected int currentMark = 0;
    protected boolean isEnabled = true;
    protected int x, y;
    protected boolean trap;
    protected Field[] range = new Field[8];
    protected int trapsInRange;

    public Field(int x, int y, boolean f) {
        super(" ");
        this.x = x;
        this.y = y;
        trap = f;
        setFocusable(false);
        addMouseListener(this);
        setFont(new Font("sans serif", Font.PLAIN, 20));
    }

    public boolean isTrap() {
        return trap;
    }

    // we can't use the getX() or getY() names, because
    // those methods are already defined
    public int askX() {
        return x;
    }

    public int askY() {
        return y;
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        if (SwingUtilities.isLeftMouseButton(me) && getText().equals(" ")) {
            if (trapsInRange == 0 && !isTrap()) {
                traverse();
            } else {
                open();
            }
        } else if (SwingUtilities.isRightMouseButton(me)) {
            if (isEnabled) {
                if (currentMark < 2) {
                    currentMark++;
                } else {
                    currentMark = 0;
                }
                setText("" + markings[currentMark]);
            }
        }
    }

    // Opening up the field
    private void open() {
        TrapField.remFields--;
        isEnabled = false;
        TrapField.opened[x][y] = true;
        if (isTrap()) {
            setBackground(Color.RED);
            UIManager.put("OptionPane.okButtonText", "New Game");
            JOptionPane.showOptionDialog(null, "You stepped on a trap. Game over.",
                    "Game over", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, null, null);
            GameController.startOver(TrapField.currY);
        } else {
            // Displaying the number and its color
            if (trapsInRange > 0) {
                setText("" + trapsInRange);
                if (trapsInRange == 1) {
                    setForeground(Color.BLUE);
                } else if (trapsInRange == 2) {
                    setForeground(Color.GREEN);
                } else if (trapsInRange == 3) {
                    setForeground(Color.MAGENTA);
                } else if (trapsInRange == 4) {
                    setForeground(Color.ORANGE);
                } else if (trapsInRange == 5) {
                    setForeground(Color.YELLOW);
                } else if (trapsInRange == 6) {
                    setForeground(Color.CYAN);
                } else if (trapsInRange == 7) {
                    setForeground(Color.RED);
                } else {
                    setForeground(Color.PINK);
                }
            }
            setEnabled(false);
        }
        // Has the player won
        if (TrapField.isGameOver()) {
            UIManager.put("OptionPane.okButtonText", "New Game");
            JOptionPane.showOptionDialog(null, "Congratulations! You avoided all the mines!",
                    "You won!", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);
            GameController.startOver(TrapField.currY);
        }
    }

    // If the field is empty, we look around what other fields we can open up
    public void traverse() {
        open();
        for (int i = 0; i < range.length; i++) {
            // If this adjacent field exists
            if (range[i] != null && (TrapField.opened[range[i].askX()][range[i].askY()] != true)) {
                if (range[i].getTrapsInRange() == 0) { // If the adjacent field is empty
                    range[i].traverse();
                } else {
                    range[i].open();
                }
            }
        }
    }

    public void setTrapsInRange(int n) {
        trapsInRange = n;
    }

    public int getTrapsInRange() {
        return trapsInRange;
    }

    public void setRange(Field[] r) {
        range = r;
    }

    public Field[] getRange() {
        return range;
    }

    public String toString() {
        return x + " " + y;
    }

    @Override
    public void mousePressed(MouseEvent arg0) {

    }

    @Override
    public void mouseReleased(MouseEvent arg0) {

    }

    @Override
    public void mouseEntered(MouseEvent arg0) {

    }

    @Override
    public void mouseExited(MouseEvent arg0) {

    }

}
