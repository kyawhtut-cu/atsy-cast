package com.kyawhut.atsycast.ui.card.view

import android.content.Context
import com.kyawhut.atsycast.R
import com.kyawhut.atsycast.data.network.response.HomeFeatureResponse
import com.kyawhut.atsycast.databinding.CardFeaturesBinding
import com.kyawhut.atsycast.share.base.BaseCardView

/**
 * @author kyawhtut
 * @date 8/31/21
 */
class FeatureCardView(context: Context) : BaseCardView<CardFeaturesBinding>(
    R.layout.card_features, context
) {

    override fun bindUI(data: Any) {
        val feature = data as HomeFeatureResponse.Data
        vb.apply {
            featureLogo = feature.featureCover
            featureName = feature.featureName
        }.executePendingBindings()
    }

    override fun setSelected(selected: Boolean) {
        vb.isFocus = selected
    }
}
