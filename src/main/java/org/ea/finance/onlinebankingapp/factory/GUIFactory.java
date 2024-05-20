package org.ea.finance.onlinebankingapp.factory;

import org.ea.finance.onlinebankingapp.ui.*;

public class GUIFactory {

    public static FeaturePanel createPanel(String screenSelection) {
        try {
            return (FeaturePanel) GUIOptions.valueOf(screenSelection).getPanelClass().newInstance();
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid menu type: " + screenSelection+", Error detail:"+e.getMessage());
        }
    }
}
