package com.kyawhut.atsycast.share.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kyawhut.atsycast.share.R
import com.kyawhut.atsycast.share.databinding.ItemGenresBinding

/**
 * @author kyawhtut
 * @date 9/7/21
 */
internal class GenresAdapter : RecyclerView.Adapter<GenresAdapter.GenresViewHolder>() {

    private val itemList: MutableList<String> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenresViewHolder {
        return GenresViewHolder(
            ItemGenresBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: GenresViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    fun setGenres(genres: String) {
        itemList.add(genres)
        notifyDataSetChanged()
    }

    class GenresViewHolder(
        private val bindView: ItemGenresBinding
    ) : RecyclerView.ViewHolder(bindView.root) {

        fun bind(data: String) {
            bindView.apply {
                genresColor = itemView.context.resources.getIntArray(R.array.randomColor).random()
                genresName = data
                executePendingBindings()
            }
        }
    }
}
