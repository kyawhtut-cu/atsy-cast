package com.kyawhut.atsycast.zcm.ui.card.view

import android.content.Context
import com.kyawhut.atsycast.share.base.BaseCardView
import com.kyawhut.atsycast.zcm.R
import com.kyawhut.atsycast.zcm.data.network.response.EpisodeResponse
import com.kyawhut.atsycast.zcm.databinding.CardZcmEpisodeBinding

/**
 * @author kyawhtut
 * @date 9/7/21
 */
internal class EpisodeCardView(
    context: Context,
    private val poster: String,
) : BaseCardView<CardZcmEpisodeBinding>(R.layout.card_zcm_episode, context) {

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
