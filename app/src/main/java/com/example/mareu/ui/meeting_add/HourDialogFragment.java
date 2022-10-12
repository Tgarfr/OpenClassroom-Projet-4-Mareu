package com.example.mareu.ui.meeting_add;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.mareu.R;

public class HourDialogFragment extends DialogFragment {

    TimePicker timePicker;
    private int hourOfDay;
    private int minute;

    interface ValidHourListener {
        void validHourClick(int hourOfDay, int minute);
    }

    public HourDialogFragment(int hourOfDay, int minute) {
        this.hourOfDay = hourOfDay;
        this.minute = minute;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        View view = getLayoutInflater().inflate(R.layout.dialog_hour, null);
        timePicker = view.findViewById(R.id.dialog_hour);
        Button validateButton = view.findViewById(R.id.dialog_hour_button);
        setHourToTimePicker();

        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePickerView, int timePikerHourOfDay, int timePikerminute) {
                hourOfDay = timePikerHourOfDay;
                minute = timePikerminute;
            }
        });

        validateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ValidHourListener listener = (ValidHourListener) getActivity();
                listener.validHourClick(hourOfDay, minute);
                dismiss();
            }
        });

        builder.setView(view);
        return builder.create();
    }

    public void setHourToTimePicker() {
        timePicker.setCurrentHour(hourOfDay);
        timePicker.setCurrentMinute(minute);
    }
}
