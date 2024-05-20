package org.ea.finance.onlinebankingapp.model.authentication;

import org.ea.finance.onlinebankingapp.model.common.AuditableEntity;

public class RegularUser extends User {
    public RegularUser(int userid,String username, String firstName, String lastName, String email, String password,String isAdminUser) {
        setUserid(userid);
        setUsername(username);
        setFirstName(firstName);
        setLastName(lastName);
        setEmail(email);
        setPassword(password);
        setUserType(UserType.NORMAL);
        setIsAdminUser(isAdminUser);
    }
    public RegularUser() {

    }

}
