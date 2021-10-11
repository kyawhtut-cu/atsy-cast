package com.kyawhut.atsycast.gs_mm.ui.card

import android.content.Context
import com.kyawhut.atsycast.gs_mm.ui.card.view.EpisodeCardView
import com.kyawhut.atsycast.share.base.BaseCardPresenter

/**
 * @author kyawhtut
 * @date 9/29/21
 */
internal class EpisodeCardPresenter(
    context: Context
) : BaseCardPresenter<EpisodeCardView>(context) {

    override fun onCreateView(): EpisodeCardView {
        return EpisodeCardView(context)
    }
}
