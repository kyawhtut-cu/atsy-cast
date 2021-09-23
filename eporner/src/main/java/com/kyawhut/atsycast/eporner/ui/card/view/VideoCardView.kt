package com.kyawhut.atsycast.eporner.ui.card.view

import android.content.Context
import com.kyawhut.atsycast.eporner.R
import com.kyawhut.atsycast.eporner.data.network.response.EPornerVideoResponse
import com.kyawhut.atsycast.eporner.databinding.CardEpornerVideoBinding
import com.kyawhut.atsycast.share.base.BaseCardView

/**
 * @author kyawhtut
 * @date 9/14/21
 */
internal class VideoCardView(context: Context) : BaseCardView<CardEpornerVideoBinding>(
    R.layout.card_eporner_video, context
) {

    override fun setSelected(selected: Boolean) {
        super.setSelected(selected)
        vb.isFocus = selected
    }

    override fun bindUI(data: Any) {
        if (data is EPornerVideoResponse) {
            vb.apply {
                this.data = data
                executePendingBindings()
            }
        }
    }
}
