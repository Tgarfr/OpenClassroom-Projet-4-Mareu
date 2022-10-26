package com.example.mareu;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.mareu.model.Meeting;
import com.example.mareu.model.Room;
import com.example.mareu.repository.MeetingRepository;
import com.example.mareu.ui.MainActivity;
import com.example.mareu.ui.meeting_list.MeetingListFragment;
import com.example.mareu.utils.FakeMeeting;
import com.example.mareu.utils.RecyclerViewComparatorAssertion;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Calendar;

@RunWith(AndroidJUnit4.class)
public class MeetingSortTest {

    private MeetingRepository meetingRepository;

    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule = new ActivityScenarioRule<MainActivity> (MainActivity.class);

    @Before
    public void setUp() {
        meetingRepository = MeetingRepository.getInstance();
        meetingRepository.clearList();
        Meeting fakeMeeting = FakeMeeting.getFakeMeeting();
        Calendar hour1 = Calendar.getInstance();
        Calendar hour3 = Calendar.getInstance();
        Calendar hour4 = Calendar.getInstance();
        Calendar hour7 = Calendar.getInstance();
        Calendar hour8 = Calendar.getInstance();
        hour1.set(Calendar.HOUR_OF_DAY, 1);
        hour3.set(Calendar.HOUR_OF_DAY, 3);
        hour4.set(Calendar.HOUR_OF_DAY, 4);
        hour7.set(Calendar.HOUR_OF_DAY, 7);
        hour8.set(Calendar.HOUR_OF_DAY, 8);
        meetingRepository.addMeeting("Meeting 3", hour3, fakeMeeting.getEndDate(), new Room(3, "3"), fakeMeeting.getParticipantList());
        meetingRepository.addMeeting("Meeting 8", hour8, fakeMeeting.getEndDate(), new Room(8, "8"), fakeMeeting.getParticipantList());
        meetingRepository.addMeeting("Meeting 1", hour1, fakeMeeting.getEndDate(), new Room(1, "1"), fakeMeeting.getParticipantList());
        meetingRepository.addMeeting("Meeting 7", hour7, fakeMeeting.getEndDate(), new Room(7, "7"), fakeMeeting.getParticipantList());
        meetingRepository.addMeeting("Meeting 4", hour4, fakeMeeting.getEndDate(), new Room(4, "4"), fakeMeeting.getParticipantList());
    }

    @Test
    public void meetingSortByDate_check() {
        // Given

        // When
        onView(ViewMatchers.withId(R.id.sort)).perform(click());
        onView(withText(R.string.by_date)).perform(click());

        // Then
        ViewInteraction view = onView(withId(R.id.list_meetings));
        for (int i = 0; i < meetingRepository.countMeeting()-1; i++) {
            view.check(new RecyclerViewComparatorAssertion(i, i+1, RecyclerViewComparatorAssertion.SortBy.DATE));
        }
    }

    @Test
    public void meetingSortByRoom_check() {
        // Given

        // When
        onView(ViewMatchers.withId(R.id.sort)).perform(click());
        onView(withText(R.string.by_room)).perform(click());

        // Then
        ViewInteraction view = onView(withId(R.id.list_meetings));
        for (int i = 0; i < meetingRepository.countMeeting()-1; i++) {
            view.check(new RecyclerViewComparatorAssertion(i, i+1, RecyclerViewComparatorAssertion.SortBy.ROOM));
        }
    }
}
