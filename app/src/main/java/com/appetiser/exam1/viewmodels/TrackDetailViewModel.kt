package com.appetiser.exam1.viewmodels


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.appetiser.exam1.data.TrackRepository
import com.appetiser.exam1.data.UserActivityRepository
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject

/**
 * The ViewModel used in [TrackDetailFragment].
 */
class TrackDetailViewModel @AssistedInject constructor(
    trackRepository: TrackRepository,
    userActivityRepository: UserActivityRepository,
    @Assisted private val trackId: String
) : ViewModel() {


    val userActivity = userActivityRepository.getLatest(trackId).asLiveData()
    val track = trackRepository.getOne(trackId).asLiveData()

    @AssistedInject.Factory
    interface AssistedFactory {
        fun create(trackId: String): TrackDetailViewModel
    }

    companion object {
        fun provideFactory(
            assistedFactory: AssistedFactory,
            trackId: String
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return assistedFactory.create(trackId) as T
            }
        }
    }
}
