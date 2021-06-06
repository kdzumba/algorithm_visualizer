package com.kdzumba.algo.views;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class AlgoWindow extends JFrame {
    /**
     * Creates a frame with xDimension nodes per row and yDimension nodes
     * per column
     * @param xDimension Number of nodes per row
     * @param yDimension Number of nodes per col
     */
    public AlgoWindow(int xDimension, int yDimension){
        this.setTitle("Algorithm Visualizer");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);

        AlgoBoard algoBoard = new AlgoBoard(xDimension, yDimension);
        AlgoControlsMenu menu = new AlgoControlsMenu(algoBoard);
        AlgoTerrainPicker nodePicker = new AlgoTerrainPicker(algoBoard);
        nodePicker.setAlignmentX(Component.LEFT_ALIGNMENT);
        AlgoMetrics metrics = new AlgoMetrics(algoBoard.getGraph());

        List<JComponent> sidePanelComponents = new ArrayList<>();
        sidePanelComponents.add(menu);
        sidePanelComponents.add(nodePicker);
        sidePanelComponents.add(metrics);
        SidePanel sidePanel = new SidePanel(sidePanelComponents);
        sidePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 0));

        this.add(algoBoard, BorderLayout.CENTER);
        this.add(sidePanel, BorderLayout.LINE_START);
        this.pack();
        this.setVisible(true);
    }
}
