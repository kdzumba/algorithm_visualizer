package com.kdzumba.algo;

import com.kdzumba.algo.models.AlgoGraphModel;
import com.kdzumba.algo.models.AlgoNodeModel;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class Algorithms {
    public static double runningTime;
    public static double currentPathLength;
    private static double timeScaler = 1000000.0;
    /**
     * Performs a breadth first search operation on a given graph model to find the shortest path
     * between the source node and the destination node
     * @param src Source node for the search
     * @param dest Destination node for the search
     * @param algoGraphModel Graph on which the search is made
     * @return A stack of nodes that were visited on the search for the shortest path
     */
    public static Queue<AlgoNodeModel> breadthFirstSearch(final AlgoNodeModel src, final AlgoNodeModel dest, AlgoGraphModel algoGraphModel){

        long startTime = System.nanoTime();

        Queue<AlgoNodeModel> toBeProcessed = new LinkedList<>();
        toBeProcessed.add(src);
        Queue<AlgoNodeModel> processed = new LinkedList<>();

        while(!toBeProcessed.isEmpty()){
            AlgoNodeModel current = toBeProcessed.remove();
            current.setIsProcessed(true);
            processed.add(current);

            current.getNeighbours().forEach(next -> {
                if(!next.isWall() && !next.isProcessed()){
                    next.setGCost(current.getGCost() + next.getWeight());
                    next.setIsProcessed(true);
                    next.setParent(current);
                    processed.add(next);
                    toBeProcessed.add(next);
                }
            });

            //Break out of the loop if the destination node has been found
            if(current == dest){
                break;
            }
        }

        long endTime = System.nanoTime();
        runningTime = (endTime - startTime) / timeScaler;

        return processed;
    }

    public static Queue<AlgoNodeModel> depthFirstSearch(AlgoNodeModel src, AlgoNodeModel dest, AlgoGraphModel algoGraphModel){
        long startTime = System.nanoTime();

        Stack<AlgoNodeModel> toBeProcessed = new Stack<>();
        Queue<AlgoNodeModel> processed = new LinkedList<>();

        src.setGCost(0);
        toBeProcessed.push(src);
        while(!toBeProcessed.isEmpty()){
            AlgoNodeModel current = toBeProcessed.pop();
            if(!current.isProcessed()){
                processed.add(current);
                current.setIsProcessed(true);

                for(AlgoNodeModel neighbour : current.getNeighbours()){
                    if(!neighbour.isWall() && !neighbour.isProcessed()) {
                        neighbour.setGCost(current.getGCost() + neighbour.getWeight());
                        toBeProcessed.push(neighbour);
                        neighbour.setParent(current);
                    }
                }
            }

            if(current == dest){
                break;
            }
        }

        long endTime = System.nanoTime();
        runningTime = (endTime - startTime) / timeScaler;

        return processed;
    }

    public static Queue<AlgoNodeModel>  randomizedDepthFirstSearch(AlgoNodeModel src, AlgoNodeModel dest, AlgoGraphModel algoGraphModel){
        long startTime = System.nanoTime();

        Stack<AlgoNodeModel> toBeProcessed = new Stack<>();
        Queue<AlgoNodeModel> processed = new LinkedList<>();

        src.setGCost(0);
        toBeProcessed.push(src);
        while(!toBeProcessed.isEmpty()){
            AlgoNodeModel current = toBeProcessed.pop();
            processed.add(current);
            current.setIsProcessed(true);

            Random rand = new Random();
            int selectedIndex = rand.nextInt(current.getNeighbours().size());

            for(AlgoNodeModel neighbour : current.getNeighbours()){
                if(!neighbour.isWall() && !neighbour.isProcessed() && neighbour != current.getNeighbours().get(selectedIndex)) {
                    neighbour.setGCost(current.getGCost() + neighbour.getWeight());
                    toBeProcessed.push(neighbour);
                    neighbour.setParent(current);
                }
            }

            AlgoNodeModel next = current.getNeighbours().get(selectedIndex);
            if(!next.isWall() && !next.isProcessed()){
                next.setGCost(current.getGCost() + next.getWeight());
                toBeProcessed.push(next);
                next.setParent(current);
            }

            if(current == dest){
                break;
            }
        }

        long endTime = System.nanoTime();
        runningTime = (endTime - startTime) / timeScaler;

        return processed;
    }

    public static Queue<AlgoNodeModel> dijkstra(AlgoNodeModel src, AlgoNodeModel dest, AlgoGraphModel algoGraphModel){
        long startTime = System.nanoTime();

        Queue<AlgoNodeModel> processed = new LinkedList<>();
        Queue<AlgoNodeModel> toBeProcessed = new PriorityQueue<>(Comparator.comparingDouble(AlgoNodeModel::getGCost));

        for(AlgoNodeModel nodeModel : algoGraphModel.getNodeList()){
            if(!nodeModel.isStart()) {
                nodeModel.setGCost(Double.POSITIVE_INFINITY);
            }
        }

        src.setGCost(0);
        toBeProcessed.add(src);

        while(!toBeProcessed.isEmpty()){
            AlgoNodeModel current = toBeProcessed.remove();
            if(!current.isProcessed()){
                processed.add(current);
                current.setIsProcessed(true);

                for(AlgoNodeModel neighbour : current.getNeighbours()){
                    double costSoFar = current.getGCost() + neighbour.getWeight();
                    if(!neighbour.isWall() && neighbour.getGCost() > costSoFar){
                        neighbour.setGCost(costSoFar);
                        neighbour.setParent(current);
                        toBeProcessed.add(neighbour);
                    }
                }
            }

            if(current == dest){
                break;
            }
        }

        long endTime = System.nanoTime();
        runningTime = (endTime - startTime) / timeScaler;

        return processed;
    }

    public static Queue<AlgoNodeModel> aStar(AlgoNodeModel src, AlgoNodeModel dest, AlgoGraphModel algoGraphModel){
        long startTime = System.nanoTime();

        Queue<AlgoNodeModel> processed = new LinkedList<>();
        Queue<AlgoNodeModel> toBeProcessed = new PriorityQueue<>(new AlgoNodeModel.NodeComparator());

        for(AlgoNodeModel nodeModel : algoGraphModel.getNodeList()){
            if(!nodeModel.isStart()) {
                nodeModel.setFCost(Double.POSITIVE_INFINITY);
                nodeModel.setGCost(Double.POSITIVE_INFINITY);
                nodeModel.setHCost(Double.POSITIVE_INFINITY);
            }
        }
        src.setFCost(0);
        src.setGCost(0);
        toBeProcessed.add(src);

        while(!toBeProcessed.isEmpty()){
            AlgoNodeModel current = toBeProcessed.remove();

            if(!current.isProcessed()) {
                processed.add(current);
                current.setIsProcessed(true);
            }

            for(AlgoNodeModel neighbour : current.getNeighbours()){
                double costSoFar = current.getGCost() + neighbour.getWeight();
                if(!neighbour.isWall() && costSoFar < neighbour.getGCost()){
                    neighbour.setGCost(costSoFar);
                    manhattanDistance(neighbour, dest);
                    neighbour.setFCost(neighbour.getHCost() + neighbour.getGCost());
                    neighbour.setParent(current);
                    toBeProcessed.add(neighbour);
                }
            }

            if(current == dest){
                break;
            }
        }

        long endTime = System.nanoTime();
        runningTime = (endTime - startTime) / timeScaler;

        return processed;
    }

    public static void manhattanDistance(AlgoNodeModel node1, AlgoNodeModel node2){
        double manhattanDistance = Math.abs(node1.position().getX() - node2.position().getX()) + Math.abs(node1.position().getY() - node2.position().getY());
        node1.setHCost(manhattanDistance);
    }

    /**
     * Traces the shortest path from a destination node to the start node. Note that this function is agnostic to the
     * path finding algorithm that was performed on the graph
     * @param dest The destination node for a search algorithm
     * @return A stack of nodes on the shortest path
     */
    public static Stack<AlgoNodeModel> shortestPath(final AlgoNodeModel dest){
        Stack<AlgoNodeModel> path = new Stack<>();
        currentPathLength = 0;
        AlgoNodeModel current = dest;

        while(current.getParent() != null){
            currentPathLength += current.getWeight();
            current.setOnShortestPath(true);
            path.add(current);
            current = current.getParent();
        }
        return path;
    }
}
