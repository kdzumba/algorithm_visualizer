package com.kdzumba.algo;

public class AlgoVisualizer implements  Runnable {
    private final Gui gui = new Gui(30 ,30);

    public static void main(String[] args){
        new Thread(new AlgoVisualizer()).start();
    }

    @Override
    public void run(){
        gui.repaint();
    }
}
