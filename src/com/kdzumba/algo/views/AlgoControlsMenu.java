package com.kdzumba.algo.views;

import com.kdzumba.algo.Algorithms;
import com.kdzumba.algo.models.AlgoNodeModel;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;
import java.util.Queue;
import java.util.Stack;

public class AlgoControlsMenu extends JPanel {
    enum  CollectionType{
        STACK,
        QUEUE
    }

    private int animationDuration = 20; //in milliseconds

    AlgoControlsMenu(final AlgoBoard algoBoard){
        this.setBackground(Color.darkGray);
        AlgoButton clearBoardButton = new AlgoButton("Clear Board");
        clearBoardButton.addActionListener(e -> {
            for(AlgoNodeModel algoNodeModel : algoBoard.getGraph().getNodeList()){
                if(!algoNodeModel.isBoundaryNode()) {
                    algoNodeModel.setObstruction(false);
                }
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
            this.animate(algoBoard);
        });

        this.add(visualizeButton);
        this.add(clearBoardButton);
        this.add(clearPathButton);
    }

    /**
     * Updates the observers of each node in the graph and pauses the execution of the program
     * by a given amount. This is how animations of the nodes visitation and path are performed
     * @param collection A collection of nodes to be animated (updated one after the other)
     * @param collectionType The type of collection that is passed into the method, Can be stack or queue
     */
    private void animate(Collection collection, CollectionType collectionType){
        while(!collection.isEmpty()){
            AlgoNodeModel current;
            switch(collectionType){
                case QUEUE:
                    current = ((Queue<AlgoNodeModel>) collection).remove();
                    current.updateObservers();
                    break;
                case STACK:
                    current = ((Stack<AlgoNodeModel>) collection).pop();
                    current.updateObservers();
                    break;
            }
            try{
                Thread.sleep(animationDuration);
            }
            catch(InterruptedException exception){

            }
        }
    }

    /**
     * Probably not the best way to do this animation, but this function animates the visitation of the nodes
     * on the graph, as well as the shortest path that it finds
     * @param board The AlgoBoard on which to perform the animation
     */
    private void animate(AlgoBoard board){
        //TODO: try to figure out another way to perform animation, this one feels off
        //Each animation is handled by its own thread (node visiting and shortest path)
        //The path thread is joined to the visited thread so that we only visualize the
        //shortest path after the visited nodes have been animated
        Thread visitedThread = new Thread(() -> {
            Queue<AlgoNodeModel> visitedNodes = Algorithms.breadthFirstSearch(board.getGraph().getStartNode(), board.getGraph().getDestinationNode(), board.getGraph());
            this.animate(visitedNodes, CollectionType.QUEUE);
        });
        visitedThread.start();

        Thread pathThread = new Thread(() -> {
            try{
                visitedThread.join();
            }
            catch(InterruptedException exception){
            }
            Stack<AlgoNodeModel> pathNodes = Algorithms.shortestPath(board.getGraph().getDestinationNode());
            this.animate(pathNodes, CollectionType.STACK);

        });
        pathThread.start();
    }
}
