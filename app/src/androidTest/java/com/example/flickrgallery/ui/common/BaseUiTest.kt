package com.example.flickrgallery.ui.common

import EspressoIdlingResource
import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.IdlingRegistry
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.rule.GrantPermissionRule
import com.example.flickrgallery.ui.MainActivity
import okhttp3.mockwebserver.MockResponse
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.rules.RuleChain
import org.koin.test.KoinTest


open class BaseUiTest : KoinTest {

    companion object {
        const val photosJsonFile = "nearby_photos.json"
    }

    protected lateinit var context: Context
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
        context = ApplicationProvider.getApplicationContext()
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
        mockWebServerRule.server.enqueue(
            MockResponse().fromJson(photosJsonFile)
        )
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }
}