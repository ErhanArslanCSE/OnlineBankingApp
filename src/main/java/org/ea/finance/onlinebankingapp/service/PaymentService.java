package org.ea.finance.onlinebankingapp.service;

import org.ea.finance.onlinebankingapp.dao.impl.PaymentDAOImpl;
import org.ea.finance.onlinebankingapp.factory.TransactionFactory;
import org.ea.finance.onlinebankingapp.model.payment.Payment;
import org.ea.finance.onlinebankingapp.model.payment.PaymentType;
import org.ea.finance.onlinebankingapp.model.transactions.Transaction;
import org.ea.finance.onlinebankingapp.model.transactions.TransactionType;

import java.util.List;

public class PaymentService extends BaseService<Payment> {
    private final PaymentDAOImpl paymentDAO;

    public PaymentService() {
        super(new PaymentDAOImpl());
        this.paymentDAO = (PaymentDAOImpl) dao;
    }

    @Override
    public void add(Payment payment) {
        super.add(payment);
        Transaction sourceT;
        Transaction targetT = null;
        TransactionType sourceTransactionType = null;
        TransactionType targetTransactionType = null;

        switch (payment.getType()) {
            case BANK_TRANSFER, CREDIT_CARD -> {
                sourceTransactionType = TransactionType.TRANSFER;
                targetTransactionType = TransactionType.TRANSFER;
            }
            case PAYPAL, BILL_PAYMENT -> sourceTransactionType = TransactionType.EXPENSE;
            case SALARY_PAYMENT -> {
                sourceTransactionType = TransactionType.EXPENSE;
                targetTransactionType = TransactionType.INCOME;
            }
        }

        sourceT = TransactionFactory.createTransaction(
                -1,
                sourceTransactionType,
                -payment.getAmount(),
                payment.getAccount(),
                payment.getAccount(),
                payment.getDestinationAccount(),
                false
        );

        if (targetTransactionType != null) {
            targetT = TransactionFactory.createTransaction(
                    -1,
                    targetTransactionType,
                    payment.getAmount(),
                    payment.getDestinationAccount(),
                    payment.getAccount(),
                    payment.getDestinationAccount(),
                    false
            );
        }

        TransactionService transactionService = new TransactionService();
        transactionService.add(sourceT);
        if (targetT != null) {
            transactionService.add(targetT);
        }
    }

    public List<Payment> getPaymentsByAccountAndType(int accountId, PaymentType type) {
        return paymentDAO.getPaymentsByAccountAndType(accountId, type);
    }
}
