package com.kyawhut.atsycast.ets2mm.ui.card

import android.content.Context
import com.kyawhut.atsycast.ets2mm.ui.card.view.VideoCardView
import com.kyawhut.atsycast.share.base.BaseCardPresenter

/**
 * @author kyawhtut
 * @date 9/10/21
 */
internal class VideoCardPresenter(context: Context) : BaseCardPresenter<VideoCardView>(context) {

    override fun onCreateView(): VideoCardView {
        return VideoCardView(context)
    }
}
