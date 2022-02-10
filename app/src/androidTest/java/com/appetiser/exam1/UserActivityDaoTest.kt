package com.appetiser.exam1

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.asLiveData
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.appetiser.exam1.data.AppDatabase
import com.appetiser.exam1.data.UserActivity
import com.appetiser.exam1.data.UserActivityDao
import junit.framework.TestCase.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * This is not meant to be a full set of tests. For simplicity, most of your samples do not
 * include tests. However, when building the Room, it is helpful to make sure it works before
 * adding the UI.
 */
@RunWith(AndroidJUnit4::class)
class UserActivityDaoTest {
    private lateinit var userActivityDao: UserActivityDao
    private lateinit var mDb: AppDatabase
    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        mDb = Room.inMemoryDatabaseBuilder(
            context,
            AppDatabase::class.java
        ) // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()
        userActivityDao = mDb.userActivityDao()
    }

    @After
    fun closeDb() {
        mDb.close()
    }

    @Test
    fun getAll() {
        suspend {
            val uas = listOf(UserActivity("1"), UserActivity("2"))

            userActivityDao.insertAll(uas)
            val allUserActivities: List<UserActivity> =
                LiveDataTestUtil.getValue(userActivityDao.getAll().asLiveData()) ?: emptyList()
            assertEquals(allUserActivities[0].timestamp, uas[0].timestamp)
            assertEquals(allUserActivities[0].trackId, uas[0].trackId)
            assertEquals(allUserActivities[1].timestamp, uas[1].timestamp)
            assertEquals(allUserActivities[1].trackId, uas[1].trackId)
        }
    }

}
