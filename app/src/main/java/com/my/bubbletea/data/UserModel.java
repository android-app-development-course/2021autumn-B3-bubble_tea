package com.my.bubbletea.data;

/**
 * [Wait for review]...
 * maintain a in-memory cache storing user information
 */
public class UserModel {
    private boolean isLoggedIn;
    private String username;


    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        isLoggedIn = loggedIn;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }



}
