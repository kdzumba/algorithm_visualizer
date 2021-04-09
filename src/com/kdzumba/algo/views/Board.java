package com.kdzumba.algo.views;

import com.kdzumba.algo.Algorithms;
import com.kdzumba.algo.models.Graph;
import com.kdzumba.algo.models.NodeModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Board extends JPanel {

    class MouseMotionHandler implements MouseMotionListener {
        @Override
        public void mouseDragged(MouseEvent e) {
            //TODO Handle situation where mouse is dragged on same node for long. This obstructs
            //TODO the node and undoes the obstruction after
            for(NodeModel nodeModel : graph.getNodeList()){
                if(nodeModel.containsPoint(e.getX(), e.getY())){
                    nodeModel.setObstruction(true);
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
            for(NodeModel nodeModel : graph.getNodeList()){
                if(nodeModel.containsPoint(e.getX(), e.getY())) {
                    nodeModel.setObstruction(!nodeModel.isObstruction());
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

    private final int xDimension;
    private final int yDimension;
    private final int NODEMARGIN = 2;
    private final Graph graph = new Graph();

    /**
     * @param xDimension Number nodes per row (Not width)
     * @param yDimension Number nodes per col (Not height)
     */
    Board(int xDimension, int yDimension){
        this.xDimension = xDimension;
        this.yDimension = yDimension;
        this.setBackground(new Color(80, 80, 80));
        graph.createGridGraphWithObstacles(xDimension, yDimension);
        MouseMotionHandler mouseMotionHandler = new MouseMotionHandler();
        this.addMouseMotionListener(mouseMotionHandler);
        MouseClickHandler mouseClickHandler = new MouseClickHandler();
        this.addMouseListener(mouseClickHandler);
        this.setLayout(new GridLayout(xDimension, yDimension, NODEMARGIN, NODEMARGIN));

        for(NodeModel nodeModel : graph.getNodeList()){
            this.add(new NodeView(nodeModel));
        }

        //validate the component hierarchy to display added components
        this.validate();
        //Update board to display start and end node
        this.updateBoard();
    }

    public Graph getGraph(){
        return this.graph;
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
}
