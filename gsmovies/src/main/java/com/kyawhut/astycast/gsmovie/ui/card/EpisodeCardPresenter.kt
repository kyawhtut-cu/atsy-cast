package com.kyawhut.astycast.gsmovie.ui.card

import android.content.Context
import com.kyawhut.astycast.gsmovie.ui.card.view.EpisodeCardView
import com.kyawhut.atsycast.share.base.BaseCardPresenter

/**
 * @author kyawhtut
 * @date 9/29/21
 */
internal class EpisodeCardPresenter(
    context: Context,
    private val poster: String
) : BaseCardPresenter<EpisodeCardView>(context) {

    override fun onCreateView(): EpisodeCardView {
        return EpisodeCardView(context, poster)
    }
}
