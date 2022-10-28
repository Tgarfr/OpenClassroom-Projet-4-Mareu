package com.example.mareu.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.mareu.R;
import com.example.mareu.repository.MeetingRepository;
import com.example.mareu.ui.meeting_add.MeetingAddFragment;
import com.example.mareu.ui.meeting_list.MeetingListFragment;

public class MainActivity extends AppCompatActivity {

    MeetingRepository meetingRepository;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        meetingRepository = MeetingRepository.getInstance();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.sort_by_date) {
            meetingRepository.sortByDate();
        }
        if (item.getItemId() == R.id.sort_by_room) {
            meetingRepository.sortByRoom();
        }

        getSupportFragmentManager().setFragmentResult("refreshMeetingList", null);

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.activity_main_fragment_1);
        if (fragment instanceof MeetingAddFragment) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.activity_main_fragment_1, MeetingListFragment.class, null)
                    .setReorderingAllowed(true)
                    .commit();
        }
    }
}
