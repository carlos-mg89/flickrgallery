package com.example.flickrgallery.ui


import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withContentDescription
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.example.flickrgallery.R
import com.example.flickrgallery.ui.common.BaseUiTest
import org.junit.Test


class FromExploreToPhotoDetailsUiTest : BaseUiTest() {

    @Test
    fun checkContentDescriptionChangesAfterClickOnFab() {
        onView(withId(R.id.recyclerview)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                6,
                click()
            )
        )

        onView(withId(R.id.save_image_fab)).check(
            matches(withContentDescription(R.string.photo_details_save_btn))
        )

        onView(withId(R.id.save_image_fab)).perform(click())

        onView(withId(R.id.save_image_fab)).check(
            matches(withContentDescription(R.string.photo_details_unsave_btn))
        )
    }
}
