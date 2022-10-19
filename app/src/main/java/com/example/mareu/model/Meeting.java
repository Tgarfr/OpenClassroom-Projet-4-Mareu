package com.example.mareu.model;

import java.util.Calendar;
import java.util.List;

public class Meeting {

    private Long id;
    private String name;
    private Calendar beginDate;
    private Calendar endDate;
    private Room room;
    private List<Participant> participantList;

    public Meeting(Long id, String name, Calendar beginDate, Calendar endDate, Room room, List<Participant> participantList) {
        this.id = id;
        this.name = name;
        this.beginDate = beginDate;
        this.endDate =  endDate;
        this.room = room;
        this.participantList = participantList;
    }

    public Long getId() {
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

    public Room getRoom() {
        return room;
    }

    public List<Participant> getParticipantList() {
        return participantList;
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

    public void setRoom(Room room) {
        this.room = room;
    }

    public void setParticipantList(List<Participant> participantList) {
        this.participantList = participantList;
    }
}
