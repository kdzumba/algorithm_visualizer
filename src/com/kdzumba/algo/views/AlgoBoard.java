package com.kdzumba.algo.views;

import com.kdzumba.algo.Algorithms;
import com.kdzumba.algo.models.AlgoGraphModel;
import com.kdzumba.algo.models.AlgoNodeModel;
import com.kdzumba.algo.models.AlgoPositionModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Random;
import java.util.Stack;

public class AlgoBoard extends JPanel {

    private final int xDimension;
    private final int yDimension;
    private final int NODEMARGIN = 1;
    private final AlgoGraphModel algoGraphModel = new AlgoGraphModel();

    class MouseMotionHandler implements MouseMotionListener {
        @Override
        public void mouseDragged(MouseEvent e) {
            for(AlgoNodeModel algoNodeModel : algoGraphModel.getNodeList()){
                if(algoNodeModel.containsPoint(e.getX(), e.getY())){
                    algoNodeModel.setObstruction(true);
                }
            }
            updateBoard();
        }

        @Override
        public void mouseMoved(MouseEvent e) {
        }
    }

    class MouseClickHandler implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            for(AlgoNodeModel algoNodeModel : algoGraphModel.getNodeList()){
                //Invert the obstruction state of a node model when it's clicked on
                if(algoNodeModel.containsPoint(e.getX(), e.getY())) {
                    algoNodeModel.setObstruction(!algoNodeModel.isObstruction());
                }
            }
            updateBoard();
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
    }

    /**
     * @param xDimension Number nodes per row (Not width)
     * @param yDimension Number nodes per col (Not height)
     */
    AlgoBoard(int xDimension, int yDimension){
        this.xDimension = xDimension;
        this.yDimension = yDimension;
        this.setBackground(new Color(80, 80, 80));
        Random random = new Random();

        //Randomly set the source and destination nodes on graph creation
        AlgoPositionModel srcPos = new AlgoPositionModel(random.nextInt(xDimension), random.nextInt(yDimension));
        AlgoPositionModel destPos = new AlgoPositionModel(random.nextInt(xDimension), random.nextInt(yDimension));
        this.algoGraphModel.createGridGraphWithObstacles(xDimension, yDimension, srcPos, destPos);

        MouseMotionHandler mouseMotionHandler = new MouseMotionHandler();
        this.addMouseMotionListener(mouseMotionHandler);
        MouseClickHandler mouseClickHandler = new MouseClickHandler();
        this.addMouseListener(mouseClickHandler);
        this.setLayout(new GridLayout(xDimension, yDimension, NODEMARGIN, NODEMARGIN));

        //Create a node view for each node model in the graph's node list
        for(AlgoNodeModel algoNodeModel : algoGraphModel.getNodeList()){
            this.add(new AlgoNodeView(algoNodeModel));
        }

        //validate the component hierarchy to display added components
        this.validate();
        //Update board to display start and end node
        this.updateBoard();
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

    //TODO: properly implement this functionality
    public AlgoDialog displayDialog(JFrame frame){
        Stack<AlgoNodeModel> shortestPath = Algorithms.shortestPath(this.algoGraphModel.getDestinationNode());
        if(shortestPath.isEmpty()){
            return new AlgoDialog(frame, "Destination Node cannot be reached", true);
        }
        else return null;
    }
}
