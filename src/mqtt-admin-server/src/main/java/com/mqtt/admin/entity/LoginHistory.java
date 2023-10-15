package com.mqtt.admin.entity;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
public class LoginHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Timestamp timestamp;
    private String ipSrc;
    private LoginType type;
    @ManyToOne
    private User user;

    public LoginHistory(Timestamp timestamp, String ipSrc, LoginType type, User user) {
        this.timestamp = timestamp;
        this.ipSrc = ipSrc;
        this.type = type;
        this.user = user;
    }

    public LoginHistory() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getIpSrc() {
        return ipSrc;
    }

    public void setIpSrc(String ipSrc) {
        this.ipSrc = ipSrc;
    }

    public LoginType getType() {
        return type;
    }

    public void setType(LoginType type) {
        this.type = type;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
