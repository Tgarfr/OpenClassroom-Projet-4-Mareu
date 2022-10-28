package com.example.mareu.ui.meeting_add;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import androidx.fragment.app.DialogFragment;

import com.example.mareu.R;

import java.util.Calendar;

public class DateDialogFragment extends DialogFragment {

    private DatePicker datePickerView;
    private final int dayOfMonth;
    private final int month;
    private final int year;

    public DateDialogFragment(int dayOfMonth, int month, int year) {
        this.dayOfMonth = dayOfMonth;
        this.month = month;
        this.year = year;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        View view = getLayoutInflater().inflate(R.layout.dialog_calendar, null);
        datePickerView = view.findViewById(R.id.dialog_date_picker);
        Button validateButton = view.findViewById(R.id.dialog_date_button);
        setDateToCalendarView();

        validateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                returDateToParentFragment();
                dismiss();
            }
        });

        builder.setView(view);
        return builder.create();
    }

    private void setDateToCalendarView() {
        Calendar date = Calendar.getInstance();
        date.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        date.set(Calendar.MONTH, month);
        date.set(Calendar.YEAR, year);
        datePickerView.updateDate(year, month, dayOfMonth);
    }

    private void returDateToParentFragment() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, datePickerView.getDayOfMonth());
        calendar.set(Calendar.MONTH, datePickerView.getMonth()+1);
        calendar.set(Calendar.YEAR, datePickerView.getYear());

        Bundle bundle = new Bundle();
        bundle.putLong("calendar", calendar.getTimeInMillis());
        getParentFragmentManager().setFragmentResult("meetingAddDate", bundle);
    }
}
