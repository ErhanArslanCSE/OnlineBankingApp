package org.ea.finance.onlinebankingapp.ui;

import org.ea.finance.onlinebankingapp.ui.observer.MainApplicationObserver;
import org.ea.finance.onlinebankingapp.ui.observer.MainApplicationSubject;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public abstract class FeaturePanel extends JPanel implements MainApplicationSubject {

    private List<MainApplicationObserver> observers = new ArrayList<>();

    @Override
    public void addObserver(MainApplicationObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(MainApplicationObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(String selectedOption) {
        for (MainApplicationObserver observer : observers) {
            observer.update(selectedOption);
        }
    }

    void showMessageDialog(String message, String title, int messageType) {
        JOptionPane.showMessageDialog(null, message, title, messageType);
    }

    int showConfirmDialog(String message, String title, int optionType) {
        return JOptionPane.showConfirmDialog(null, message, title, optionType);
    }

    protected abstract void initComponents();
}
