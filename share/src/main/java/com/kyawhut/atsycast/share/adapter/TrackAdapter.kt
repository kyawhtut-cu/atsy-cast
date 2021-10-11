package com.kyawhut.atsycast.share.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kyawhut.atsycast.share.databinding.ItemTrackBinding
import com.kyawhut.atsycast.share.model.SubTitleModel
import com.kyawhut.atsycast.share.model.Track
import timber.log.Timber

/**
 * @author kyawhtut
 * @date 9/7/21
 */
internal class TrackAdapter(
    private val onClickItem: (Int) -> Unit
) : RecyclerView.Adapter<TrackAdapter.TrackViewHolder>() {

    private val itemList: MutableList<Any> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        return TrackViewHolder(
            ItemTrackBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    fun setTrack(track: Any) {
        itemList.add(track)
        notifyDataSetChanged()
    }

    fun setTrackList(track: List<Any>) {
        itemList.clear()
        itemList.addAll(0, track)
        notifyDataSetChanged()
    }

    inner class TrackViewHolder(
        private val bindView: ItemTrackBinding
    ) : RecyclerView.ViewHolder(bindView.root) {

        fun bind(data: Any) {
            var isSelected = false
            var trackName = ""
            if (data is Track) {
                trackName = data.name
                isSelected = data.isSelected
            } else if (data is SubTitleModel) {
                trackName = data.displayLanguage
                isSelected = data.isSelected
            }

            bindView.apply {
                this.trackName = trackName
                this.isSelected = isSelected
                if (isSelected) root.requestFocus()
                root.setOnClickListener {
                    onClickItem(absoluteAdapterPosition)
                }
                executePendingBindings()
            }
        }
    }
}
