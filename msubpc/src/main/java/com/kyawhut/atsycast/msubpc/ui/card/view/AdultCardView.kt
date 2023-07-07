package com.kyawhut.atsycast.msubpc.ui.card.view

import android.content.Context
import com.kyawhut.atsycast.msubpc.R
import com.kyawhut.atsycast.msubpc.data.network.response.AdultResponse
import com.kyawhut.atsycast.msubpc.databinding.CardMoviesBinding
import com.kyawhut.atsycast.share.base.BaseCardView
import com.kyawhut.atsycast.share.utils.extension.Extension.getDrawableValue

/**
 * @author kyawhtut
 * @date 9/6/21
 */
internal class AdultCardView(context: Context) : BaseCardView<CardMoviesBinding>(
    R.layout.card_movies, context
) {

    override fun setSelected(selected: Boolean) {
        super.setSelected(selected)
        vb.isFocus = selected
    }

    override fun bindUI(data: Any) {
        if (data is AdultResponse) {
            vb.apply {
                moviesName = data.adultTitle
                moviesPoster = data.adultImage
                movieIMDB = ""
                moviesResolution = ""
                moviesYear = data.adultYear
                moviesResolutionType = context.getDrawableValue(R.drawable.bg_resolution_default)
                executePendingBindings()
            }
        }
    }

}
