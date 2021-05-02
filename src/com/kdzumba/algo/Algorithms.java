package com.kdzumba.algo;

import com.kdzumba.algo.models.AlgoGraphModel;
import com.kdzumba.algo.models.AlgoNodeModel;

import java.util.*;

public class Algorithms {

    /**
     * Performs a breadth first search operation on a given graph model to find the shortest path
     * between the source node and the destination node
     * @param src Source node for the search
     * @param dest Destination node for the search
     * @param algoGraphModel Graph on which the search is made
     * @return A stack of nodes that were visited on the search for the shortest path
     */
    public static Stack<AlgoNodeModel> breadthFirstSearch(final AlgoNodeModel src, final AlgoNodeModel dest, AlgoGraphModel algoGraphModel){
        Queue<AlgoNodeModel> frontier = new LinkedList<>();
        frontier.add(src);
        Stack<AlgoNodeModel> visited = new Stack<>();
        visited.add(src);

        while(!frontier.isEmpty()){
            AlgoNodeModel current = frontier.remove();
            current.getNeighbours().forEach(next -> {
                if(!next.isObstruction() && !visited.contains(next)){
                    next.setIsVisited(true);
                    next.setParent(current);
                    visited.add(next);
                    frontier.add(next);
                }
            });

            //Break out of the loop if the destination node has been found
            if(visited.contains(dest)){
                break;
            }
        }
        return visited;
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
