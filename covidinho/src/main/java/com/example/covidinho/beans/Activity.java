package com.example.covidinho.beans;

import java.sql.Date;
import java.sql.Timestamp;

public class Activity {
    private Timestamp begining;
    private Timestamp end;
    private String place;

    public Activity(Timestamp begining, Timestamp end, String place) {
        this.begining = begining;
        this.end = end;
        this.place = place;
    }

    public Timestamp getBegining() {
        return begining;
    }

    public void setBegining(Timestamp begining) {
        this.begining = begining;
    }

    public Timestamp getEnd() {
        return end;
    }

    public void setEnd(Timestamp end) {
        this.end = end;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }
}
