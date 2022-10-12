package com.example.mareu.ui.meeting_add;

import android.app.DatePickerDialog;
import android.content.Intent;
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
import androidx.core.app.ActivityCompat;

import com.example.mareu.R;
import com.example.mareu.repository.MeetingRepository;
import com.example.mareu.repository.RoomRepository;
import com.example.mareu.ui.meeting_list.MeetingListActivity;

import java.util.Calendar;

public class MeetingAddActivity extends AppCompatActivity implements DateDialogFragment.ValidDateListener {

    private Button dateLayout;
    private Calendar date;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meeting);

        EditText nameLayout = findViewById(R.id.add_meeting_name);
        dateLayout = findViewById(R.id.add_meeting_date_button);
        Button timeLayout = findViewById(R.id.add_meeting_time_button);
        Button durationLayout = findViewById(R.id.add_meeting_duration_button);
        Spinner roomLayout = findViewById(R.id.add_meeting_room_spinner);

        RoomRepository roomRepositoryRepository = RoomRepository.getInstance();
        RoomSpinnerAdapter adapter = new RoomSpinnerAdapter(roomRepositoryRepository);
        roomLayout.setAdapter(adapter);

        dateLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateDialogFragment dateDialogFragment =  new DateDialogFragment();
                dateDialogFragment.show(getSupportFragmentManager(),"Calendrier");
            }
        });
    }

    @Override
    public void validDateClick(Calendar date) {
        this.date = date;
        dateLayout.setText(date.get(Calendar.DAY_OF_MONTH)+"-"+date.get(Calendar.MONTH)+"-"+date.get(Calendar.YEAR));
    }
}