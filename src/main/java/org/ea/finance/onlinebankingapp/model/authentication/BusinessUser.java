package org.ea.finance.onlinebankingapp.model.authentication;

import org.ea.finance.onlinebankingapp.model.common.AuditableEntity;

public class BusinessUser extends User {

    public BusinessUser(int userid,String username, String firstName, String lastName, String email, String password,String isAdminUser) {
        setUserid(userid);
        setUsername(username);
        setFirstName(firstName);
        setLastName(lastName);
        setEmail(email);
        setPassword(password);
        setUserType(UserType.BUSINESS);
        setIsAdminUser(isAdminUser);
    }
    public BusinessUser() {

    }
}
