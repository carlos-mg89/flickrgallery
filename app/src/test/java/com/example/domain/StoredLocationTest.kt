package com.example.domain

import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*


class StoredLocationTest {

    @Test
    fun `getting savedDateString renders a correct string from date`() {
        val storedLocation = StoredLocation()
        val date = GregorianCalendar(2021, 0, 31).time
        storedLocation.savedDate = date
        val expectedDateStr = "1/31/21, 12:00 AM"

        val actualDateStr = storedLocation.savedDateString

        assertEquals(expectedDateStr, actualDateStr)
    }
}