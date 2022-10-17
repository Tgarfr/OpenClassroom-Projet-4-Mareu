package com.example.mareu.repository;

import com.example.mareu.model.Meeting;
import com.example.mareu.model.Participant;
import com.example.mareu.model.Room;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MeetingRepository {

    List<Meeting> meetingList;

    private MeetingRepository() {
        meetingList = new ArrayList<Meeting>();
    }

    private static final MeetingRepository INSTANCE = new MeetingRepository();

    public static MeetingRepository getInstance() {
        return INSTANCE;
    }

    public List<Meeting> getMeetingList() {
        return meetingList;
    }

    public void addMeeting(String name, Calendar beginDate, Calendar endDate, Room room, List<Participant> participantList) {
        Meeting newMeeting = new Meeting(1, name, beginDate, endDate, room, participantList);
        meetingList.add(newMeeting);
    }

    public void deleteMeeting(Meeting meeting) {
        if (meetingList.contains(meeting)) {
            meetingList.remove(meeting);
        }
    }

    public int countMeeting() {
        return meetingList.size();
    }
}
