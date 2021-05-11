package com.kdzumba.algo.models;

import java.util.*;

public class AlgoGraphModel extends AlgoObservable{
    private final List<AlgoNodeModel> nodeList = new ArrayList<>();
    private final List<AlgoEdgeModel> edgeList = new ArrayList<>();
    private AlgoNodeModel startNode;
    private AlgoNodeModel destinationNode;

    public AlgoGraphModel(){
        startNode = null;
        destinationNode = null;
    }

    /**
     * Creates a graph with two nodes, a given start node and a given destination node
     * @param startNode Start node for the graph to be created
     * @param destinationNode Destination node for the graph to be created
     */
    public AlgoGraphModel(final AlgoNodeModel startNode, final AlgoNodeModel destinationNode){
        this.startNode = startNode;
        this.startNode.setIsStart(true);
        this.destinationNode = destinationNode;
        this.destinationNode.setIsDestination(true);
        this.addNode(this.startNode);
        this.addNode(this.destinationNode);
    }

    /**
     * This is convenience method for creating a graph that is in the for of a grid.
     * The resulting graph is undirected, so u->v == v->u
     * @param xDimension Number of nodes per row
     * @param yDimension Number of nodes per col
     * @param src The source node position (should be within the dimensions of the board
     * @param dest The destination node position (should be within the dimensions of the board)
     */
    public void createGridGraph(int xDimension, int yDimension, AlgoPositionModel src, AlgoPositionModel dest){

        //TODO: Find a better way of checking src and dest compliance maybe?? This looks messy
        if(src.getX() < 0 || src.getX() > xDimension || src.getY() < 0 || src.getY() > yDimension){
            System.out.println("source node position must be within the dimensions of the board");
            return;
        }
        
        if(dest.getX() < 0 || dest.getX() > xDimension || dest.getY() < 0 || dest.getY() > yDimension){
            System.out.println("Destination node position must be within the dimensions of the board");
            return;
        }

        for(int i = 0; i < xDimension; i++){
            for(int j = 0; j < yDimension; j++){
                AlgoNodeModel node = new AlgoNodeModel(j * AlgoNodeModel.SIZE, i * AlgoNodeModel.SIZE);
                if(this.startNode == null && i == src.getX() && j == src.getY()){
                    node.setIsStart(true);
                    this.startNode = node;
                }
                if(this.destinationNode == null && i == dest.getX() && j == dest.getY()){
                    node.setIsDestination(true);
                    this.destinationNode = node;
                }

//                Uncomment for border of obstructions around the grid
//                if(i == xDimension - 1 || i == 0 || j == yDimension - 1 || j == 0){
//                    node.setBoundaryNode(true);
//                    node.setObstruction(true);
//                }
                this.addNode(node);
            }
        }
        //Hypotenuse of a (SIZE, SIZE, sqrt(SIZE^2 + SIZE^2)) resulting from fixed size node
//        double maxDistance = AlgoNodeModel.SIZE * Math.sqrt(2); //Used when diagonal movement is allowed
        nodeList.forEach(node -> {
            for(AlgoNodeModel other : nodeList){
                if(!node.equals(other) && node.distanceTo(other) <= AlgoNodeModel.SIZE){
                    node.addNeighbour(other);
                    AlgoEdgeModel edgeModel = new AlgoEdgeModel(node, other, node.distanceTo(other));
                    node.addEdge(edgeModel);
                    edgeList.add(edgeModel);
                }
            }
        });
    }

    /**
     * Creates a grid graph with random nodes set as obstruction nodes
     * @param xDimension Number of nodes per row
     * @param yDimension Number of nodes per column
     * @param srcPos Position of the source node for the graph
     * @param destPos Position of the destination node for the graph
     */
    public void createGridGraphWithObstacles(int xDimension, int yDimension, AlgoPositionModel srcPos, AlgoPositionModel destPos){
        this.createGridGraph(xDimension, yDimension, srcPos, destPos);
        this.generateObstructions(xDimension, yDimension);
    }

    /**
     * Sets random nodes as obstruction nodes for the graph
     * @param xDimension Number of nodes per row
     * @param yDimension Number of nodes per column
     */
    public void generateObstructions(int xDimension, int yDimension){
        Random random = new Random();
        int obstructionsCount = 400; //TODO Get rid of this magic number

        for(int i = 0; i < obstructionsCount; i++){
            int randomX = random.nextInt(xDimension);
            int randomY = random.nextInt(yDimension);
            AlgoPositionModel algoPositionModel = new AlgoPositionModel(randomX * AlgoNodeModel.SIZE, randomY * AlgoNodeModel.SIZE);

            for (AlgoNodeModel node : this.nodeList) {
                //Make sure that start and end node don't become obstruction nodes
                if(!node.equals(startNode) && !node.equals(destinationNode) && node.position().equals(algoPositionModel)){
                    node.setObstruction(true);
                }
            }
        }
    }

    public void clearShortestPath(){
        for(AlgoNodeModel node : this.nodeList){
            node.setOnShortestPath(false);
        }
    }

    public void clearVisitedNodes(){
        for(AlgoNodeModel nodeModel : this.nodeList){
            nodeModel.setIsVisited(false);
        }
    }

    public void clearObstructions(){
        for(AlgoNodeModel nodeModel : this.nodeList){
            nodeModel.setObstruction(false);
        }
    }

    public void updateNodeObservers(){
        for(AlgoNodeModel nodeModel : this.nodeList){
            nodeModel.updateObservers();
        }
    }

    public AlgoNodeModel getStartNode(){
        return this.startNode;
    }

    public AlgoNodeModel getDestinationNode(){
        return this.destinationNode;
    }

    /**
     * This method returns a copy of the graph's node set instead of the actual
     * set to avoid modifications of the set through the view
     * @return Copy of the graph's node set
     */
    public List<AlgoNodeModel> getNodeList(){
        return this.nodeList;
    }

    /**
     * Creates an edge between two nodes in the graph's node set
     * @param src Source node
     * @param dest Destination node
     * @param weight Cost of moving from source to destination (and vice versa)
     */
    public void createEdge(final AlgoNodeModel src, final AlgoNodeModel dest, double weight){
        //edge set should only contain nodes that are in the nodes set
        if(!this.nodeList.contains(src) || !this.nodeList.contains(dest)){
            System.out.println("Source and destination should be in the graph");
        }else{
            AlgoEdgeModel edge = new AlgoEdgeModel(src, dest, weight);
            this.edgeList.add(edge);
        }
    }

    /**
     * Creates an edge between source and destination nodes. The cost of
     * moving from source to destination is calculated as the distance
     * between the two nodes
     * @param src source node
     * @param dest destination node
     */
    public void createEdge(final AlgoNodeModel src, final AlgoNodeModel dest){
        double weight = src.distanceTo(dest);
        this.createEdge(src, dest, weight);
    }

    /**
     * Removes an edge from this graph if it is contained in the list of edges
     * @param edge An edge to be removed
     */
    public void removeEdge(final AlgoEdgeModel edge){
        this.edgeList.remove(edge);
    }

    /**
     * Addes a node to the graph
     * @param node Node to be added to the graph
     */
    public void addNode(final AlgoNodeModel node){
        this.nodeList.add(node);
    }

    /**
     * Removes a given node from the graph, as well as any edges that contain
     * this node
     * @param node A node to be removed from the graph
     */
    public void removeNode(final AlgoNodeModel node){
        //First remove all edges that are comprised of the node to be removed
        //TODO Figure out if we really neet a try catch here??
       try{
           edgeList.forEach(edge -> {
               if(edge.source.equals(node) || edge.destination.equals(node)){
                   this.edgeList.remove(edge);
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

    public List<AlgoEdgeModel> getEdgeList() {
        return this.edgeList;
    }

    public int getVisitedNodesCount() {
        int count = 0;
        for(AlgoNodeModel nodeModel : this.nodeList){
            if(nodeModel.isVisited()){
                count ++;
            }
        }

        return count;
    }

    public int getPathLength() {
        int count = 0;
        for(AlgoNodeModel nodeModel : this.nodeList){
            if(nodeModel.onShortestPath()){
                count ++;
            }
        }

        return count;
    }
}
