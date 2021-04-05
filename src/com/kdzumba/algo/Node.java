package com.kdzumba.algo;

import java.util.HashSet;
import java.util.Set;
import java.lang.Math;

public class Node {

    final static int SIZE = 25;
    private boolean isDestination = false;
    private boolean isStart = false;
    private boolean isObstruction = false;
    private boolean isVisited = false;
    private boolean onShortestPath = false;
    private Node parent = null;
    private NodePosition position;
    private final Set<Node> neighbours = new HashSet<>();

    Node(int xPos, int yPos){
        this.position = new NodePosition(xPos, yPos);
    }

    Node(int xPos, int yPos, boolean isDestination, boolean isStart){
        this(xPos, yPos);
        this.isDestination = isDestination;
        this.isStart = isStart;
    }

    public NodePosition position(){
        return this.position;
    }

    public void setPosition(NodePosition position){
        this.position = position;
    }

    public Node getParent(){
        return this.parent;
    }

    public void setOnShortestPath(boolean onShortestPath){
        this.onShortestPath = onShortestPath;
    }

    public boolean onShortestPath(){
        return this.onShortestPath;
    }

    public void setParent(Node parent){
        this.parent = parent;
    }

    public void setIsVisited(boolean visited){
        this.isVisited = visited;
    }

    public boolean isVisited(){
        return this.isVisited;
    }

    public void setObstruction(boolean obstruction){
        this.isObstruction = obstruction;
    }

    public boolean isObstruction(){
        return this.isObstruction;
    }

    public void setIsDestination(boolean isDestination){
        this.isDestination = isDestination;
    }

    public boolean isDestination(){
        return this.isDestination;
    }

    public void setIsStart(boolean isStart){
        this.isStart = isStart;
    }

    public boolean isStart(){
        return this.isStart;
    }

    public void addNeighbour(final Node node){
        this.neighbours.add(node);
    }

    public Set<Node> getNeighbours() {
        return neighbours;
    }

    //Computes the distance between this node and other
    public double distanceTo(final Node other){
        return Math.sqrt(Math.pow(this.position.x - other.position.x, 2)  + (Math.pow(this.position.y - other.position.y, 2)));
    }

    public boolean containsPoint(int x, int y){
        return this.position.x < x && this.position.y < y && this.position.x + SIZE > x && this.position.y + SIZE > y;
    }

    @Override
    public boolean equals(Object o){
        //Two nodes are equal if they are at the same position
        if(o == null){
            return false;
        }

        if(o.getClass() != this.getClass()){
            return false;
        }

        Node node = (Node) o;
        return this.position.equals(node.position);
    }

    public static class NodePosition {
        private final int x;
        private final int y;

        NodePosition(int xPos, int yPos){
            this.x = xPos;
            this.y = yPos;
        }

        public int getX(){
            return this.x;
        }

        public int getY(){
            return this.y;
        }

        @Override
        public boolean equals(Object o){
            //Two positions are equal if they have the same x and y values
            if(o == null){
                return false;
            }

            if(o.getClass() != this.getClass()){
                return false;
            }

            NodePosition p = (NodePosition) o;
            return p.x == this.x && p.y == this.y;
        }
    }
}
