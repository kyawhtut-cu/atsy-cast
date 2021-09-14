package com.kyawhut.atsycast.zcm.ui.card.view

import android.content.Context
import com.kyawhut.atsycast.share.base.BaseCardView
import com.kyawhut.atsycast.zcm.R
import com.kyawhut.atsycast.zcm.data.network.response.MoviesResponse
import com.kyawhut.atsycast.zcm.databinding.CardZcmVideosBinding

/**
 * @author kyawhtut
 * @date 9/10/21
 */
internal class VideoCardView(context: Context) : BaseCardView<CardZcmVideosBinding>(
    R.layout.card_zcm_videos, context
) {

    override fun setSelected(selected: Boolean) {
        super.setSelected(selected)
        vb.isFocus = selected
    }

    override fun bindUI(data: Any) {
        if (data is MoviesResponse) {
            vb.apply {
                moviesName = data.moviesTitle
                moviesPoster = data.moviesImage
                movieIMDB = if (!data.imdb.isNullOrEmpty()) String.format(
                    "%.1f",
                    data.imdb.toDouble()
                ) else ""
                moviesYear = data.moviesYear
                executePendingBindings()
            }
        }
    }
}
