package com.example.mareu.ui.meeting_list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mareu.R;
import com.example.mareu.model.Meeting;
import com.example.mareu.repository.MeetingRepository;
import com.example.mareu.ui.meeting_add.MeetingAddFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.Locale;

public class MeetingListFragment extends Fragment implements MeetingListRecyclerViewAdapter.OnMeetingClickListener {

    private MeetingRepository meetingRepository;
    private MeetingListRecyclerViewAdapter meetingListRecyclerViewAdapter;
    private List<Meeting> displayMeetingList;
    private FragmentManager fragmentManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_meeting, container, false);

        fragmentManager = getParentFragmentManager();
        Locale locale = getResources().getConfiguration().locale;

        meetingRepository = MeetingRepository.getInstance();
        displayMeetingList = meetingRepository.getMeetingList();

        RecyclerView recyclerView = view.findViewById(R.id.list_meetings);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        meetingListRecyclerViewAdapter = new MeetingListRecyclerViewAdapter(displayMeetingList, getActivity(), locale, this);
        recyclerView.setAdapter(meetingListRecyclerViewAdapter);

        if (!getResources().getBoolean(R.bool.landscapeMode)) {
            FloatingActionButton AddButton = view.findViewById(R.id.add_meeting_button);
            AddButton.setOnClickListener(AddButtonClick);
        }

        fragmentManager.setFragmentResultListener("refreshMeetingList", this, (requestKey, bundle) -> onResume());

        getParentFragmentManager().setFragmentResultListener("filterByDate", this, (String requestKey, Bundle bundle) -> filterByDate());
        getParentFragmentManager().setFragmentResultListener("filterByRoom", this, (String requestKey, Bundle bundle) -> filterByRoom());

        return view;
    }

    public void refreshList() {
        meetingListRecyclerViewAdapter.notifyDataSetChanged();
    }

    View.OnClickListener AddButtonClick = new View.OnClickListener() {
        public void onClick(View v) {
            fragmentManager.beginTransaction()
                    .replace(R.id.activity_main_fragment_1, MeetingAddFragment.class, null)
                    .setReorderingAllowed(true)
                    .commit();
        }
    };

    private void filterByDate() {
        meetingListRecyclerViewAdapter.setMeetingList(meetingRepository.sortByDate(displayMeetingList));
        refreshList();
    }

    private void filterByRoom() {
        meetingListRecyclerViewAdapter.setMeetingList(meetingRepository.sortByRoom(displayMeetingList));
        refreshList();
    }

    public void deleteMeeting(int position) {
        Meeting deleteMeeting = displayMeetingList.get(position);
        meetingRepository.deleteMeeting(deleteMeeting);
        displayMeetingList.remove(deleteMeeting);
        meetingListRecyclerViewAdapter.notifyItemRemoved(position);
    }

    @Override
    public void onMeetingClick(int position) {
        deleteMeeting(position);
    }
}