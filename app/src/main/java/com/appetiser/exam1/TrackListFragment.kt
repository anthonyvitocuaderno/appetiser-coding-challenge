package com.appetiser.exam1

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.Callback
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.appetiser.exam1.adapters.ItemMoveCallback
import com.appetiser.exam1.adapters.TrackAdapter
import com.appetiser.exam1.data.UserActivityRepository
import com.appetiser.exam1.databinding.FragmentListTrackBinding
import com.appetiser.exam1.viewmodels.TrackListViewModel
import com.appetiser.exam1.workers.FetchTracksWorker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@AndroidEntryPoint
class TrackListFragment : Fragment() {

    private val trackListViewModel: TrackListViewModel by viewModels()
    @Inject
    lateinit var userActivityRepository: UserActivityRepository

    override fun onResume() {
        super.onResume()

        activity?.let {

            val request = OneTimeWorkRequestBuilder<FetchTracksWorker>()
                .build()
            WorkManager.getInstance(it).enqueue(request)
        }
    }

    override fun onPause() {
        super.onPause()
        runBlocking {
            userActivityRepository.save(null)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = DataBindingUtil.inflate<FragmentListTrackBinding>(
            inflater,
            R.layout.fragment_list_track,
            container,
            false
        ).apply {
            viewModel = trackListViewModel
            lifecycleOwner = viewLifecycleOwner

            val adapter = TrackAdapter()

            val callback: Callback = ItemMoveCallback(adapter)
            val touchHelper = ItemTouchHelper(callback)
            touchHelper.attachToRecyclerView(list)
            list.adapter = adapter
            subscribeUi(adapter)

            setHasOptionsMenu(true)

            trackListViewModel.userActivity.observe(
                viewLifecycleOwner,
                Observer {
                    Log.d("UserActivity", it.toString())
                }
            )

            trackListViewModel.featured.observe(viewLifecycleOwner) { track ->
                featuredItem.track = track
                featuredItem.setClickListener {
                    val direction = TrackListFragmentDirections.actionTrackListFragmentToTrackDetailFragment(
                        track.trackId
                    )
                    this@TrackListFragment.findNavController().navigate(direction)
                }
            }
        }

        return binding.root
    }

    private fun subscribeUi(adapter: TrackAdapter) {
        trackListViewModel.tracks.observe(viewLifecycleOwner) { tracks ->
            adapter.submitList(tracks)
        }
    }
}
