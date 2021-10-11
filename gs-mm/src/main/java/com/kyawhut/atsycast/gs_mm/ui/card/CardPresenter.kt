package com.kyawhut.atsycast.gs_mm.ui.card

import android.content.Context
import com.kyawhut.atsycast.gs_mm.data.network.response.EpisodeResponse
import com.kyawhut.atsycast.gs_mm.data.network.response.VideoResponse
import com.kyawhut.atsycast.share.base.BaseCardPresenter
import com.kyawhut.atsycast.share.base.BaseCardPresenterSelector
import com.kyawhut.atsycast.share.db.entity.RecentlyWatchEntity
import com.kyawhut.atsycast.share.ui.card.RecentlyWatchCardPresenter

/**
 * @author kyawhtut
 * @date 9/29/21
 */
internal class CardPresenter(
    context: Context,
    private val isPlaylist: Boolean = false,
) : BaseCardPresenterSelector<CardType>(context) {

    override fun getCardType(item: Any?): CardType {
        return when (item) {
            is VideoResponse -> {
                if (!isPlaylist) CardType.VIDEO
                else CardType.PLAYLIST
            }
            is EpisodeResponse.Data -> CardType.EPISODE
            is RecentlyWatchEntity -> CardType.RECENTLY_WATCH
            else -> throw RuntimeException("Unknown item type => $item")
        }
    }

    override fun getPresenter(type: CardType, context: Context): BaseCardPresenter<*> {
        return when (type) {
            is CardType.VIDEO -> VideoCardPresenter(context)
            is CardType.EPISODE -> EpisodeCardPresenter(context)
            is CardType.PLAYLIST -> PlaylistCardPresenter(context)
            is CardType.RECENTLY_WATCH -> RecentlyWatchCardPresenter(context)
        }
    }
}
