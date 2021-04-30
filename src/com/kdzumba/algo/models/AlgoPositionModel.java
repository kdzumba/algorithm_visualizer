package com.kdzumba.algo.models;

public class AlgoPositionModel {
    private final int x;
    private final int y;

    public AlgoPositionModel(int xPos, int yPos){
        this.x = xPos;
        this.y = yPos;
    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

    @Override
    public boolean equals(Object o){
        //Two positions are equal if they have the same x and y values
        if(o == null){
            return false;
        }

        if(o.getClass() != this.getClass()){
            return false;
        }

        AlgoPositionModel p = (AlgoPositionModel) o;
        return p.x == this.x && p.y == this.y;
    }
}
