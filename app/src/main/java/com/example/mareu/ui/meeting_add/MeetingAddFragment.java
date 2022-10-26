package com.example.mareu.ui.meeting_add;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import com.example.mareu.R;
import com.example.mareu.model.Participant;
import com.example.mareu.model.Room;
import com.example.mareu.repository.MeetingRepository;
import com.example.mareu.repository.RoomRepository;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MeetingAddFragment extends Fragment implements View.OnClickListener {

    private String name;
    private Calendar beginTime;
    private Calendar endTime;
    private Room room;
    private List<Participant> participantList;

    private View view;
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

    public MeetingAddFragment() {
        name = null;
        beginTime = Calendar.getInstance();
        endTime = Calendar.getInstance();
        endTime.add(Calendar.MINUTE, 45);
        room = null;
        participantList = new ArrayList<Participant>();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_meeting, container, false);
        initLayout();
        setDataToViews();

        getParentFragmentManager().setFragmentResultListener("meetingAddDate", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle bundle) {
                Long timeInMillis = bundle.getLong("calendar");
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(timeInMillis);

                beginTime.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
                beginTime.set(Calendar.MONTH, calendar.get(Calendar.MONTH));
                beginTime.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH));
                beginTimeDateViewRefresh();
            }
        });
        getParentFragmentManager().setFragmentResultListener("meetingAddBeginHour", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle bundle) {
                Long timeInMillis = bundle.getLong("calendar");
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(timeInMillis);

                beginTime.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY));
                beginTime.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE));
                beginTimeHourViewRefresh();
                calendar.add(Calendar.MINUTE, 45);
                endTime.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY));
                endTime.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE));
                endTimeHourViewRefresh();
            }
        });
        getParentFragmentManager().setFragmentResultListener("meetingAddEndHour", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle bundle) {
                Long timeInMillis = bundle.getLong("calendar");
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(timeInMillis);

                endTime.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY));
                endTime.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE));
                endTimeHourViewRefresh();
            }
        });

        return view;
    }

    private void initLayout() {
        nameView = view.findViewById(R.id.add_meeting_name);
        beginTimeDateView = view.findViewById(R.id.add_meeting_date_button);
        beginTimeDateView.setOnClickListener(this);
        beginTimeHourView = view.findViewById(R.id.add_meeting_hour_button);
        beginTimeHourView.setOnClickListener(this);
        endTimeHourView = view.findViewById(R.id.add_meeting_endTime_button);
        endTimeHourView.setOnClickListener(this);
        roomView = view.findViewById(R.id.add_meeting_room_spinner);
        participantTitleView = view.findViewById(R.id.add_meeting_participant_title);
        participantListView = view.findViewById(R.id.add_meeting_participant_list_button);
        participantListView.setOnClickListener(this);
        participantEditTextView = view.findViewById(R.id.add_meeting_participant_add_EditText);
        participantAddView = view.findViewById(R.id.add_meeting_participant_add_button);
        participantAddView.setOnClickListener(this);
        validButtonView = view.findViewById(R.id.add_meeting_valid_button);
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
    public void onClick(View view) {
        if (view == beginTimeDateView) {
            DateDialogFragment dateDialogFragment =  new DateDialogFragment(beginTime.get(Calendar.DAY_OF_MONTH), beginTime.get(Calendar.MONTH), beginTime.get(Calendar.YEAR));
            dateDialogFragment.show(this.getParentFragmentManager(),"Calendrier");
        } else if (view == beginTimeHourView) {
            HourDialogFragment hourDialogFragment = new HourDialogFragment(beginTime.get(Calendar.HOUR_OF_DAY), beginTime.get(Calendar.MINUTE), HourDialogFragment.Tag.BEGINHOUR);
            hourDialogFragment.show(this.getParentFragmentManager(), "Hour");
        } else if (view == endTimeHourView) {
            HourDialogFragment hourDialogFragment = new HourDialogFragment(endTime.get(Calendar.HOUR_OF_DAY), endTime.get(Calendar.MINUTE), HourDialogFragment.Tag.ENDHOUR);
            hourDialogFragment.show(this.getParentFragmentManager(), "EndTime");
        } else if (view == participantListView) {
            ParticipantListDialogFragment contributorListDialogFragment = new ParticipantListDialogFragment(participantList);
            contributorListDialogFragment.show(this.getParentFragmentManager(), "EndTime");
        } else if (view == participantAddView) {
            String newParticipant = participantEditTextView.getText().toString();
            if (newParticipant.equals("")) {
                Toast.makeText(getActivity(), "Merci d'indiquer une adresse email", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(getActivity(), "Merci d'indiquer un nom de réunion'", Toast.LENGTH_SHORT).show();
        } else if (room == null) {
            Toast.makeText(getActivity(), "Merci de selectionner une salle", Toast.LENGTH_SHORT).show();
        } else if (participantList.size() == 0) {
            Toast.makeText(getActivity(), "Merci d'ajouter des participants", Toast.LENGTH_SHORT).show();
        } else {
            meetingRepository.addMeeting(name, beginTime, endTime, room, participantList);
            Toast.makeText(getActivity(), "Nouvelle réunion ajouté", Toast.LENGTH_SHORT).show();

            if (getResources().getBoolean(R.bool.landscapeMode)) {
                getParentFragmentManager().setFragmentResult("refreshMeetingList", null);
            }
            else {
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.activity_main_fragment_1, MeetingAddFragment.class, null)
                        .setReorderingAllowed(true)
                        .commit();
            }
        }
    }
}