package com.kyawhut.atsycast.ets2mm.ui.card.view

import android.content.Context
import com.kyawhut.atsycast.ets2mm.R
import com.kyawhut.atsycast.ets2mm.data.network.response.VideoResponse
import com.kyawhut.atsycast.ets2mm.databinding.CardEt2sVideosBinding
import com.kyawhut.atsycast.share.base.BaseCardView
import com.kyawhut.atsycast.share.utils.extension.Extension.getDrawableValue

/**
 * @author kyawhtut
 * @date 9/10/21
 */
internal class VideoCardView(context: Context) : BaseCardView<CardEt2sVideosBinding>(
    R.layout.card_et2s_videos, context
) {

    override fun setSelected(selected: Boolean) {
        super.setSelected(selected)
        vb.isFocus = selected
    }

    override fun bindUI(data: Any) {
        if (data is VideoResponse) {
            vb.apply {
                moviesName = data.videoTitle
                moviesPoster = data.videoPoster
                movieIMDB = ""
                moviesYear = data.videoYear
                moviesResolution = data.videoQuality
                moviesResolutionType = context.getDrawableValue(data.qualityDrawable)
                executePendingBindings()
            }
        }
    }
}
