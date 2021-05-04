package com.kdzumba.algo;

import com.kdzumba.algo.views.AlgoWindow;

import javax.swing.*;

public class AlgoVisualizer{

    public static void main(String[] args){
        SwingUtilities.invokeLater(() -> new AlgoWindow(30, 30));
    }
}
