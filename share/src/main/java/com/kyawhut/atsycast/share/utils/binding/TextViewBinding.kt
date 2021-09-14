package com.kyawhut.atsycast.share.utils.binding

import android.graphics.Typeface
import android.widget.TextView
import androidx.databinding.BindingAdapter

/**
 * @author kyawhtut
 * @date 9/2/21
 */
object TextViewBinding {

    @JvmStatic
    @BindingAdapter("isMMFont")
    fun TextView.applyMMFont(isMMFont: Boolean? = true) {
        if (isMMFont == false) return
        isMMFont?.let {
            this.typeface = Typeface.createFromAsset(context.assets, "fonts/mmrtext.ttf")
        }
    }
}
