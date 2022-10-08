package com.example.mareu.ui.meeting_add;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mareu.R;
import com.example.mareu.repository.MeetingRepository;
import com.example.mareu.repository.RoomRepository;

import java.util.Calendar;

public class MeetingAddActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meeting);

        EditText nameLayout = findViewById(R.id.add_meeting_name);
        Button dateLayout = findViewById(R.id.add_meeting_date_button);
        Button timeLayout = findViewById(R.id.add_meeting_time_button);
        Button durationLayout = findViewById(R.id.add_meeting_duration_button);
        Spinner roomLayout = findViewById(R.id.add_meeting_room_spinner);

        RoomRepository roomRepositoryRepository = RoomRepository.getInstance();
        RoomSpinnerAdapter adapter = new RoomSpinnerAdapter(roomRepositoryRepository);
        roomLayout.setAdapter(adapter);
    }
}
