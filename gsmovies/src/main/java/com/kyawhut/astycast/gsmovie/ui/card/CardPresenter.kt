package com.kyawhut.astycast.gsmovie.ui.card

import android.content.Context
import com.kyawhut.astycast.gsmovie.data.network.response.VideoEpisodeResponse
import com.kyawhut.astycast.gsmovie.data.network.response.VideoResponse
import com.kyawhut.atsycast.share.base.BaseCardPresenter
import com.kyawhut.atsycast.share.base.BaseCardPresenterSelector

/**
 * @author kyawhtut
 * @date 9/29/21
 */
internal class CardPresenter(
    context: Context,
    private val poster: String = ""
) : BaseCardPresenterSelector<CardType>(context) {

    override fun getCardType(item: Any?): CardType {
        return when (item) {
            is VideoResponse.Data -> CardType.VIDEO
            is VideoEpisodeResponse -> CardType.EPISODE
            else -> throw RuntimeException("Unknown item type => $item")
        }
    }

    override fun getPresenter(type: CardType, context: Context): BaseCardPresenter<*> {
        return when (type) {
            is CardType.VIDEO -> VideoCardPresenter(context)
            is CardType.EPISODE -> EpisodeCardPresenter(context, poster)
        }
    }
}
