package com.kyawhut.atsycast.gs_mm.ui.card

import android.content.Context
import com.kyawhut.atsycast.gs_mm.ui.card.view.PlaylistCardView
import com.kyawhut.atsycast.share.base.BaseCardPresenter

/**
 * @author kyawhtut
 * @date 9/29/21
 */
internal class PlaylistCardPresenter(
    context: Context
) : BaseCardPresenter<PlaylistCardView>(context) {

    override fun onCreateView(): PlaylistCardView {
        return PlaylistCardView(context)
    }
}
