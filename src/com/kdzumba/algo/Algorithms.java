package com.kdzumba.algo;

import java.util.*;

public class Algorithms {

    public Stack<Node> breadthFirstSearch(final Node src, final Node dest, Graph graph){
        Queue<Node> frontier = new LinkedList<>();
        frontier.add(src);
        Stack<Node> visited = new Stack<>();
        visited.add(src);

        while(!frontier.isEmpty()){
            Node current = frontier.remove();
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

    public Stack<Node> shortestPath(final Node dest, Stack<Node> visited){
        Stack<Node> path = new Stack<Node>();
        Node current = dest;
        while(current.getParent() != null){
            current.setOnShortestPath(true);
            path.add(current);
            current = current.getParent();
        }
        return path;
    }
}
