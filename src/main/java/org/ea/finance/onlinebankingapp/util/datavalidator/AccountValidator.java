package org.ea.finance.onlinebankingapp.util.datavalidator;

import org.ea.finance.onlinebankingapp.model.account.*;
import org.ea.finance.onlinebankingapp.service.AccountService;
import org.ea.finance.onlinebankingapp.util.datavalidator.util.ValidationResult;
import org.ea.finance.onlinebankingapp.util.datavalidator.util.Validator;

import java.util.List;

public class AccountValidator extends Validator {

    @Override
    public ValidationResult validateObject(Object account,boolean existingObject) {
        if (!existingObject)
        {
            List<Account> userExistingAccounts=new AccountService().getAccountByUserid(((Account)account).getUserid());
            int bankCounter=0;
            int cardCounter=0;
            for(Account acc:userExistingAccounts)
            {
                switch(acc.getAccountType())
                {
                    case BANK:
                        bankCounter++;
                        break;
                    case CREDITCARD:
                        cardCounter++;
                        break;
                    default:
                        break;
                }
            }

            switch (((Account)account).getAccountType()) {
                case BANK:
                    if(((Account)account).getBalance()<=0)
                    {
                        return new ValidationResult(false, "Amount can not be less than 0");
                    }
                    if(bankCounter>0)
                    {
                        return new ValidationResult(false, "User can only have one BANK Account");
                    }
                    break;
                case CREDITCARD:
                    if(bankCounter<1)
                    {
                        return new ValidationResult(false, "You can not open CREDITCARD Account, if you do not have a BANK Account.");
                    }
                    if(cardCounter>0)
                    {
                        return new ValidationResult(false, "You already have a CREDITCARD Account.");
                    }
                    break;
                case INVESTMENT:
                    if(bankCounter<1)
                    {
                        return new ValidationResult(false, "You can not open LOAN Account, if you do not have a BANK Account.");
                    }
                    break;
                default:
                    return new ValidationResult(true, "Account is valid");
            }

        }
        return new ValidationResult(true, "Account is valid");

    }
}
