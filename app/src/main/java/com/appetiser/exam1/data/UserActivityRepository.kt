package com.appetiser.exam1.data

import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository module for handling data operations.
 *
 * Collecting from the Flows in [UserActivityDao] is main-safe.  Room supports Coroutines and moves the
 * query execution off of the main thread.
 */
@Singleton
class UserActivityRepository @Inject constructor(private val userActivityDao: UserActivityDao) {
    // TODO BASE
    fun getAll() = userActivityDao.getAll()

    fun getLatest(trackId: String?) = userActivityDao.getLatest(trackId)

    fun getLatest() = userActivityDao.getLatest()

    suspend fun save(trackId: String? = null) {
        val list = ArrayList<UserActivity>()
        list.add(UserActivity(trackId))
        userActivityDao.insertAll(list)
    }
}