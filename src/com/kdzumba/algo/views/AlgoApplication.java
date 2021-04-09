package com.kdzumba.algo.views;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class AlgoApplication extends JFrame {
    /**
     * Creates a frame with xDimension nodes per row and yDimension nodes
     * per column
     * @param xDimension Number of nodes per row
     * @param yDimension Number of nodes per col
     */
    public AlgoApplication(int xDimension, int yDimension){
        this.setTitle("Algorithm Visualizer");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(new BorderLayout());
        Board board = new Board(xDimension, yDimension);
        ControlsMenu menu = new ControlsMenu(board);
        this.add(board, BorderLayout.PAGE_END);
        this.add(menu, BorderLayout.PAGE_START);
        this.pack();
        this.setVisible(true);
    }
}
