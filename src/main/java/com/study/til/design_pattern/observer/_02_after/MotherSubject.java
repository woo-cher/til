package com.study.til.design_pattern.observer._02_after;

import java.util.ArrayList;
import java.util.List;

public class MotherSubject implements Subject {
    List<Observer> observers;
    private boolean isAngry;

    MotherSubject() {
        this.observers = new ArrayList<>();
    }

    MotherSubject(boolean isAngry) {
        this.isAngry = isAngry;
    }

    public void setAngry(boolean angry) {
        isAngry = angry;
    }

    @Override
    public void registerObserver(Observer o) {
        this.observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        if (this.observers.size() != 0) {
            this.observers.remove(o);
        }
    }

    @Override
    public void notifyObservers() {
        for (Observer listener : observers) {
            listener.update(isAngry);
        }
    }
}
