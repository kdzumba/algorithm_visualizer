package com.kdzumba.algo.models;

import java.util.*;

public class Graph {
    //Using List for nodes and edges because a List maintains insert order, and this
    //is useful for mapping between NodeModels and NodeViews
    private final List<NodeModel> nodeList = new ArrayList<>();
    private final List<Edge> edgeSet = new ArrayList<>();
    private int size;
    private NodeModel startNode;
    private NodeModel destinationNode;

    public Graph(){
        startNode = null;
        destinationNode = null;
    }

    public Graph(final NodeModel startNode, final NodeModel destinationNode){
        this.startNode = startNode;
        this.startNode.setIsStart(true);
        this.destinationNode = destinationNode;
        this.destinationNode.setIsDestination(true);
        this.addNode(this.startNode);
        this.addNode(this.destinationNode);
    }

    /**
     * This is convenience method for creating a graph that is in the form
     * of a grid. The resulting graph is undirected, so u->v == v->u
     * @param xDimension Number of nodes per row
     * @param yDimension Number of nodes per col
     */
    public void createGridGraph(int xDimension, int yDimension){
        for(int i = 0; i < xDimension; i++){
            for(int j = 0; j < yDimension; j++){
                NodeModel node = new NodeModel(j * NodeModel.SIZE, i * NodeModel.SIZE);
                if(this.startNode == null && i == 0 && j == 0){
                    node.setIsStart(true);
                    this.startNode = node;
                }
                if(this.destinationNode == null && i == xDimension - 1 && j == yDimension - 1){
                    node.setIsDestination(true);
                    this.destinationNode = node;
                }
                this.addNode(node);
            }
        }
        //Hypotenuse of a (SIZE, SIZE, sqrt(SIZE^2 + SIZE^2)) resulting from fixed size node
        //double maxDistance = Node.SIZE * Math.sqrt(2); //Used when diagonal movement is allowed
        nodeList.forEach(node -> {
            for(NodeModel other : nodeList){
                if(!node.equals(other) && node.distanceTo(other) <= NodeModel.SIZE){
                    node.addNeighbour(other);
                    this.createEdge(node, other);
                }
            }
        });
    }

    public void createGridGraphWithObstacles(int xDimension, int yDimension){
        this.createGridGraph(xDimension, yDimension);
        this.generateObstructions(xDimension, yDimension);
    }

    public void generateObstructions(int xDimension, int yDimension){
        Random random = new Random();
        int obstructionsCount = 270;

        for(int i = 0; i < obstructionsCount; i++){
            int randomX = random.nextInt(xDimension);
            int randomY = random.nextInt(yDimension);
            Position position = new Position(randomX * NodeModel.SIZE, randomY * NodeModel.SIZE);

            for (NodeModel node : this.nodeList) {
                //Make sure that start and end node don't become obstruction nodes
                if(!node.equals(startNode) && !node.equals(destinationNode) && node.position().equals(position)){
                    node.setObstruction(true);
                }
            }
        }
    }

    public void clearShortestPath(){
        for(NodeModel node : this.nodeList){
            node.setOnShortestPath(false);
        }
    }

    public NodeModel getStartNode(){
        return this.startNode;
    }

    public NodeModel getDestinationNode(){
        return this.destinationNode;
    }

    /**
     * This method returns a copy of the graph's node set instead of the actual
     * set to avoid modifications of the set through the view
     * @return Copy of the graph's node set
     */
    public List<NodeModel> getNodeList(){
        return this.nodeList;
    }

    public int getSize(){
        return this.size;
    }

    /**
     * Creates an edge between two nodes in the graph's node set
     * @param src Source node
     * @param dest Destination node
     * @param weight Cost of moving from source to destination (and vice versa)
     */
    public void createEdge(final NodeModel src, final NodeModel dest, double weight){
        //edge set should only contain nodes that are in the nodes set
        if(!this.nodeList.contains(src) || !this.nodeList.contains(dest)){
            System.out.println("Source and destination should be in the graph");
        }else{
            Edge edge = new Edge(src, dest, weight);
            this.edgeSet.add(edge);
        }
    }

    /**
     * Creates an edge between source and destination nodes. The cost of
     * moving from source to destination is calculated as the distance
     * between the two nodes
     * @param src source node
     * @param dest destination node
     */
    public void createEdge(final NodeModel src, final NodeModel dest){
        double weight = src.distanceTo(dest);
        this.createEdge(src, dest, weight);
    }

    public void removeEdge(final Edge edge){
        this.edgeSet.remove(edge);
    }

    public void addNode(final NodeModel node){
        this.nodeList.add(node);
        this.size ++;
    }

    public void removeNode(final NodeModel node){
        //First remove all edges that are comprised of the node to be removed
       try{
           edgeSet.forEach(edge -> {
               if(edge.source.equals(node) || edge.destination.equals(node)){
                   this.edgeSet.remove(edge);
               }
           });
       }
       catch(Exception e){
           System.out.println("Could not remove edge as exception was thrown");
           System.out.println(e.getMessage());
        }
       //Then remove the node
       this.nodeList.remove(node);
    }

    public static class Edge{
        NodeModel source;
        NodeModel destination;
        double weight;

        Edge(NodeModel fromNode, NodeModel toNode, double weight){
            this.source = fromNode;
            this.destination = toNode;
            this.weight = weight;
        }
    }
}
