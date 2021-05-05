package com.kdzumba.algo.views;

import com.kdzumba.algo.interfaces.AlgoObserver;
import com.kdzumba.algo.models.AlgoNodeModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;

/**
 * The AlgoNodeView is a visual representation of an AlgoNodeModel's state
 */
public class AlgoNodeView extends JButton implements AlgoObserver {
    private final AlgoNodeModel algoNodeModel;

    @Override
    public void update() {
        this.updateColor();
    }

    private enum NodeColor {
        OBSTRUCTION(new Color(139, 69, 19)),
        START(new Color(0, 191, 255)),
        DESTINATION(new Color(34, 139, 34)),
        SHORTESTPATH(new Color(222, 184, 135)),
        VISITED(new Color(100, 150, 100)),
        DEFAULT(new Color(150, 150, 150))
        ;
        private final Color color;
        NodeColor(Color color) {
            this.color = color;
        }
    }

    AlgoNodeView(AlgoNodeModel algoNodeModel){
        this.algoNodeModel = algoNodeModel;
        this.addActionListener(e -> {
            if(!algoNodeModel.isBoundaryNode()){
                algoNodeModel.setObstruction(!algoNodeModel.isObstruction());
                updateColor();
            }
        });

        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                Component source = (Component) e.getSource();
                AlgoBoard parent = (AlgoBoard) source.getParent();
                //This sends the location of the node view on which the event originated
                //and is used as an offset of the event's x-position in the parent
                //mouse dragged handler
                parent.setFocusedLocation((int)getLocation().getX(), (int)getLocation().getY());
                //Mouse dragged events handled by the parent of the node view(to allow for dragging
                //over multiple nodes)
                parent.dispatchEvent(e);
            }
        });
        this.setBackground(NodeColor.DEFAULT.color);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(AlgoNodeModel.SIZE, AlgoNodeModel.SIZE);
    }

    public void updateColor(){
        if(this.algoNodeModel.isObstruction()){
            this.setBackground(NodeColor.OBSTRUCTION.color);
        }
        else if(this.algoNodeModel.isDestination()){
            this.setBackground(NodeColor.DESTINATION.color);
        }
        else if(this.algoNodeModel.isStart()){
            this.setBackground(NodeColor.START.color);
        }
        else if(this.algoNodeModel.onShortestPath()){
            this.setBackground(NodeColor.SHORTESTPATH.color);
        }
        else if(this.algoNodeModel.isVisited()){
            this.setBackground(NodeColor.VISITED.color);
        }
        else{
            this.setBackground(NodeColor.DEFAULT.color);
        }

        repaint();
    }
}
