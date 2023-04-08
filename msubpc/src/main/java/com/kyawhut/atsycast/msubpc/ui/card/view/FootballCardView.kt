package com.kyawhut.atsycast.msubpc.ui.card.view

import android.content.Context
import com.kyawhut.atsycast.msubpc.R
import com.kyawhut.atsycast.msubpc.data.network.response.FootballResponse
import com.kyawhut.atsycast.msubpc.databinding.CardMsubpcFootballBinding
import com.kyawhut.atsycast.share.base.BaseCardView

/**
 * @author kyawhtut
 * @date 9/6/21
 */
internal class FootballCardView(context: Context) : BaseCardView<CardMsubpcFootballBinding>(
    R.layout.card_msubpc_football, context
) {

    override fun onFocus() {
        vb.cardFootball.strokeLineWidth = 2f
    }

    override fun onUnFocus() {
        vb.cardFootball.strokeLineWidth = 0f
    }

    override fun bindUI(data: Any) {
        if (data is FootballResponse) {
            vb.apply {
                football = data
                executePendingBindings()
            }
        }
    }

}
