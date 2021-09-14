package com.kyawhut.atsycast.msubpc.ui.card.view

import android.content.Context
import com.kyawhut.atsycast.msubpc.R
import com.kyawhut.atsycast.msubpc.data.network.response.EpisodeResponse
import com.kyawhut.atsycast.msubpc.databinding.CardEpisodeBinding
import com.kyawhut.atsycast.share.base.BaseCardView

/**
 * @author kyawhtut
 * @date 9/7/21
 */
internal class EpisodeCardView(
    context: Context,
    private val poster: String,
) : BaseCardView<CardEpisodeBinding>(R.layout.card_episode, context) {

    override fun setSelected(selected: Boolean) {
        super.setSelected(selected)
        vb.isFocus = selected
    }

    override fun bindUI(data: Any) {
        if (data is EpisodeResponse) {
            vb.apply {
                episodeName = data.episodeName
                episodePoster = poster
                episodeDescription = "Size - %s".format(data.fileSize)
                executePendingBindings()
            }
        }
    }
}
