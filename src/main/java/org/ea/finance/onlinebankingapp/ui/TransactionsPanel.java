package org.ea.finance.onlinebankingapp.ui;

import org.ea.finance.onlinebankingapp.model.account.Account;
import org.ea.finance.onlinebankingapp.model.authentication.CurrentUser;
import org.ea.finance.onlinebankingapp.model.transactions.Transaction;
import org.ea.finance.onlinebankingapp.service.AccountService;
import org.ea.finance.onlinebankingapp.service.TransactionService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TransactionsPanel extends FeaturePanel {

    private final TransactionService transactionService = new TransactionService();
    private final JPanel incomePanel;
    private final JPanel expensePanel;
    private final JPanel transferPanel;

    public TransactionsPanel() {
        // Calculate the height for each table panel (one-fourth of the main panel's height)
        int panelHeight = getPreferredSize().height / 4;

        // Create panels for each transaction type
        incomePanel = createTransactionPanel("Income Transactions", panelHeight);
        expensePanel = createTransactionPanel("Expense Transactions", panelHeight);
        transferPanel = createTransactionPanel("Transfer Transactions", panelHeight);

        // Add panels to the main panel
        add(incomePanel);
        add(expensePanel);
        add(transferPanel);

        // Set layout for the main panel
        setLayout(new GridLayout(3, 1));

        initComponents();
    }

    @Override
    protected void initComponents() {

        List<Transaction> transactions=new ArrayList<>();
        try {
            for(Account userAcc:new AccountService().getAccountByUserid(CurrentUser.getInstance().getUser().getUserid()))
            {
                transactions.addAll(transactionService.getTransactionsByAccountId(userAcc.getId()));
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        for (Transaction transaction : transactions) {
            switch (transaction.getType()) {
                case INCOME:
                    addTransactionToPanel(incomePanel, transaction);
                    break;
                case EXPENSE:
                    addTransactionToPanel(expensePanel, transaction);
                    break;
                case TRANSFER:
                    addTransactionToPanel(transferPanel, transaction);
                    break;
                default:
                    break;
            }
        }
    }

    // Helper method to create a panel for a transaction type
    private JPanel createTransactionPanel(String title, int height) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(title));
        panel.setPreferredSize(new Dimension(getPreferredSize().width, height));
        JTable table = new JTable(new DefaultTableModel(
                new Object[][]{},
                new String[]{"ID", "Amount", "Type", "Source Account", "Destination Account", "Creation Date"}
        ));
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    // Helper method to add a transaction to a panel
    private void addTransactionToPanel(JPanel panel, Transaction transaction) {
        JTable table = (JTable) ((JViewport) ((JScrollPane) panel.getComponent(0)).getComponent(0)).getView();
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        Object[] rowData;
        rowData = new Object[]{
                transaction.getId(),
                transaction.getAmount(),
                transaction.getType(),
                transaction.getSourceAccount() != null ? transaction.getSourceAccount().getAccountNumber() : null,
                transaction.getDestinationAccount() != null ? transaction.getDestinationAccount().getAccountNumber() : null,
                transaction.getCreatedAt()
        };

        model.addRow(rowData);
    }
}
