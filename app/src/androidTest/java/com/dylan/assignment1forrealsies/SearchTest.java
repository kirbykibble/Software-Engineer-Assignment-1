package com.dylan.assignment1forrealsies;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class SearchTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.dylan.assignment1forrealsies", appContext.getPackageName());
    }
    @Rule
    public ActivityTestRule<MainActivity> mainAct = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void searchTest() {
        onView(withId(R.id.commentInner)).perform(replaceText("testString")).check(matches(withText("testString")));
        onView(withId(R.id.SaveButton)).perform(click());
        onView(withId(R.id.NextButton)).perform(click());
        onView(withId(R.id.searchInner)).perform(replaceText("testString"));
        onView(withId(R.id.SearchButton)).perform(click());
        onView(withId(R.id.commentInner)).check(matches(withText("testString")));
    }
}
