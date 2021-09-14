package com.kyawhut.atsycast.msubpc.ui.card

import android.content.Context
import com.kyawhut.atsycast.msubpc.ui.card.view.MoviesCardView
import com.kyawhut.atsycast.share.base.BaseCardPresenter

/**
 * @author kyawhtut
 * @date 9/6/21
 */
internal class MoviesCardPresenter(context: Context) : BaseCardPresenter<MoviesCardView>(context) {

    override fun onCreateView(): MoviesCardView {
        return MoviesCardView(context)
    }
}
