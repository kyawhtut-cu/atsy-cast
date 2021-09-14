package com.kyawhut.atsycast.ui.card

import android.content.Context
import com.kyawhut.atsycast.share.base.BaseCardPresenter
import com.kyawhut.atsycast.ui.card.view.FeatureCardView

/**
 * @author kyawhtut
 * @date 8/31/21
 */
class FeaturePresenter(context: Context) : BaseCardPresenter<FeatureCardView>(context) {

    override fun onCreateView(): FeatureCardView {
        return FeatureCardView(context)
    }
}
