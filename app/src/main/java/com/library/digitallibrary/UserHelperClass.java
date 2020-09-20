package com.library.digitallibrary;

public class UserHelperClass {

    public String username,email;

    public UserHelperClass() {
    }

    public UserHelperClass(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
