package com.example.mareu.ui.meeting_list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mareu.R;
import com.example.mareu.model.Meeting;
import com.example.mareu.model.Participant;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MeetingListRecyclerViewAdapter extends RecyclerView.Adapter<MeetingListRecyclerViewAdapter.ViewHolder> {

    List<Meeting> displayMeetingList;
    Locale locale;
    Context context;
    OnMeetingClickListener onMeetingClickListener;

    interface OnMeetingClickListener {
        void onMeetingClick(int position);
    }

    public MeetingListRecyclerViewAdapter(List<Meeting> displayMeetingList, Context context, Locale locale, OnMeetingClickListener onMeetingClickListener) {
        this.displayMeetingList = displayMeetingList;
        this.context = context;
        this.locale = locale;
        this.onMeetingClickListener = onMeetingClickListener;
    }

    public void setMeetingList(List<Meeting> meetingList) {
        this.displayMeetingList = meetingList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_meeting, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Meeting meeting = displayMeetingList.get(position);

        if (meeting.getBeginDate().getTimeInMillis() < Calendar.getInstance().getTimeInMillis()) {
            holder.meetingCircleLayout.setColorFilter(ContextCompat.getColor(context, R.color.meeting_list_red_circle));
        } else {
            holder.meetingCircleLayout.setColorFilter(ContextCompat.getColor(context, R.color.meeting_list_green_circle));
        }

        final String hourDisplay = new SimpleDateFormat("HH:mm", locale).format(meeting.getBeginDate().getTimeInMillis());

        holder.meetingNameLayout.setText(String.format("%s - %s - %s", meeting.getName(), hourDisplay, meeting.getRoom().getName()));

        holder.meetingEmailsLayout.setText(participantEmailListDisplay(meeting));

        holder.meetingDeleteButtonLayout.setOnClickListener(view -> onMeetingClickListener.onMeetingClick(position));
    }

    @Override
    public int getItemCount() {
        return displayMeetingList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView meetingCircleLayout = itemView.findViewById(R.id.item_meeting_circle);
        public TextView meetingNameLayout = itemView.findViewById(R.id.item_meeting_name);
        public TextView meetingEmailsLayout = itemView.findViewById(R.id.item_meeting_emails);
        public ImageButton meetingDeleteButtonLayout = itemView.findViewById(R.id.item_meeting_delete_button);

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    private String participantEmailListDisplay(Meeting meeting) {
        final List<Participant> participantList = meeting.getParticipantList();
        StringBuilder participantEmailListDiplay = new StringBuilder(participantList.get(0).getEmail());
        for (int i = 1; i < participantList.size(); i++ ) {
            participantEmailListDiplay.append(", ").append(participantList.get(i).getEmail());
        }
        return participantEmailListDiplay.toString();
    }
}