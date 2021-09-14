package com.kyawhut.atsycast.share.ui.card.view

import android.content.Context
import androidx.core.view.updateLayoutParams
import com.kyawhut.atsycast.share.R
import com.kyawhut.atsycast.share.base.BaseCardView
import com.kyawhut.atsycast.share.databinding.CardRecentlyWatchBinding
import com.kyawhut.atsycast.share.db.entity.RecentlyWatchEntity
import com.kyawhut.atsycast.share.utils.extension.Extension.convertDpToPixel

/**
 * @author kyawhtut
 * @date 9/9/21
 */
class RecentlyWatchCardView(context: Context) : BaseCardView<CardRecentlyWatchBinding>(
    R.layout.card_recently_watch, context
) {

    private val realWidth: Int by lazy {
        context.convertDpToPixel(162f).toInt()
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
