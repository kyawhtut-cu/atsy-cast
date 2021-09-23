package com.kyawhut.atsycast.eporner.ui.card

import android.content.Context
import com.kyawhut.atsycast.eporner.ui.card.view.VideoCardView
import com.kyawhut.atsycast.share.base.BaseCardPresenter

/**
 * @author kyawhtut
 * @date 9/14/21
 */
internal class VideoCardPresenter(context: Context) : BaseCardPresenter<VideoCardView>(context) {

    override fun onCreateView(): VideoCardView {
        return VideoCardView(context)
    }
}
