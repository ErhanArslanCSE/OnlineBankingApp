package org.ea.finance.onlinebankingapp.ui;

import org.ea.finance.onlinebankingapp.model.account.Account;
import org.ea.finance.onlinebankingapp.model.authentication.CurrentUser;
import org.ea.finance.onlinebankingapp.model.payment.PaymentType;
import org.ea.finance.onlinebankingapp.model.payment.Payment;
import org.ea.finance.onlinebankingapp.service.AccountService;
import org.ea.finance.onlinebankingapp.service.PaymentService;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class PaymentsPanel extends FeaturePanel {

    private final PaymentService paymentService = new PaymentService();
    private final JSplitPane splitPane;

    public PaymentsPanel() {
        // Calculate the height for each payment type panel (one-fifth of the main panel's height)
        int panelHeight = getPreferredSize().height / 5;

        // Create tables for each payment type
        JTable creditCardTable = createPaymentTable(PaymentType.CREDIT_CARD, panelHeight);
        JTable bankTransferTable = createPaymentTable(PaymentType.BANK_TRANSFER, panelHeight);
        JTable payPalTable = createPaymentTable(PaymentType.PAYPAL, panelHeight);
        JTable billPaymentTable = createPaymentTable(PaymentType.BILL_PAYMENT, panelHeight);

        // Create split panes for horizontal arrangement
        JSplitPane splitPane1 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        JSplitPane splitPane2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);

        // Set the size of the divider
        splitPane1.setDividerSize(5);
        splitPane2.setDividerSize(5);

        // Set initial divider location
        splitPane1.setResizeWeight(0.5);
        splitPane2.setResizeWeight(0.5);

        // Add tables to split panes
        splitPane1.setLeftComponent(createTitledScrollPane(creditCardTable, "Credit Card Payments"));
        splitPane1.setRightComponent(createTitledScrollPane(bankTransferTable, "Bank Transfer Payments"));

        splitPane2.setLeftComponent(createTitledScrollPane(payPalTable, "PayPal Payments"));
        splitPane2.setRightComponent(createTitledScrollPane(billPaymentTable, "Bill Payments"));

        // Set orientation and arrangement of split panes
        splitPane1.setOrientation(JSplitPane.VERTICAL_SPLIT);
        splitPane2.setOrientation(JSplitPane.VERTICAL_SPLIT);

        // Add split panes to main split pane
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, splitPane1, splitPane2);
        splitPane.setResizeWeight(0.5);

        // Add the "Make Payment" button
        JButton makePaymentButton = new JButton("Make Payment");
        makePaymentButton.addActionListener(this::makePaymentButtonClicked);

        // Set layout for the panel
        setLayout(new BorderLayout());
        add(splitPane, BorderLayout.CENTER);
        add(makePaymentButton, BorderLayout.SOUTH);

        // Set preferred size of the panel
        setPreferredSize(new Dimension(600, 400));
    }

    @Override
    protected void initComponents() {
        // Nothing to initialize here
    }

    private JTable createPaymentTable(PaymentType paymentType, int height) {
        DefaultTableModel model = new DefaultTableModel(
                new Object[][]{},
                new String[]{"ID", "Amount", "Account","Target Account", "Date"}
        );

        for(Account acc:new AccountService().getAccountByUserid(CurrentUser.getInstance().getUser().getUserid()))
        {
            List<Payment> payments = paymentService.getPaymentsByAccountAndType(acc.getId(),paymentType);

            for (Payment payment : payments) {
                model.addRow(new Object[]{
                        payment.getId(),
                        payment.getAmount(),
                        payment.getAccount().getAccountNumber(),
                        payment.getDestinationAccount() != null ? payment.getDestinationAccount().getAccountNumber() : null,
                        payment.getDate()
                });
            }
        }

        JTable table = new JTable(model);
        table.setFillsViewportHeight(true);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(getPreferredSize().width, height));
        return table;
    }

    private JScrollPane createTitledScrollPane(Component component, String title) {
        JScrollPane scrollPane = new JScrollPane(component);
        scrollPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK),
                title, TitledBorder.CENTER, TitledBorder.TOP));
        return scrollPane;
    }

    private void makePaymentButtonClicked(ActionEvent e) {

        notifyObservers("MakePayment");
    }
}
