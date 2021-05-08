package com.kdzumba.algo.models;

public class AlgoEdgeModel {
    AlgoNodeModel source;
    AlgoNodeModel destination;
    double weight;

    AlgoEdgeModel(AlgoNodeModel fromNode, AlgoNodeModel toNode, double weight) {
        this.source = fromNode;
        this.destination = toNode;
        this.weight = weight;
    }

    public double getWeight() {
        return this.weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public AlgoNodeModel getSource(){
        return this.source;
    }

    public AlgoNodeModel getDestination(){
        return this.destination;
    }
}
