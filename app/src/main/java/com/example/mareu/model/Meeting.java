package com.example.mareu.model;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;

public class Meeting {

    private int id;
    private String name;
    private Calendar date;
    private int room;

    public Meeting(int id, String name, Calendar date, int room) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.room = room;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Calendar getDate() {
        return date;
    }

    public int getRoom() {
        return room;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public void setRoom(int room) {
        this.room = room;
    }
}
