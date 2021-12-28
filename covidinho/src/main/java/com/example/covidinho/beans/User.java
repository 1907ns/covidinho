package com.example.covidinho.beans;

public class User {
    private int id;
    private String username;
    private String password;
    private String name;
    private String firstname;
    private String birthdate;
    private int admin;
    private int isPositive;
    private String positiveDate;
    private int isVaccinated;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getPositiveDate() {
        return positiveDate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public void setPositiveDate(String positiveDate) {
        this.positiveDate = positiveDate;
    }

    public int getAdmin() {
        return admin;
    }

    public int getIsPositive() {
        return isPositive;
    }

    public int getIsVaccinated() {
        return isVaccinated;
    }

    public void setAdmin(int admin) {
        this.admin = admin;
    }

    public void setIsPositive(int isPositive) {
        this.isPositive = isPositive;
    }

    public void setIsVaccinated(int isVaccinated) {
        this.isVaccinated = isVaccinated;
    }
}
