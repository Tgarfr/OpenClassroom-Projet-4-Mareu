package com.example.mareu.ui.meeting_add;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;

import androidx.fragment.app.DialogFragment;

import com.example.mareu.R;

import java.util.Calendar;

public class DateDialogFragment extends DialogFragment {

    private Calendar date;

    interface ValidDateListener {
        void validDateClick(Calendar date);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        View view = getLayoutInflater().inflate(R.layout.dialog_calendar, null);
        CalendarView calendarView = view.findViewById(R.id.dialog_calendar_calendar);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
              @Override
              public void onSelectedDayChange(CalendarView calendarView, int year, int month, int dayOfMonth) {
                  date = Calendar.getInstance();
                  date.set(year, month, dayOfMonth);
                  ValidDateListener listener = (ValidDateListener) getActivity();
                  listener.validDateClick(date);
                  dismiss();
              }
        });

        builder.setView(view);
        return builder.create();
    }
}
