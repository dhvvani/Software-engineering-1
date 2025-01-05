package com.example.myapplication;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;

import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.CoreMatchers.anything;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


import static androidx.test.espresso.action.ViewActions.longClick;

import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.TestCase.assertEquals;
import static org.hamcrest.Matchers.allOf;

import android.view.View;
import android.widget.ListView;

import presentation.CarActivity;
import presentation.FavoriteActivity;
import presentation.MainActivity;


@RunWith(AndroidJUnit4.class)

public class FavoriteSystemTest {

    @BeforeClass
    public static void setUp() {
        MainActivity.shouldClearData = true;
    }

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void addAndDeleteSystemTest() {
        //click into favorite page
        onView(withId(R.id.favoriteButton)).perform(click());
        pressBack();
        //click into car details page
        onData(anything())
                .inAdapterView(allOf(withId(R.id.customListView), isDisplayed()))
                .atPosition(1)
                .perform(click());

        //add to favorite
        onView(withId(R.id.addFavorite)).perform(click());
        pressBack();

        //go back to favorite page and check the favorite list

        onView(withId(R.id.favoriteButton)).perform(click());
        onData(anything())
                .inAdapterView(withId(R.id.favoriteList))
                .atPosition(0)
                .onChildView(withText("Toyota"))
                .check(matches(isDisplayed()));

        // assume long click remove the car from the favorite list
        onData(anything()).inAdapterView(withId(R.id.favoriteList)).atPosition(0).perform(longClick());

        //check it
        onView(withId(R.id.favoriteList)).check(matches(isDisplayed()));
        onView(withText("Toyota")).check(doesNotExist());
    }
}

