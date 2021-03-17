package com.example.flickrgallery.ui


import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.example.flickrgallery.R
import com.example.flickrgallery.ui.common.BaseUiTest
import org.junit.Test


class BasicUiTest : BaseUiTest() {

    @Test
    fun checkPhotosAreDisplayedInExploreFragment() {
        onView(withId(R.id.recyclerview)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                2,
                click()
            )
        )
    }
}
