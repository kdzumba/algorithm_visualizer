package com.kdzumba.algo.views;

import com.kdzumba.algo.Algorithms;
import com.kdzumba.algo.models.AlgoNodeModel;
import com.kdzumba.algo.models.AlgoPositionModel;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class AlgoControlsMenu extends JPanel {
    enum  CollectionType{
        STACK,
        QUEUE,
        GRAPH,
    }

    enum Algorithm{
        BFS,
        DIJKSTRA,
        ASTAR,
        DFS,
        RANDOMIZED_DFS,
    }

    private int animationDuration = AlgoSlider.DEFAULT_DELAY; //in milliseconds
    private String algorithms[] = {"BFS", "DFS","Randomized DFS", "Dijkstra", "A*"};
    private Algorithm selectedAlgorithm;
    private final AlgoButton clearBoardButton;
    private final AlgoButton clearPathButton;
    private final AlgoButton clearVisitedButton;
    private final AlgoButton clearObstructionsButton;
    private final AlgoButton visualizeButton;
    private final AlgoButton generateMazeButton;
    private final AlgoComboBox algorithmsSelect;
    private final AlgoSlider animationController;

    AlgoControlsMenu(final AlgoBoard algoBoard){
        this.setBackground(Color.darkGray);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
//        this.setBorder(new EmptyBorder(new Insets(10, 10, 10, 5)));

        clearBoardButton = new AlgoButton("Clear Board");
        clearBoardButton.addActionListener(e -> {
            algoBoard.getGraph().resetGraph();
            algoBoard.getGraph().updateNodeObservers();
            algoBoard.getGraph().updateObservers();
        });

        clearPathButton = new AlgoButton("Clear Path");
        clearPathButton.addActionListener(e -> {
            algoBoard.getGraph().clearShortestPath();
            algoBoard.getGraph().updateNodeObservers();
            algoBoard.getGraph().updateObservers();
        });

        clearVisitedButton = new AlgoButton("Clear Visited");
        clearVisitedButton.addActionListener(e -> {
            algoBoard.getGraph().clearVisitedNodes();
            algoBoard.getGraph().updateNodeObservers();
            algoBoard.getGraph().updateObservers();
        });

        clearObstructionsButton = new AlgoButton("Clear Obstructions");
        clearObstructionsButton.addActionListener(e -> {
            algoBoard.getGraph().clearObstructions();
            algoBoard.getGraph().updateNodeObservers();
        });

        visualizeButton = new AlgoButton("Visualize");
        visualizeButton.addActionListener(e -> {
            algoBoard.getGraph().clearShortestPath();
            algoBoard.getGraph().clearVisitedNodes();
            algoBoard.getGraph().updateNodeObservers();
            this.doAnimate(algoBoard);
        });

        generateMazeButton = new AlgoButton("Generate Maze");
        generateMazeButton.addActionListener(e -> {
            algoBoard.getGraph().resetGraph();
            algoBoard.getGraph().updateObservers();
            algoBoard.getGraph().updateNodeObservers();

            int xDimension = algoBoard.getxDimension();
            int yDimension = algoBoard.getyDimension();
            algoBoard.getGraph().generateObstructions(xDimension, yDimension);
            algoBoard.getGraph().updateObservers();
            algoBoard.getGraph().updateNodeObservers();
        });

        algorithmsSelect = new AlgoComboBox(this.algorithms);
        algorithmsSelect.addActionListener(e -> {
            AlgoComboBox comboBox = (AlgoComboBox) e.getSource();
            String selectedAlgo = (String) comboBox.getSelectedItem();
            if(selectedAlgo == "BFS"){
                this.selectedAlgorithm = Algorithm.BFS;
            }
            else if(selectedAlgo == "DFS"){
                this.selectedAlgorithm = Algorithm.DFS;
            }
            else if(selectedAlgo == "Dijkstra"){
                this.selectedAlgorithm = Algorithm.DIJKSTRA;
            }
            else if(selectedAlgo == "A*"){
                this.selectedAlgorithm = Algorithm.ASTAR;
            }
            else if(selectedAlgo == "Randomized DFS"){
                this.selectedAlgorithm = Algorithm.RANDOMIZED_DFS;
            }
        });
        algorithmsSelect.setSelectedItem("BFS");
        algorithmsSelect.setAlignmentX(Component.LEFT_ALIGNMENT);

        animationController = new AlgoSlider(JSlider.HORIZONTAL);
        animationController.setAlignmentX(Component.LEFT_ALIGNMENT);
        animationController.addChangeListener(e -> animationDuration = ((AlgoSlider)e.getSource()).getValue());

        this.add(algorithmsSelect);
        this.add(visualizeButton);
        this.add(clearBoardButton);
        this.add(clearPathButton);
        this.add(clearVisitedButton);
        this.add(clearObstructionsButton);
        this.add(generateMazeButton);
        this.add(animationController);
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
                case GRAPH:

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
    private void doAnimate(AlgoBoard board){

        this.deactivateButtons();
        //TODO: try to figure out another way to perform animation, this one feels off
        //Each animation is handled by its own thread (node visiting and shortest path)
        //The path thread is joined to the visited thread so that we only visualize the
        //shortest path after the visited nodes have been animated
        Thread algorithmThread = new Thread(() -> {
            Queue<AlgoNodeModel> visitedNodes = new LinkedList<>();
            switch(this.selectedAlgorithm){
                case BFS:
                    visitedNodes = Algorithms.breadthFirstSearch(board.getGraph().getStartNode(), board.getGraph().getDestinationNode(), board.getGraph());
                    break;
                case DIJKSTRA:
                    visitedNodes = Algorithms.dijkstra(board.getGraph().getStartNode(), board.getGraph().getDestinationNode(), board.getGraph());
                    break;

                case ASTAR:
                    visitedNodes = Algorithms.aStar(board.getGraph().getStartNode(), board.getGraph().getDestinationNode(), board.getGraph());
                    break;
                case DFS:
                    visitedNodes = Algorithms.depthFirstSearch(board.getGraph().getStartNode(), board.getGraph().getDestinationNode(), board.getGraph());
                    break;
                case RANDOMIZED_DFS:
                    visitedNodes = Algorithms.randomizedDepthFirstSearch(board.getGraph().getStartNode(), board.getGraph().getDestinationNode(), board.getGraph());
            }

            this.animate(visitedNodes, CollectionType.QUEUE);
        });
        algorithmThread.start();

        Thread pathThread = new Thread(() -> {
            try{
                algorithmThread.join();
            }
            catch(InterruptedException exception){
            }

            Stack<AlgoNodeModel> pathNodes = Algorithms.shortestPath(board.getGraph().getDestinationNode());
            this.animate(pathNodes, CollectionType.STACK);
            this.activateButtons();

        });
        pathThread.start();

        Thread metricsThread = new Thread(() -> {
            try{
                pathThread.join();
            }
            catch (InterruptedException exception){

            }
            board.getGraph().updateObservers();
        });
        metricsThread.start();
    }

    private void deactivateButtons(){
        this.visualizeButton.setEnabled(false);
        this.clearBoardButton.setEnabled(false);
        this.clearObstructionsButton.setEnabled(false);
        this.clearVisitedButton.setEnabled(false);
        this.clearPathButton.setEnabled(false);
    }

    private void activateButtons(){
        this.visualizeButton.setEnabled(true);
        this.clearBoardButton.setEnabled(true);
        this.clearObstructionsButton.setEnabled(true);
        this.clearVisitedButton.setEnabled(true);
        this.clearPathButton.setEnabled(true);
    }
}
