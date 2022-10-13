package com.example.mareu.ui.meeting_add;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.example.mareu.R;
import com.example.mareu.repository.RoomRepository;

import java.util.Calendar;

public class MeetingAddActivity extends AppCompatActivity implements DateDialogFragment.ValidDateListener, HourDialogFragment.ValidHourListener {

    private Button dateLayout;
    private Button hourLayout;
    private Button endTimeLayout;
    private Calendar date;
    private Calendar endTime;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meeting);
        date = Calendar.getInstance();
        endTime = Calendar.getInstance();
        endTime.add(Calendar.MINUTE, 45);

        EditText nameLayout = findViewById(R.id.add_meeting_name);
        dateLayout = findViewById(R.id.add_meeting_date_button);
        hourLayout = findViewById(R.id.add_meeting_hour_button);
        endTimeLayout = findViewById(R.id.add_meeting_endTime_button);
        Spinner roomLayout = findViewById(R.id.add_meeting_room_spinner);

        displayDate();
        displayHour();
        displayEndTime();

        RoomRepository roomRepositoryRepository = RoomRepository.getInstance();
        RoomSpinnerAdapter adapter = new RoomSpinnerAdapter(roomRepositoryRepository);
        roomLayout.setAdapter(adapter);

        dateLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateDialogFragment dateDialogFragment =  new DateDialogFragment(date.get(Calendar.DAY_OF_MONTH),date.get(Calendar.MONTH),date.get(Calendar.YEAR));
                dateDialogFragment.show(getSupportFragmentManager(),"Calendrier");
            }
        });
        hourLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HourDialogFragment hourDialogFragment = new HourDialogFragment(date.get(Calendar.HOUR_OF_DAY), date.get(Calendar.MINUTE), HourDialogFragment.Tag.BEGINHOUR);
                hourDialogFragment.show(getSupportFragmentManager(), "Hour");
           }
        });
        endTimeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HourDialogFragment hourDialogFragment = new HourDialogFragment(endTime.get(Calendar.HOUR_OF_DAY), endTime.get(Calendar.MINUTE), HourDialogFragment.Tag.ENDHOUR);
                hourDialogFragment.show(getSupportFragmentManager(), "EndTime");
            }
        });
    }

    @Override
    public void validDateClick(int dayOfMonth, int month, int year) {
        date.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        date.set(Calendar.MONTH, month);
        date.set(Calendar.YEAR, year);
        displayDate();
    }

    @Override
    public void validHourClick(int hourOfDay, int minute, HourDialogFragment.Tag tag) {
        if (tag == HourDialogFragment.Tag.BEGINHOUR) {
            date.set(Calendar.HOUR_OF_DAY, hourOfDay);
            date.set(Calendar.MINUTE, minute);
            displayHour();
        }
        else if (tag == HourDialogFragment.Tag.ENDHOUR) {
            endTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            endTime.set(Calendar.MINUTE, minute);
            displayEndTime();
        }
    }

    public void displayDate() {
        dateLayout.setText(date.get(Calendar.DAY_OF_MONTH)+"-"+date.get(Calendar.MONTH)+"-"+date.get(Calendar.YEAR));
    }
    public void displayHour() {
        hourLayout.setText(date.get(Calendar.HOUR_OF_DAY)+" : "+date.get(Calendar.MINUTE));
    }
    public void displayEndTime() {
        endTimeLayout.setText(endTime.get(Calendar.HOUR_OF_DAY)+" : "+endTime.get(Calendar.MINUTE));
    }

}