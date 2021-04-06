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
    private final int xDimension;
    private final int yDimension;
    Graph graph = new Graph();
    Algorithms algorithms = new Algorithms();

    /**
     * @param xDimension Number nodes per row (Not width)
     * @param yDimension Number nodes per col (Not height)
     */
    Board(int xDimension, int yDimension){
        this.xDimension = xDimension;
        this.yDimension = yDimension;
        graph.createGridGraphWithObstacles(xDimension, yDimension);
        MouseMotionHandler mouseMotionHandler = new MouseMotionHandler();
        this.addMouseMotionListener(mouseMotionHandler);
        MouseClickHandler mouseClickHandler = new MouseClickHandler();
        this.addMouseListener(mouseClickHandler);
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setColor(Color.darkGray);

        g.fillRect(0, 0, NodeModel.SIZE * xDimension, NodeModel.SIZE * yDimension);

        for(NodeModel node : graph.getNodeSet()){
            NodeView nodeView = new NodeView(node);
            nodeView.paintComponent(g);
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
            for(NodeModel node : graph.getNodeSet()){
                if(node.containsPoint(e.getX(), e.getY())){
                    node.setObstruction(true);
                }
            }
            repaint();
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            //TODO Implement some logic for when the mouse is moved here
        }
    }

    class MouseClickHandler implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            for(NodeModel node : graph.getNodeSet()){
                if(node.containsPoint(e.getX(), e.getY())){
                    node.setObstruction(!node.isObstruction());
                }
            }
            repaint();
        }

        @Override
        public void mousePressed(MouseEvent e) {

            //TODO Move this code to be executed when a visualize button is clicked
            graph.clearShortestPath();
            algorithms.breadthFirstSearch(graph.getStartNode(), graph.getDestinationNode(), graph);
            algorithms.shortestPath(graph.getDestinationNode());

            repaint();
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
