package com.gnarlyhub.minesweeper;

import javax.swing.*;
import javax.swing.UIManager.LookAndFeelInfo;
import java.awt.*;

public class MineSweeper extends JFrame {

    JMenuBar menuBar;
    JMenu newGame;
    JMenuItem eightByEight, sixteenBySixteen, sixteenByThirty;
    TrapField trapField;
    Dimension dim;
    static int currY;

    public MineSweeper() {
        setTitle("Minesweeper");
        this.setLayout(new BorderLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        try {
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        dim = Toolkit.getDefaultToolkit().getScreenSize();

        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new FlowLayout());
        menuBar = new JMenuBar();
        menuPanel.add(menuBar);
        newGame = new JMenu("New Game");
        menuBar.add(newGame);

        eightByEight = new JMenuItem("Beginner (8x8)");
        eightByEight.addActionListener(ae -> startBeginner());
        newGame.add(eightByEight);

        sixteenBySixteen = new JMenuItem("Intermediate (16x16)");
        sixteenBySixteen.addActionListener(ae -> startIntermediate());
        newGame.add(sixteenBySixteen);

        sixteenByThirty = new JMenuItem("Advanced (16x30)");
        sixteenByThirty.addActionListener(ae -> startAdvanced() );
        newGame.add(sixteenByThirty);

        setJMenuBar(menuBar);

        startBeginner();
        setVisible(true);
        setFocusable(true);
        requestFocusInWindow();
    }

    public void startBeginner() { startGame(8, 8, 10); }

    public void startIntermediate() { startGame(16, 16, 40); }

    public void startAdvanced() { startGame(16, 30, 99); }

    private void startGame(int x, int y, int t) {
        currY = y;
        if(trapField != null && trapField.getParent() != null) {
            trapField.getParent().removeAll();
        }

        trapField = new TrapField(x, y, t);

        add(trapField, BorderLayout.CENTER);
        revalidate();
        repaint();

        if(y == 30) {
            setSize(x * 82, y * 24 + 30);
        } else {
            setSize(x * 45, y * 45 + 30);
        }

        // Arrange in the middle of the screen
        setLocation(dim.width / 2 - this.getSize().width / 2,
                dim.height / 2 - this.getSize().height / 2);
        setResizable(false);
    }

}
