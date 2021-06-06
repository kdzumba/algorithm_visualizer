package com.kdzumba.algo.views;

import com.kdzumba.algo.Algorithms;
import com.kdzumba.algo.interfaces.AlgoObserver;
import com.kdzumba.algo.models.AlgoGraphModel;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class AlgoMetrics extends JPanel implements AlgoObserver {

    private final AlgoLabel visitedNodesLabel;
    private final AlgoLabel pathLengthLabel;
    private final AlgoLabel runningTimeLabel;
    private final AlgoLabel pathCostLabel;
    private final int SIZE = 4;
    AlgoGraphModel graphModel;

    public AlgoMetrics(AlgoGraphModel graphModel){
        this.graphModel = graphModel;
        graphModel.addObserver(this);
        this.setBackground(UICommon.COMPONENT_BACKGROUND);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        Border metricsBorder = BorderFactory.createLoweredBevelBorder();
        this.setBorder(BorderFactory.createTitledBorder(metricsBorder, "Algorithm Metrics", TitledBorder.LEFT, TitledBorder.TOP, UICommon.ALGO_FONT, Color.white));
        this.visitedNodesLabel = new AlgoLabel("Visited Nodes: " );
        this.pathLengthLabel = new AlgoLabel("Path Length: ");
        this.pathCostLabel = new AlgoLabel("Path Cost: ");
        this.runningTimeLabel = new AlgoLabel("Running Time: ");

        this.add(this.visitedNodesLabel);
        this.add(this.pathLengthLabel);
        this.add(this.pathCostLabel);
        this.add(this.runningTimeLabel);
    }

    @Override
    public Dimension getPreferredSize(){
        return new Dimension(UICommon.COMPONENT_WIDTH, UICommon.COMPONENT_HEIGHT * this.SIZE);
    }

    @Override
    public Dimension getMaximumSize(){
        return new Dimension(UICommon.COMPONENT_WIDTH, UICommon.COMPONENT_HEIGHT * this.SIZE);
    }

    @Override
    public Dimension getMinimumSize(){
        return new Dimension(UICommon.COMPONENT_WIDTH, UICommon.COMPONENT_HEIGHT * this.SIZE);
    }

    @Override
    public void update() {
        this.visitedNodesLabel.setText("Visited Nodes: " + this.graphModel.getVisitedNodesCount());
        this.pathLengthLabel.setText("Path Length: " + this.graphModel.getPathLength());
        this.pathCostLabel.setText("Path Cost: " + this.graphModel.getDestinationNode().getGCost());
        this.runningTimeLabel.setText("Running Time: " + Algorithms.runningTime + " ms");
    }
}
