package com.kdzumba.algo;

import com.kdzumba.algo.models.Graph;
import com.kdzumba.algo.models.NodeModel;

import java.util.*;

public class Algorithms {

    public Stack<NodeModel> breadthFirstSearch(final NodeModel src, final NodeModel dest, Graph graph){
        Queue<NodeModel> frontier = new LinkedList<>();
        frontier.add(src);
        Stack<NodeModel> visited = new Stack<>();
        visited.add(src);

        while(!frontier.isEmpty()){
            NodeModel current = frontier.remove();
            current.getNeighbours().forEach(next -> {
                if(!next.isObstruction() && !visited.contains(next)){
                    next.setIsVisited(true);
                    next.setParent(current);
                    visited.add(next);
                    frontier.add(next);
                }
            });
        }
        return visited;
    }

    public Stack<NodeModel> shortestPath(final NodeModel dest){
        Stack<NodeModel> path = new Stack<>();
        NodeModel current = dest;
        while(current.getParent() != null){
            current.setOnShortestPath(true);
            path.add(current);
            current = current.getParent();
        }
        return path;
    }
}
