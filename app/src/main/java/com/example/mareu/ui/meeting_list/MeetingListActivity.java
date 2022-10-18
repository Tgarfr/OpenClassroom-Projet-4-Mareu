package com.example.mareu.ui.meeting_list;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mareu.R;
import com.example.mareu.model.Participant;
import com.example.mareu.repository.MeetingRepository;
import com.example.mareu.repository.RoomRepository;
import com.example.mareu.ui.meeting_add.MeetingAddActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;

public class MeetingListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MeetingRepository meetingRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_meeting);

        Locale locale = getResources().getConfiguration().locale;
        meetingRepository = MeetingRepository.getInstance();

        recyclerView = findViewById(R.id.list_meetings);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MeetingListRecyclerViewAdapter(meetingRepository, this, locale));

        FloatingActionButton AddButton = findViewById(R.id.add_meeting_button);
        AddButton.setOnClickListener(AddButtonClick);
    }

    @Override
    protected void onResume() {
        super.onResume();
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    View.OnClickListener AddButtonClick = new View.OnClickListener() {
        public void onClick(View v) {
            Intent intent = new Intent(MeetingListActivity.this, MeetingAddActivity.class);
            ActivityCompat.startActivity(MeetingListActivity.this,intent,null);
        }
    };

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
            recyclerView.getAdapter().notifyDataSetChanged();
        }
        if (item.getItemId() == R.id.sort_by_room) {
            meetingRepository.sortByRoom();
            recyclerView.getAdapter().notifyDataSetChanged();
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
            recyclerView.getAdapter().notifyDataSetChanged();
        }
        return super.onOptionsItemSelected(item);
    }
}