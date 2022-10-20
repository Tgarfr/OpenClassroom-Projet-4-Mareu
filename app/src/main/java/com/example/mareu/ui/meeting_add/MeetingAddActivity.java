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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MeetingAddActivity extends AppCompatActivity implements DateDialogFragment.ValidDateDialogListener, HourDialogFragment.ValidHourDialogListener, View.OnClickListener {

    private String name;
    private Calendar beginTime;
    private Calendar endTime;
    private Room room;
    private List<Participant> participantList;

    private EditText nameView;
    private Button beginTimeDateView;
    private Button beginTimeHourView;
    private Button endTimeHourView;
    private Spinner roomView;
    private TextView participantTitleView;
    private Button participantListView;
    private EditText participantEditTextView;
    private Button participantAddView;
    private Button validButtonView;

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
        setDataToViews();
    }

    private void initLayout() {
        nameView = findViewById(R.id.add_meeting_name);
        beginTimeDateView = findViewById(R.id.add_meeting_date_button);
        beginTimeDateView.setOnClickListener(this);
        beginTimeHourView = findViewById(R.id.add_meeting_hour_button);
        beginTimeHourView.setOnClickListener(this);
        endTimeHourView = findViewById(R.id.add_meeting_endTime_button);
        endTimeHourView.setOnClickListener(this);
        roomView = findViewById(R.id.add_meeting_room_spinner);
        participantTitleView = findViewById(R.id.add_meeting_participant_title);
        participantListView = findViewById(R.id.add_meeting_participant_list_button);
        participantListView.setOnClickListener(this);
        participantEditTextView = findViewById(R.id.add_meeting_participant_add_EditText);
        participantAddView = findViewById(R.id.add_meeting_participant_add_button);
        participantAddView.setOnClickListener(this);
        validButtonView = findViewById(R.id.add_meeting_valid_button);
        validButtonView.setOnClickListener(this);
        roomSpinnerDisplay();
    }

    private void roomSpinnerDisplay() {
        RoomRepository roomRepositoryRepository = RoomRepository.getInstance();
        RoomSpinnerAdapter adapter = new RoomSpinnerAdapter(roomRepositoryRepository);
        roomView.setAdapter(adapter);
        roomView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                room = (Room) adapterView.getAdapter().getItem(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void setDataToViews() {
        nameViewRefresh();
        beginTimeDateViewRefresh();
        beginTimeHourViewRefresh();
        endTimeHourViewRefresh();
        participantCountViewRefresh();
    }

    public void nameViewRefresh() {
        nameView.setText(name);
    }

    public void beginTimeDateViewRefresh() {
        beginTimeDateView.setText(new SimpleDateFormat("d-M-y", getResources().getConfiguration().locale).format(beginTime.getTime()));
    }

    public void beginTimeHourViewRefresh() {
        beginTimeHourView.setText(new SimpleDateFormat("HH : mm", getResources().getConfiguration().locale).format(beginTime.getTime()));
    }

    public void endTimeHourViewRefresh() {
        endTimeHourView.setText(new SimpleDateFormat("HH : mm", getResources().getConfiguration().locale).format(endTime.getTime()));
    }

    public void participantCountViewRefresh() {
        participantTitleView.setText("Participants : "+ participantList.size());
    }

    @Override
    public void getDateDialogFragment(int dayOfMonth, int month, int year) {
        beginTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        beginTime.set(Calendar.MONTH, month);
        beginTime.set(Calendar.YEAR, year);
        beginTimeDateViewRefresh();
    }

    @Override
    public void getHourDialogFragment(int hourOfDay, int minute, HourDialogFragment.Tag tag) {
        if (tag == HourDialogFragment.Tag.BEGINHOUR) {
            beginTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            beginTime.set(Calendar.MINUTE, minute);
            beginTimeHourViewRefresh();
            endTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            endTime.set(Calendar.MINUTE, minute+45);
            endTimeHourViewRefresh();
        }
        else if (tag == HourDialogFragment.Tag.ENDHOUR) {
            endTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            endTime.set(Calendar.MINUTE, minute);
            endTimeHourViewRefresh();
        }
    }

    @Override
    public void onClick(View view) {
        if (view == beginTimeDateView) {
            DateDialogFragment dateDialogFragment =  new DateDialogFragment(beginTime.get(Calendar.DAY_OF_MONTH), beginTime.get(Calendar.MONTH), beginTime.get(Calendar.YEAR));
            dateDialogFragment.show(getSupportFragmentManager(),"Calendrier");
        } else if (view == beginTimeHourView) {
            HourDialogFragment hourDialogFragment = new HourDialogFragment(beginTime.get(Calendar.HOUR_OF_DAY), beginTime.get(Calendar.MINUTE), HourDialogFragment.Tag.BEGINHOUR);
            hourDialogFragment.show(getSupportFragmentManager(), "Hour");
        } else if (view == endTimeHourView) {
            HourDialogFragment hourDialogFragment = new HourDialogFragment(endTime.get(Calendar.HOUR_OF_DAY), endTime.get(Calendar.MINUTE), HourDialogFragment.Tag.ENDHOUR);
            hourDialogFragment.show(getSupportFragmentManager(), "EndTime");
        } else if (view == participantListView) {
            ParticipantListDialogFragment contributorListDialogFragment = new ParticipantListDialogFragment(participantList);
            contributorListDialogFragment.show(getSupportFragmentManager(), "EndTime");
        } else if (view == participantAddView) {
            String newParticipant = participantEditTextView.getText().toString();
            if (newParticipant.equals("")) {
                Toast.makeText(getApplicationContext(), "Merci d'indiquer une adresse email", Toast.LENGTH_SHORT).show();
            } else {
                Participant participantAdd = new Participant(newParticipant);
                participantList.add(participantAdd);
                participantCountViewRefresh();
            }
            participantEditTextView.setText(null);
        } else if (view == validButtonView) {
            addNewMeetingToRepository();
        }
    }

    public void addNewMeetingToRepository() {
        MeetingRepository meetingRepository = MeetingRepository.getInstance();
        name = nameView.getText().toString();
        if (name == "") {
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
}