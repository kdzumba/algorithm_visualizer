package com.kdzumba.algo.views;

import javax.swing.*;
import java.awt.*;
import java.util.Hashtable;

public class AlgoSlider extends JSlider {
    static final public int MIN_DELAY = 1;
    static final public int MAX_DELAY = 200;
    static final public int DEFAULT_DELAY = 20;

    public AlgoSlider(int orientation){
        super(orientation);
        this.setMinimum(this.MIN_DELAY);
        this.setMaximum(this.MAX_DELAY);
        this.setValue(this.DEFAULT_DELAY);
        this.setBackground(UICommon.COMPONENT_BACKGROUND);
        this.setFont(UICommon.ALGO_FONT);
        this.setForeground(Color.white);

        Hashtable labelTable = new Hashtable();
        JLabel slow = new JLabel("Fast");
        slow.setFont(UICommon.ALGO_FONT);
        slow.setForeground(Color.white);
        JLabel fast = new JLabel("Slow");
        fast.setFont(UICommon.ALGO_FONT);
        fast.setForeground(Color.white);
        labelTable.put(this.MIN_DELAY, slow);
        labelTable.put(this.MAX_DELAY,fast);
        this.setLabelTable(labelTable );
        this.setPaintLabels(true);
    }

    @Override
    public Dimension getPreferredSize(){
        return new Dimension(UICommon.COMPONENT_WIDTH, UICommon.COMPONENT_HEIGHT);
    }

    @Override
    public Dimension getMaximumSize(){
        return new Dimension(UICommon.COMPONENT_WIDTH, UICommon.COMPONENT_HEIGHT);
    }

    @Override
    public Dimension getMinimumSize(){
        return new Dimension(UICommon.COMPONENT_WIDTH, UICommon.COMPONENT_HEIGHT);
    }
}
