package com.kyawhut.atsycast.share.utils.focus

import android.view.View
import androidx.leanback.graphics.ColorOverlayDimmer
import androidx.leanback.widget.ShadowOverlayContainer
import androidx.leanback.widget.ShadowOverlayHelper

/**
 * @author kyawhtut
 * @date 9/3/21
 */
class FocusDimmer(
    private val view: View,
    private val scale: Float,
    useDimmer: Boolean,
) {
    private val dimmer: ColorOverlayDimmer? = if (useDimmer) {
        ColorOverlayDimmer.createDefault(view.context)
    } else null
    private val wrapper: ShadowOverlayContainer? = if (view is ShadowOverlayContainer) {
        view
    } else null

    fun animate(isHasFocus: Boolean, immediate: Boolean) {
        if (wrapper != null) {
            wrapper.setShadowFocusLevel(2f)
        } else {
            ShadowOverlayHelper.setNoneWrapperShadowFocusLevel(view, if (isHasFocus) 1f else 0.25f)
        }

        dimmer?.let {
            it.setActiveLevel(if (isHasFocus) 1f else 0.25f)
            val color = it.paint.color
            if (wrapper != null) {
                wrapper.setOverlayColor(color)
            } else {
                ShadowOverlayHelper.setNoneWrapperOverlayColor(view, color)
            }
        }

        if (!immediate) {
            view.translationZ = if (isHasFocus) 1f else 0f
            view.animate().scaleX(if (isHasFocus) scale else 1f)
                .scaleY(if (isHasFocus) scale else 1f)
                .setDuration(150).start()
        }
    }
}
