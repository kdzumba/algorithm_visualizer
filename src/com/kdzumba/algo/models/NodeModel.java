package com.kdzumba.algo.models;

import java.util.HashSet;
import java.util.Set;
import java.lang.Math;

public class NodeModel {

    public final static int SIZE = 25; //Width and height (Nodes are square blocks)
    private boolean isDestination = false;
    private boolean isStart = false;
    private boolean isObstruction = false;
    private boolean isVisited = false;
    private boolean onShortestPath = false;
    private NodeModel parent;
    private Position position;
    private final Set<NodeModel> neighbours = new HashSet<>();

    public NodeModel(int xPos, int yPos){
        this.position = new Position(xPos, yPos);
    }

    NodeModel(int xPos, int yPos, boolean isDestination, boolean isStart){
        this(xPos, yPos);
        this.isDestination = isDestination;
        this.isStart = isStart;
    }

    public Position position(){
        return this.position;
    }

    public void setPosition(Position position){
        this.position = position;
    }

    public NodeModel getParent(){
        return this.parent;
    }

    public void setOnShortestPath(boolean onShortestPath){
        this.onShortestPath = onShortestPath;
    }

    public boolean onShortestPath(){
        return this.onShortestPath;
    }

    public void setParent(NodeModel parent){
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

    public void addNeighbour(final NodeModel node){
        this.neighbours.add(node);
    }

    public Set<NodeModel> getNeighbours() {
        return neighbours;
    }

    //Computes the distance between this node and other
    public double distanceTo(final NodeModel other){
        return Math.sqrt(Math.pow(this.position.getX() - other.position.getX(), 2)  + (Math.pow(this.position.getY() - other.position.getY(), 2)));
    }

    public boolean containsPoint(int x, int y){
        return this.position.getX() < x && this.position.getY() < y && this.position.getX() + SIZE > x && this.position.getY() + SIZE > y;
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

        NodeModel node = (NodeModel) o;
        return this.position.equals(node.position);
    }
}
