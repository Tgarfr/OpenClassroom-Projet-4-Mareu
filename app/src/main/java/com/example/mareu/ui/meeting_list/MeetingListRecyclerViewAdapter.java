package com.example.mareu.ui.meeting_list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.mareu.R;
import com.example.mareu.model.Participant;
import com.example.mareu.model.Room;
import com.example.mareu.repository.MeetingRepository;

import java.util.Calendar;
import java.util.List;

public class MeetingListRecyclerViewAdapter extends RecyclerView.Adapter<MeetingListRecyclerViewAdapter.ViewHolder> {

    MeetingRepository meetingRepository;

    public MeetingListRecyclerViewAdapter(MeetingRepository meetingRepository) {
        this.meetingRepository = meetingRepository;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_meeting, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.meetingNameLayout.setText(line1Display(position));
        holder.meetingEmailsLayout.setText(line2Display(position));

        holder.meetingDeleteButtonLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                meetingRepository.deleteMeeting(meetingRepository.getMeetingList().get(position));
                notifyItemRemoved(position);
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

    private String line1Display(int positionItem) {
        final String nameDisplay = meetingRepository.getMeetingList().get(positionItem).getName();
        final Calendar beginDate = meetingRepository.getMeetingList().get(positionItem).getBeginDate();
        final String hourDisplay = beginDate.get(Calendar.HOUR_OF_DAY)+"h"+beginDate.get(Calendar.MINUTE);
        final Room room = meetingRepository.getMeetingList().get(positionItem).getRoom();
        final String roomDiplay = room.getName();
        return nameDisplay+" - "+hourDisplay+" - "+roomDiplay;
    }

    private String line2Display(int positionItem) {
        final List<Participant> participantList = meetingRepository.getMeetingList().get(positionItem).getParticipantList();
        String participantEmailListDiplay = participantList.get(0).getEmail();
        for (int i = 1; i < participantList.size(); i++ ) {
            participantEmailListDiplay = participantEmailListDiplay+", "+participantList.get(i).getEmail();
        }
        return participantEmailListDiplay;
    }
}
