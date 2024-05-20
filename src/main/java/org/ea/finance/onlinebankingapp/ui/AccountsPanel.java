package org.ea.finance.onlinebankingapp.ui;


import org.ea.finance.onlinebankingapp.model.authentication.CurrentUser;
import org.ea.finance.onlinebankingapp.model.account.Account;
import org.ea.finance.onlinebankingapp.model.transactions.Transaction;
import org.ea.finance.onlinebankingapp.service.AccountService;
import org.ea.finance.onlinebankingapp.service.TransactionService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.List;

public class AccountsPanel extends FeaturePanel{

    private final AccountService accountService = new AccountService();

    private DefaultTableModel accountsTableModel;
    private DefaultTableModel transactionsTableModel;
    private JTable accountsTable;
    private JTable transactionsTable;

    private JButton addAccountButton;
    private JButton deleteAccountButton;
    private String selectedAccountNumber;

    private double selectedAccountBalance;

    public AccountsPanel() {
        initComponents();
        refreshAccountsTable();
    }

    @Override
    protected void initComponents() {
        setLayout(new BorderLayout());

        // Initialize table model for accounts
        String[] accountColumnNames = {"Account Number", "Type", "Balance"};
        accountsTableModel = new DefaultTableModel(accountColumnNames, 0);
        accountsTable = new JTable(accountsTableModel);

        // Initialize table model for transactions
        String[] transactionColumnNames = {"ID", "Amount", "Type", "Creation Date"};
        transactionsTableModel = new DefaultTableModel(transactionColumnNames, 0);
        transactionsTable = new JTable(transactionsTableModel);

        // Add accounts and transactions tables to scroll panes
        JScrollPane accountsScrollPane = new JScrollPane(accountsTable);
        JScrollPane transactionsScrollPane = new JScrollPane(transactionsTable);

        // Create a split pane to display tables side by side
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, accountsScrollPane, transactionsScrollPane);
        splitPane.setResizeWeight(0.5);

        add(splitPane, BorderLayout.CENTER);

        // Initialize buttons
        addAccountButton = new JButton("Add Account");
        deleteAccountButton = new JButton("Delete Account");

        // Add button listeners
        addAccountButton.addActionListener(this::addAccountButtonClicked);
        deleteAccountButton.addActionListener(this::deleteAccountButtonClicked);

        // Add buttons to a panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(addAccountButton);
        buttonPanel.add(deleteAccountButton);

        add(buttonPanel, BorderLayout.SOUTH);

        accountsTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = accountsTable.getSelectedRow();
                if (selectedRow != -1) {
                    // Get the account associated with the selected row
                    //selectedAccountNumber = getAccountIdFromSelectedRow(selectedRow);
                    selectedAccountNumber = getAccountNumberFromSelectedRow(selectedRow);
                    selectedAccountBalance= Double.parseDouble(getAccountBalanceFromSelectedRow(selectedRow));
                    displayTransactionsForAccount();
                }
            }
        });
    }

    private String getAccountNumberFromSelectedRow(int selectedRow) {
        int accountNumberIndex = 0; // Adjust this index based on your table structure
        int accountTypeIndex = 1; // Adjust this index based on your table structure
        int balanceIndex = 2; // Adjust this index based on your table structure

        return accountsTableModel.getValueAt(selectedRow, accountNumberIndex).toString();
    }

    private String getAccountBalanceFromSelectedRow(int selectedRow) {
        int accountNumberIndex = 0; // Adjust this index based on your table structure
        int accountTypeIndex = 1; // Adjust this index based on your table structure
        int balanceIndex = 2; // Adjust this index based on your table structure

        return accountsTableModel.getValueAt(selectedRow, balanceIndex).toString();
    }

    private void refreshAccountsTable() {
        // Clear existing data from accounts table
        accountsTableModel.setRowCount(0);

        // Fetch accounts belonging to the current user
        List<Account> userAccounts = accountService.getAccountByUserid(CurrentUser.getInstance().getUser().getUserid());

        // Populate accounts table with updated data
        for (Account account : userAccounts) {
            Object[] rowData = {account.getAccountNumber(), account.getAccountType(), account.getBalance()};
            accountsTableModel.addRow(rowData);
        }
    }

    private void displayTransactionsForAccount() {
        // Clear existing data from transactions table
        transactionsTableModel.setRowCount(0);
        int selectedAccountId=accountService.getAccountByUseridAndAccountNumber(CurrentUser.getInstance().getUser().getUserid(),selectedAccountNumber);

        // Fetch transactions for the selected account from the service
        List<Transaction> transactions;
        try {
            transactions = new TransactionService().getTransactionsByAccountId(selectedAccountId);

            // Populate transactions table with data
            for (Transaction transaction : transactions) {

                Object[] rowData = {transaction.getId(), transaction.getAmount(), transaction.getType(),  transaction.getCreatedAt()};
                transactionsTableModel.addRow(rowData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addAccountButtonClicked(ActionEvent e) {

        notifyObservers("AddAccount");
    }

    private void deleteAccountButtonClicked(ActionEvent e) {
        int option = showConfirmDialog("Do you want to continue?", "Confirmation", JOptionPane.YES_NO_OPTION);

        if (option == JOptionPane.YES_OPTION) {
            if (selectedAccountBalance>0)
            {
                showMessageDialog("You cannot delete an Account with balance>0 .", "Error", JOptionPane.ERROR_MESSAGE);
            }
            else
            {
                int selectedAccountId=accountService.getAccountByUseridAndAccountNumber(CurrentUser.getInstance().getUser().getUserid(),selectedAccountNumber);
                accountService.delete(selectedAccountId);
                showMessageDialog("Account deleted successfully.", "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

}
