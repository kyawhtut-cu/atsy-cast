package com.kyawhut.atsycast.msys.ui.card.view

import android.content.Context
import com.kyawhut.atsycast.msys.R
import com.kyawhut.atsycast.msys.data.network.response.MoviesResponse
import com.kyawhut.atsycast.msys.databinding.CardVideosBinding
import com.kyawhut.atsycast.share.base.BaseCardView

/**
 * @author kyawhtut
 * @date 9/10/21
 */
internal class VideoCardView(context: Context) : BaseCardView<CardVideosBinding>(
    R.layout.card_videos, context
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
                movieIMDB = data.imdb ?: ""
                moviesYear = data.moviesYear
                executePendingBindings()
            }
        }
    }
}
