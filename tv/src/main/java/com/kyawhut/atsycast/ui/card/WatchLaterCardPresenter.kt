package com.kyawhut.atsycast.ui.card

import android.content.Context
import com.kyawhut.atsycast.share.base.BaseCardPresenter
import com.kyawhut.atsycast.ui.card.view.WatchLaterCardView

/**
 * @author kyawhtut
 * @date 10/5/21
 */
class WatchLaterCardPresenter(
    context: Context
) : BaseCardPresenter<WatchLaterCardView>(context) {

    override fun onCreateView(): WatchLaterCardView {
        return WatchLaterCardView(context)
    }
}
