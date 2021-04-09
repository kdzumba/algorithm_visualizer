package com.kdzumba.algo.views;

import javax.swing.*;
import java.awt.*;

public class AlgoButton extends JButton {
    private final String text;
    private final int width = 200;
    private final int height = 40;

    AlgoButton(String text){
        super(text);
        this.text = text;
        this.setBounds(0, 0, this.width, this.height);
        this.setBackground(new Color(40, 40, 40));
        this.setFont(new Font("Arial", Font.PLAIN, 18));
        this.setForeground(Color.white);
        this.setFocusable(false);
        this.setPreferredSize(new Dimension(this.width, this.height));
    }
}
