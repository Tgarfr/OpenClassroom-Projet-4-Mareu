package com.example.mareu.ui.meeting_list;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.mareu.R;
import com.example.mareu.model.Meeting;
import com.example.mareu.model.Room;
import com.example.mareu.repository.MeetingRepository;
import com.example.mareu.ui.meeting_add.MeetingAddActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;

public class MeetingListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MeetingRepository meetingRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_meeting);

        meetingRepository = MeetingRepository.getInstance();

        recyclerView = findViewById(R.id.list_meetings);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MeetingListRecyclerViewAdapter(meetingRepository));

        FloatingActionButton AddButton = findViewById(R.id.add_meeting_button);
        AddButton.setOnClickListener(AddButtonClick);
    }

    @Override
    protected void onResume() {
        super.onResume();
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    View.OnClickListener AddButtonClick = new View.OnClickListener() {
        public void onClick(View v) {
            Intent intent = new Intent(MeetingListActivity.this, MeetingAddActivity.class);
            ActivityCompat.startActivity(MeetingListActivity.this,intent,null);
        }
    };
}