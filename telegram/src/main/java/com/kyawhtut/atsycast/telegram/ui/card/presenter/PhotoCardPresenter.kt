package com.kyawhtut.atsycast.telegram.ui.card.presenter

import android.content.Context
import com.kyawhtut.atsycast.telegram.ui.card.view.PhotoCardView
import com.kyawhut.atsycast.share.base.BaseCardPresenter

/**
 * @author kyawhtut
 * @date 19/10/2022
 */
internal class PhotoCardPresenter(
    context: Context
) : BaseCardPresenter<PhotoCardView>(context) {

    override fun onCreateView(): PhotoCardView {
        return PhotoCardView(context)
    }
}
