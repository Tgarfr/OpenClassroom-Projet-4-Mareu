package com.example.mareu.ui.meeting_add;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;

import androidx.fragment.app.DialogFragment;

import com.example.mareu.R;

import java.util.Calendar;

public class DateDialogFragment extends DialogFragment {

    CalendarView calendarView;
    private int dayOfMonth;
    private int month;
    private int year;

    interface ValidDateListener {
        void validDateClick(int dayOfMonth, int month, int year);
    }

    public DateDialogFragment(int dayOfMonth, int month, int year) {
        this.dayOfMonth = dayOfMonth;
        this.month = month;
        this.year = year;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        View view = getLayoutInflater().inflate(R.layout.dialog_calendar, null);
        calendarView = view.findViewById(R.id.dialog_calendar);
        Button validateButton = view.findViewById(R.id.dialog_date_button);
        setDateToCalendarView();

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
              @Override
              public void onSelectedDayChange(CalendarView calendarView, int calendarViewYear, int calendarViewMonth, int calendarViewDayOfMonth) {
                  dayOfMonth = calendarViewDayOfMonth;
                  month = calendarViewMonth;
                  year = calendarViewYear;
              }
        });

        validateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ValidDateListener listener = (ValidDateListener) getActivity();
                listener.validDateClick(dayOfMonth, month, year);
                dismiss();
            }
        });

        builder.setView(view);
        return builder.create();
    }

    public void setDateToCalendarView() {
        Calendar date = Calendar.getInstance();
        date.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        date.set(Calendar.MONTH, month);
        date.set(Calendar.YEAR, year);
        calendarView.setDate(date.getTimeInMillis());
    }
}
