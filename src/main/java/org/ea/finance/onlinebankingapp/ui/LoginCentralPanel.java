package org.ea.finance.onlinebankingapp.ui;

import org.ea.finance.onlinebankingapp.model.authentication.CurrentUser;
import org.ea.finance.onlinebankingapp.model.authentication.User;
import org.ea.finance.onlinebankingapp.service.AccountService;
import org.ea.finance.onlinebankingapp.service.UserService;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class LoginCentralPanel extends FeaturePanel{
    public LoginCentralPanel() {
        initComponents();
    }

    @Override
    protected void initComponents() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Username label and text field
        JLabel usernameLabel = new JLabel("Username:");
        formPanel.add(usernameLabel, gbc);

        gbc.gridx++;
        JTextField usernameField = new JTextField(15); // Adjust size as needed
        formPanel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        // Password label and text field
        JLabel passwordLabel = new JLabel("Password:");
        formPanel.add(passwordLabel, gbc);

        gbc.gridx++;
        JPasswordField passwordField = new JPasswordField(15); // Adjust size as needed
        formPanel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        // Login button
        JButton loginButton = new JButton("Login");
        formPanel.add(loginButton, gbc);

        loginButton.addActionListener(e -> {
            // Retrieve username and password
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            // Call the login method with the provided credentials
            login(username, password);
        });

        add(formPanel, BorderLayout.CENTER);
    }
    private void login(String username, String password) {
        // Assuming you have a UserServiceImpl instance to interact with user data
        UserService userService = new UserService();

        // Call the authenticateUser method to check if the username and password are valid
        boolean isAuthenticated = false;
        try {
            isAuthenticated = userService.authenticateUser(username, password);
            if (isAuthenticated) {
                // Authentication successful, proceed to the main application

                try {
                    User usr=userService.getUserByUsername(username);


                    CurrentUser currentUser = CurrentUser.getInstance();
                    currentUser.setUser(usr);
                    notifyObservers("Home");
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password", "Login Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Invalid username or password", "Login Error", JOptionPane.ERROR_MESSAGE);
        }


    }

}
