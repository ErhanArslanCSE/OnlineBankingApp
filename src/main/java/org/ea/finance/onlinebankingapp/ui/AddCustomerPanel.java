package org.ea.finance.onlinebankingapp.ui;

import org.ea.finance.onlinebankingapp.factory.AccountFactory;
import org.ea.finance.onlinebankingapp.factory.UserFactory;
import org.ea.finance.onlinebankingapp.model.account.AccountType;
import org.ea.finance.onlinebankingapp.model.authentication.User;
import org.ea.finance.onlinebankingapp.model.authentication.UserType;
import org.ea.finance.onlinebankingapp.service.AccountService;
import org.ea.finance.onlinebankingapp.service.UserService;
import org.ea.finance.onlinebankingapp.util.datavalidator.UserRegistrationValidator;
import org.ea.finance.onlinebankingapp.util.datavalidator.util.ValidationResult;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class AddCustomerPanel extends FeaturePanel {

    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField emailField;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox<UserType> userTypeComboBox;
    private JButton saveButton;

    private JTextField accountOwnerField;
    private JTextField accountEmailField;
    private JTextField accountNumberField;
    private JComboBox<AccountType> accountTypeComboBox;
    private JTextField balanceField;
    private JButton addAccountButton;

    private User createdUser;

    private final UserService userService = new UserService();


    public AddCustomerPanel() {
        initComponents();
    }

    @Override
    protected void initComponents() {
        setLayout(new BorderLayout());

        // Create panel for user creation
        JPanel userPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel firstNameLabel = new JLabel("First Name:");
        JLabel lastNameLabel = new JLabel("Last Name:");
        JLabel emailLabel = new JLabel("Email:");
        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");
        JLabel userTypeLabel = new JLabel("User Type:");

        firstNameField = new JTextField(20);
        lastNameField = new JTextField(20);
        emailField = new JTextField(20);
        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        UserType[] userTypes = {UserType.NORMAL, UserType.PREMIUM, UserType.BUSINESS};
        userTypeComboBox = new JComboBox<>(userTypes);

        Dimension fieldSize = new Dimension(200, 25);
        firstNameField.setPreferredSize(fieldSize);
        lastNameField.setPreferredSize(fieldSize);
        emailField.setPreferredSize(fieldSize);
        usernameField.setPreferredSize(fieldSize);
        passwordField.setPreferredSize(fieldSize);
        userTypeComboBox.setPreferredSize(fieldSize);

        userPanel.add(firstNameLabel, gbc);
        gbc.gridy++;
        userPanel.add(lastNameLabel, gbc);
        gbc.gridy++;
        userPanel.add(emailLabel, gbc);
        gbc.gridy++;
        userPanel.add(usernameLabel, gbc);
        gbc.gridy++;
        userPanel.add(passwordLabel, gbc);
        gbc.gridy++;
        userPanel.add(userTypeLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        userPanel.add(firstNameField, gbc);
        gbc.gridy++;
        userPanel.add(lastNameField, gbc);
        gbc.gridy++;
        userPanel.add(emailField, gbc);
        gbc.gridy++;
        userPanel.add(usernameField, gbc);
        gbc.gridy++;
        userPanel.add(passwordField, gbc);
        gbc.gridy++;
        userPanel.add(userTypeComboBox, gbc);

        saveButton = new JButton("Save Customer");
        userPanel.add(saveButton);
        saveButton.addActionListener(e -> {
            createUser();
        });

        // Create panel for account addition
        JPanel accountPanel = new JPanel(new GridBagLayout());
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;

        JLabel accountNumberLabel = new JLabel("New Account Number:");
        JLabel accountTypeLabel = new JLabel("Account Type:");
        JLabel balanceLabel = new JLabel("Balance:");

        accountNumberField = new JTextField(20);
        accountNumberField.setEditable(false);
        accountNumberField.setText(AccountService.generateUniqueAccountValue());
        accountTypeComboBox = new JComboBox<>(AccountType.values());
        balanceField = new JTextField(20);

        accountNumberField.setPreferredSize(fieldSize);
        accountTypeComboBox.setPreferredSize(fieldSize);
        balanceField.setPreferredSize(fieldSize);

        accountPanel.add(accountNumberLabel, gbc);
        gbc.gridy++;
        accountPanel.add(accountTypeLabel, gbc);
        gbc.gridy++;
        accountPanel.add(balanceLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        accountPanel.add(accountNumberField, gbc);
        gbc.gridy++;
        accountPanel.add(accountTypeComboBox, gbc);
        gbc.gridy++;
        accountPanel.add(balanceField, gbc);

        addAccountButton = new JButton("Add Account");
        accountPanel.add(addAccountButton);
        addAccountButton.addActionListener(e -> addAccount());

        // Create split pane to separate user creation and account addition
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, userPanel, accountPanel);
        splitPane.setResizeWeight(0.5);
        splitPane.setDividerLocation(0.5);

        add(splitPane, BorderLayout.CENTER);
    }

    private void createUser() {
        // Get user data from input fields
        String firstName = getUserFirstName();
        String lastName = getUserLastName();
        String email = getUserEmail();
        String username = getUserUsername();
        String password = getUserPassword();
        UserType userType = getUserType();


        // Check if any field is empty
        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Create a new user instance using the factory method
        User user = UserFactory.createUser(username, firstName, lastName, email, password, userType, "N");
        UserRegistrationValidator newValidator=new UserRegistrationValidator();
        ValidationResult rs=newValidator.validateObject(user,false);

        if(rs.isValid())
        {
            try {
                userService.add(user);
                JOptionPane.showMessageDialog(this, "User created successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                //clearUserFields();

                createdUser=userService.getUserByUsername(user.getUsername());
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error creating user: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }

        }
        else
        {
            JOptionPane.showMessageDialog(this, rs.getErrorMessage());
        }
    }
    private void addAccount() {
        // Retrieve data from fields and perform the add account action
        String accountNumber = accountNumberField.getText();
        AccountType accountType = (AccountType) accountTypeComboBox.getSelectedItem();
        double balance = Double.parseDouble(balanceField.getText());

        // Get the user ID from CurrentUser
        int userId = createdUser.getUserid();
        // Call service method to add the account
        try {
            new AccountService().createAccount(AccountFactory.createAccount(-1, userId, accountNumber, accountType, balance, false));
            showMessageDialog("Account created successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (IllegalArgumentException ex) {
            showMessageDialog(ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        // Notify observers that an account has been added
        notifyObservers("AddAccount");
    }

    public String getUserFirstName() {
        return firstNameField.getText();
    }

    public String getUserLastName() {
        return lastNameField.getText();
    }

    public String getUserEmail() {
        return emailField.getText();
    }

    public String getUserUsername() {
        return usernameField.getText();
    }

    public String getUserPassword() {
        return new String(passwordField.getPassword());
    }

    public UserType getUserType() {
        return (UserType) userTypeComboBox.getSelectedItem();
    }

    public void clearUserFields() {
        firstNameField.setText("");
        lastNameField.setText("");
        emailField.setText("");
        usernameField.setText("");
        passwordField.setText("");
        userTypeComboBox.setSelectedIndex(0);
    }

    public String getAccountNumber() {
        return accountNumberField.getText();
    }

    public AccountType getAccountType() {
        return (AccountType) accountTypeComboBox.getSelectedItem();
    }

    public double getAccountBalance() {
        return Double.parseDouble(balanceField.getText());
    }

    public void clearAccountFields() {
        accountNumberField.setText(AccountService.generateUniqueAccountValue());
        accountTypeComboBox.setSelectedIndex(0);
        balanceField.setText("");
    }
}
