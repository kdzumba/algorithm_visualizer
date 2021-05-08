package com.kdzumba.algo.views;

import javax.swing.*;
import java.awt.*;

public class AlgoButton extends JButton {
    private final int width = 200;
    private final int height = 50;

    AlgoButton(String text){
        super(text);
        this.setBackground(new Color(40, 40, 40));
        this.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 16));
        this.setForeground(Color.white);
        this.setFocusable(false);
    }

    @Override
    public Dimension getPreferredSize(){
        return new Dimension(this.width, this.height);
    }

    @Override
    public Dimension getMaximumSize(){
        return new Dimension(this.width, this.height);
    }

    @Override
    public Dimension getMinimumSize(){
        return new Dimension(this.width, this.height);
    }
}
