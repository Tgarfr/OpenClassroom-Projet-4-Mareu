package com.example.mareu.utils;

import com.example.mareu.model.Meeting;
import com.example.mareu.model.Participant;
import com.example.mareu.model.Room;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class FakeMeeting {

    public static Meeting getFakeMeeting() {
        String name = "Meeting Name Test";
        Calendar beginDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.MINUTE, 50);
        Room room = new Room(3, "Salle 3");
        List<Participant> participantList = new ArrayList<Participant>();
        participantList.add(new Participant("test@yahoo.fr"));
        participantList.add(new Participant("test2@google.fr"));
        participantList.add(new Participant("test3@laposte.net"));
        return new Meeting(0L, name, beginDate, endDate, room, participantList);
    }
}