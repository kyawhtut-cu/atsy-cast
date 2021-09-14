package com.kyawhut.atsycast.share.ui.card

import android.content.Context
import com.kyawhut.atsycast.share.base.BaseCardPresenter
import com.kyawhut.atsycast.share.ui.card.view.RecentlyWatchCardView

/**
 * @author kyawhtut
 * @date 9/9/21
 */
class RecentlyWatchCardPresenter(
    context: Context
) : BaseCardPresenter<RecentlyWatchCardView>(context) {

    override fun onCreateView(): RecentlyWatchCardView {
        return RecentlyWatchCardView(context)
    }
}
