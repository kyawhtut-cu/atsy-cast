package com.kyawhut.atsycast.free2air.ui.card

import android.content.Context
import com.kyawhut.atsycast.free2air.data.network.response.Free2AirResponse
import com.kyawhut.atsycast.share.base.BaseCardPresenter
import com.kyawhut.atsycast.share.base.BaseCardPresenterSelector

/**
 * @author kyawhtut
 * @date 9/1/21
 */
class CardPresenter(context: Context) : BaseCardPresenterSelector<CardType>(context) {

    override fun getCardType(item: Any?): CardType {
        return when (item) {
            is Free2AirResponse.Data -> CardType.F2A
            else -> throw RuntimeException("$item is not supported")
        }
    }

    override fun getPresenter(type: CardType, context: Context): BaseCardPresenter<*> {
        return when (type) {
            CardType.F2A -> F2ACardPresenter(context)
        }
    }
}
