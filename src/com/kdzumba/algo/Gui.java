package com.kdzumba.algo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Stack;

public class Gui extends JFrame {

    /**
     * Creates a frame with xDimension nodes per row and yDimension nodes
     * per column
     * @param xDimension Number of nodes per row
     * @param yDimension Number of nodes per col
     */
    public Gui(int xDimension, int yDimension){
        this.setTitle("Algorithm Visualizer");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Board board = new Board(xDimension, yDimension);
        this.add(board);
        this.pack();
        this.setVisible(true);
    }

    public static class Board extends JPanel {
        private final int xDimension;
        private final int yDimension;
        Graph graph = new Graph();
        Algorithms algorithms = new Algorithms();
        private final int NODESPACING = 2;

        /**
         * @param xDimension Number nodes per row
         * @param yDimension Number nodes per col
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
            g.fillRect(0, 0, Node.SIZE * xDimension, Node.SIZE * yDimension);
            g.setColor(Color.gray);

            for(Node node : graph.getNodeSet()){
                if(node.isObstruction()){
                    g.setColor(new Color(139, 69, 19));
                }
                else if(node.isStart()){
                    g.setColor(new Color(0, 191, 255));
                }
                else if(node.isDestination()){
                    g.setColor(new Color(34, 139, 34));
                }
                else if(node.onShortestPath()){
                    g.setColor(new Color(222, 184, 135));
                    g.fillRect(node.position().getX(), node.position().getY(), Node.SIZE, Node.SIZE);
                    continue;
                }
                else{
                    g.setColor(Color.gray);
                }
                g.fillRect(node.position().getX(), node.position().getY(), Node.SIZE - NODESPACING, Node.SIZE - NODESPACING);
            }
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(xDimension * Node.SIZE, yDimension * Node.SIZE);
        }

        class MouseMotionHandler implements MouseMotionListener{

            @Override
            public void mouseDragged(MouseEvent e) {
                //TODO Unmark node that's already an obstruction
                for(Node node : graph.getNodeSet()){
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

        class MouseClickHandler implements MouseListener{

            @Override
            public void mouseClicked(MouseEvent e) {
                for(Node node : graph.getNodeSet()){
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
                Stack<Node> visited = algorithms.breadthFirstSearch(graph.getStartNode(), graph.getDestinationNode(), graph);
                algorithms.shortestPath(graph.getDestinationNode(), visited);
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

}