package com.kyawhut.atsycast.share.utils.extension

import android.view.View
import android.view.ViewGroup
import androidx.databinding.BindingAdapter

/**
 * @author kyawhtut
 * @date 8/31/21
 */
object ViewExtension {

    @JvmStatic
    @BindingAdapter("isTv")
    fun View.isTv(isTv: Boolean?) {
        this.apply {
            if (this is ViewGroup)
                descendantFocusability = if (isTv == true) ViewGroup.FOCUS_AFTER_DESCENDANTS
                else ViewGroup.FOCUS_BLOCK_DESCENDANTS
            isFocusable = isTv == true
            isFocusableInTouchMode = isTv == true
            isEnabled = isTv == true
        }
    }
}
