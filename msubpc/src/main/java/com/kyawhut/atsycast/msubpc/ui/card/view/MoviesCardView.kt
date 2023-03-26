package com.kyawhut.atsycast.msubpc.ui.card.view

import android.content.Context
import com.kyawhut.atsycast.msubpc.R
import com.kyawhut.atsycast.msubpc.data.network.response.VideoResponse
import com.kyawhut.atsycast.msubpc.databinding.CardMoviesBinding
import com.kyawhut.atsycast.share.base.BaseCardView
import com.kyawhut.atsycast.share.utils.extension.Extension.getDrawableValue

/**
 * @author kyawhtut
 * @date 9/6/21
 */
internal class MoviesCardView(context: Context) : BaseCardView<CardMoviesBinding>(
    R.layout.card_movies, context
) {

    override fun setSelected(selected: Boolean) {
        super.setSelected(selected)
        vb.isFocus = selected
    }

    override fun bindUI(data: Any) {
        if (data is VideoResponse) {
            vb.apply {
                moviesName = data.videoTitle
                moviesPoster = data.videoPosterImage
                movieIMDB = data.videoRating ?: ""
                moviesResolution = data.videoQuality ?: ""
                moviesYear = data.videoYear ?: ""
                moviesResolutionType = context.getDrawableValue(data.qualityDrawable)
                executePendingBindings()
            }
        }
    }

}
