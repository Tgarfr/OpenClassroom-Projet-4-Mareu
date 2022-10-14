package com.example.mareu.ui.meeting_add;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.mareu.R;
import com.example.mareu.model.Participant;

import java.util.List;

public class ParticipantListDialogFragmentAdapter extends RecyclerView.Adapter<ParticipantListDialogFragmentAdapter.ViewHolder> {

    List<Participant> contributorsList;

    public ParticipantListDialogFragmentAdapter(List<Participant> contributorList) {
        this.contributorsList = contributorList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_dialog_participant_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.contributorEmailLayout.setText(contributorsList.get(position).getEmail());
    }

    @Override
    public int getItemCount() {
        return contributorsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView contributorEmailLayout = itemView.findViewById(R.id.item_participant_email);

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
