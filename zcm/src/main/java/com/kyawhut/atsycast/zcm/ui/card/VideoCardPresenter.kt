package com.kyawhut.atsycast.zcm.ui.card

import android.content.Context
import com.kyawhut.atsycast.share.base.BaseCardPresenter
import com.kyawhut.atsycast.zcm.ui.card.view.VideoCardView

/**
 * @author kyawhtut
 * @date 9/10/21
 */
internal class VideoCardPresenter(context: Context) : BaseCardPresenter<VideoCardView>(context) {

    override fun onCreateView(): VideoCardView {
        return VideoCardView(context)
    }
}
