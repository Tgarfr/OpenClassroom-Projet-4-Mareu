package com.example.mareu.utils;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.ViewAssertion;

import com.example.mareu.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RecyclerViewComparatorAssertion implements ViewAssertion {
    int itemPosition1;
    int itemPosition2;
    public enum SortBy { ROOM, DATE };
    private SortBy sortBy;

    public RecyclerViewComparatorAssertion(int itemPosition1, int itemPosition2, SortBy sortBy) {
        this.itemPosition1 = itemPosition1;
        this.itemPosition2 = itemPosition2;
        this.sortBy = sortBy;
    }

    @Override
    public void check(View view, NoMatchingViewException noViewFoundException) {
        RecyclerView recyclerView = (RecyclerView)  view;
        RecyclerView.ViewHolder viewHolder1 = recyclerView.findViewHolderForLayoutPosition(itemPosition1);
        RecyclerView.ViewHolder viewHolder2 = recyclerView.findViewHolderForLayoutPosition(itemPosition2);
        TextView childView1 = viewHolder1.itemView.findViewById(R.id.item_meeting_name);
        TextView childView2 = viewHolder2.itemView.findViewById(R.id.item_meeting_name);
        String item1Line = childView1.getText().toString();
        String Item2Line = childView2.getText().toString();
        String[] item1LineArray = item1Line.split(" - ");
        String[] item2LineArray = Item2Line.split(" - ");

        if (sortBy == SortBy.DATE) {
           sortByDateCheck(item1LineArray[1], item2LineArray[1]);
        }
        else if (sortBy == SortBy.ROOM) {
            sortByRoomCheck(item1LineArray[2], item2LineArray[2]);
        }
        else fail("Sort error");
    }

    private void sortByDateCheck(String string1, String string2) {
        Date hour1 = null;
        Date hour2 = null;
        try {
            hour1 = new SimpleDateFormat("HH:mm").parse(string1);
            hour2 = new SimpleDateFormat("HH:mm").parse(string2);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (hour1 == null || hour2 == null) {
            fail("Sort error");
        } else if (hour1.getTime() <= hour2.getTime() ) {
            assertTrue(true);
        } else {
            fail("Meeting date sort error");
        }
    }

    private void sortByRoomCheck(String string1, String string2) {
        int room1 = Integer.parseInt(string1);
        int room2 = Integer.parseInt(string2);

        if (room1 <= room2 ) {
            assertTrue(true);
        }
        else {
            fail("Meeting room sort error");
        }
    }
}
