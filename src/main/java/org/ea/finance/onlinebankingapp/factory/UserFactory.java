package org.ea.finance.onlinebankingapp.factory;

import org.ea.finance.onlinebankingapp.model.authentication.RegularUser;
import org.ea.finance.onlinebankingapp.model.authentication.PremiumUser;
import org.ea.finance.onlinebankingapp.model.authentication.BusinessUser;
import org.ea.finance.onlinebankingapp.model.authentication.User;
import org.ea.finance.onlinebankingapp.model.authentication.UserType;
public class UserFactory {

    // Factory method to create different types of users
    public static User createUser(String username, String firstName, String lastName, String email, String password,UserType type, String isAdminUser) {
        switch (type) {
            case NORMAL:
                return new RegularUser(-1,username, firstName, lastName, email, password,isAdminUser);
            case PREMIUM:
                return new PremiumUser(-1,username, firstName, lastName, email, password,isAdminUser);
            case BUSINESS:
                return new BusinessUser(-1,username, firstName, lastName, email, password,isAdminUser);
            default:
                throw new IllegalArgumentException("Invalid user type: " + type);
        }
    }
}
