package com.kyawhtut.atsycast.telegram.ui.card.presenter

import android.content.Context
import com.kyawhtut.atsycast.telegram.ui.card.view.MessageCardView
import com.kyawhut.atsycast.share.base.BaseCardPresenter

/**
 * @author kyawhtut
 * @date 19/10/2022
 */
internal class MessageCardPresenter(
    context: Context
) : BaseCardPresenter<MessageCardView>(context) {

    override fun onCreateView(): MessageCardView {
        return MessageCardView(context)
    }
}
