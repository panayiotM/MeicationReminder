package com.example.medreminder3;

public class User {
    private String userName;
    private String password;
    private String email;
    private int age ;
    public User(String userName, String email, int age) {
        this.userName = userName;
        this.email = email;
        this.age =age;

    }
    public User() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
