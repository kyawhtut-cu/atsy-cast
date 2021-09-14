package com.kyawhut.atsycast.msys.ui.card.view

import android.content.Context
import com.kyawhut.atsycast.msys.R
import com.kyawhut.atsycast.msys.data.network.response.EpisodeResponse
import com.kyawhut.atsycast.msys.databinding.CardMsysEpisodeBinding
import com.kyawhut.atsycast.share.base.BaseCardView

/**
 * @author kyawhtut
 * @date 9/7/21
 */
internal class EpisodeCardView(
    context: Context,
    private val poster: String,
) : BaseCardView<CardMsysEpisodeBinding>(R.layout.card_msys_episode, context) {

    override fun setSelected(selected: Boolean) {
        super.setSelected(selected)
        vb.isFocus = selected
    }

    override fun bindUI(data: Any) {
        if (data is EpisodeResponse) {
            vb.apply {
                episodeName = data.episodeTitle
                episodePoster = data.episodeImage ?: poster
                episodeDescription = data.episodeDescription ?: ""
                executePendingBindings()
            }
        }
    }
}
