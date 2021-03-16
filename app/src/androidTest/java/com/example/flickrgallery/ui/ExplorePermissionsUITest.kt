package com.example.flickrgallery.ui

import android.content.Context
import android.os.Build
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiSelector
import com.example.flickrgallery.R
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class ExplorePermissionsUITest {

    private lateinit var context: Context

    @get:Rule
    var testRule: RuleChain = RuleChain
        .outerRule(ActivityScenarioRule(MainActivity::class.java))

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
    }

    @Test
    fun appStarts_PermissionsDenied_SnackBarShown() {
        denyPermission()

        val snackBarText = context.getString(R.string.location_permission_denied)
        val dialogSaveButton = Espresso.onView(ViewMatchers.withText(snackBarText))
        dialogSaveButton.check(matches(isDisplayed()))
    }

    private fun denyPermission() {
        val instrumentation = InstrumentationRegistry.getInstrumentation()
        if (Build.VERSION.SDK_INT >= 23) {
            val denyPermission = UiDevice.getInstance(instrumentation).findObject(
                UiSelector().text(
                    if (Build.VERSION.SDK_INT in 24..28) "DENY" else "Deny"
            ))
            if (denyPermission.exists()) {
                denyPermission.click()
            }
        }
    }
}