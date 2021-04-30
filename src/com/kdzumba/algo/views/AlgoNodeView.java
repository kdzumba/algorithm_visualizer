package com.kdzumba.algo.views;

import com.kdzumba.algo.models.AlgoNodeModel;

import javax.swing.*;
import java.awt.*;

public class AlgoNodeView extends JPanel{
    private final AlgoNodeModel algoNodeModel;

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
    }
}
