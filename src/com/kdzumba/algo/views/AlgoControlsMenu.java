package com.kdzumba.algo.views;

import com.kdzumba.algo.Algorithms;
import com.kdzumba.algo.models.AlgoNodeModel;

import javax.swing.*;
import java.awt.*;
import java.util.Stack;

public class AlgoControlsMenu extends JPanel {
    private int animationDuration = 300; //in milliseconds

    AlgoControlsMenu(final AlgoBoard algoBoard){
        this.setBackground(Color.darkGray);
        AlgoButton clearBoardButton = new AlgoButton("Clear Board");
        clearBoardButton.addActionListener(e -> {
            for(AlgoNodeModel algoNodeModel : algoBoard.getGraph().getNodeList()){
                algoNodeModel.setObstruction(false);
                algoNodeModel.setOnShortestPath(false);
                algoNodeModel.setIsVisited(false);
                algoNodeModel.updateObservers();
            }
        });

        AlgoButton clearPathButton = new AlgoButton("Clear Path");
        clearPathButton.addActionListener(e -> algoBoard.getGraph().clearShortestPath());

        AlgoButton visualizeButton = new AlgoButton("Visualize");
        visualizeButton.addActionListener(e -> {
            algoBoard.getGraph().clearShortestPath();
            Algorithms.breadthFirstSearch(algoBoard.getGraph().getStartNode(), algoBoard.getGraph().getDestinationNode(), algoBoard.getGraph());
            animateShortestPath(Algorithms.shortestPath(algoBoard.getGraph().getDestinationNode()));
        });

        this.add(visualizeButton);
        this.add(clearBoardButton);
        this.add(clearPathButton);
    }

    private void animateShortestPath(Stack<AlgoNodeModel> pathNodes){
        Thread thread = new Thread(() -> {
            while (!pathNodes.isEmpty()) {
                AlgoNodeModel current = pathNodes.pop();
                current.updateObservers();
                try {
                    //TODO: make duration of sleep a controlled variable that the user can change
                    Thread.sleep(animationDuration);
                }
                catch(InterruptedException exception){

                }
            }
        });
        thread.start();
    }
}
