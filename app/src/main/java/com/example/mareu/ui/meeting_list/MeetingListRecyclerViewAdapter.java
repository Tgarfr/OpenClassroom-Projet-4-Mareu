package com.example.mareu.ui.meeting_list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mareu.R;
import com.example.mareu.model.Meeting;
import com.example.mareu.model.Participant;
import com.example.mareu.repository.MeetingRepository;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MeetingListRecyclerViewAdapter extends RecyclerView.Adapter<MeetingListRecyclerViewAdapter.ViewHolder> {

    MeetingRepository meetingRepository;
    Locale locale;
    Context context;

    public MeetingListRecyclerViewAdapter(MeetingRepository meetingRepository, Context context, Locale locale) {
        this.meetingRepository = meetingRepository;
        this.context = context;
        this.locale = locale;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_meeting, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Meeting meeting = meetingRepository.getMeetingList().get(position);

        if (meeting.getBeginDate().getTimeInMillis() < Calendar.getInstance().getTimeInMillis()) {
            holder.meetingCircleLayout.setColorFilter(ContextCompat.getColor(context, R.color.meeting_list_red_circle));
        } else {
            holder.meetingCircleLayout.setColorFilter(ContextCompat.getColor(context, R.color.meeting_list_green_circle));
        }

        final String hourDisplay = new SimpleDateFormat("HH:mm", locale).format(meeting.getBeginDate().getTimeInMillis());
        holder.meetingNameLayout.setText(meeting.getName()+" - "+hourDisplay+" - "+meeting.getRoom().getName());

        holder.meetingEmailsLayout.setText(participantEmailListDisplay(meeting));

        holder.meetingDeleteButtonLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                meetingRepository.deleteMeeting(meetingRepository.getMeetingList().get(position));
                notifyDataSetChanged();
            }
        });
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

    private String participantEmailListDisplay(Meeting meeting) {
        final List<Participant> participantList = meeting.getParticipantList();
        String participantEmailListDiplay = participantList.get(0).getEmail();
        for (int i = 1; i < participantList.size(); i++ ) {
            participantEmailListDiplay = participantEmailListDiplay+", "+participantList.get(i).getEmail();
        }
        return participantEmailListDiplay;
    }
}
