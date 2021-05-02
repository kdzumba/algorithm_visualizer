package com.kdzumba.algo.views;

import com.kdzumba.algo.Algorithms;
import com.kdzumba.algo.models.AlgoNodeModel;

import javax.swing.*;
import java.awt.*;

public class AlgoControlsMenu extends JPanel {

    //TODO Find a way of controlling node change animations from here
    
    AlgoControlsMenu(final AlgoBoard algoBoard){
        this.setBackground(Color.darkGray);
        AlgoButton clearBoardButton = new AlgoButton("Clear Board");
        clearBoardButton.addActionListener(e -> {
            for(AlgoNodeModel algoNodeModel : algoBoard.getGraph().getNodeList()){
                algoNodeModel.setObstruction(false);
                algoNodeModel.setOnShortestPath(false);
                algoNodeModel.setIsVisited(false);
            }
            algoBoard.updateBoard();
        });

        AlgoButton clearPathButton = new AlgoButton("Clear Path");
        clearPathButton.addActionListener(e -> {
            algoBoard.getGraph().clearShortestPath();
            algoBoard.updateBoard();
        });

        AlgoButton visualizeButton = new AlgoButton("Visualize");
        visualizeButton.addActionListener(e -> {
            algoBoard.getGraph().clearShortestPath();
            Algorithms.breadthFirstSearch(algoBoard.getGraph().getStartNode(), algoBoard.getGraph().getDestinationNode(), algoBoard.getGraph());
            Algorithms.shortestPath(algoBoard.getGraph().getDestinationNode());
            algoBoard.updateBoard();
        });

        this.add(visualizeButton);
        this.add(clearBoardButton);
        this.add(clearPathButton);
    }
}
