package com.example.alec.tunetrain;

import android.content.Intent;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.espresso.matcher.ViewMatchers.*;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.RunWith;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.isDialog;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class EspressoTests {

    @Before
    public void init(){
        Espresso.onView(withText("Spotify")).inRoot(isDialog()) // <---
                .check(matches(isDisplayed()))
                .perform(click());
    }

    @Rule
    public ActivityTestRule<TrainingActivity> activityRule
            = new ActivityTestRule<>(TrainingActivity.class);

    @Rule
    public ActivityTestRule<StatsActivity> statsRule
            = new ActivityTestRule<>(StatsActivity.class);

    @Test
    public void pressPad() {
        // Type text and then press the button.
        Espresso.onView(withId(R.id.pad1))
                .perform(ViewActions.click());
    }

    @Test
    public void expandPlaylists() {
        Espresso.onView(withId(R.id.playlist)).perform(ViewActions.click());
        Espresso.onView(withId(R.id.playlist_spinner)).check(matches(isDisplayed()));
    }

    @Test
    public void spamPad() {
        // Type text and then press the button.
        for (int x = 0; x < 15; x++) {
            Espresso.onView(withId(R.id.pad1))
                    .perform(ViewActions.click());
        }
    }

    @Test
    public void checkFavoriteNote() {
        Intent intent = new Intent();
        statsRule.launchActivity(intent);
        Espresso.onView(withId(R.id.favorite_note)).check(matches(withText("A")));
    }



}
