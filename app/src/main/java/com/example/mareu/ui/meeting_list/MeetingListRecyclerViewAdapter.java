package com.example.mareu.ui.meeting_list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mareu.R;
import com.example.mareu.repository.MeetingRepository;

public class MeetingListRecyclerViewAdapter extends RecyclerView.Adapter<MeetingListRecyclerViewAdapter.ViewHolder> {

    MeetingRepository meetingRepository;

    public MeetingListRecyclerViewAdapter(MeetingRepository meetingRepository) {
        this.meetingRepository = meetingRepository;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_meeting, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.meetingNameLayout.setText(meetingRepository.getMeetingList().get(position).getName());
    }

    @Override
    public int getItemCount() {
        return meetingRepository.countMeeting();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView meetingCircleLayout = itemView.findViewById(R.id.item_meeting_circle);
        public TextView meetingNameLayout = itemView.findViewById(R.id.item_meeting_name);
        public TextView meetingEmailsLayout = itemView.findViewById(R.id.item_meeting_emails);
        public ImageButton meetingDeleteButtonLayout = itemView.findViewById(R.id.item_meeting_delete_button);

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
