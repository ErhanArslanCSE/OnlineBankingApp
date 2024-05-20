package org.ea.finance.onlinebankingapp.util.datavalidator;

import org.ea.finance.onlinebankingapp.model.authentication.User;
import org.ea.finance.onlinebankingapp.util.datavalidator.util.Validator;
import org.ea.finance.onlinebankingapp.util.datavalidator.util.ValidationResult;

public class UserRegistrationValidator  extends Validator {

    @Override
    public ValidationResult validateObject(Object user,boolean existingObject) {
        boolean isEmailValid = isValidEMail(((User)user).getEmail());
        boolean isPasswordValid = isValidLength(((User)user).getPassword(),4,10);

        if (!isEmailValid) {
            return new ValidationResult(false, "Geçersiz email adresi");
        }

        if (!isPasswordValid) {
            return new ValidationResult(false, "Şifre en az 6 ve en fazla 20 karakter olmalıdır");
        }

        return new ValidationResult(true, "Kullanıcı kaydı başarıyla doğrulandı");
    }
}
