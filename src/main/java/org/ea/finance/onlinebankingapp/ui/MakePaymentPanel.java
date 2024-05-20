package org.ea.finance.onlinebankingapp.ui;

import org.ea.finance.onlinebankingapp.factory.PaymentFactory;
import org.ea.finance.onlinebankingapp.model.account.Account;
import org.ea.finance.onlinebankingapp.model.authentication.CurrentUser;
import org.ea.finance.onlinebankingapp.model.payment.Payment;
import org.ea.finance.onlinebankingapp.model.payment.PaymentType;
import org.ea.finance.onlinebankingapp.service.AccountService;
import org.ea.finance.onlinebankingapp.service.PaymentService;
import org.ea.finance.onlinebankingapp.util.datavalidator.util.Validator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;

public class MakePaymentPanel extends FeaturePanel {

    private JComboBox<Account> accountComboBox;
    private JComboBox<PaymentType> paymentTypeComboBox;
    private JTextField targetAccountField;
    private JTextField amountField;
    private JButton saveButton;

    private final PaymentService paymentService = new PaymentService();
    private final AccountService accountService = new AccountService();

    public MakePaymentPanel() {
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

        // Account Selection
        formPanel.add(new JLabel("Source Account:"), gbc);
        gbc.gridx = 1;
        accountComboBox = new JComboBox<>();
        populateAccountComboBox();
        accountComboBox.setRenderer(new AccountComboBoxRenderer()); // Set custom renderer
        formPanel.add(accountComboBox, gbc);

        // Payment Type Selection
        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(new JLabel("Payment Type:"), gbc);
        gbc.gridx = 1;
        paymentTypeComboBox = new JComboBox<>(PaymentType.values());
        formPanel.add(paymentTypeComboBox, gbc);

        // Target Account Input (Text Field)
        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(new JLabel("Target Account ID:"), gbc);
        gbc.gridx = 1;
        targetAccountField = new JTextField(20);
        formPanel.add(targetAccountField, gbc);

        // Amount Input
        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(new JLabel("Amount:"), gbc);
        gbc.gridx = 1;
        amountField = new JTextField(20);
        formPanel.add(amountField, gbc);

        add(formPanel, BorderLayout.CENTER);

        // Save Button
        saveButton = new JButton("Save Payment");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                savePayment();
            }
        });
        add(saveButton, BorderLayout.SOUTH);
    }

    private void populateAccountComboBox() {
        List<Account> accounts = accountService.getAccountByUserid(CurrentUser.getInstance().getUser().getUserid());
        for (Account account : accounts) {
            accountComboBox.addItem(account);
        }
    }

    private void savePayment() {
        // Retrieve payment details from the form fields
        Account sourceAccount = (Account) accountComboBox.getSelectedItem();
        PaymentType paymentType = (PaymentType) paymentTypeComboBox.getSelectedItem();
        int targetAccountIdNum = accountService.getAccountByUseridAndAccountNumber(CurrentUser.getInstance().getUser().getUserid(),targetAccountField.getText());
        double amount = Validator.isValidDouble(amountField.getText()) ? Double.parseDouble(amountField.getText()) : 0;

        try {
            Payment payment = PaymentFactory.createPayment(-1, amount, paymentType, sourceAccount.getId(), targetAccountIdNum, new Date(), false);
            paymentService.add(payment);
            showMessageDialog("Payment saved successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            // Notify observers
            notifyObservers("MakePayment");
        } catch (IllegalArgumentException ex) {
            showMessageDialog(ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Custom renderer to display account number and type in combo box
    private class AccountComboBoxRenderer extends DefaultListCellRenderer {
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if (value instanceof Account) {
                Account account = (Account) value;
                setText(account.getAccountNumber() + " - " + account.getAccountType());
            }
            return this;
        }
    }
}
