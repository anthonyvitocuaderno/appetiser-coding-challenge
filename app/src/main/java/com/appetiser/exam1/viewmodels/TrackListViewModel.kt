package com.appetiser.exam1.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.appetiser.exam1.data.Track
import com.appetiser.exam1.data.TrackRepository
import com.appetiser.exam1.data.UserActivityRepository

/**
 * The ViewModel for [TrackListFragment].
 */
class TrackListViewModel @ViewModelInject internal constructor(
    private val trackRepository: TrackRepository,
    private val userActivityRepository: UserActivityRepository
) : ViewModel() {

    val userActivity = userActivityRepository.getLatest(null).asLiveData()
    val tracks: LiveData<List<Track>> = trackRepository.getAll().asLiveData()
    val featured: LiveData<Track> = trackRepository.getFeatured().asLiveData()
}
