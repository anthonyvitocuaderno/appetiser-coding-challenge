package com.appetiser.exam1.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.appetiser.exam1.TrackListFragmentDirections
import com.appetiser.exam1.data.Track
import com.appetiser.exam1.databinding.ListItemTrackBinding

/**
 * Adapter for the [RecyclerView] in [TrackListFragment].
 */
class TrackAdapter : ListAdapter<Track, RecyclerView.ViewHolder>(TrackDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return TrackViewHolder(
            ListItemTrackBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val track = getItem(position)
        (holder as TrackViewHolder).bind(track)
    }

    class TrackViewHolder(
        private val binding: ListItemTrackBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.setClickListener {
                binding.track?.let { track ->
                    navigateToTrack(track, it)
                }
            }
        }

        private fun navigateToTrack(
            track: Track,
            view: View
        ) {
            val direction = TrackListFragmentDirections.actionTrackListFragmentToTrackDetailFragment(track.trackId)
            view.findNavController().navigate(direction)
        }

        fun bind(item: Track) {
            binding.apply {
                track = item
                executePendingBindings()
            }
        }
    }
}

private class TrackDiffCallback : DiffUtil.ItemCallback<Track>() {

    override fun areItemsTheSame(oldItem: Track, newItem: Track): Boolean {
        return oldItem.trackId == newItem.trackId
    }

    override fun areContentsTheSame(oldItem: Track, newItem: Track): Boolean {
        return oldItem == newItem
    }
}
