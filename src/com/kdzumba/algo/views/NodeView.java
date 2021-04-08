package com.kdzumba.algo.views;

import com.kdzumba.algo.models.NodeModel;

import javax.swing.*;
import java.awt.*;

public class NodeView extends JPanel{
    public final NodeModel nodeModel;

    private enum NodeColor {
        OBSTRUCTION(new Color(139, 69, 19)),
        START(new Color(0, 191, 255)),
        DESTINATION(new Color(34, 139, 34)),
        SHORTESTPATH(new Color(222, 184, 135)),
        DEFAULT(Color.gray)
        ;
        private final Color color;
        NodeColor(Color color) {
            this.color = color;
        }
    }

    NodeView(NodeModel nodeModel){
        this.nodeModel = nodeModel;
        this.setBackground(NodeColor.DEFAULT.color);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(NodeModel.SIZE, NodeModel.SIZE);
    }

    public void updateColor(){
        if(this.nodeModel.isObstruction()){
            this.setBackground(NodeColor.OBSTRUCTION.color);
        }
        else if(this.nodeModel.isDestination()){
            this.setBackground(NodeColor.DESTINATION.color);
        }
        else if(this.nodeModel.isStart()){
            this.setBackground(NodeColor.START.color);
        }
        else if(this.nodeModel.onShortestPath()){
            this.setBackground(NodeColor.SHORTESTPATH.color);
        }
    }
}
