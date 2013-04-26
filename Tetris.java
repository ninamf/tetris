import java.awt.*; //for border layout and dimension

import javax.swing.JFrame;

/**
 * Runs a game of Tetris.
 * 
 * @verison 2.0
 * @author Kristina Flaherty
 */
public class Tetris {
    public static void main(String[] args) {

        JFrame frame = new JFrame("CS 1331 Tetris");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 10 (wide) x 25 (tall) playing grid
		InfoPanel infoPanel = new InfoPanel();
        BoardPanel boardPanel = new BoardPanel(10, 25, infoPanel);

        frame.add(boardPanel);
		frame.add(infoPanel, BorderLayout.EAST);

        frame.pack();
        frame.setVisible(true);


    }
}


