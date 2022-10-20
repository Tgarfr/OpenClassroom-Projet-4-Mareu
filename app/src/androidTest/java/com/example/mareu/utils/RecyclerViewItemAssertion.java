package com.example.mareu.utils;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.ViewAssertion;
import androidx.test.espresso.matcher.ViewMatchers;

import org.hamcrest.Matcher;

public class RecyclerViewItemAssertion implements ViewAssertion {
    int itemPosition;
    int testedViewId;
    Matcher<View> matcher;

    public RecyclerViewItemAssertion(int itemPosition, int testedViewIdInItem, Matcher<View> matcher) {
        this.itemPosition = itemPosition;
        this.testedViewId = testedViewIdInItem;
        this.matcher = matcher;
    }

    @Override
    public void check(View view, NoMatchingViewException noViewFoundException) {
        RecyclerView recyclerView = (RecyclerView)  view;
        RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForLayoutPosition(itemPosition);
        View childView = viewHolder.itemView.findViewById(testedViewId);
        ViewMatchers.assertThat(childView, matcher);
    }
}
