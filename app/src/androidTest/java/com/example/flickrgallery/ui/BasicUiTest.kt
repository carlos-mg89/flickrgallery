package com.example.flickrgallery.ui


import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.rule.GrantPermissionRule
import com.example.flickrgallery.R
import com.example.flickrgallery.data.source.PhotosFlickerDb
import com.example.flickrgallery.ui.utils.fromJson
import com.jakewharton.espresso.OkHttp3IdlingResource
import okhttp3.mockwebserver.MockResponse
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.koin.test.KoinTest
import org.koin.test.get


class BasicUiTest : KoinTest {

    private lateinit var context: Context
    private val mockWebServerRule = MockWebServerRule()

    @get:Rule
    var testRule: RuleChain = RuleChain
        .outerRule(mockWebServerRule)
        .around(
            GrantPermissionRule.grant(
                "android.permission.ACCESS_FINE_LOCATION"
            )
        )
        .around(ActivityScenarioRule(MainActivity::class.java))

    @Before
    fun setup() {
        context = getApplicationContext()
        mockWebServerRule.server.enqueue(
            MockResponse().fromJson("nearby_photos.json")
        )

        val resource = OkHttp3IdlingResource.create("OkHttp", get<PhotosFlickerDb>().okHttpClient)
        IdlingRegistry.getInstance().register(resource)
    }

    @Test
    fun checkPhotosAreDisplayedInExploreFragment() {
        onView(withId(R.id.recyclerview)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                2,
                click()
            )
        )

        onView(withId(R.id.description_text))
            .check(matches(hasDescendant(withText("MEU"))))
    }
}
