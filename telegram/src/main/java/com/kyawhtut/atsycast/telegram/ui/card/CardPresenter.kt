package com.kyawhtut.atsycast.telegram.ui.card

import android.content.Context
import com.kyawhtut.atsycast.telegram.data.model.ChatModel
import com.kyawhtut.atsycast.telegram.data.model.MessageType
import com.kyawhtut.atsycast.telegram.ui.card.presenter.ChatCardPresenter
import com.kyawhtut.atsycast.telegram.ui.card.presenter.MessageCardPresenter
import com.kyawhut.atsycast.share.base.BaseCardPresenter
import com.kyawhut.atsycast.share.base.BaseCardPresenterSelector

/**
 * @author kyawhtut
 * @date 19/10/2022
 */
internal class CardPresenter(
    context: Context
) : BaseCardPresenterSelector<CardType>(context) {

    override fun getCardType(item: Any?): CardType {
        return when (item) {
            is ChatModel -> CardType.Chat
            is MessageType -> CardType.Message
            else -> super.getCardType(item)
        }
    }

    override fun getPresenter(type: CardType, context: Context): BaseCardPresenter<*> {
        return when (type) {
            is CardType.Chat -> ChatCardPresenter(context)
            is CardType.Message -> MessageCardPresenter(context)
        }
    }
}
