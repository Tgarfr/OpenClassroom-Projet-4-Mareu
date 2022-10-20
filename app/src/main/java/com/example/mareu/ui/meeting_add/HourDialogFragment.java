package com.example.mareu.ui.meeting_add;

import android.app.Dialog;
import android.os.Build;
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
    public enum Tag { BEGINHOUR, ENDHOUR };
    private final Tag tag;

    interface ValidHourDialogListener {
        void getHourDialogFragment(int hourOfDay, int minute, Tag tag);
    }

    public HourDialogFragment(int hourOfDay, int minute, Tag tag) {
        this.hourOfDay = hourOfDay;
        this.minute = minute;
        this.tag = tag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        View view = getLayoutInflater().inflate(R.layout.dialog_hour, null);
        timePicker = view.findViewById(R.id.dialog_hour);
        timePicker.setIs24HourView(true);
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
                ValidHourDialogListener listener = (ValidHourDialogListener) getActivity();
                listener.getHourDialogFragment(hourOfDay, minute, tag);
                dismiss();
            }
        });

        builder.setView(view);
        return builder.create();
    }

    public void setHourToTimePicker() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            timePicker.setHour(hourOfDay);
            timePicker.setMinute(minute);
        }
        else {
            timePicker.setCurrentHour(hourOfDay);
            timePicker.setCurrentMinute(minute);
        }
    }
}
