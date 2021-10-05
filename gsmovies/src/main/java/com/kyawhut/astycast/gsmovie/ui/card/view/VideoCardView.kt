package com.kyawhut.astycast.gsmovie.ui.card.view

import android.content.Context
import com.kyawhut.astycast.gsmovie.R
import com.kyawhut.astycast.gsmovie.data.network.response.VideoResponse
import com.kyawhut.astycast.gsmovie.databinding.CardGsVideosBinding
import com.kyawhut.atsycast.share.base.BaseCardView

/**
 * @author kyawhtut
 * @date 9/10/21
 */
internal class VideoCardView(context: Context) : BaseCardView<CardGsVideosBinding>(
    R.layout.card_gs_videos, context
) {

    override fun setSelected(selected: Boolean) {
        super.setSelected(selected)
        vb.isFocus = selected
    }

    override fun bindUI(data: Any) {
        if (data is VideoResponse.Data) {
            vb.apply {
                moviesName = data.videoTitle
                moviesPoster = data.videoPoster
                moviesEpisode = data.videoEpisode
                moviesViewCount = data.videoViewCount
                executePendingBindings()
            }
        }
    }
}
