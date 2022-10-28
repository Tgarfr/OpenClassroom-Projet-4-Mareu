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

import java.util.Calendar;

public class HourDialogFragment extends DialogFragment {

    public enum Tag { BEGINHOUR, ENDHOUR };

    private TimePicker timePickerView;
    private int hourOfDay;
    private int minute;
    private final Tag tag;

    public HourDialogFragment(int hourOfDay, int minute, Tag tag) {
        this.hourOfDay = hourOfDay;
        this.minute = minute;
        this.tag = tag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        View view = getLayoutInflater().inflate(R.layout.dialog_hour, null);
        timePickerView = view.findViewById(R.id.dialog_hour);
        timePickerView.setIs24HourView(true);
        Button validateButton = view.findViewById(R.id.dialog_hour_button);
        setHourToTimePicker();

        timePickerView.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePickerView, int timePikerHourOfDay, int timePikerminute) {
                hourOfDay = timePikerHourOfDay;
                minute = timePikerminute;
            }
        });
        validateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                returHourToParentFragment();
                dismiss();
            }
        });

        builder.setView(view);
        return builder.create();
    }

    private void setHourToTimePicker() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            timePickerView.setHour(hourOfDay);
            timePickerView.setMinute(minute);
        }
        else {
            timePickerView.setCurrentHour(hourOfDay);
            timePickerView.setCurrentMinute(minute);
        }
    }

    private void returHourToParentFragment() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);

        Bundle bundle = new Bundle();
        bundle.putLong("calendar", calendar.getTimeInMillis());

        if (tag == Tag.BEGINHOUR) {
            getParentFragmentManager().setFragmentResult("meetingAddBeginHour", bundle);
        }
        else if (tag == Tag.ENDHOUR) {
            getParentFragmentManager().setFragmentResult("meetingAddEndHour", bundle);
        }
    }
}
