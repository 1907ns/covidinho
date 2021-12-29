package com.example.covidinho.beans;

public class Friendship {
    private int idUser1;
    private int idUser2;
    private String friendUsername;


    public int getIdUser1() {
        return idUser1;
    }

    public int getIdUser2() {
        return idUser2;
    }

    public void setIdUser1(int idUser1) {
        this.idUser1 = idUser1;
    }

    public void setIdUser2(int idUser2) {
        this.idUser2 = idUser2;
    }

    public String getFriendUsername() {
        return friendUsername;
    }

    public void setFriendUsername(String friendUsername) {
        this.friendUsername = friendUsername;
    }
}
