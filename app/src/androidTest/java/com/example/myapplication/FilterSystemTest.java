package com.example.myapplication;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;

import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.CoreMatchers.anything;

import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;




import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

import android.widget.ListView;


import business.CarHandler;
import presentation.MainActivity;

@RunWith(AndroidJUnit4.class)
public class FilterSystemTest {

    @BeforeClass
    public static void setUp() {
        MainActivity.shouldClearData = true;
    }

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<>(MainActivity.class);
    @Test
    public void testFilter(){
        //go to filter page
        onView(withId(R.id.filterButton)).perform(click());

        //select make type

        onView(withId(R.id.make)).perform(click());
        onData(anything()).atPosition(0).perform(click());

        // Select model
        onView(withId(R.id.model)).perform(click());
        onData(anything()).atPosition(0).perform(click());

        // Select a transmission type
        onView(withId(R.id.trans)).perform(click());
        onData(anything()).atPosition(0).perform(click());

        // Select a fuel type
        onView(withId(R.id.fuel)).perform(click());
        onData(anything()).atPosition(0).perform(click());

        // Enter year and km in EditTexts
        onView(withId(R.id.yearSearch)).perform(typeText("1"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.kmSearch)).perform(typeText("1000000"), ViewActions.closeSoftKeyboard());

        // Click on search button
        onView(withId(R.id.search)).perform(click());

        //check the result

        onData(anything())
                .inAdapterView(withId(R.id.resultList))
                .atPosition(0)
                .onChildView(withText("Honda"))
                .check(matches(isDisplayed()));
    }


}
