package com.kyawhtut.atsycast.telegram.ui.card.presenter

import android.content.Context
import com.kyawhtut.atsycast.telegram.ui.card.view.VideoCardView
import com.kyawhut.atsycast.share.base.BaseCardPresenter

/**
 * @author kyawhtut
 * @date 19/10/2022
 */
internal class VideoCardPresenter(
    context: Context
) : BaseCardPresenter<VideoCardView>(context) {

    override fun onCreateView(): VideoCardView {
        return VideoCardView(context)
    }
}
