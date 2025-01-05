package com.example.myapplication;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.CoreMatchers.anything;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import business.CarHandler; // Import CarHandler
import presentation.MainActivity;

import android.util.Log;
import android.view.View;
import android.widget.ListView;

import static org.junit.Assert.assertEquals;


@RunWith(AndroidJUnit4.class)
public class TestBuyCar {

    @BeforeClass
    public static void setUp() {
        MainActivity.shouldClearData = true;
    }

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void testBuyButton() {
        // Get the instance of CarHandler
        CarHandler carHandler = new CarHandler();

        // Verify initial state
        int initialSize = carHandler.getAllCars().size();
        Log.d("Test", "Initial size: " + initialSize); // Add logging

        // Simulate user interaction to select the topmost car
        final int[] numberOfAdapterItems = new int[1];
        onView(withId(R.id.customListView)).check(matches(new TypeSafeMatcher<View>() {
            @Override
            public boolean matchesSafely(View view) {
                ListView listView = (ListView) view;

                //here we assume the adapter has been fully loaded already
                numberOfAdapterItems[0] = listView.getAdapter().getCount();
                return true;
            }
            @Override
            public void describeTo(Description description) {}
        }));

        onData(anything()).inAdapterView(withId(R.id.customListView)).atPosition(numberOfAdapterItems[0] - 1).perform(click());

        // Perform action
        onView(withId(R.id.buyButton)).perform(click());
        onView(withId(R.id.confirmBuy)).perform(click());

        // Verify final state
        int finalSize = carHandler.getAllCars().size();
        Log.d("Test", "Final size: " + finalSize); // Add logging
        assertEquals(initialSize - 1, finalSize);
    }
}