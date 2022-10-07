package com.example.mareu.repository;

import com.example.mareu.model.Meeting;

import java.util.ArrayList;
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

    public int countMeeting() {
        return meetingList.size();
    }
}
