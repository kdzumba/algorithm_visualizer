package com.kdzumba.algo.views;

import com.kdzumba.algo.Algorithms;
import com.kdzumba.algo.models.NodeModel;

import javax.swing.*;
import java.awt.*;

public class ControlsMenu extends JPanel {

    ControlsMenu(final Board board){
        this.setBackground(Color.darkGray);
        AlgoButton clearBoardButton = new AlgoButton("Clear Board");
        clearBoardButton.addActionListener(e -> {
            for(NodeModel nodeModel : board.getGraph().getNodeList()){
                nodeModel.setObstruction(false);
                nodeModel.setOnShortestPath(false);
                nodeModel.setIsVisited(false);
            }
            board.updateBoard();
        });

        AlgoButton clearPathButton = new AlgoButton("Clear Path");
        clearPathButton.addActionListener(e -> {
            board.getGraph().clearShortestPath();
            board.updateBoard();
        });

        AlgoButton visualizeButton = new AlgoButton("Visualize");
        visualizeButton.addActionListener(e -> {
            board.getGraph().clearShortestPath();
            Algorithms.breadthFirstSearch(board.getGraph().getStartNode(), board.getGraph().getDestinationNode(), board.getGraph());
            Algorithms.shortestPath(board.getGraph().getDestinationNode());
            board.updateBoard();
        });

        this.add(visualizeButton);
        this.add(clearBoardButton);
        this.add(clearPathButton);
    }
}
