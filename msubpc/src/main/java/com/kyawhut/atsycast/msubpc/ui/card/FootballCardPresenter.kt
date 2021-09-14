package com.kyawhut.atsycast.msubpc.ui.card

import android.content.Context
import com.kyawhut.atsycast.msubpc.ui.card.view.FootballCardView
import com.kyawhut.atsycast.share.base.BaseCardPresenter

/**
 * @author kyawhtut
 * @date 9/6/21
 */
internal class FootballCardPresenter(context: Context) :
    BaseCardPresenter<FootballCardView>(context) {

    override fun onCreateView(): FootballCardView {
        return FootballCardView(context)
    }
}
