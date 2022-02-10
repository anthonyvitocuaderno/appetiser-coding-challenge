package com.appetiser.exam1

import com.appetiser.exam1.data.UserActivity
import junit.framework.Assert.*
import org.junit.Before
import org.junit.Test

class UserActivityTest {
    private lateinit var userActivity: UserActivity

    @Before
    fun setup() {
        userActivity = UserActivity("1014147187")
    }

    @Test
    fun test_default_values() {
        val default = UserActivity()
        assertEquals(null, default.trackId)
        assertTrue("invalid timestamp", default.timestamp <= System.currentTimeMillis())
    }

    @Test
    fun test_getDateTimeString() {
        assertNotNull(userActivity.getDateTimeString())
        assertNotSame("", userActivity.getDateTimeString())
    }
}
