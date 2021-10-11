package com.kyawhut.atsycast.gs_mm.ui.card.view

import android.content.Context
import com.kyawhut.atsycast.gs_mm.R
import com.kyawhut.atsycast.gs_mm.data.network.response.VideoResponse
import com.kyawhut.atsycast.gs_mm.databinding.CardGsmmVideosBinding
import com.kyawhut.atsycast.share.base.BaseCardView

/**
 * @author kyawhtut
 * @date 9/10/21
 */
internal class VideoCardView(context: Context) : BaseCardView<CardGsmmVideosBinding>(
    R.layout.card_gsmm_videos, context
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
                moviesCompany = data.videoCompanyName
                executePendingBindings()
            }
        }
    }
}
