package com.kyawhut.atsycast.gs_mm.ui.card.view

import android.content.Context
import com.kyawhut.atsycast.gs_mm.R
import com.kyawhut.atsycast.gs_mm.data.network.response.EpisodeResponse
import com.kyawhut.atsycast.gs_mm.databinding.CardGsmmEpisodeBinding
import com.kyawhut.atsycast.share.base.BaseCardView

/**
 * @author kyawhtut
 * @date 9/7/21
 */
internal class EpisodeCardView(
    context: Context
) : BaseCardView<CardGsmmEpisodeBinding>(R.layout.card_gsmm_episode, context) {

    override fun setSelected(selected: Boolean) {
        super.setSelected(selected)
        vb.isFocus = selected
    }

    override fun bindUI(data: Any) {
        if (data is EpisodeResponse.Data) {
            vb.apply {
                episodeName = data.episodeTitle
                episodePoster = data.episodeCover
                episodeDescription = data.episodeDescription
                executePendingBindings()
            }
        }
    }
}
