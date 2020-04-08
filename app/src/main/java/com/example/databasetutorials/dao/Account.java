package com.example.databasetutorials.dao;

public class Account {
    private int id;
    private String username;
    private String role;
    private String password;

    //tao constructor

    public Account(int id, String username, String password,String role ) {
        this.id = id;
        this.username = username;
        this.role = role;
        this.password = password;
    }

    public Account(String username, String role, String password) {
        this.id = id;
        this.username = username;
        this.role = role;
        this.password = password;
    }

    //getter/setter

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
