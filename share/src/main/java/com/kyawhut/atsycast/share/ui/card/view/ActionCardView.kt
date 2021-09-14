package com.kyawhut.atsycast.share.ui.card.view

import android.content.Context
import com.kyawhut.atsycast.share.R
import com.kyawhut.atsycast.share.base.BaseCardView
import com.kyawhut.atsycast.share.databinding.CardActionBinding
import com.kyawhut.atsycast.share.model.ActionModel

/**
 * @author kyawhtut
 * @date 9/7/21
 */
class ActionCardView(context: Context) : BaseCardView<CardActionBinding>(
    R.layout.card_action, context
) {

    override fun setSelected(selected: Boolean) {
        super.setSelected(selected)
        vb.isFocus = selected
    }

    override fun bindUI(data: Any) {
        val action = data as ActionModel
        vb.apply {
            actionIcon = action.icon
            actionName = action.actionName
            executePendingBindings()
        }
    }
}
