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

import androidx.fragment.app.Fragment;

import com.example.mareu.R;
import com.example.mareu.model.Participant;
import com.example.mareu.model.Room;
import com.example.mareu.repository.MeetingRepository;
import com.example.mareu.repository.RoomRepository;
import com.example.mareu.ui.meeting_list.MeetingListFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MeetingAddFragment extends Fragment  {

    private String meetingName;
    private Calendar meetingBeginCalendar;
    private Calendar meetingEndCalendar;
    private Room meetingRoom;
    private List<Participant> meetingParticipantList;

    private View view;
    private EditText nameEditText;
    private Button beginDateButton;
    private Button beginHourButton;
    private Button endHourButton;
    private Spinner roomSpinner;
    private TextView participantTitleTextView;
    private Button participantListButton;
    private EditText participantEditText;
    private Button participantAddButton;
    private Button meetingAddButton;

    public MeetingAddFragment() {
        meetingName = null;
        meetingBeginCalendar = Calendar.getInstance();
        meetingEndCalendar = Calendar.getInstance();
        meetingEndCalendar.add(Calendar.MINUTE, 45);
        meetingRoom = null;
        meetingParticipantList = new ArrayList<Participant>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_meeting, container, false);
        initLayout();
        setDataToViews();

        beginDateButton.setOnClickListener( (View view) -> { beginDateButtonClick(); } );
        beginHourButton.setOnClickListener( (View view) -> { beginHourButtonClick(); } );
        endHourButton.setOnClickListener( (View view) -> { endHourButtonClick(); } );
        participantListButton.setOnClickListener( (View view) -> { participantListButtonClick(); } );
        participantAddButton.setOnClickListener( (View view) -> { participantAddButtonClick(); } );
        meetingAddButton.setOnClickListener( (View view) -> { addNewMeetingToRepository(); } );

        getParentFragmentManager().setFragmentResultListener("meetingAddDate", this, (String requestKey, Bundle bundle) -> { getBeginDateFromDialog(bundle); } );
        getParentFragmentManager().setFragmentResultListener("meetingAddBeginHour", this, (String requestKey, Bundle bundle) -> { getBeginHourFromDialog(bundle); } );
        getParentFragmentManager().setFragmentResultListener("meetingAddEndHour", this, (String requestKey, Bundle bundle) -> { getEndHourFromDialog(bundle); } );

        return view;
    }

    private void initLayout() {
        nameEditText = view.findViewById(R.id.add_meeting_name);
        beginDateButton = view.findViewById(R.id.add_meeting_date_button);
        beginHourButton = view.findViewById(R.id.add_meeting_hour_button);
        endHourButton = view.findViewById(R.id.add_meeting_endTime_button);
        roomSpinner = view.findViewById(R.id.add_meeting_room_spinner);
        participantTitleTextView = view.findViewById(R.id.add_meeting_participant_title);
        participantListButton = view.findViewById(R.id.add_meeting_participant_list_button);
        participantEditText = view.findViewById(R.id.add_meeting_participant_add_EditText);
        participantAddButton = view.findViewById(R.id.add_meeting_participant_add_button);
        meetingAddButton = view.findViewById(R.id.add_meeting_valid_button);
        roomSpinnerDisplay();
    }

    private void roomSpinnerDisplay() {
        RoomRepository roomRepositoryRepository = RoomRepository.getInstance();
        RoomSpinnerAdapter adapter = new RoomSpinnerAdapter(roomRepositoryRepository);
        roomSpinner.setAdapter(adapter);
        roomSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                meetingRoom = (Room) adapterView.getAdapter().getItem(i);
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
        nameEditText.setText(meetingName);
    }

    public void beginTimeDateViewRefresh() {
        beginDateButton.setText(new SimpleDateFormat("d-M-y", getResources().getConfiguration().locale).format(meetingBeginCalendar.getTime()));
    }

    public void beginTimeHourViewRefresh() {
        beginHourButton.setText(new SimpleDateFormat("HH : mm", getResources().getConfiguration().locale).format(meetingBeginCalendar.getTime()));
    }

    public void endTimeHourViewRefresh() {
        endHourButton.setText(new SimpleDateFormat("HH : mm", getResources().getConfiguration().locale).format(meetingEndCalendar.getTime()));
    }

    public void participantCountViewRefresh() {
        participantTitleTextView.setText("Participants : "+ meetingParticipantList.size());
    }

    private void getBeginDateFromDialog(Bundle bundle) {
        long timeInMillis = bundle.getLong("calendar");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeInMillis);

        meetingBeginCalendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
        meetingBeginCalendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH));
        meetingBeginCalendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH));
        beginTimeDateViewRefresh();
    }

    private void getBeginHourFromDialog(Bundle bundle) {
        long timeInMillis = bundle.getLong("calendar");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeInMillis);

        meetingBeginCalendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY));
        meetingBeginCalendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE));
        beginTimeHourViewRefresh();
        calendar.add(Calendar.MINUTE, 45);
        meetingEndCalendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY));
        meetingEndCalendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE));
        endTimeHourViewRefresh();
    }

    private void getEndHourFromDialog(Bundle bundle) {
        long timeInMillis = bundle.getLong("calendar");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeInMillis);

        meetingEndCalendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY));
        meetingEndCalendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE));
        endTimeHourViewRefresh();
    }

    private void beginDateButtonClick() {
        DateDialogFragment dateDialogFragment =  new DateDialogFragment(meetingBeginCalendar.get(Calendar.DAY_OF_MONTH), meetingBeginCalendar.get(Calendar.MONTH), meetingBeginCalendar.get(Calendar.YEAR));
        dateDialogFragment.show(this.getParentFragmentManager(),"Calendrier");
    }

    private void beginHourButtonClick() {
        HourDialogFragment hourDialogFragment = new HourDialogFragment(meetingBeginCalendar.get(Calendar.HOUR_OF_DAY), meetingBeginCalendar.get(Calendar.MINUTE), HourDialogFragment.Tag.BEGINHOUR);
        hourDialogFragment.show(this.getParentFragmentManager(), "Hour");
    }

    private void endHourButtonClick() {
        HourDialogFragment hourDialogFragment = new HourDialogFragment(meetingEndCalendar.get(Calendar.HOUR_OF_DAY), meetingEndCalendar.get(Calendar.MINUTE), HourDialogFragment.Tag.ENDHOUR);
        hourDialogFragment.show(this.getParentFragmentManager(), "EndTime");
    }

    private void participantListButtonClick() {
        ParticipantListDialogFragment contributorListDialogFragment = new ParticipantListDialogFragment(meetingParticipantList);
        contributorListDialogFragment.show(this.getParentFragmentManager(), "EndTime");
    }

    private void participantAddButtonClick() {
        String newParticipant = participantEditText.getText().toString();
        if (!verifyEmail(newParticipant)) {
            Toast.makeText(getActivity(), R.string.meeting_add_email_false, Toast.LENGTH_SHORT).show();
        } else {
            Participant participantAdd = new Participant(newParticipant);
            meetingParticipantList.add(participantAdd);
            participantCountViewRefresh();
            participantEditText.setText(null);
        }
    }

    private boolean verifyEmail(String email) {
        String emailPattern = "^[a-zA-Z0-9._-]+@[a-zA-Z0-9._-]+\\.[a-zA-Z0-9]+$";
        if (email.matches(emailPattern)) {
            return true;
        } else {
            return false;
        }
    }

    private void addNewMeetingToRepository() {
        MeetingRepository meetingRepository = MeetingRepository.getInstance();
        meetingName = nameEditText.getText().toString();
        if (meetingName.equals("")) {
            Toast.makeText(getActivity(), "Merci d'indiquer un nom de réunion'", Toast.LENGTH_SHORT).show();
        } else if (meetingRoom == null) {
            Toast.makeText(getActivity(), "Merci de selectionner une salle", Toast.LENGTH_SHORT).show();
        } else if (meetingParticipantList.size() == 0) {
            Toast.makeText(getActivity(), "Merci d'ajouter des participants", Toast.LENGTH_SHORT).show();
        } else {
            meetingRepository.addMeeting(meetingName, meetingBeginCalendar, meetingEndCalendar, meetingRoom, meetingParticipantList);
            Toast.makeText(getActivity(), "Nouvelle réunion ajouté", Toast.LENGTH_SHORT).show();

            if (getResources().getBoolean(R.bool.landscapeMode)) {
                getParentFragmentManager().setFragmentResult("refreshMeetingList", null);
            }
            else {
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.activity_main_fragment_1, MeetingListFragment.class, null)
                        .setReorderingAllowed(true)
                        .commit();
            }
        }
    }
}