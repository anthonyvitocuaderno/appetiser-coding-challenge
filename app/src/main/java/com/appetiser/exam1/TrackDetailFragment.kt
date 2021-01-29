package com.appetiser.exam1

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import androidx.core.widget.NestedScrollView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.appetiser.exam1.data.UserActivityRepository
import com.appetiser.exam1.databinding.FragmentTrackDetailBinding
import com.appetiser.exam1.viewmodels.TrackDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

/**
 * A fragment representing a single Track detail screen.
 */
@AndroidEntryPoint
class TrackDetailFragment : Fragment() {

    private val args: TrackDetailFragmentArgs by navArgs()

    @Inject
    lateinit var trackDetailViewModelFactory: TrackDetailViewModel.AssistedFactory
    @Inject
    lateinit var userActivityRepository: UserActivityRepository

    private val trackDetailViewModel: TrackDetailViewModel by viewModels {
        TrackDetailViewModel.provideFactory(
            trackDetailViewModelFactory,
            args.trackId
        )
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
        val track = trackDetailViewModel.track.value?.let {
            runBlocking {
                userActivityRepository.save(it.trackId)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = DataBindingUtil.inflate<FragmentTrackDetailBinding>(
            inflater,
            R.layout.fragment_track_detail,
            container,
            false
        ).apply {
            viewModel = trackDetailViewModel
            lifecycleOwner = viewLifecycleOwner

            trackDetailViewModel.track.observe(viewLifecycleOwner, Observer {

                if (it.isPreviewAvailable() && !videoView.isPlaying) {
                    //Creating MediaController
                    val mediaController = MediaController(activity)
                    mediaController.setAnchorView(videoView)
                    //specify the location of media file
                    val uri: Uri = Uri.parse(trackDetailViewModel.track.value!!.previewUrl)
                    //Setting MediaController and URI, then starting the videoView
                    videoView.setMediaController(mediaController)
                    videoView.setVideoURI(uri)
                    videoView.requestFocus()
                    videoView.start()


                    // TODO video error handling
                }
            })

            var isToolbarShown = false
            // scroll change listener begins at Y = 0 when image is fully collapsed
            trackDetailScrollview.setOnScrollChangeListener(
                NestedScrollView.OnScrollChangeListener { _, _, scrollY, _, _ ->

                    // User scrolled past image to height of toolbar and the title text is
                    // underneath the toolbar, so the toolbar should be shown.
                    val shouldShowToolbar = scrollY > toolbar.height

                    // The new state of the toolbar differs from the previous state; update
                    // appbar and toolbar attributes.
                    if (isToolbarShown != shouldShowToolbar) {
                        isToolbarShown = shouldShowToolbar

                        // Use shadow animator to add elevation if toolbar is shown
                        appbar.isActivated = shouldShowToolbar

                        // Show the plant name if toolbar is shown
                        toolbarLayout.isTitleEnabled = shouldShowToolbar
                    }
                }
            )

            toolbar.setNavigationOnClickListener { view ->
                view.findNavController().navigateUp()
            }

            trackDetailViewModel.userActivity.observe(viewLifecycleOwner, Observer {
                Log.d("UserActivity", it.toString())
                userActivity.text = it?.getDateTimeString()
            })
        }




        return binding.root
    }
}
