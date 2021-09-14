package com.kyawhut.atsycast.msubpc.ui.card

import android.content.Context
import com.kyawhut.atsycast.msubpc.ui.card.view.EpisodeCardView
import com.kyawhut.atsycast.share.base.BaseCardPresenter

/**
 * @author kyawhtut
 * @date 9/7/21
 */
internal class EpisodeCardPresenter(
    context: Context, private val poster: String
) : BaseCardPresenter<EpisodeCardView>(context) {

    override fun onCreateView(): EpisodeCardView {
        return EpisodeCardView(context, poster)
    }
}
