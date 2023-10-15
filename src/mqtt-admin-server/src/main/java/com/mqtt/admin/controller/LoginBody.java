package com.mqtt.admin.controller;

public class LoginBody {
    public String username;
    public String password;

    public LoginBody(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public LoginBody() {
    }
}
