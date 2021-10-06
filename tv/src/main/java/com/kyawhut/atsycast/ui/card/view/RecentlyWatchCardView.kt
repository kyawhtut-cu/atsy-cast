package com.kyawhut.atsycast.ui.card.view

import android.content.Context
import androidx.core.view.updateLayoutParams
import com.kyawhut.atsycast.R
import com.kyawhut.atsycast.databinding.CardHomeRecentlyWatchBinding
import com.kyawhut.atsycast.share.base.BaseCardView
import com.kyawhut.atsycast.share.db.entity.RecentlyWatchEntity
import com.kyawhut.atsycast.share.utils.extension.Extension.convertDpToPixel

/**
 * @author kyawhtut
 * @date 10/5/21
 */
class RecentlyWatchCardView(context: Context) : BaseCardView<CardHomeRecentlyWatchBinding>(
    R.layout.card_home_recently_watch, context
) {

    private val realWidth: Int by lazy {
        context.convertDpToPixel(204f).toInt()
    }

    override fun setSelected(selected: Boolean) {
        super.setSelected(selected)
        vb.isFocus = selected
    }

    override fun bindUI(data: Any) {
        if (data is RecentlyWatchEntity) {
            vb.apply {
                moviesName = data.videoTitle
                moviesPoster = data.videoCover
                viewProgressPercent.updateLayoutParams {
                    this.width = data.getWidth(realWidth)
                }
                executePendingBindings()
            }
        }
    }
}
