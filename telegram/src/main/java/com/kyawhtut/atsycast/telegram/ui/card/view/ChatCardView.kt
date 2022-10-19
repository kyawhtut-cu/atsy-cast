package com.kyawhtut.atsycast.telegram.ui.card.view

import android.content.Context
import com.kyawhtut.atsycast.telegram.R
import com.kyawhtut.atsycast.telegram.data.model.ChatModel
import com.kyawhtut.atsycast.telegram.databinding.CardChatBinding
import com.kyawhut.atsycast.share.base.BaseCardView

/**
 * @author kyawhtut
 * @date 19/10/2022
 */
internal class ChatCardView(context: Context) : BaseCardView<CardChatBinding>(
    R.layout.card_chat, context
) {

    override fun setSelected(selected: Boolean) {
        super.setSelected(selected)
        vb.isFocus = selected
    }

    override fun bindUI(data: Any) {
        if (data is ChatModel) {
            vb.apply {
                vb.chatName = data.chatTitle
                vb.chatPhoto = data.chatPhoto
            }
        }
    }
}
