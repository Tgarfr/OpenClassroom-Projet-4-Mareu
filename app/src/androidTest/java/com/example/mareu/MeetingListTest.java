package com.example.mareu;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.core.StringContains.containsString;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.*;

import com.example.mareu.model.Meeting;
import com.example.mareu.model.Participant;
import com.example.mareu.model.Room;
import com.example.mareu.repository.MeetingRepository;
import com.example.mareu.ui.meeting_list.MeetingListActivity;
import com.example.mareu.utils.RecyclerViewItemAssertion;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class MeetingListTest {

    private MeetingRepository meetingRepository;

    @Rule
    public ActivityScenarioRule<MeetingListActivity> activityScenarioRule = new ActivityScenarioRule<MeetingListActivity> (MeetingListActivity.class);

    @Before
    public void setUp() {
        meetingRepository = MeetingRepository.getInstance();
        Meeting fakeMeeting = getFakeMeeting();
        meetingRepository.addMeeting(fakeMeeting.getName(), fakeMeeting.getBeginDate(), fakeMeeting.getEndDate(), fakeMeeting.getRoom(), fakeMeeting.getParticipantList());
        meetingRepository.addMeeting(fakeMeeting.getName(), fakeMeeting.getBeginDate(), fakeMeeting.getEndDate(), fakeMeeting.getRoom(), fakeMeeting.getParticipantList());
        meetingRepository.addMeeting(fakeMeeting.getName(), fakeMeeting.getBeginDate(), fakeMeeting.getEndDate(), fakeMeeting.getRoom(), fakeMeeting.getParticipantList());
        meetingRepository.addMeeting(fakeMeeting.getName(), fakeMeeting.getBeginDate(), fakeMeeting.getEndDate(), fakeMeeting.getRoom(), fakeMeeting.getParticipantList());
    }

    @Test
    public void meetingListTest_checkList() {
        ViewInteraction view = onView(withId(R.id.list_meetings));
        for (int i = 0; i < meetingRepository.countMeeting(); i++) {
            view.check(new RecyclerViewItemAssertion(i, R.id.item_meeting_name, withText(containsString(meetingRepository.getMeetingList().get(i).getName()))));
        }
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
