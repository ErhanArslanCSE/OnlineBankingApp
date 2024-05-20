package org.ea.finance.onlinebankingapp.util.datavalidator;


import org.ea.finance.onlinebankingapp.model.account.Account;
import org.ea.finance.onlinebankingapp.model.account.AccountType;
import org.ea.finance.onlinebankingapp.model.payment.Payment;
import org.ea.finance.onlinebankingapp.model.payment.PaymentType;
import org.ea.finance.onlinebankingapp.util.datavalidator.util.ValidationResult;
import org.ea.finance.onlinebankingapp.util.datavalidator.util.Validator;

public class PaymentValidator extends Validator {

    @Override
    public ValidationResult validateObject(Object  payment,boolean existingObject) {

        if(!existingObject)
        {
            if(((Payment)payment).getAccount().getAccountType()==AccountType.BANK  && ((Payment)payment).getAccount().getBalance() < ((Payment)payment).getAmount())
            {
                return new ValidationResult(false, "Insufficient balance in the source account.");
            }
            if( (((Payment)payment).getType()== PaymentType.BANK_TRANSFER || ((Payment)payment).getType()== PaymentType.CREDIT_CARD)
                && ((Payment)payment).getDestinationAccount() ==null )
            {
                return new ValidationResult(false, "Enter a valid Destination Account.");
            }
            if(((Payment)payment).getType()==PaymentType.CREDIT_CARD && ((Payment)payment).getDestinationAccount().getAccountType()!= AccountType.CREDITCARD)
            {
                return new ValidationResult(false, "Enter a valid Credit Card Account.");
            }
            if (((Payment)payment).getDestinationAccount()!=null)
            {
                if(((Payment)payment).getAccount().getId()==((Payment)payment).getDestinationAccount().getId())
                {
                    return new ValidationResult(false, "Destination Account can not be same.");
                }
            }

        }

        return new ValidationResult(true, "Payment validated.");
    }
}