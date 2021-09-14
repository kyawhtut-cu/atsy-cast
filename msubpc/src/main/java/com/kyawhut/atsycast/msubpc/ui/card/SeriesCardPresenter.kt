package com.kyawhut.atsycast.msubpc.ui.card

import android.content.Context
import com.kyawhut.atsycast.msubpc.ui.card.view.SeriesCardView
import com.kyawhut.atsycast.share.base.BaseCardPresenter

/**
 * @author kyawhtut
 * @date 9/6/21
 */
internal class SeriesCardPresenter(context: Context) : BaseCardPresenter<SeriesCardView>(context) {

    override fun onCreateView(): SeriesCardView {
        return SeriesCardView(context)
    }
}
