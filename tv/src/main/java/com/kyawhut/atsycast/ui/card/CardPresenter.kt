package com.kyawhut.atsycast.ui.card

import android.content.Context
import com.kyawhut.atsycast.data.network.response.HomeFeatureResponse
import com.kyawhut.atsycast.share.base.BaseCardPresenter
import com.kyawhut.atsycast.share.base.BaseCardPresenterSelector
import com.kyawhut.atsycast.share.db.entity.RecentlyWatchEntity
import com.kyawhut.atsycast.share.db.entity.WatchLaterEntity

/**
 * @author kyawhtut
 * @date 8/31/21
 */
class CardPresenter(context: Context) : BaseCardPresenterSelector<CardType>(context) {

    override fun getCardType(item: Any?): CardType {
        return when (item) {
            is HomeFeatureResponse.Data -> CardType.FEATURE
            is WatchLaterEntity -> CardType.WATCHLATER
            is RecentlyWatchEntity -> CardType.RECENTLYWATCH
            else -> throw RuntimeException("$item is not supported")
        }
    }

    override fun getPresenter(type: CardType, context: Context): BaseCardPresenter<*> {
        return when (type) {
            is CardType.FEATURE -> FeaturePresenter(context)
            is CardType.WATCHLATER -> WatchLaterCardPresenter(context)
            is CardType.RECENTLYWATCH -> RecentlyWatchCardPresenter(context)
        }
    }
}
