package com.example.mareu.model;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;

public class Meeting {

    private int id;
    private String name;
    private Calendar beginDate;
    private Calendar endDate;
    private int room;

    public Meeting(int id, String name, Calendar beginDate, Calendar endDate, int room) {
        this.id = id;
        this.name = name;
        this.beginDate = beginDate;
        this.endDate =  endDate;
        this.room = room;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Calendar getBeginDate() {
        return beginDate;
    }

    public Calendar getEndDate() {
        return endDate;
    }

    public int getRoom() {
        return room;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBeginDate(Calendar beginDate) {
        this.beginDate = beginDate;
    }

    public void setEndDate(Calendar endDate) {
        this.endDate = endDate;
    }

    public void setRoom(int room) {
        this.room = room;
    }
}
