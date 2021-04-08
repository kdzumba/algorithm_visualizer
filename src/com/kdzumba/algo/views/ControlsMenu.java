package com.kdzumba.algo.views;

import javax.swing.*;
import java.awt.*;

public class ControlsMenu extends JPanel {
    ControlsMenu(){
        this.setLayout(new GridLayout(2, 1));
        this.add(new AlgoButton("Visualize"));
        this.add(new AlgoButton("TestBtn"));
    }
}
