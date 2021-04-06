package com.kdzumba.algo.views;

import javax.swing.*;

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

        Board board = new Board(xDimension, yDimension);
        this.add(board);
        this.pack();
        this.setVisible(true);
    }
}
