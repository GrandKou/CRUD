package com.example.csit228f2_2;

import java.net.MalformedURLException;

public class User {
    int id;
    String username;
    String password;

    public User(String username, String password) throws MalformedURLException {
        this.username = username;
        this.password = password;
    }
}
