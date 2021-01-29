package com.appetiser.exam1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil.setContentView
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.findNavController
import com.appetiser.exam1.data.UserActivityRepository
import com.appetiser.exam1.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var userActivityRepository: UserActivityRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView<ActivityMainBinding>(this, R.layout.activity_main)


    }

    override fun onStart() {
        super.onStart()
        runBlocking {
            userActivityRepository.getLatest().take(1).collect {
                val previousSession = it?.let {
                    if (!it.trackId.isNullOrEmpty()) {
                        val direction = TrackListFragmentDirections.actionTrackListFragmentToTrackDetailFragment(it.trackId)

                        findNavController(R.id.nav_host).navigate(direction)
                    }
                }
            }
        }
    }
}