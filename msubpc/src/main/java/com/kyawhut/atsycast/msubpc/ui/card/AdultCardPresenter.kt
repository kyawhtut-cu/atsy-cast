package com.kyawhut.atsycast.msubpc.ui.card

import android.content.Context
import com.kyawhut.atsycast.msubpc.ui.card.view.AdultCardView
import com.kyawhut.atsycast.share.base.BaseCardPresenter

/**
 * @author kyawhtut
 * @date 9/6/21
 */
internal class AdultCardPresenter(context: Context) : BaseCardPresenter<AdultCardView>(context) {

    override fun onCreateView(): AdultCardView {
        return AdultCardView(context)
    }
}
