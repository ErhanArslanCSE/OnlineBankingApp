package org.ea.finance.onlinebankingapp.model.authentication;

public class CurrentUser {
    private static CurrentUser instance;
    private User user;

    private CurrentUser() {}

    // Method to get the singleton instance
    public static synchronized CurrentUser getInstance() {
        if (instance == null) {
            instance = new CurrentUser();
        }
        return instance;
    }

    // Method to set the current user
    public void setUser(User user) {
        this.user = user;
    }

    // Method to get the current user
    public User getUser() {
        return user;
    }
}
