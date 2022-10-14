package com.example.mareu.ui.meeting_add;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mareu.R;
import com.example.mareu.model.Participant;

import java.util.List;

public class ParticipantListDialogFragment extends DialogFragment {

    private List<Participant> participantList;

    public ParticipantListDialogFragment(List<Participant> participantList) {
        this.participantList = participantList;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        View view = getLayoutInflater().inflate(R.layout.dialog_participant_list, null);
        RecyclerView recyclerView = view.findViewById(R.id.dialog_participant_list_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new ParticipantListDialogFragmentAdapter(participantList));

        Button buttonParticipantDialogLayout = view.findViewById(R.id.dialog_participant_button);
        buttonParticipantDialogLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        builder.setTitle("Liste des participants");

        builder.setView(view);
        return builder.create();
    }
}
