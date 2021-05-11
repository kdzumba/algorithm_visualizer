package com.kdzumba.algo.views;

import javax.swing.*;
import java.awt.*;

public class UICommon {
    public static final Color COMPONENT_BACKGROUND = new Color(6, 133, 145);
    public static final int COMPONENT_HEIGHT = 50;
    public static final int COMPONENT_WIDTH = 300;
    public static final int H_SEPARATOR = 5;
    public static final Font ALGO_FONT = new Font(Font.MONOSPACED, Font.BOLD, 16);

    private static final Dimension MIN_SIZE = new Dimension(COMPONENT_WIDTH, H_SEPARATOR);
    private static final Dimension PREF_SIZE = new Dimension(COMPONENT_WIDTH, H_SEPARATOR);
    private static final Dimension MAX_SIZE = new Dimension(COMPONENT_WIDTH, H_SEPARATOR);

    public static JComponent createSeparator(float alignment){
        JComponent separator = new Box.Filler(MIN_SIZE, PREF_SIZE, MAX_SIZE);
        separator.setAlignmentX(alignment);
        return separator;
    }
}
