package com.kdzumba.algo.views;

import com.kdzumba.algo.Algorithms;
import com.kdzumba.algo.models.Graph;
import com.kdzumba.algo.models.NodeModel;
import org.w3c.dom.Node;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Board extends JPanel {
    private final int xDimension;
    private final int yDimension;
    private final int NODEMARGIN = 2;
    private final Graph graph = new Graph();
    Algorithms algorithms = new Algorithms();

    /**
     * @param xDimension Number nodes per row (Not width)
     * @param yDimension Number nodes per col (Not height)
     */
    Board(int xDimension, int yDimension){
        this.xDimension = xDimension;
        this.yDimension = yDimension;
        this.setBackground(Color.darkGray);
        graph.createGridGraph(xDimension, yDimension);
        MouseMotionHandler mouseMotionHandler = new MouseMotionHandler();
        this.addMouseMotionListener(mouseMotionHandler);
        MouseClickHandler mouseClickHandler = new MouseClickHandler();
        this.addMouseListener(mouseClickHandler);

        this.setLayout(new GridLayout(xDimension, yDimension, NODEMARGIN, NODEMARGIN));
        for(NodeModel nodeModel : graph.getNodeSet()){
            this.add(new NodeView(nodeModel));
        }

        this.validate();
        //Update board to display start and end node
        this.updateBoard();
    }

    //Render each node with the latest correct color for its state
    public void updateBoard(){
        for(Component node : this.getComponents()){
            if(node instanceof NodeView){
                ((NodeView) node).updateColor();
            }
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(xDimension * NodeModel.SIZE, yDimension * NodeModel.SIZE);
    }

    class MouseMotionHandler implements MouseMotionListener {
        @Override
        public void mouseDragged(MouseEvent e) {
            //TODO Unmark node that's already an obstruction
            for(NodeModel nodeModel : graph.getNodeSet()){
                if(nodeModel.containsPoint(e.getX(), e.getY())){
                    nodeModel.setObstruction(!nodeModel.isObstruction());
                }
            }
            updateBoard();
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            //TODO Implement some logic for when the mouse is moved here
        }
    }

    class MouseClickHandler implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            for(NodeModel nodeModel : graph.getNodeSet()){
                if(nodeModel.containsPoint(e.getX(), e.getY())){
                    nodeModel.setObstruction(!nodeModel.isObstruction());
                }
            }
            updateBoard();
        }

        @Override
        public void mousePressed(MouseEvent e) {

            //TODO Move this code to be executed when a visualize button is clicked
            graph.clearShortestPath();
            algorithms.breadthFirstSearch(graph.getStartNode(), graph.getDestinationNode(), graph);
            algorithms.shortestPath(graph.getDestinationNode());
            updateBoard();
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
}
