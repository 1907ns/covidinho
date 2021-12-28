package com.example.covidinho.beans;

public class Notification {
    private int id;
    private int userId;
    private int senderId;
    private int type;
    private String content;
    private int read;


    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public int getType() {
        return type;
    }

    public String getContent() {
        return content;
    }

    public int getRead() {
        return read;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setRead(int read) {
        this.read = read;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }
}
