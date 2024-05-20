package org.ea.finance.onlinebankingapp.ui;

import org.ea.finance.onlinebankingapp.factory.GUIFactory;
import org.ea.finance.onlinebankingapp.model.authentication.CurrentUser;
import org.ea.finance.onlinebankingapp.ui.observer.MainApplicationObserver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainApplicationFrame extends JFrame implements MainApplicationObserver {

    private JPanel leftPanel;
    private JPanel centralPanel;

    public MainApplicationFrame(String title) {
        super(title);
        if (!GraphicsEnvironment.isHeadless()) {
            setSize(800, 600);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            createGUI();
        } else {
            System.err.println("Headless environment detected. Exiting...");
            System.exit(1);
        }
    }

    void createGUI() {
        // Create the left panel with a sidebar menu
        leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS)); // Vertical layout
        leftPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding

        // Create the central panel
        centralPanel = new JPanel();
        centralPanel.setLayout(new BorderLayout());

        // Add components to the frame
        getContentPane().add(leftPanel, BorderLayout.WEST);
        getContentPane().add(centralPanel, BorderLayout.CENTER);

        // Display the login or home screen based on the user's authentication status
        if (CurrentUser.getInstance().getUser() == null) {
            displayNewScreen(GUIOptions.Login);
        } else {
            displayNewScreen(GUIOptions.Home);
        }
    }

    private void displayNewScreen(GUIOptions screenSelection) {
        centralPanel.removeAll();
        FeaturePanel currentScreen = GUIFactory.createPanel(screenSelection.toString());
        currentScreen.addObserver(this);
        currentScreen.setBackground(Color.WHITE);
        centralPanel.add(currentScreen, BorderLayout.CENTER);
        centralPanel.revalidate();
        centralPanel.repaint();
    }

    private void updateLeftPanel() {
        leftPanel.removeAll();

        // Create buttons for each menu item based on the user's authentication status
        if (CurrentUser.getInstance().getUser() != null) {
            for (String menuItem : GUIOptions.getMenuItems()) {
                // Exclude the "Login" button
                if (!menuItem.equalsIgnoreCase(String.valueOf(GUIOptions.Login))) {
                    if(menuItem.equalsIgnoreCase(String.valueOf(GUIOptions.AddCustomer)))
                    {
                        if (CurrentUser.getInstance().getUser().getIsAdminUser().equalsIgnoreCase("Y")) {
                            JButton menuItemButton = new JButton(menuItem);
                            menuItemButton.setAlignmentX(Component.LEFT_ALIGNMENT); // Align buttons to the left
                            menuItemButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, menuItemButton.getPreferredSize().height)); // Set maximum width
                            menuItemButton.addActionListener(e -> update(menuItem));
                            leftPanel.add(Box.createRigidArea(new Dimension(0, 5))); // Add spacing between buttons
                            leftPanel.add(menuItemButton);
                        }
                    }
                    else
                    {
                        JButton menuItemButton = new JButton(menuItem);
                        menuItemButton.setAlignmentX(Component.LEFT_ALIGNMENT); // Align buttons to the left
                        menuItemButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, menuItemButton.getPreferredSize().height)); // Set maximum width
                        menuItemButton.addActionListener(e -> update(menuItem));
                        leftPanel.add(Box.createRigidArea(new Dimension(0, 5))); // Add spacing between buttons
                        leftPanel.add(menuItemButton);
                    }


                }
            }
        }

        leftPanel.revalidate();
        leftPanel.repaint();
    }


    @Override
    public void update(String selectedOption) {
        if(selectedOption.equalsIgnoreCase(String.valueOf(GUIOptions.Logout)))
        {
            CurrentUser.getInstance().setUser(null);
        }
        updateLeftPanel();
        displayNewScreen(GUIOptions.valueOf(selectedOption));

    }
}
