package com.kdzumba.algo.views;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class SidePanel extends JPanel {
    public SidePanel(List<JComponent> components){
        super();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(Color.DARK_GRAY);
        this.setBorder(BorderFactory.createEmptyBorder(UICommon.H_SEPARATOR, UICommon.H_SEPARATOR, UICommon.H_SEPARATOR, UICommon.H_SEPARATOR));

        for(JComponent component : components){
            this.add(component);
            this.add(UICommon.createSeparator(Component.LEFT_ALIGNMENT));
        }
    }
}
