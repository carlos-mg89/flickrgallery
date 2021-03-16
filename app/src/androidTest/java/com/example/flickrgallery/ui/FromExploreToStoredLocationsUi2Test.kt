package com.example.flickrgallery.ui


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers.*
import com.example.flickrgallery.R
import com.example.flickrgallery.ui.common.BaseUiTest
import com.example.flickrgallery.ui.common.TestUtils
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.hamcrest.TypeSafeMatcher
import org.junit.Test


class FromExploreToStoredLocationsUi2Test : BaseUiTest() {

    companion object {
        const val storedLocationDescription = "A UI test StoredLocation"
    }

    @Test
    fun swipeToStoredLocationsTabToCheckRecyclerViewContents() {
        saveStoredLocationWithDescription()

        transitionNavigationFromExploreToStoredLocationsTest()

        onView(TestUtils.withRecyclerView(R.id.recycler_view).atPosition(0))
            .check(
                ViewAssertions.matches(
                    hasDescendant(withText(storedLocationDescription))
                )
            )
    }

    private fun saveStoredLocationWithDescription() {
        onView(withId(R.id.explore_fragment_fab)).perform(click())

        onView(withText(R.string.explore_location_dialog_input_title))

        onView(withId(R.id.input_text)).perform(
            typeText(storedLocationDescription)
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
