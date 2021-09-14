package com.kyawhut.atsycast.msubpc.ui.card.view

import android.content.Context
import com.kyawhut.atsycast.msubpc.R
import com.kyawhut.atsycast.msubpc.data.network.response.VideoResponse
import com.kyawhut.atsycast.msubpc.databinding.CardSeriesBinding
import com.kyawhut.atsycast.share.base.BaseCardView

/**
 * @author kyawhtut
 * @date 9/6/21
 */
internal class SeriesCardView(context: Context) : BaseCardView<CardSeriesBinding>(
    R.layout.card_series, context
) {

    override fun setSelected(selected: Boolean) {
        super.setSelected(selected)
        vb.isFocus = selected
    }

    override fun bindUI(data: Any) {
        if (data is VideoResponse) {
            vb.apply {
                seriesName = data.videoTitle
                seriesPoster = data.videoPosterImage
                seriesYear = data.videoYear ?: ""
                seriesStatus = "%s".format(
                    if (data.videoSeasonNumber == 0) "Complete" else "Season %s".format(data.videoSeasonNumber)
                )
                seriesEpisodeCount = data.videoEpisodeNumber ?: "0"
                executePendingBindings()
            }
        }
    }

}
