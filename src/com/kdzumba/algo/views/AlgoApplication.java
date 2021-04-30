package com.kdzumba.algo.views;

import javax.swing.*;
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
        AlgoBoard algoBoard = new AlgoBoard(xDimension, yDimension);
        AlgoControlsMenu menu = new AlgoControlsMenu(algoBoard);
        this.add(algoBoard, BorderLayout.PAGE_END);
        this.add(menu, BorderLayout.PAGE_START);
        this.pack();
        this.setVisible(true);
    }
}
