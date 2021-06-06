package com.kdzumba.algo.views;

import com.kdzumba.algo.models.AlgoNodeModel;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class AlgoTerrainPicker extends JPanel {
    public static NodeType selectedType = NodeType.WALL;

    public enum NodeType{
        WALL,
        ROCKY,
        WATER,
        PORTAL,
        GRASS
    }

    public AlgoTerrainPicker(AlgoBoard board){
        this.setBackground(UICommon.COMPONENT_BACKGROUND);
        this.setLayout(new GridBagLayout());

        Border metricsBorder = BorderFactory.createLoweredBevelBorder();
        this.setBorder(BorderFactory.createTitledBorder(metricsBorder, "Select Terrain", TitledBorder.LEFT, TitledBorder.TOP, UICommon.ALGO_FONT, Color.white));

        JButton obstructionButton = new JButton();
        obstructionButton.setBackground(Color.black);
        obstructionButton.addActionListener(e -> {
            selectedType = NodeType.WALL;
        });
        AlgoLabel obstructionLabel = new AlgoLabel("Wall");

        JButton rockyButton = new JButton();
        rockyButton.setBackground(UICommon.ROCK_COLOR);
        rockyButton.addActionListener(e -> {
            selectedType = NodeType.ROCKY;
        });
        AlgoLabel rockyLabel = new AlgoLabel("Rocky (Weight = " + (int) (2 * AlgoNodeModel.SIZE) + ")");

        JButton waterButton = new JButton();
        waterButton.setBackground(UICommon.WATER_COLOR);
        waterButton.addActionListener(e -> {
            selectedType = NodeType.WATER;
        });
        AlgoLabel waterLabel = new AlgoLabel("Water (Weight = " + (int) (2.5 * AlgoNodeModel.SIZE) + ")");

        JButton grassButton = new JButton();
        grassButton.setBackground(UICommon.GRASS_COLOR);
        grassButton.addActionListener(e -> {
            selectedType = NodeType.GRASS;
        });
        AlgoLabel portalLabel = new AlgoLabel("Grass (Weight = " + (int) (1.5 * AlgoNodeModel.SIZE) + ")");

        GridBagConstraints layoutConstraints = new GridBagConstraints();
        layoutConstraints.fill = GridBagConstraints.HORIZONTAL;
        layoutConstraints.gridy = 0;
        layoutConstraints.anchor = GridBagConstraints.LINE_START;
        layoutConstraints.ipadx = 10;
        this.add(obstructionLabel, layoutConstraints);
        layoutConstraints.ipadx = 0;
        layoutConstraints.ipady = 20;
        this.add(obstructionButton, layoutConstraints);

        layoutConstraints.gridy = 1;
        layoutConstraints.ipadx = 10;
        layoutConstraints.ipady = 0;
        this.add(rockyLabel, layoutConstraints);
        layoutConstraints.ipadx = 0;
        layoutConstraints.ipady = 20;
        this.add(rockyButton, layoutConstraints);

        layoutConstraints.gridy = 2;
        layoutConstraints.ipadx = 10;
        layoutConstraints.ipady = 0;
        this.add(waterLabel, layoutConstraints);
        layoutConstraints.ipadx = 0;
        layoutConstraints.ipady = 20;
        this.add(waterButton, layoutConstraints);

        layoutConstraints.gridy = 3;
        layoutConstraints.ipadx = 10;
        layoutConstraints.ipady = 0;
        this.add(portalLabel, layoutConstraints);
        layoutConstraints.ipadx = 0;
        layoutConstraints.ipady = 20;
        this.add(grassButton, layoutConstraints);
    }

    @Override
    public Dimension getPreferredSize(){
        return new Dimension(UICommon.COMPONENT_WIDTH, UICommon.COMPONENT_HEIGHT * 4);
    }

    @Override
    public Dimension getMaximumSize(){
        return new Dimension(UICommon.COMPONENT_WIDTH, UICommon.COMPONENT_HEIGHT * 4);
    }

    @Override
    public Dimension getMinimumSize(){
        return new Dimension(UICommon.COMPONENT_WIDTH, UICommon.COMPONENT_HEIGHT * 4);
    }
}
