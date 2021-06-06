package com.kdzumba.algo.models;

import java.util.LinkedList;
import java.util.List;
import java.lang.Math;

public class AlgoNodeModel extends AlgoObservable{

    public final static int SIZE = 30; //Width and height (Nodes are square blocks)
    private boolean isDestination = false;
    private boolean isStart = false;
    private boolean isWall = false;
    private boolean isVisited = false;
    private boolean onShortestPath = false;
    private boolean isBoundaryNode = false;
    private double gCost; //This is the distance from the start node to this node
    private double fCost;
    private double weight = SIZE;
    private AlgoNodeModel parent;
    private AlgoPositionModel algoPositionModel;
    private final List<AlgoNodeModel> neighbours = new LinkedList<>();
    private final List<AlgoEdgeModel> edges = new LinkedList<>();
    private boolean isRocky;
    private boolean isWater;
    private boolean isPortal;
    private boolean isGrass;

    public void setWeight(int weight){
        this.weight = weight;
    }

    public double getWeight(){
        return this.weight;
    }

    public AlgoNodeModel(int xPos, int yPos){
        this.algoPositionModel = new AlgoPositionModel(xPos, yPos);
    }

    public double getGCost(){
        return this.gCost;
    }

    public void setGCost(double gCost){
        this.gCost = gCost;
    }

    public double getFCost() {return  this.fCost;}
    public void setFCost(double hCost){ this.fCost = hCost;}

    public AlgoPositionModel position(){
        return this.algoPositionModel;
    }

    public void setBoundaryNode(boolean isBoundaryNode){
        this.isBoundaryNode = isBoundaryNode;
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
     * Sets this node to be an wall node or not an wall node
     * @param wall True if this node should be an ostruction node, false otherwise
     */
    public void setWall(boolean wall){
        this.isWall = wall;
        this.updateObservers();
    }

    /**
     * Checks whether or not this node is an wall node or not
     * @return True if this is an wall node, false otherwise
     */
    public boolean isWall(){
        return this.isWall;
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

    public void addEdge(AlgoEdgeModel edge){
        this.edges.add(edge);
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

    public void setRocky(boolean isRocky) {
        this.isRocky = isRocky;
        this.weight = 2 * SIZE;
        this.updateObservers();
    }

    public boolean isRocky(){
        return this.isRocky;
    }

    public void setWater(boolean isWater) {
        this.isWater = isWater;
        this.weight = 2.5 * SIZE;
        this.updateObservers();
    }

    public boolean isWater(){
        return this.isWater;
    }

    public void setPortal(boolean isPortal) {
        this.isPortal = isPortal;
        this.weight = 0.5 * SIZE;
//        this.updateObservers();
    }

    public boolean isPortal(){
        return this.isPortal;
    }

    public void setGrass(boolean isGrass) {
        this.isGrass = isGrass;
        this.weight = 1.5 * SIZE;
        this.updateObservers();
    }

    public boolean isGrass(){
        return this.isGrass;
    }
}
