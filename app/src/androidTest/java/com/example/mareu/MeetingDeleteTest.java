package com.example.mareu;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.example.mareu.utils.RecyclerViewCountAssertion.withItemCount;
import static org.hamcrest.Matchers.containsString;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.mareu.model.Meeting;
import com.example.mareu.repository.MeetingRepository;
import com.example.mareu.ui.MainActivity;
import com.example.mareu.ui.meeting_list.MeetingListFragment;
import com.example.mareu.utils.FakeMeeting;
import com.example.mareu.utils.RecyclerViewDeleteItemAction;
import com.example.mareu.utils.RecyclerViewItemAssertion;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class MeetingDeleteTest {

    private MeetingRepository meetingRepository;

    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule = new ActivityScenarioRule<MainActivity>(MainActivity.class);

    @Before
    public void setUp() {
        meetingRepository = MeetingRepository.getInstance();
        Meeting fakeMeeting = FakeMeeting.getFakeMeeting();
        meetingRepository.addMeeting(fakeMeeting.getName()+" 1", fakeMeeting.getBeginDate(), fakeMeeting.getEndDate(), fakeMeeting.getRoom(), fakeMeeting.getParticipantList());
        meetingRepository.addMeeting(fakeMeeting.getName()+" 2", fakeMeeting.getBeginDate(), fakeMeeting.getEndDate(), fakeMeeting.getRoom(), fakeMeeting.getParticipantList());
        meetingRepository.addMeeting(fakeMeeting.getName()+" 3", fakeMeeting.getBeginDate(), fakeMeeting.getEndDate(), fakeMeeting.getRoom(), fakeMeeting.getParticipantList());
        meetingRepository.addMeeting(fakeMeeting.getName()+" 4", fakeMeeting.getBeginDate(), fakeMeeting.getEndDate(), fakeMeeting.getRoom(), fakeMeeting.getParticipantList());
    }

    @Test
    public void meetingDeleteTest_check() {
        // Given
        final int positionItemRemoved = 2;
        int expectedItemCount = meetingRepository.countMeeting();
        final String expectedMeetingName = meetingRepository.getMeetingList().get(positionItemRemoved).getName();
        onView(ViewMatchers.withId(R.id.list_meetings)).check(withItemCount(expectedItemCount));

        // When
        onView(ViewMatchers.withId(R.id.list_meetings))
                .perform(RecyclerViewActions.actionOnItemAtPosition(positionItemRemoved,new RecyclerViewDeleteItemAction()));

        // Then
        onView(ViewMatchers.withId(R.id.list_meetings))
                .check(withItemCount(expectedItemCount-1))
                .check(new RecyclerViewItemAssertion(positionItemRemoved, R.id.item_meeting_name, withText(containsString(expectedMeetingName)), false));
    }
}