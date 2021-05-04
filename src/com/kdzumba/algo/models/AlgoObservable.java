package com.kdzumba.algo.models;

import com.kdzumba.algo.interfaces.AlgoObserver;

import java.util.ArrayList;
import java.util.List;

public class AlgoObservable {
    private final List<AlgoObserver> observers = new ArrayList<>();

    public AlgoObserver addObserver(AlgoObserver observer){
        this.observers.add(observer);
        return observer;
    }

    public AlgoObserver removeObserver(AlgoObserver observer){
        this.observers.remove(observer);
        return observer;
    }

    public void updateObservers(){
        for(AlgoObserver observer : this.observers){
            observer.update();
        }
    }

    public List<AlgoObserver> getObservers(){
        return this.observers;
    }
}
