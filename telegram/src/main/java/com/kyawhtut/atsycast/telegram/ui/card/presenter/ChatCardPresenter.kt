package com.kyawhtut.atsycast.telegram.ui.card.presenter

import android.content.Context
import com.kyawhtut.atsycast.telegram.ui.card.view.ChatCardView
import com.kyawhut.atsycast.share.base.BaseCardPresenter

/**
 * @author kyawhtut
 * @date 19/10/2022
 */
internal class ChatCardPresenter(context: Context) : BaseCardPresenter<ChatCardView>(context) {

    override fun onCreateView(): ChatCardView {
        return ChatCardView(context)
    }
}
