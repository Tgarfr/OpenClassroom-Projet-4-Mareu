package com.example.mareu.ui.meeting_add;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mareu.R;
import com.example.mareu.repository.MeetingRepository;
import com.example.mareu.repository.RoomRepository;

public class MeetingAddActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meeting);

        RoomRepository roomRepositoryRepository = RoomRepository.getInstance();
        RoomSpinnerAdapter adapter = new RoomSpinnerAdapter(roomRepositoryRepository);
        Spinner meetingNameLayout = findViewById(R.id.add_meeting_room_spinner);
        meetingNameLayout.setAdapter(adapter);

    }
}
