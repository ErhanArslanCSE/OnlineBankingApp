package org.ea.finance.onlinebankingapp.ui;

import org.ea.finance.onlinebankingapp.model.account.Account;
import org.ea.finance.onlinebankingapp.model.authentication.CurrentUser;
import org.ea.finance.onlinebankingapp.service.AccountService;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class HomeCentralPanel extends FeaturePanel {
    private final AccountService bankService = new AccountService();
    private JLabel welcomeLabel;
    private JLabel dateLabel;
    private JLabel totalAssetsLabel;
    private JPanel chartPanel;

    public HomeCentralPanel() {
        initComponents();
    }

    @Override
    protected void initComponents() {
        setLayout(new GridLayout(4, 1, 0, 10)); // 4 rows, 1 column, 10px vertical gap

        // Create and format welcome label
        welcomeLabel = new JLabel();
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(welcomeLabel);

        // Create and format date label
        dateLabel = new JLabel();
        dateLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        dateLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(dateLabel);

        // Create and format total assets label
        totalAssetsLabel = new JLabel();
        totalAssetsLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        totalAssetsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(totalAssetsLabel);

        // Create panel for pie chart
        chartPanel = new JPanel();
        chartPanel.setLayout(new BorderLayout());
        add(chartPanel);

        // Update labels with user information
        updateUserInformation();
    }

    private void updateUserInformation() {
        // Get current user's first and last names
        String firstName = CurrentUser.getInstance().getUser().getFirstName();
        String lastName = CurrentUser.getInstance().getUser().getLastName();

        // Set welcome label text
        welcomeLabel.setText("Welcome, " + firstName + " " + lastName + "!");

        // Get today's date and format it
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault());
        String todayDate = dateFormat.format(new Date());

        // Set date label text
        dateLabel.setText("Today's Date: " + todayDate);

        // Update total assets label and pie chart
        updateTotalAssets();
    }

    private void updateTotalAssets() {
        try {
            // Fetch accounts belonging to the current user
            List<Account> userAccounts = bankService.getAccountByUserid(CurrentUser.getInstance().getUser().getUserid());

            // Calculate total assets by summing up the balances of all accounts
            BigDecimal totalAssets = BigDecimal.ZERO;
            for (Account account : userAccounts) {
                totalAssets = totalAssets.add(BigDecimal.valueOf(account.getBalance()));
            }

            // Format total assets using locale-specific number formatting
            NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.getDefault());
            String formattedTotalAssets = currencyFormat.format(totalAssets);

            // Set the formatted total assets value to the label
            totalAssetsLabel.setText("Total Assets: " + formattedTotalAssets);

            // Create a pie chart dataset
            DefaultPieDataset dataset = createDataset(userAccounts);

            // Create the pie chart
            JFreeChart pieChart = ChartFactory.createPieChart("Assets Distribution", dataset, true, true, false);
            ChartPanel chartPanel = new ChartPanel(pieChart);
            this.chartPanel.removeAll();
            this.chartPanel.add(chartPanel, BorderLayout.CENTER);
            this.chartPanel.revalidate();
            this.chartPanel.repaint();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private DefaultPieDataset createDataset(List<Account> accounts) {
        DefaultPieDataset dataset = new DefaultPieDataset();
        Map<String, BigDecimal> accountTypeMap = new HashMap<>();

        // Calculate total balance for each account type
        for (Account account : accounts) {
            BigDecimal balance = BigDecimal.valueOf(account.getBalance());
            String accountType = String.valueOf(account.getAccountType());

            if (accountTypeMap.containsKey(accountType)) {
                accountTypeMap.put(accountType, accountTypeMap.get(accountType).add(balance));
            } else {
                accountTypeMap.put(accountType, balance);
            }
        }

        // Add data to the dataset
        for (Map.Entry<String, BigDecimal> entry : accountTypeMap.entrySet()) {
            dataset.setValue(entry.getKey(), entry.getValue());
        }

        return dataset;
    }
}
