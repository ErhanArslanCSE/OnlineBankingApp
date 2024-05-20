package org.ea.finance.onlinebankingapp.ui;

import org.ea.finance.onlinebankingapp.factory.AccountFactory;
import org.ea.finance.onlinebankingapp.model.account.AccountType;
import org.ea.finance.onlinebankingapp.model.authentication.CurrentUser;
import org.ea.finance.onlinebankingapp.service.AccountService;

import javax.swing.*;
import java.awt.*;

public class AddAccountPanel extends FeaturePanel {

    private JTextField accountOwnerField;
    private JTextField accountEmailField;
    private JTextField accountNumberField;
    private JComboBox<AccountType> accountTypeComboBox;
    private JTextField balanceField;
    private JButton saveButton;

    private final AccountService bankService = new AccountService();

    public AddAccountPanel() {
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

        formPanel.add(new JLabel("Account Owner:"), gbc);
        accountOwnerField = new JTextField(20);
        accountOwnerField.setEditable(false);
        accountOwnerField.setText(CurrentUser.getInstance().getUser().getFirstName() + " " + CurrentUser.getInstance().getUser().getLastName());
        gbc.gridx = 1;
        formPanel.add(accountOwnerField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(new JLabel("Owner Email:"), gbc);
        accountEmailField = new JTextField(20);
        accountEmailField.setEditable(false);
        accountEmailField.setText(CurrentUser.getInstance().getUser().getEmail());
        gbc.gridx = 1;
        formPanel.add(accountEmailField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(new JLabel("New Account Number:"), gbc);
        accountNumberField = new JTextField(20);
        accountNumberField.setEditable(false);
        accountNumberField.setText(AccountService.generateUniqueAccountValue());
        gbc.gridx = 1;
        formPanel.add(accountNumberField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(new JLabel("Account Type:"), gbc);
        accountTypeComboBox = new JComboBox<>(AccountType.values());
        gbc.gridx = 1;
        formPanel.add(accountTypeComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(new JLabel("Balance:"), gbc);
        balanceField = new JTextField(20);
        balanceField.setEditable(false);
        balanceField.setText("0.0");
        gbc.gridx = 1;
        formPanel.add(balanceField, gbc);

        add(formPanel, BorderLayout.CENTER);

        saveButton = new JButton("Add Account");
        saveButton.addActionListener(e -> addAccount());
        add(saveButton, BorderLayout.SOUTH);
    }

    private void addAccount() {
        // Retrieve data from fields and perform the add account action
        String accountNumber = accountNumberField.getText();
        AccountType accountType = (AccountType) accountTypeComboBox.getSelectedItem();
        double balance = Double.parseDouble(balanceField.getText());

        // Get the user ID from CurrentUser
        int userId = CurrentUser.getInstance().getUser().getUserid();
        // Call service method to add the account
        try {
            bankService.createAccount(AccountFactory.createAccount(-1, userId, accountNumber, accountType, balance, false));
            showMessageDialog("Account created successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (IllegalArgumentException ex) {
            showMessageDialog(ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        // Notify observers that an account has been added
        notifyObservers("AddAccount");
    }
}
