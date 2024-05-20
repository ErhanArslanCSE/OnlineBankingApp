package org.ea.finance.onlinebankingapp.model.authentication;

import org.ea.finance.onlinebankingapp.model.common.AuditableEntity;


public class User extends AuditableEntity {
    private int userid;
    // Kullanıcının kullanıcı adı
    private String username;

    // Kullanıcının adı
    private String firstName;

    // Kullanıcının soyadı
    private String lastName;

    // Kullanıcının e-posta adresi
    private String email;

    // Kullanıcının şifresi
    private String password;

    // Kullanıcı türü
    private UserType userType;

    private String isAdminUser;

    
    public int getUserid() {
        return userid;
    }

    
    public void setUserid(int userid) {
        this.userid = userid;
    }

    // Kullanıcının kullanıcı adını döndüren metot
    
    public String getUsername() {
        return username;
    }

    // Kullanıcının kullanıcı adını ayarlayan metot
    
    public void setUsername(String username) {
        this.username = username;
    }

    // Kullanıcının adını döndüren metot
    
    public String getFirstName() {
        return firstName;
    }

    // Kullanıcının adını ayarlayan metot
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    // Kullanıcının soyadını döndüren metot
    
    public String getLastName() {
        return lastName;
    }

    // Kullanıcının soyadını ayarlayan metot
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    // Kullanıcının e-posta adresini döndüren metot
    
    public String getEmail() {
        return email;
    }

    // Kullanıcının e-posta adresini ayarlayan metot
    
    public void setEmail(String email) {
        this.email = email;
    }

    // Kullanıcının şifresini döndüren metot
    
    public String getPassword() {
        return password;
    }

    // Kullanıcının şifresini ayarlayan metot
    
    public void setPassword(String password) {
        this.password = password;
    }

    
    public UserType getUserType() {
        return this.userType;
    }

    
    public void setUserType(UserType userType) {
        this.userType=userType;
    }

    
    public String getIsAdminUser() {
        return this.isAdminUser;
    }
    
    public void setIsAdminUser(String isAdminUser) {
        this.isAdminUser=isAdminUser;
    }
}
