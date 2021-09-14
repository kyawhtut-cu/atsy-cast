package com.kyawhut.atsycast.share.utils.binding

import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.kyawhut.atsycast.share.components.IOSLoading

/**
 * @author kyawhtut
 * @date 9/7/21
 */
object BindingUtils {

    @JvmStatic
    @BindingAdapter("toggleLoading")
    fun IOSLoading.toggleLoading(isLoading: Boolean?) {
        this.apply {
            toggleAnimation(isLoading == true)
            isVisible = isLoading == true
        }
    }
}
