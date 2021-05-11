package com.kdzumba.algo.views;

import javax.swing.*;
import java.awt.*;

public class AlgoButton extends JButton {
    AlgoButton(String text){
        super(text);
        this.setBackground(UICommon.COMPONENT_BACKGROUND);
        this.setFont(UICommon.ALGO_FONT);
        this.setForeground(Color.white);
        this.setFocusable(false);
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
