package com.kdzumba.algo.models;

import java.util.ArrayList;
import java.util.List;
import java.lang.Math;

public class AlgoNodeModel extends AlgoObservable{

    public final static int SIZE = 25; //Width and height (Nodes are square blocks)
    private boolean isDestination = false;
    private boolean isStart = false;
    private boolean isObstruction = false;
    private boolean isVisited = false;
    private boolean onShortestPath = false;
    private boolean isBoundaryNode = false;
    private AlgoNodeModel parent;
    private AlgoPositionModel algoPositionModel;
    private final List<AlgoNodeModel> neighbours = new ArrayList<>();

    public AlgoNodeModel(int xPos, int yPos){
        this.algoPositionModel = new AlgoPositionModel(xPos, yPos);
    }

    AlgoNodeModel(int xPos, int yPos, boolean isDestination, boolean isStart){
        this(xPos, yPos);
        this.isDestination = isDestination;
        this.isStart = isStart;
    }

    public AlgoPositionModel position(){
        return this.algoPositionModel;
    }

    public void setBoundaryNode(boolean isBoundaryNode){
        this.isBoundaryNode = isBoundaryNode;
    }

    public void setPosition(AlgoPositionModel algoPositionModel){
        this.algoPositionModel = algoPositionModel;
    }

    /**
     * Gets the parent node for this node
     * @return A parent node for this node
     */
    public AlgoNodeModel getParent(){
        return this.parent;
    }

    /**
     * Sets this node to be on the shortest path or not
     * @param onShortestPath True if should be on shortest path, false otherwise
     */
    public void setOnShortestPath(boolean onShortestPath){
        this.onShortestPath = onShortestPath;
        //this.updateObservers();
    }

    /**
     * Checks whether or not this node is on the shortest path or not
     * @return True if on shortest path, false otherwise
     * TODO: Figure out if this really needs to be here, feels like too much coupling to the algorithms class
     */
    public boolean onShortestPath(){
        return this.onShortestPath;
    }

    /**
     * Sets the parent node for this node. A parent node is a node that leads to the visitation of this node
     * @param parent The parent node for this node
     */
    public void setParent(AlgoNodeModel parent){
        this.parent = parent;
    }

    /**
     * Sets this node to be either in a visited state or unvisited state
     * @param visited True if this node should be in visited state, false otherwise
     */
    public void setIsVisited(boolean visited){
        this.isVisited = visited;
    }

    /**
     * Checks whether or not this node has been visited or not
     * @return True if visited, false otherwise
     */
    public boolean isVisited(){
        return this.isVisited;
    }

    /**
     * Sets this node to be an obstruction node or not an obstruction node
     * @param obstruction True if this node should be an ostruction node, false otherwise
     */
    public void setObstruction(boolean obstruction){
        this.isObstruction = obstruction;
        this.updateObservers();
    }

    /**
     * Checks whether or not this node is an obstruction node or not
     * @return True if this is an obstruction node, false otherwise
     */
    public boolean isObstruction(){
        return this.isObstruction;
    }

    /**
     * Sets this node to be a destination node or not a destination node
     * @param isDestination True if this node should be destination and false otherwise
     */
    public void setIsDestination(boolean isDestination){
        this.isDestination = isDestination;
        this.updateObservers();
    }

    /**
     * Checks whether or not this node is a destination node or not
     * @return True if this is a destination node, false otherwise
     */
    public boolean isDestination(){
        return this.isDestination;
    }

    /**
     * Sets this node to be a start node or not a start node
     * @param isStart True if this node should be start and false otherwise
     */
    public void setIsStart(boolean isStart){
        this.isStart = isStart;
        this.updateObservers();
    }

    /**
     * Checks whether or not this node is the start node or not
     * @return True if this is a start node and false otherwise
     */
    public boolean isStart(){
        return this.isStart;
    }

    /**
     * Adds a given node to the list of neighbouring nodes for this node
     * @param node Node to be added to the set of neighbours for this node
     */
    public void addNeighbour(final AlgoNodeModel node){
        this.neighbours.add(node);
    }

    /**
     * Gets the neighbouring nodes for this node
     * @return A set of nodes that are adjacent to this node
     */
    public List<AlgoNodeModel> getNeighbours() {
        return neighbours;
    }

    /**
     * Computes the distance between this node and other
     * @param other node to calculte distance to
     * @return Distance between this node and other
     */
    public double distanceTo(final AlgoNodeModel other){
        return Math.sqrt(Math.pow(this.algoPositionModel.getX() - other.algoPositionModel.getX(), 2)  + (Math.pow(this.algoPositionModel.getY() - other.algoPositionModel.getY(), 2)));
    }

    /**
     * Determines whether a given point is contained within the dimension of this node
     * @param x x co-ordinate
     * @param y y co-ordinate
     * @return Whether or not a point (x,y) is contained within this node
     */
    public boolean containsPoint(int x, int y){
        return this.algoPositionModel.getX() < x && this.algoPositionModel.getY() < y && this.algoPositionModel.getX() + SIZE > x && this.algoPositionModel.getY() + SIZE > y;
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

        AlgoNodeModel node = (AlgoNodeModel) o;
        return this.algoPositionModel.equals(node.algoPositionModel);
    }

    public boolean isBoundaryNode() {
        return this.isBoundaryNode;
    }
}
