package org.ea.finance.onlinebankingapp;

import org.ea.finance.onlinebankingapp.ui.MainApplicationFrame;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.swing.*;
import java.awt.*;

@SpringBootApplication
public class OnlineBankingApplication {

    public static void main(String[] args) {
        if (GraphicsEnvironment.isHeadless()) {
            // Handle headless environment
            System.err.println("Application is running in headless mode. Exiting...");
            System.exit(1);
        } else {
            // Run Swing GUI
            SpringApplication.run(OnlineBankingApplication.class, args);
            //System.setProperty("java.awt.headless", "false");

            // Now you can initialize your Swing GUI components
            JFrame frame = new MainApplicationFrame("Online Banking App");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

        }
    }

}
