package com.kdzumba.algo.views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;

public class AlgoLabel extends JButton {
    public AlgoLabel(String text){
        super(text);
        this.setFont(UICommon.ALGO_FONT);
        this.setBackground(UICommon.COMPONENT_BACKGROUND);
        this.setForeground(Color.WHITE);
        this.setFocusable(false);

        //TODO: Figure out if there is no better way of doing this, feels off
        for(MouseListener listener : this.getMouseListeners()){
            this.removeMouseListener(listener);
        }
    }
}
