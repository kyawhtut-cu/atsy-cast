package com.kyawhut.atsycast.gs_mm.ui.card

import android.content.Context
import com.kyawhut.atsycast.gs_mm.ui.card.view.VideoCardView
import com.kyawhut.atsycast.share.base.BaseCardPresenter

/**
 * @author kyawhtut
 * @date 9/29/21
 */
internal class VideoCardPresenter(context: Context) : BaseCardPresenter<VideoCardView>(context) {

    override fun onCreateView(): VideoCardView {
        return VideoCardView(context)
    }
}
