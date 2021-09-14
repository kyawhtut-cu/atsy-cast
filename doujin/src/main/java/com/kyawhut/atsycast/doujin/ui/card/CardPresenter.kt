package com.kyawhut.atsycast.doujin.ui.card

import android.content.Context
import com.google.gson.Gson
import com.kyawhut.atsycast.doujin.data.network.response.DoujinVideoResponse
import com.kyawhut.atsycast.share.base.BaseCardPresenter
import com.kyawhut.atsycast.share.base.BaseCardPresenterSelector

/**
 * @author kyawhtut
 * @date 9/14/21
 */
internal class CardPresenter(context: Context) : BaseCardPresenterSelector<CardType>(context) {

    override fun getCardType(item: Any?): CardType {
        return when (item) {
            is DoujinVideoResponse -> CardType.VIDEO
            else -> throw RuntimeException("Unknown item => ${Gson().toJson(item)}")
        }
    }

    override fun getPresenter(type: CardType, context: Context): BaseCardPresenter<*> {
        return when (type) {
            is CardType.VIDEO -> VideoCardPresenter(context)
        }
    }
}