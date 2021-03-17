package com.example.flickrgallery.ui


import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import com.example.flickrgallery.R
import com.example.flickrgallery.ui.common.BaseUiTest
import com.example.flickrgallery.ui.common.EspressoTestsMatchers
import org.junit.Test


class FromExploreToPhotoDetailsUiTest : BaseUiTest() {

    @Test
    fun checkPhotoIsSavedAfterClickOnFab() {
        onView(withId(R.id.recyclerview)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                6,
                click()
            )
        )

        onView(withId(R.id.save_image_fab)).check(
            matches(EspressoTestsMatchers.withDrawable(R.drawable.photo_no_saved))
        )

        onView(withId(R.id.save_image_fab)).perform(click())

        onView(withId(R.id.save_image_fab)).check(
            matches(EspressoTestsMatchers.withDrawable(R.drawable.photo_saved))
        )
    }
}
