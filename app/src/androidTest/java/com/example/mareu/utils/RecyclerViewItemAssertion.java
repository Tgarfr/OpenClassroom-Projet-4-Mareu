package com.example.mareu.utils;

import static org.hamcrest.Matchers.not;

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
    private boolean matcherIs;

    public RecyclerViewItemAssertion(int itemPosition, int testedViewIdInItem, Matcher<View> matcher, boolean matcherIs) {
        this.itemPosition = itemPosition;
        this.testedViewId = testedViewIdInItem;
        this.matcher = matcher;
        this.matcherIs = matcherIs;
    }

    @Override
    public void check(View view, NoMatchingViewException noViewFoundException) {
        RecyclerView recyclerView = (RecyclerView)  view;
        RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForLayoutPosition(itemPosition);
        View childView = viewHolder.itemView.findViewById(testedViewId);

        if (matcherIs) {
            ViewMatchers.assertThat(childView, matcher);
        }
        else {
            ViewMatchers.assertThat(childView, not(matcher));
        }

    }
}
