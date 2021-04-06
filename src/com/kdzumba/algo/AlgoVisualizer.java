package com.kdzumba.algo;

import com.kdzumba.algo.views.AlgoApplication;

public class AlgoVisualizer implements  Runnable {
    //x and y dimensions specify the number of nodes per row and col respectively
    private final AlgoApplication app = new AlgoApplication(30 ,30);

    public static void main(String[] args){
        new Thread(new AlgoVisualizer()).start();
    }

    @Override
    public void run(){
        app.repaint();
    }
}
