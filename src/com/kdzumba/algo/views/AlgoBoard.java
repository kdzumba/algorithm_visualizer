package com.kdzumba.algo.views;

import com.kdzumba.algo.models.AlgoGraphModel;
import com.kdzumba.algo.models.AlgoNodeModel;
import com.kdzumba.algo.models.AlgoPositionModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.Random;

/**
 * The AlgoBoard class models a visualization of a 2 dimensional graph
 */
public class AlgoBoard extends JPanel {

    private final int xDimension;
    private final int yDimension;
    private final int NODEMARGIN = 0;
    private final AlgoGraphModel algoGraphModel = new AlgoGraphModel();
    private AlgoPositionModel focusedLocation;

    class MouseMotionHandler extends MouseMotionAdapter {
        @Override
        public void mouseDragged(MouseEvent e) {
            for(AlgoNodeModel algoNodeModel : algoGraphModel.getNodeList()){
                if(!algoNodeModel.isBoundaryNode() && algoNodeModel.containsPoint(e.getX() + focusedLocation.getX(), e.getY() + focusedLocation.getY())){
                    switch (AlgoTerrainPicker.selectedType){
                        case WALL -> algoNodeModel.setWall(true);
                        case ROCKY -> algoNodeModel.setRocky(true);
                        case WATER -> algoNodeModel.setWater(true);
                        case PORTAL -> algoNodeModel.setPortal(true);
                        case GRASS -> algoNodeModel.setGrass(true);
                    }
                }
            }
        }
    }

    /**
     * @param xDimension Number nodes per row (Not width)
     * @param yDimension Number nodes per col (Not height)
     */
    AlgoBoard(int xDimension, int yDimension){
        this.xDimension = xDimension;
        this.yDimension = yDimension;
        this.setBackground(Color.darkGray);// Color(40, 40, 40));
        this.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 5));
        Random random = new Random();

        //Randomly set the source and destination nodes on graph creation
        AlgoPositionModel srcPos = new AlgoPositionModel(random.nextInt(xDimension), random.nextInt(yDimension));
        AlgoPositionModel destPos = new AlgoPositionModel(random.nextInt(xDimension), random.nextInt(yDimension));
        this.algoGraphModel.createGridGraph(xDimension, yDimension, srcPos, destPos);

        MouseMotionHandler mouseMotionHandler = new MouseMotionHandler();
        this.addMouseMotionListener(mouseMotionHandler);
        this.setLayout(new GridLayout(xDimension, yDimension, NODEMARGIN, NODEMARGIN));

        //Create a node view for each node model in the graph's node list
        for(AlgoNodeModel algoNodeModel : algoGraphModel.getNodeList()){
            //TODO: Clean this up after testing, it's a mess
            AlgoNodeView nodeView = new AlgoNodeView(algoNodeModel);
            algoNodeModel.addObserver(nodeView);
            this.add(nodeView);
        }

        //validate the component hierarchy to display added components
        this.validate();
        //Update board to display start and end node
        this.updateBoard();
    }

    public int getxDimension(){ return this.xDimension;}
    public int getyDimension(){return this.yDimension;}

    public void setFocusedLocation(int x, int y){
        this.focusedLocation = new AlgoPositionModel(x, y);
    }

    public AlgoGraphModel getGraph(){
        return this.algoGraphModel;
    }

    //Render each node with the latest correct color for its state (from the model)
    //Note: All update operations are performed on the node model and the node view
    //simply displays the state of the node model at a time
    public void updateBoard(){
        //TODO: Figure out if this is the best way to do this (using component)
        for(Component node : this.getComponents()){
            if(node instanceof AlgoNodeView){
                ((AlgoNodeView) node).updateColor();
            }
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(xDimension * AlgoNodeModel.SIZE, yDimension * AlgoNodeModel.SIZE);
    }
}
