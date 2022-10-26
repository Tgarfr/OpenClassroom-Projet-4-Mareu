package com.example.mareu.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mareu.R;
import com.example.mareu.model.Participant;
import com.example.mareu.repository.MeetingRepository;
import com.example.mareu.repository.RoomRepository;

import java.util.Arrays;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    MeetingRepository meetingRepository;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        meetingRepository = MeetingRepository.getInstance();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.sort_by_date) {
            meetingRepository.sortByDate();
        }
        if (item.getItemId() == R.id.sort_by_room) {
            meetingRepository.sortByRoom();
        }
        if (item.getItemId() == R.id.sampleMeeting) {
            RoomRepository roomRepository = RoomRepository.getInstance();
            Calendar calendar1 = Calendar.getInstance();
            Calendar calendar2 = Calendar.getInstance();
            calendar2.add(Calendar.MINUTE,45);
            meetingRepository.addMeeting("Réunion 1", calendar1, calendar2, roomRepository.getRoomsList().get(1), Arrays.asList(new Participant("Participant 1"), new Participant("Participant 2"), new Participant("Participant 3")));
            calendar1.add(Calendar.HOUR,5);
            calendar2.add(Calendar.HOUR,5);
            calendar2.add(Calendar.MINUTE,45);
            meetingRepository.addMeeting("Réunion 2", calendar1, calendar2, roomRepository.getRoomsList().get(5), Arrays.asList(new Participant("Participant 1"), new Participant("Participant 2"), new Participant("Participant 3")));
            calendar1.add(Calendar.HOUR,22);
            calendar2.add(Calendar.HOUR,22);
            calendar2.add(Calendar.MINUTE,45);
            meetingRepository.addMeeting("Réunion 3", calendar1, calendar2, roomRepository.getRoomsList().get(3), Arrays.asList(new Participant("Participant 1"), new Participant("Participant 2"), new Participant("Participant 3")));
            calendar1.add(Calendar.HOUR,2);
            calendar2.add(Calendar.HOUR,2);
            calendar2.add(Calendar.MINUTE,45);
            meetingRepository.addMeeting("Réunion 4", calendar1, calendar2, roomRepository.getRoomsList().get(7), Arrays.asList(new Participant("Participant 1"), new Participant("Participant 2"), new Participant("Participant 3")));
        }

        Bundle result = new Bundle();
        getSupportFragmentManager().setFragmentResult("refreshMeetingList", null);

        return super.onOptionsItemSelected(item);
    }
}
