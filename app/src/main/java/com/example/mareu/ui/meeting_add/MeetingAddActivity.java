package com.example.mareu.ui.meeting_add;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mareu.R;
import com.example.mareu.model.Participant;
import com.example.mareu.model.Room;
import com.example.mareu.repository.MeetingRepository;
import com.example.mareu.repository.RoomRepository;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MeetingAddActivity extends AppCompatActivity implements DateDialogFragment.ValidDateListener, HourDialogFragment.ValidHourListener {

    private String name;
    private Calendar beginTime;
    private Calendar endTime;
    private Room room;
    private List<Participant> participantList;

    private EditText nameLayout;
    private Button beginTimeDateLayout;
    private Button beginTileHourLayout;
    private Button endTimeHourLayout;
    private Spinner roomLayout;
    private TextView participantTitleLayout;
    private Button participantListLayout;
    private EditText participantEditTextLayout;
    private Button participantAddLayout;
    private Button validButtonLayout;

    public MeetingAddActivity() {
        name = null;
        beginTime = Calendar.getInstance();
        endTime = Calendar.getInstance();
        endTime.add(Calendar.MINUTE, 45);
        room = null;
        participantList = new ArrayList<Participant>();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meeting);

        initLayout();
        initRoomSpinner();
        setDataToLayout();

        beginTimeDateLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateDialogFragment dateDialogFragment =  new DateDialogFragment(beginTime.get(Calendar.DAY_OF_MONTH), beginTime.get(Calendar.MONTH), beginTime.get(Calendar.YEAR));
                dateDialogFragment.show(getSupportFragmentManager(),"Calendrier");
            }
        });
        beginTileHourLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HourDialogFragment hourDialogFragment = new HourDialogFragment(beginTime.get(Calendar.HOUR_OF_DAY), beginTime.get(Calendar.MINUTE), HourDialogFragment.Tag.BEGINHOUR);
                hourDialogFragment.show(getSupportFragmentManager(), "Hour");
           }
        });
        endTimeHourLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HourDialogFragment hourDialogFragment = new HourDialogFragment(endTime.get(Calendar.HOUR_OF_DAY), endTime.get(Calendar.MINUTE), HourDialogFragment.Tag.ENDHOUR);
                hourDialogFragment.show(getSupportFragmentManager(), "EndTime");
            }
        });
        roomLayout.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                room = (Room) adapterView.getAdapter().getItem(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        participantListLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParticipantListDialogFragment contributorListDialogFragment = new ParticipantListDialogFragment(participantList);
                contributorListDialogFragment.show(getSupportFragmentManager(), "EndTime");
            }
        });
        participantAddLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newParticipant = participantEditTextLayout.getText().toString();
                if (newParticipant.equals("")) {
                    Toast.makeText(getApplicationContext(), "Merci d'indiquer une adresse email", Toast.LENGTH_SHORT).show();
                } else {
                    Participant participantAdd = new Participant(newParticipant);
                    participantList.add(participantAdd);
                    displayParticipantCount();
                }
                participantEditTextLayout.setText(null);
            }
        });
        validButtonLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MeetingRepository meetingRepository = MeetingRepository.getInstance();
                name = nameLayout.getText().toString();
                if (name.equals("")) {
                    Toast.makeText(getApplicationContext(), "Merci d'indiquer un nom de réunion'", Toast.LENGTH_SHORT).show();
                } else if (room == null) {
                    Toast.makeText(getApplicationContext(), "Merci de selectionner une salle", Toast.LENGTH_SHORT).show();
                } else if (participantList.size() == 0) {
                    Toast.makeText(getApplicationContext(), "Merci d'ajouter des participants", Toast.LENGTH_SHORT).show();
                } else {
                    meetingRepository.addMeeting(name, beginTime, endTime, room, participantList);
                    Toast.makeText(getApplicationContext(), "Nouvelle réunion ajouté", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }

    @Override
    public void validDateClick(int dayOfMonth, int month, int year) {
        beginTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        beginTime.set(Calendar.MONTH, month);
        beginTime.set(Calendar.YEAR, year);
        displayBeginTimeDate();
    }

    @Override
    public void validHourClick(int hourOfDay, int minute, HourDialogFragment.Tag tag) {
        if (tag == HourDialogFragment.Tag.BEGINHOUR) {
            beginTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            beginTime.set(Calendar.MINUTE, minute);
            displayBeginTimeHour();
        }
        else if (tag == HourDialogFragment.Tag.ENDHOUR) {
            endTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            endTime.set(Calendar.MINUTE, minute);
            displayEndTimeHour();
        }
    }

    private void initLayout() {
        nameLayout = findViewById(R.id.add_meeting_name);
        beginTimeDateLayout = findViewById(R.id.add_meeting_date_button);
        beginTileHourLayout = findViewById(R.id.add_meeting_hour_button);
        endTimeHourLayout = findViewById(R.id.add_meeting_endTime_button);
        roomLayout = findViewById(R.id.add_meeting_room_spinner);
        participantTitleLayout = findViewById(R.id.add_meeting_participant_title);
        participantListLayout = findViewById(R.id.add_meeting_participant_list_button);
        participantEditTextLayout = findViewById(R.id.add_meeting_participant_add_EditText);
        participantAddLayout = findViewById(R.id.add_meeting_participant_add_button);
        validButtonLayout = findViewById(R.id.add_meeting_valid_button);
    }
    private void initRoomSpinner() {
        RoomRepository roomRepositoryRepository = RoomRepository.getInstance();
        RoomSpinnerAdapter adapter = new RoomSpinnerAdapter(roomRepositoryRepository);
        roomLayout.setAdapter(adapter);
    }

    public void setDataToLayout() {
        displayName();
        displayBeginTimeDate();
        displayBeginTimeHour();
        displayEndTimeHour();
        displayParticipantCount();
    }
    public void displayName() {
        nameLayout.setText(name);
    }
    public void displayBeginTimeDate() {
        beginTimeDateLayout.setText(new SimpleDateFormat("d-M-y", getResources().getConfiguration().locale).format(beginTime.getTime()));
    }
    public void displayBeginTimeHour() {
        beginTimeHourLayout.setText(new SimpleDateFormat("HH : mm", getResources().getConfiguration().locale).format(beginTime.getTime()));
    }
    public void displayEndTimeHour() {
        endTimeHourLayout.setText(new SimpleDateFormat("HH : mm", getResources().getConfiguration().locale).format(endTime.getTime()));
    }
    public void displayParticipantCount() {
        participantTitleLayout.setText("Participants : "+ participantList.size());
    }

}