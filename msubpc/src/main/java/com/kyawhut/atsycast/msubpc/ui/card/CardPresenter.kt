package com.kyawhut.atsycast.msubpc.ui.card

import android.content.Context
import com.google.gson.Gson
import com.kyawhut.atsycast.msubpc.data.network.response.EpisodeResponse
import com.kyawhut.atsycast.msubpc.data.network.response.FootballResponse
import com.kyawhut.atsycast.msubpc.data.network.response.VideoResponse
import com.kyawhut.atsycast.share.base.BaseCardPresenter
import com.kyawhut.atsycast.share.base.BaseCardPresenterSelector
import com.kyawhut.atsycast.share.db.entity.RecentlyWatchEntity
import com.kyawhut.atsycast.share.ui.card.RecentlyWatchCardPresenter

/**
 * @author kyawhtut
 * @date 9/6/21
 */
internal class CardPresenter(
    context: Context,
    private val poster: String = "",
) : BaseCardPresenterSelector<CardType>(context) {
    override fun getCardType(item: Any?): CardType {
        return when {
            item is VideoResponse && item.isMovies -> CardType.MOVIES
            item is VideoResponse && !item.isMovies -> CardType.SERIES
            item is EpisodeResponse -> CardType.EPISODE
            item is RecentlyWatchEntity -> CardType.RECENTLY_WATCH
            item is FootballResponse.Data -> CardType.FOOTBALL
            else -> throw RuntimeException("Unknown item => ${Gson().toJson(item)}")
        }
    }

    override fun getPresenter(type: CardType, context: Context): BaseCardPresenter<*> {
        return when (type) {
            CardType.MOVIES -> MoviesCardPresenter(context)
            CardType.SERIES -> SeriesCardPresenter(context)
            CardType.EPISODE -> EpisodeCardPresenter(context, poster)
            CardType.RECENTLY_WATCH -> RecentlyWatchCardPresenter(context)
            CardType.FOOTBALL -> FootballCardPresenter(context)
        }
    }
}
