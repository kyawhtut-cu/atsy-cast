package com.kyawhut.atsycast.gs_mm.ui.card.view

import android.content.Context
import com.kyawhut.atsycast.gs_mm.R
import com.kyawhut.atsycast.gs_mm.data.network.response.VideoResponse
import com.kyawhut.atsycast.gs_mm.databinding.CardGsmmPlaylistBinding
import com.kyawhut.atsycast.share.base.BaseCardView

/**
 * @author kyawhtut
 * @date 9/7/21
 */
internal class PlaylistCardView(
    context: Context
) : BaseCardView<CardGsmmPlaylistBinding>(R.layout.card_gsmm_playlist, context) {

    override fun setSelected(selected: Boolean) {
        super.setSelected(selected)
        vb.isFocus = selected
    }

    override fun bindUI(data: Any) {
        if (data is VideoResponse) {
            vb.apply {
                playlistName = data.videoTitle
                playlistPoster = data.videoCover
                playlistCompanyName = data.videoCompanyName
                executePendingBindings()
            }
        }
    }
}
