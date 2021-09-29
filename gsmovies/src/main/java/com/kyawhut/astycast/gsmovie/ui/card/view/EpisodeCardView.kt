package com.kyawhut.astycast.gsmovie.ui.card.view

import android.content.Context
import com.kyawhut.astycast.gsmovie.R
import com.kyawhut.astycast.gsmovie.data.network.response.VideoEpisodeResponse
import com.kyawhut.astycast.gsmovie.databinding.CardGsEpisodeBinding
import com.kyawhut.atsycast.share.base.BaseCardView

/**
 * @author kyawhtut
 * @date 9/7/21
 */
internal class EpisodeCardView(
    context: Context,
    private val poster: String,
) : BaseCardView<CardGsEpisodeBinding>(R.layout.card_gs_episode, context) {

    override fun setSelected(selected: Boolean) {
        super.setSelected(selected)
        vb.isFocus = selected
    }

    override fun bindUI(data: Any) {
        if (data is VideoEpisodeResponse) {
            vb.apply {
                episodeName = data.episodeTitle
                episodePoster = poster
                episodeDescription = ""
                executePendingBindings()
            }
        }
    }
}
