package com.example.flickrgallery.ui


import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import com.example.flickrgallery.R
import com.example.flickrgallery.ui.common.BaseUiTest
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.hamcrest.TypeSafeMatcher
import org.junit.Test


class FromExploreToStoredLocationsUiTest : BaseUiTest() {

    @Test
    fun saveStoredLocationAndSwipeToStoredLocationsTabToCheckRecyclerViewContents() {
        saveStoredLocationWithDescription()
        saveStoredLocationWithDescription()

        transitionNavigationFromExploreToStoredLocationsTest()

        onView(withId(R.id.recycler_view)).check(
            ViewAssertions.matches(hasChildCount(2))
        )

        onView(withId(R.id.recycler_view)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )
    }

    private fun saveStoredLocationWithDescription() {
        onView(withId(R.id.explore_fragment_fab)).perform(click())

        onView(withText(R.string.explore_location_dialog_input_title))

        onView(withId(R.id.input_text)).perform(
            typeText("A UI test StoredLocation")
        )

        onView(withText(R.string.save)).perform(click())
    }

    private fun transitionNavigationFromExploreToStoredLocationsTest() {
        val bottomNavigationItemView = onView(
            Matchers.allOf(
                withId(R.id.storedLocationsFragment),
                withContentDescription(R.string.stored_locations),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.bottom_navigation),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        bottomNavigationItemView.perform(click())
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
