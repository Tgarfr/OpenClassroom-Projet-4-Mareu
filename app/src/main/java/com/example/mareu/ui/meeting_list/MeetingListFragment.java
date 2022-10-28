package com.example.mareu.ui.meeting_list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mareu.R;
import com.example.mareu.repository.MeetingRepository;
import com.example.mareu.ui.meeting_add.MeetingAddFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Locale;

public class MeetingListFragment extends Fragment {

    private RecyclerView recyclerView;
    private MeetingListRecyclerViewAdapter meetingListRecyclerViewAdapter;
    private MeetingRepository meetingRepository;
    private FragmentManager fragmentManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_meeting, container, false);

        Locale locale = getResources().getConfiguration().locale;
        meetingRepository = MeetingRepository.getInstance();
        fragmentManager = getParentFragmentManager();

        recyclerView = view.findViewById(R.id.list_meetings);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        meetingListRecyclerViewAdapter = new MeetingListRecyclerViewAdapter(meetingRepository, getActivity(), locale);
        recyclerView.setAdapter(meetingListRecyclerViewAdapter);

        if (getResources().getBoolean(R.bool.landscapeMode) == false) {
            FloatingActionButton AddButton = view.findViewById(R.id.add_meeting_button);
            AddButton.setOnClickListener(AddButtonClick);
        }

        fragmentManager.setFragmentResultListener("refreshMeetingList", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(String requestKey, Bundle bundle) {
                onResume();
            }
        });

        getParentFragmentManager().setFragmentResultListener("filterByDate", this, (String requestKey, Bundle bundle) -> { filterByDate(); } );
        getParentFragmentManager().setFragmentResultListener("filterByRoom", this, (String requestKey, Bundle bundle) -> { filterByRoom(); } );

        return view;
    }

    @Override
    public void onResume() {
        meetingListRecyclerViewAdapter.notifyDataSetChanged();
        super.onResume();
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
        meetingListRecyclerViewAdapter.filterByDate();
    }

    private void filterByRoom() {
        meetingListRecyclerViewAdapter.filterByRoom();
    }
}