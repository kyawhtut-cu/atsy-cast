package com.kyawhut.atsycast.doujin.ui.card.view

import android.content.Context
import com.kyawhut.atsycast.doujin.R
import com.kyawhut.atsycast.doujin.data.network.response.DoujinVideoResponse
import com.kyawhut.atsycast.doujin.databinding.CardDoujinVideoBinding
import com.kyawhut.atsycast.share.base.BaseCardView
import timber.log.Timber

/**
 * @author kyawhtut
 * @date 9/14/21
 */
internal class VideoCardView(context: Context) : BaseCardView<CardDoujinVideoBinding>(
    R.layout.card_doujin_video, context
) {

    override fun setSelected(selected: Boolean) {
        super.setSelected(selected)
        vb.isFocus = selected
    }

    override fun bindUI(data: Any) {
        if (data is DoujinVideoResponse) {
            vb.apply {
                this.data = data
                executePendingBindings()
            }
        }
    }
}
