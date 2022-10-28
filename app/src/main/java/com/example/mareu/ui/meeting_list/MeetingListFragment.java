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
    private MeetingRepository meetingRepository;
    private View view;
    private FragmentManager fragmentManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_list_meeting, container, false);

        Locale locale = getResources().getConfiguration().locale;
        meetingRepository = MeetingRepository.getInstance();
        fragmentManager = getParentFragmentManager();

        recyclerView = view.findViewById(R.id.list_meetings);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new MeetingListRecyclerViewAdapter(meetingRepository, getActivity(), locale));

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

        return view;
    }

    @Override
    public void onResume() {
        recyclerView.getAdapter().notifyDataSetChanged();
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
}