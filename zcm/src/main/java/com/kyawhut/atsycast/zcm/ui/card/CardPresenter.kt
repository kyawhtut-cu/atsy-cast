package com.kyawhut.atsycast.zcm.ui.card

import android.content.Context
import com.google.gson.Gson
import com.kyawhut.atsycast.share.base.BaseCardPresenter
import com.kyawhut.atsycast.share.base.BaseCardPresenterSelector
import com.kyawhut.atsycast.share.db.entity.RecentlyWatchEntity
import com.kyawhut.atsycast.share.ui.card.RecentlyWatchCardPresenter
import com.kyawhut.atsycast.zcm.data.network.response.EpisodeResponse
import com.kyawhut.atsycast.zcm.data.network.response.MoviesResponse

/**
 * @author kyawhtut
 * @date 9/10/21
 */
internal class CardPresenter(
    context: Context, private val poster: String = ""
) : BaseCardPresenterSelector<CardType>(context) {

    override fun getCardType(item: Any?): CardType {
        return when (item) {
            is MoviesResponse -> CardType.VIDEOS
            is EpisodeResponse -> CardType.EPISODE
            is RecentlyWatchEntity -> CardType.RECENTLY_WATCH
            else -> throw RuntimeException("Unknown item => ${Gson().toJson(item)}")
        }
    }

    override fun getPresenter(type: CardType, context: Context): BaseCardPresenter<*> {
        return when (type) {
            is CardType.VIDEOS -> VideoCardPresenter(context)
            is CardType.EPISODE -> EpisodeCardPresenter(context, poster)
            is CardType.RECENTLY_WATCH -> RecentlyWatchCardPresenter(context)
        }
    }
}
