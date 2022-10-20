package com.example.mareu.repository;

import com.example.mareu.model.Meeting;
import com.example.mareu.model.Participant;
import com.example.mareu.model.Room;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
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
        Meeting newMeeting = new Meeting(System.currentTimeMillis(), name, beginDate, endDate, room, participantList);
        meetingList.add(newMeeting);
    }

    public void deleteMeeting(Meeting meeting) {
        meetingList.remove(meeting);
    }

    public int countMeeting() {
        return meetingList.size();
    }

    public void sortByDate() {
        Comparator<Meeting> comparator = new Comparator<Meeting>() {
            @Override
            public int compare(Meeting meeting1, Meeting meeting2) {
                if (meeting1.getBeginDate().before(meeting2.getBeginDate())) {
                    return -1;
                }
                if (meeting1.getBeginDate().after(meeting2.getBeginDate())) {
                    return 1;
                }
                else {
                    return 0;
                }
            }
        };
        Collections.sort(meetingList,comparator);
    }

    public void sortByRoom() {
        Comparator<Meeting> comparator = new Comparator<Meeting>() {
            @Override
            public int compare(Meeting meeting1, Meeting meeting2) {
                return meeting1.getRoom().getId() - meeting2.getRoom().getId();
            }
        };
        Collections.sort(meetingList,comparator);
    }
}
