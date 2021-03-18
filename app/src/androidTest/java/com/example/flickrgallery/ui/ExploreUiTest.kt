package com.example.flickrgallery.ui


import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.flickrgallery.R
import com.example.flickrgallery.ui.common.BaseUiTest
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class ExploreUiTest : BaseUiTest() {

    @Test
    fun saveLocationWithDescription_checkRowExists_deleteRow() {
        val mockDescription = "Mock description"

        val fab = onView(withId(R.id.explore_fragment_fab))
        fab.perform(click())

        val inputText = onView(withId(R.id.input_text))
        inputText.perform(replaceText(mockDescription), closeSoftKeyboard())

        val saveStr = context.getString(R.string.save)
        val dialogSaveButton = onView(withText(saveStr))
        dialogSaveButton.perform(scrollTo(), click())

        val storedLocationsNavigationButton = onView(withId(R.id.storedLocationsFragment))
        storedLocationsNavigationButton.perform(click())

        val rowDescriptionText = onView(withId(R.id.description))
        rowDescriptionText.check(matches(withText(mockDescription)))

        val rowDeleteButton = onView(withId(R.id.delete_btn))
        rowDeleteButton.perform(click())

        rowDescriptionText.check(doesNotExist())
    }

    @Test
    fun saveLocationWithBlankDescription_checkRowExists_deleteRow() {
        val fab = onView(withId(R.id.explore_fragment_fab))
        fab.perform(click())

        val saveStr = context.getString(R.string.save)
        val dialogSaveButton = onView(withText(saveStr))
        dialogSaveButton.perform(scrollTo(), click())

        val storedLocationsNavigationButton = onView(withId(R.id.storedLocationsFragment))
        storedLocationsNavigationButton.perform(click())

        val defaultDescriptionStr = context.getString(R.string.stored_locations_item_blank_description)
        val rowDescriptionText = onView(withId(R.id.description))
        rowDescriptionText.check(matches(withText(defaultDescriptionStr)))

        val rowDeleteButton = onView(withId(R.id.delete_btn))
        rowDeleteButton.perform(click())

        rowDescriptionText.check(doesNotExist())
    }
}
