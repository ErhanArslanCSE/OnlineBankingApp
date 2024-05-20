package org.ea.finance.onlinebankingapp.ui.observer;

public interface MainApplicationSubject {
    void addObserver(MainApplicationObserver observer);
    void removeObserver(MainApplicationObserver observer);
    void notifyObservers(String selectedOption);
}