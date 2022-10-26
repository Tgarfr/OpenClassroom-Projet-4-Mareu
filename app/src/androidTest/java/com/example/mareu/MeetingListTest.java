package com.example.mareu;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.StringContains.containsString;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.mareu.model.Meeting;
import com.example.mareu.repository.MeetingRepository;
import com.example.mareu.ui.MainActivity;
import com.example.mareu.ui.meeting_list.MeetingListFragment;
import com.example.mareu.utils.FakeMeeting;
import com.example.mareu.utils.RecyclerViewItemAssertion;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class MeetingListTest {

    private MeetingRepository meetingRepository;

    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule = new ActivityScenarioRule<MainActivity> (MainActivity.class);

    @Before
    public void setUp() {
        meetingRepository = MeetingRepository.getInstance();
        Meeting fakeMeeting = FakeMeeting.getFakeMeeting();
        meetingRepository.addMeeting(fakeMeeting.getName(), fakeMeeting.getBeginDate(), fakeMeeting.getEndDate(), fakeMeeting.getRoom(), fakeMeeting.getParticipantList());
        meetingRepository.addMeeting(fakeMeeting.getName(), fakeMeeting.getBeginDate(), fakeMeeting.getEndDate(), fakeMeeting.getRoom(), fakeMeeting.getParticipantList());
        meetingRepository.addMeeting(fakeMeeting.getName(), fakeMeeting.getBeginDate(), fakeMeeting.getEndDate(), fakeMeeting.getRoom(), fakeMeeting.getParticipantList());
        meetingRepository.addMeeting(fakeMeeting.getName(), fakeMeeting.getBeginDate(), fakeMeeting.getEndDate(), fakeMeeting.getRoom(), fakeMeeting.getParticipantList());
    }

    @Test
    public void meetingListTest_checkList() {
        ViewInteraction view = onView(withId(R.id.list_meetings));
        for (int i = 0; i < meetingRepository.countMeeting(); i++) {
            view.check(new RecyclerViewItemAssertion(i, R.id.item_meeting_name, withText(containsString(meetingRepository.getMeetingList().get(i).getName())), true));
        }
    }
}
