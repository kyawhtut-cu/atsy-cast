package com.kyawhut.atsycast.share.utils.extension

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Point
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.DisplayMetrics
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.preference.PreferenceManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.kyawhut.atsycast.share.R
import com.kyawhut.atsycast.share.glide.BlurTransformation
import com.kyawhut.atsycast.share.glide.GlideApp
import com.kyawhut.atsycast.share.utils.ShareUtils
import kotlin.math.roundToInt

/**
 * @author kyawhtut
 * @date 8/31/21
 */
object Extension {

    fun Context.getColorValue(resID: Int) = ContextCompat.getColor(this, resID)

    fun Fragment.getColorValue(resID: Int) = requireContext().getColorValue(resID)

    fun Context.getDrawableValue(@DrawableRes res: Int) = ContextCompat.getDrawable(this, res)

    var Context.isAdultOpen: Boolean
        get() = PreferenceManager.getDefaultSharedPreferences(this)
            .get(getString(R.string.pref_adult), false)
        set(value) = PreferenceManager.getDefaultSharedPreferences(this)
            .put(getString(R.string.pref_adult), value)

    var Context.devicePassword: String
        get() = PreferenceManager.getDefaultSharedPreferences(this)
            .get(ShareUtils.DEVICE_PASSWORD, "")
        set(value) = PreferenceManager.getDefaultSharedPreferences(this)
            .put(ShareUtils.DEVICE_PASSWORD, value)

    var Fragment.isAdultOpen: Boolean
        get() = requireContext().isAdultOpen
        set(value) {
            requireContext().isAdultOpen = value
        }

    fun Fragment.getDrawableValue(@DrawableRes res: Int) = requireContext().getDrawableValue(res)

    fun FragmentActivity?.loadImage(
        uri: String,
        isBlur: Boolean,
        isNeedToCache: Boolean = true,
        error: Drawable?,
        width: Int? = null,
        height: Int? = null,
        callback: (Bitmap) -> Unit
    ) {
        val data = GlideApp.with(this!!)
            .asBitmap()
            .load(uri).apply {
                if (isBlur) apply(RequestOptions.bitmapTransform(BlurTransformation(8, 3)))
            }
            .error(error).apply {
                if (isNeedToCache) diskCacheStrategy(DiskCacheStrategy.ALL)
                else diskCacheStrategy(DiskCacheStrategy.NONE)
            }
        if (width == null || height == null) {
            data.into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(
                    resource: Bitmap,
                    transition: Transition<in Bitmap>?
                ) {
                    callback(resource)
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                }
            })
        } else data.into(object : CustomTarget<Bitmap>(width, height) {
            override fun onResourceReady(
                resource: Bitmap,
                transition: Transition<in Bitmap>?
            ) {
                callback(resource)
            }

            override fun onLoadCleared(placeholder: Drawable?) {
            }
        })
    }

    val Activity.displayMetrics: DisplayMetrics
        get() {
            // display metrics is a structure describing general information
            // about a display, such as its size, density, and font scaling
            val displayMetrics = DisplayMetrics()

            if (Build.VERSION.SDK_INT >= 30) {
                display?.apply {
                    getRealMetrics(displayMetrics)
                }
            } else {
                // getMetrics() method was deprecated in api level 30
                windowManager.defaultDisplay.getMetrics(displayMetrics)
            }

            return displayMetrics
        }

    val Activity.screenSize: Point
        get() {
            val point = Point()
            displayMetrics.apply {
                point.x = widthPixels
                point.y = heightPixels
            }
            return point
        }


    // extension property to get screen width and height in dp
    val Activity.screenSizeInDp: Point
        get() {
            val point = Point()
            displayMetrics.apply {
                // screen width in dp
                point.x = (widthPixels / density).roundToInt()

                // screen height in dp
                point.y = (heightPixels / density).roundToInt()
            }

            return point
        }

    fun Context?.convertDpToPixel(dp: Float): Float {
        return dp * (this!!.resources.displayMetrics.densityDpi / 160f)
    }
}
