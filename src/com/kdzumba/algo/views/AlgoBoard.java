package com.kdzumba.algo.views;

import com.kdzumba.algo.models.AlgoGraphModel;
import com.kdzumba.algo.models.AlgoNodeModel;
import com.kdzumba.algo.models.AlgoPositionModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
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
    private final int NODEMARGIN = 1;
    private final AlgoGraphModel algoGraphModel = new AlgoGraphModel();
    private AlgoPositionModel focusedLocation;

    class MouseMotionHandler extends MouseMotionAdapter {
        @Override
        public void mouseDragged(MouseEvent e) {
            for(AlgoNodeModel algoNodeModel : algoGraphModel.getNodeList()){
                if(!algoNodeModel.isBoundaryNode() && algoNodeModel.containsPoint(e.getX() + focusedLocation.getX(), e.getY() + focusedLocation.getY())){
                    algoNodeModel.setObstruction(true);
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
        this.setBackground(Color.darkGray);
        Random random = new Random();

        //Randomly set the source and destination nodes on graph creation
        AlgoPositionModel srcPos = new AlgoPositionModel(random.nextInt(xDimension) + 1, random.nextInt(yDimension ) + 1);
        AlgoPositionModel destPos = new AlgoPositionModel(random.nextInt(xDimension) + 1, random.nextInt(yDimension ) + 1);
        this.algoGraphModel.createGridGraph(xDimension, yDimension, srcPos, destPos);

        MouseMotionHandler mouseMotionHandler = new MouseMotionHandler();
        this.addMouseMotionListener(mouseMotionHandler);
        this.setLayout(new GridLayout(xDimension, yDimension, NODEMARGIN, NODEMARGIN));
//        this.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 5));

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
