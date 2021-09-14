package com.kyawhut.atsycast.share.ui.card

import android.content.Context
import com.kyawhut.atsycast.share.base.BaseCardPresenter
import com.kyawhut.atsycast.share.ui.card.view.ActionCardView

/**
 * @author kyawhtut
 * @date 9/7/21
 */
class ActionCardPresenter(context: Context) : BaseCardPresenter<ActionCardView>(context) {

    override fun onCreateView(): ActionCardView {
        return ActionCardView(context)
    }
}
