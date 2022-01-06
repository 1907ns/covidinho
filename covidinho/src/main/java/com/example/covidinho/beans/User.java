package com.example.covidinho.beans;

import com.example.covidinho.dao.UserDao;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class User {
    private int id;
    private String username;
    private String password;
    private String name;
    private String firstname;
    private String birthdate;
    private int admin;
    private int isPositive;
    private Date positiveDate;
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

    public Date getPositiveDate() {
        return positiveDate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public void setPositiveDate(Date positiveDate) {
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

    public void isCured() throws ParseException {

        if(getIsPositive()==1){
            java.util.Date today = new java.util.Date();
            Date dateFinConfinement = new Date(positiveDate.getTime() + (1000 * 60 * 60 * 24 * 7));
            if(today.after(dateFinConfinement) || today.equals(dateFinConfinement)){
                this.setIsPositive(0);
                UserDao userDao = new UserDao();
                userDao.modifyUser(this);
            }
        }
    }
}
