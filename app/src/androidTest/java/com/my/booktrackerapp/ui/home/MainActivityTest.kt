package com.my.booktrackerapp.ui.home

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.my.booktrackerapp.R
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class MainActivityTest {

    @Test
    fun testFabAddBookIsDisplayedWhenActionListClicked() {
        // Launch MainActivity
        ActivityScenario.launch(MainActivity::class.java)

        // Click on the action_list menu item
        onView(withId(R.id.action_list)).perform(click())

        // Check if the fab_addBook is displayed in ListBookActivity
        onView(withId(R.id.fab_addBook)).check(matches(isDisplayed()))
    }
}