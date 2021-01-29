package com.appetiser.exam1.data

import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository module for handling data operations.
 *
 * Collecting from the Flows in [TrackDao] is main-safe.  Room supports Coroutines and moves the
 * query execution off of the main thread.
 */
@Singleton
class TrackRepository @Inject constructor(private val trackDao: TrackDao) {

    fun getAll() = trackDao.getAll()

    fun getOne(id: String) = trackDao.getOne(id)

    companion object {
        private const val NETWORK_PAGE_SIZE = 25
    }
}
