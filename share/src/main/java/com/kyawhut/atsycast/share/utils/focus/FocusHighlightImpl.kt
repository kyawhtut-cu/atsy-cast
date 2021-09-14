package com.kyawhut.atsycast.share.utils.focus

import android.view.View
import com.kyawhut.atsycast.share.R

/**
 * @author kyawhtut
 * @date 9/3/21
 */
class FocusHighlightImpl(
    private val zoom: FocusZoom = FocusZoom.ZOOM_NONE,
    private val useDimmer: Boolean = false,
) : FocusHighlight {

    private fun getOrCreateAnimator(view: View): FocusDimmer {
        return (view.getTag(R.id.lbFocusAnimator) as FocusDimmer?) ?: FocusDimmer(
            view,
            zoom.scale,
            useDimmer
        ).also {
            view.setTag(R.id.lbFocusAnimator, it)
        }
    }

    override fun onInitializeView(view: View) {
        getOrCreateAnimator(view).animate(false, immediate = true)
    }

    override fun onItemFocused(view: View, hasFocus: Boolean) {
        getOrCreateAnimator(view).animate(hasFocus, false)
    }
}
