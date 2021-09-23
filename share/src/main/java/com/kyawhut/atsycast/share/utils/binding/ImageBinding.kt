package com.kyawhut.atsycast.share.utils.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.kyawhut.atsycast.share.R
import com.kyawhut.atsycast.share.glide.BlurTransformation
import com.kyawhut.atsycast.share.glide.GlideApp

/**
 * @author kyawhtut
 * @date 8/31/21
 */
object ImageBinding {

    @JvmStatic
    @BindingAdapter("loadImage", "isBlur", "isHorizontal", "isNeedToCache", requireAll = false)
    fun ImageView.loadImage(
        url: String?,
        isBlur: Boolean? = false,
        isHorizontal: Boolean? = true,
        isNeedToCache: Boolean? = true
    ) {
        if (url.isNullOrEmpty()) return
        GlideApp.with(this).load(url).apply {
            if (isNeedToCache == true) diskCacheStrategy(DiskCacheStrategy.ALL)
            else diskCacheStrategy(DiskCacheStrategy.NONE)
            if (isBlur == true) apply(RequestOptions.bitmapTransform(BlurTransformation(8, 3)))
        }
            .placeholder(if (isHorizontal == true) R.drawable.ic_thumbnail else R.drawable.ic_thumbnail)
            .error(if (isHorizontal == true) R.drawable.ic_thumbnail else R.drawable.ic_thumbnail)
            .into(this)
    }
}
