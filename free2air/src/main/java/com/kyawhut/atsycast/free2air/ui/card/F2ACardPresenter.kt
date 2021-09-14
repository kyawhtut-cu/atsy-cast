package com.kyawhut.atsycast.free2air.ui.card

import android.content.Context
import com.kyawhut.atsycast.free2air.ui.card.view.F2ACardView
import com.kyawhut.atsycast.share.base.BaseCardPresenter

/**
 * @author kyawhtut
 * @date 9/1/21
 */
class F2ACardPresenter(context: Context) : BaseCardPresenter<F2ACardView>(context) {

    override fun onCreateView(): F2ACardView {
        return F2ACardView(context)
    }
}
