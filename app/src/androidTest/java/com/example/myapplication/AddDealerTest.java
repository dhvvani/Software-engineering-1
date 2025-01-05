package com.example.myapplication;

import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static androidx.test.espresso.Espresso.onView;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.action.ViewActions.typeText;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.espresso.matcher.ViewMatchers;
import android.widget.AdapterView;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


import presentation.MainActivity;

@RunWith(AndroidJUnit4.class)
public class AddDealerTest
{
    @BeforeClass
    public static void setUp() {
        MainActivity.shouldClearData = true;
    }

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void createDealer()
    {
        onView(withId(R.id.addCarButton)).perform(click());
        onView(withId(R.id.buttonWDealer)).perform(click());

        //add the new dealer
        onView(withId(R.id.firstName)).perform(typeText("Barbra"));
        onView(withId(R.id.lastName)).perform(typeText("Malibu"));
        onView(withId(R.id.number)).perform(typeText("+18005248697"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.email)).perform(click());
        onView(withId(R.id.email)).perform(typeText("barbie59@gmail.com"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.dealer_add)).perform(click());

        //verify that the dealer was added
        //click on add car wout new dealer
        onView(withId(R.id.buttonWoutDealer)).perform(click());


        //click on the dealer spinner
        onView(withId(R.id.dealer_ip)).perform(scrollTo());
        onView(withId(R.id.dealer_ip)).perform(click());

        //see if there is a text that matches Barbra Malibu
        //click on Barbra as a dealer in the available options of the spinner
        Espresso.onData(Matchers.allOf(Matchers.is(Matchers.instanceOf(String.class)), Matchers.is("Barbra Malibu")))
                .inAdapterView(Matchers.allOf(ViewMatchers.isAssignableFrom(AdapterView.class), ViewMatchers.isDisplayed()))
                .perform(ViewActions.click());

        //check if the selected option matches the expected text
        Espresso.onView(ViewMatchers.withId(R.id.dealer_ip))
                .check(matches(withSpinnerText("Barbra Malibu")));
    }

}
