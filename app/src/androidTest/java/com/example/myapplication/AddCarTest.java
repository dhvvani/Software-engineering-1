package com.example.myapplication;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewAction;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.assertion.ViewAssertions.matches;

import static org.hamcrest.CoreMatchers.anything;

import android.view.View;
import android.widget.ListView;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import presentation.MainActivity;

@RunWith(AndroidJUnit4.class)
public class AddCarTest
{
    @BeforeClass
    public static void setUp() {
        MainActivity.shouldClearData = true;
    }

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void createCar()
    {
        onView(withId(R.id.addCarButton)).perform(click());
        onView(withId(R.id.buttonWoutDealer)).perform(click());

        //add the car:
        onView(withId(R.id.make_ip)).perform(typeText("Range Rover"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.model_ip)).perform(typeText("evoque"));
        onView(withId(R.id.year_ip)).perform(typeText("2021"));
        onView(withId(R.id.km_ip)).perform(typeText("5000"));
        Espresso.closeSoftKeyboard();
        
        onView(withId(R.id.des_ip)).perform(click());
        onView(withId(R.id.des_ip)).perform(typeText("White, tiny scratch on boot"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.price_ip)).perform(click());
        onView(withId(R.id.price_ip)).perform(typeText("57600"));
        Espresso.closeSoftKeyboard();

        //Click on the transmission Spinner to open its dropdown and choose automatic

        onView(withId(R.id.trans_ip)).perform(click());
        onView(withText("automatic")).perform(click());

        /*leave the fuel type as gasoline*/

        //Click on the dealer Spinner to open its dropdown and choose Paul as the dealer
        onView(withId(R.id.dealer_ip)).perform(scrollTo());
        onView(withId(R.id.dealer_ip)).perform(click());
        onView(withText("Paul Rodney")).perform(click());

        //add the car to the market for sale
        
        onView(withId(R.id.create_car)).perform(click());

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

        //see that the make, model and description match
        onView(withId(R.id.text_make)).check(matches(withText("Range Rover")));
        onView(withId(R.id.text_model)).check(matches(withText("evoque")));
        onView(withId(R.id.text_description)).check(matches(withText("White, tiny scratch on boot")));
    }
}


