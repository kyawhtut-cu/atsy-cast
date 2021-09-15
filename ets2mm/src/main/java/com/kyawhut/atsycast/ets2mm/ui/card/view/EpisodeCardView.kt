package com.kyawhut.atsycast.ets2mm.ui.card.view

import android.content.Context
import com.kyawhut.atsycast.ets2mm.R
import com.kyawhut.atsycast.ets2mm.data.network.response.EpisodeResponse
import com.kyawhut.atsycast.ets2mm.databinding.CardEt2sEpisodeBinding
import com.kyawhut.atsycast.share.base.BaseCardView

/**
 * @author kyawhtut
 * @date 9/7/21
 */
internal class EpisodeCardView(
    context: Context,
    private val poster: String,
) : BaseCardView<CardEt2sEpisodeBinding>(R.layout.card_et2s_episode, context) {

    override fun setSelected(selected: Boolean) {
        super.setSelected(selected)
        vb.isFocus = selected
    }

    override fun bindUI(data: Any) {
        if (data is EpisodeResponse) {
            vb.apply {
                episodeName = data.episodeName
                episodePoster = poster
                episodeDescription = ""
                executePendingBindings()
            }
        }
    }
}
