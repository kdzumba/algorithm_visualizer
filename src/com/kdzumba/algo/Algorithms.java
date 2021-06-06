package com.kdzumba.algo;

import com.kdzumba.algo.models.AlgoGraphModel;
import com.kdzumba.algo.models.AlgoNodeModel;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class Algorithms {
    public static double runningTime;
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

        Queue<AlgoNodeModel> frontier = new LinkedList<>();
        frontier.add(src);
        Queue<AlgoNodeModel> visited = new LinkedList<>();
        visited.add(src);

        while(!frontier.isEmpty()){
            AlgoNodeModel current = frontier.remove();

            current.getNeighbours().forEach(next -> {
                if(!next.isWall() && !visited.contains(next)){
                    next.setGCost(current.getGCost() + next.getWeight());
                    next.setIsVisited(true);
                    next.setParent(current);
                    visited.add(next);
                    frontier.add(next);
                }
            });

            //Break out of the loop if the destination node has been found
            if(current == dest){
                break;
            }
        }

        long endTime = System.nanoTime();
        runningTime = (endTime - startTime) / 1000000.0;

        return visited;
    }

    public static Queue<AlgoNodeModel> dijkstra(AlgoNodeModel src, AlgoNodeModel dest, AlgoGraphModel algoGraphModel){

        long startTime = System.nanoTime();

        Queue<AlgoNodeModel> visited = new LinkedList<>();
        Queue<AlgoNodeModel> frontier = new PriorityQueue<>(Comparator.comparingDouble(AlgoNodeModel::getGCost));

        for(AlgoNodeModel nodeModel : algoGraphModel.getNodeList()){
            if(!nodeModel.isStart()) {
                nodeModel.setGCost(Double.POSITIVE_INFINITY);
            }
        }

        src.setGCost(0);
        frontier.add(src);

        while(!frontier.isEmpty()){
            AlgoNodeModel current = frontier.remove();

            if(!visited.contains(current)) {
                visited.add(current);
                current.setIsVisited(true);
            }

            for(AlgoNodeModel neighbour : current.getNeighbours()){
                double costSoFar = current.getGCost() + neighbour.getWeight();
                if(!neighbour.isWall() && neighbour.getGCost() > costSoFar){
                    neighbour.setGCost(costSoFar);
                    neighbour.setParent(current);
                    if(!frontier.contains(neighbour)){
                        frontier.add(neighbour);
                    }
                }
            }

            if(current == dest){
                break;
            }
        }

        long endTime = System.nanoTime();
        runningTime = (endTime - startTime) / 1000000.0;

        return visited;
    }

    public static Queue<AlgoNodeModel> aStar(AlgoNodeModel src, AlgoNodeModel dest, AlgoGraphModel algoGraphModel){
        long startTime = System.nanoTime();

        Queue<AlgoNodeModel> visited = new LinkedList<>();
        Queue<AlgoNodeModel> openSet = new PriorityQueue<>(Comparator.comparingDouble(AlgoNodeModel::getFCost));

        for(AlgoNodeModel nodeModel : algoGraphModel.getNodeList()){
            if(!nodeModel.isStart()) {
                nodeModel.setFCost(Double.POSITIVE_INFINITY);
                nodeModel.setGCost(Double.POSITIVE_INFINITY);
            }
        }
        src.setFCost(0);
        src.setGCost(0);
        openSet.add(src);

        while(!openSet.isEmpty()){
            AlgoNodeModel current = openSet.remove();

            if(!visited.contains(current)) {
                visited.add(current);
                current.setIsVisited(true);
            }

            for(AlgoNodeModel neighbour : current.getNeighbours()){
                double costSoFar = current.getGCost() + neighbour.getWeight();
                if(!neighbour.isWall() && costSoFar < neighbour.getGCost()){
                    neighbour.setGCost(costSoFar);
                    neighbour.setFCost(costSoFar + manhattanDistance(current, dest));
                    neighbour.setParent(current);
                    openSet.add(neighbour);
                }
            }

            if(current == dest){
                break;
            }
        }

        long endTime = System.nanoTime();
        runningTime = (endTime - startTime) / 1000000.0;

        return visited;
    }

    public static double manhattanDistance(AlgoNodeModel node1, AlgoNodeModel node2){
        return Math.abs(node1.position().getX() - node2.position().getX()) + Math.abs(node1.position().getY() - node2.position().getY()) * (1 + 30 / 1000.0);
    }

    public static class NodeComparator implements Comparable<AlgoNodeModel>{

        @Override
        public int compareTo(@NotNull AlgoNodeModel o) {
            return 0;
        }
    }

    /**
     * Traces the shortest path from a destination node to the start node. Note that this function is agnostic to the
     * path finding algorithm that was performed on the graph
     * @param dest The destination node for a search algorithm
     * @return A stack of nodes on the shortest path
     */
    public static Stack<AlgoNodeModel> shortestPath(final AlgoNodeModel dest){
        Stack<AlgoNodeModel> path = new Stack<>();
        AlgoNodeModel current = dest;
        while(current.getParent() != null){
            current.setOnShortestPath(true);
            path.add(current);
            current = current.getParent();
        }
        return path;
    }
}
