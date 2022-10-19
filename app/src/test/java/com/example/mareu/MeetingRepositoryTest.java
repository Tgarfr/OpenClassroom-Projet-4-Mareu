package com.example.mareu;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.example.mareu.model.Meeting;
import com.example.mareu.model.Participant;
import com.example.mareu.model.Room;
import com.example.mareu.repository.MeetingRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@RunWith(JUnit4.class)
public class MeetingRepositoryTest {
    private MeetingRepository meetingRepository;

    @Before
    public void setup() {
        meetingRepository = MeetingRepository.getInstance();
    }

    @Test
    public void addMeetingWithSuccess() {
        // Arrange
        Meeting expectedMeeting = getFakeMeeting();

        // Act
        meetingRepository.addMeeting(expectedMeeting.getName(), expectedMeeting.getBeginDate(), expectedMeeting.getEndDate(), expectedMeeting.getRoom(), expectedMeeting.getParticipantList());

        // Assert
        Meeting actualMeeting = null;
        for (int i = 0; i < meetingRepository.countMeeting(); i++) {
            if (meetingRepository.getMeetingList().get(i).getName() == expectedMeeting.getName()) {
                actualMeeting = meetingRepository.getMeetingList().get(i);
            }
        }
        assertEquals(expectedMeeting.getBeginDate(), actualMeeting.getBeginDate());
        assertEquals(expectedMeeting.getEndDate(), actualMeeting.getEndDate());
        assertEquals(expectedMeeting.getRoom(), actualMeeting.getRoom());
        assertEquals(expectedMeeting.getParticipantList(), actualMeeting.getParticipantList());
        for (int i = 0; i < meetingRepository.countMeeting(); i++) {
            if (meetingRepository.getMeetingList().get(i).getName() == expectedMeeting.getName()) {
                actualMeeting = meetingRepository.getMeetingList().get(i);
            }
        }
    }

    @Test
    public void deleteMeetingWithSuccess() {
        // Arrange
        Meeting fakeMeeting = getFakeMeeting();
        meetingRepository.addMeeting(fakeMeeting.getName(), fakeMeeting.getBeginDate(), fakeMeeting.getEndDate(), fakeMeeting.getRoom(), fakeMeeting.getParticipantList());
        Meeting deleteMeeting = null;
        for (int i = 0; i < meetingRepository.countMeeting(); i++) {
            if (meetingRepository.getMeetingList().get(i).getName() == fakeMeeting.getName()) {
                deleteMeeting = meetingRepository.getMeetingList().get(i);
            }
        }
        int expectedMeetingCount = meetingRepository.countMeeting();

        // Act
        meetingRepository.deleteMeeting(deleteMeeting);

        // Assert
        int actualMeetingCount = meetingRepository.countMeeting();
        assertEquals(expectedMeetingCount-1, actualMeetingCount);
    }

    private Meeting getFakeMeeting() {
        String name = "Meeting Name Test";
        Calendar beginDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.MINUTE, 45);
        Room room = new Room(0, "Room");
        List<Participant> participantList = new ArrayList<Participant>();
        participantList.add(new Participant("test@yahoo.fr"));
        participantList.add(new Participant("test2@google.fr"));
        participantList.add(new Participant("test3@laposte.net"));
        return new Meeting(0L, name, beginDate, endDate, room, participantList);
    }


}
