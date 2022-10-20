package com.kyawhtut.atsycast.telegram.ui.card.view

import android.content.Context
import com.kyawhtut.atsycast.telegram.R
import com.kyawhtut.atsycast.telegram.data.model.MessageType
import com.kyawhtut.atsycast.telegram.databinding.CardMessageVideoBinding
import com.kyawhut.atsycast.share.base.BaseCardView

/**
 * @author kyawhtut
 * @date 19/10/2022
 */
internal class VideoCardView(context: Context) : BaseCardView<CardMessageVideoBinding>(
    R.layout.card_message_video, context
) {

    override fun setSelected(selected: Boolean) {
        super.setSelected(selected)
        vb.isFocus = selected
    }

    override fun bindUI(data: Any) {
        if (data is MessageType.MessageVideoModel) {
            vb.apply {
                vb.thumbnailPath = data.thumbnailPath
            }
        }
    }
}
