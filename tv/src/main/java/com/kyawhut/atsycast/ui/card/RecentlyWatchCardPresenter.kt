package com.kyawhut.atsycast.ui.card

import android.content.Context
import com.kyawhut.atsycast.share.base.BaseCardPresenter
import com.kyawhut.atsycast.ui.card.view.RecentlyWatchCardView

/**
 * @author kyawhtut
 * @date 10/5/21
 */
class RecentlyWatchCardPresenter(
    context: Context
) : BaseCardPresenter<RecentlyWatchCardView>(context) {

    override fun onCreateView(): RecentlyWatchCardView {
        return RecentlyWatchCardView(context)
    }
}
