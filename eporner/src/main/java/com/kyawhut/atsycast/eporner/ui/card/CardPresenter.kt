package com.kyawhut.atsycast.eporner.ui.card

import android.content.Context
import com.google.gson.Gson
import com.kyawhut.atsycast.eporner.data.network.response.EPornerVideoResponse
import com.kyawhut.atsycast.share.base.BaseCardPresenter
import com.kyawhut.atsycast.share.base.BaseCardPresenterSelector

/**
 * @author kyawhtut
 * @date 9/14/21
 */
internal class CardPresenter(context: Context) : BaseCardPresenterSelector<CardType>(context) {

    override fun getCardType(item: Any?): CardType {
        return when (item) {
            is EPornerVideoResponse -> CardType.VIDEO
            else -> throw RuntimeException("Unknown item => ${Gson().toJson(item)}")
        }
    }

    override fun getPresenter(type: CardType, context: Context): BaseCardPresenter<*> {
        return when (type) {
            is CardType.VIDEO -> VideoCardPresenter(context)
        }
    }
}