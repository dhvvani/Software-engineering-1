package com.example.myapplication;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.assertion.ViewAssertions.matches;

import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.Matchers.allOf;

import android.view.View;
import android.widget.ListView;

import androidx.test.ext.junit.rules.ActivityScenarioRule;

import presentation.MainActivity;

@RunWith(AndroidJUnit4.class)
public class CalculatorEMIToolTest
{
    @BeforeClass
    public static void setUp() {
        MainActivity.shouldClearData = true;
    }

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<>(MainActivity.class);
    @Test
    public void calculateTestWith0()
    {
        onData(anything())
                .inAdapterView(allOf(withId(R.id.customListView), isDisplayed()))
                .atPosition(1)
                .perform(click());

        //Fill the fields with information
        onView(withId(R.id.EMI_Downpayment)).perform(scrollTo());
        onView(withId(R.id.EMI_Downpayment)).perform(typeText("1000"));

        onView(withId(R.id.EMI_InterestRate)).perform(scrollTo());
        onView(withId(R.id.EMI_InterestRate)).perform(typeText("10"));

        onView(withId(R.id.EMI_LoanTerm)).perform(scrollTo());
        onView(withId(R.id.EMI_LoanTerm)).perform(typeText("0"));

        //Calculate value when loanTerm is 0
        onView(withId(R.id.EMI_Calculate)).perform(scrollTo());
        onView(withId(R.id.EMI_Calculate)).perform(click());

        //see that result matches the expected result
        onView(withId(R.id.EMI_Value)).perform(scrollTo());
        onView(withId(R.id.EMI_Value)).check(matches(withText("Loan term cannot be 0")));

    }


    @Test
    public void calculateTestWithValidValue()
    {

        onData(anything())
                .inAdapterView(allOf(withId(R.id.customListView), isDisplayed()))
                .atPosition(0)
                .perform(click());

        //Fill the fields with information
        onView(withId(R.id.EMI_Downpayment)).perform(scrollTo());
        onView(withId(R.id.EMI_Downpayment)).perform(typeText("9999"));

        onView(withId(R.id.EMI_InterestRate)).perform(scrollTo());
        onView(withId(R.id.EMI_InterestRate)).perform(typeText("10"));

        onView(withId(R.id.EMI_LoanTerm)).perform(scrollTo());
        onView(withId(R.id.EMI_LoanTerm)).perform(typeText("12"));

        //Calculate value when loanTerm is 0
        onView(withId(R.id.EMI_Calculate)).perform(scrollTo());
        onView(withId(R.id.EMI_Calculate)).perform(click());

        //see that result matches the expected result
        onView(withId(R.id.EMI_Value)).perform(scrollTo());
        onView(withId(R.id.EMI_Value)).check(matches(withText("$916.67")));
    }
}
