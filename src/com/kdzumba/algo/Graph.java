package com.kdzumba.algo;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Graph {
    private final Set<Node> nodeSet = new HashSet<>();
    private final Set<Edge> edgeSet = new HashSet<>();
    private int size;
    private Node startNode;
    private Node destinationNode;

    public Graph(){
        startNode = null;
        destinationNode = null;
    }

    public Graph(final Node startNode, final Node destinationNode){
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
                Node node = new Node(i * Node.SIZE, j * Node.SIZE);
                //Make the node at (0,0) to be the start node and node at bottom
                //right corner to be destination node (if start and dest node aren't set
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
        //Hypotenuse of a (50, 50, sqrt(50^2 + 50^2)) resulting from fixed size node
        //double maxDistance = Node.SIZE * Math.sqrt(2); //Used when diagonal movement is allowed
        nodeSet.forEach(node -> {
            for(Node other : nodeSet){
                if(!node.equals(other) && node.distanceTo(other) <= Node.SIZE){
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
        int obstructionsCount = 150;

        for(int i = 0; i < obstructionsCount; i++){
            int randomX = random.nextInt(xDimension);
            int randomY = random.nextInt(yDimension);
            Node.NodePosition position = new Node.NodePosition(randomX * Node.SIZE, randomY * Node.SIZE);

            for (Node node : this.nodeSet) {
                //Make sure that start and end node don't become obstruction nodes
                if(!node.equals(startNode) && !node.equals(destinationNode) && node.position().equals(position)){
                    node.setObstruction(true);
                }
            }
        }
    }

    public void clearShortestPath(){
        for(Node node : this.nodeSet){
            node.setOnShortestPath(false);
        }
    }

    public Node getStartNode(){
        return this.startNode;
    }

    public Node getDestinationNode(){
        return this.destinationNode;
    }

    /**
     * This method returns a copy of the graph's node set instead of the actual
     * set to avoid modifications of the set through the view
     * @return Copy of the graph's node set
     */
    public Set<Node> getNodeSet(){
        return Set.copyOf(this.nodeSet);
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
    public void createEdge(final Node src, final Node dest, double weight){
        //edge set should only contain nodes that are in the nodes set
        if(!this.nodeSet.contains(src) || !this.nodeSet.contains(dest)){
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
    public void createEdge(final Node src, final Node dest){
        double weight = src.distanceTo(dest);
        this.createEdge(src, dest, weight);
    }

    public void removeEdge(final Edge edge){
        this.edgeSet.remove(edge);
    }

    public void addNode(final Node node){
        this.nodeSet.add(node);
        this.size ++;
    }

    public void removeNode(final Node node){
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
       this.nodeSet.remove(node);
    }

    public static class Edge{
        Node source;
        Node destination;
        double weight;

        Edge(Node fromNode, Node toNode, double weight){
            this.source = fromNode;
            this.destination = toNode;
            this.weight = weight;
        }
    }
}
