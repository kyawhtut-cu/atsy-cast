package com.kyawhut.atsycast.share.base

import android.graphics.Bitmap
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.util.DisplayMetrics
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.leanback.app.BackgroundManager
import androidx.viewbinding.ViewBinding
import com.kyawhut.atsycast.share.R
import com.kyawhut.atsycast.share.utils.extension.Extension.getColorValue
import com.kyawhut.atsycast.share.utils.extension.Extension.loadImage

/**
 * @author kyawhtut
 * @date 8/31/21
 */
abstract class BaseTvActivity<VB : ViewBinding> : FragmentActivity() {

    open val layoutID: Int = -1
    protected lateinit var vb: VB

    private var mBackgroundManager: BackgroundManager? = null
    private var mDefaultBackground: Drawable? = null
    private var mBackgroundTask: Runnable? = null
    private var mMetrics: DisplayMetrics? = null
    private var mBackgroundURI: Uri? = null
    private val mHandler = Handler()
    private var isBlur: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (layoutID != -1) {
            vb = DataBindingUtil.setContentView(this, layoutID)
        }

        prepareBackgroundManager()
    }

    fun changeBackground(
        url: String? = null,
        drawable: Drawable? = null,
        bitmap: Bitmap? = null,
        isBlur: Boolean = true,
    ) {
        this.isBlur = isBlur
        when {
            drawable != null -> {
                mBackgroundManager?.drawable = drawable
            }
            bitmap != null -> {
                mBackgroundManager?.setBitmap(bitmap)
            }
            url != null || mBackgroundURI != null -> {
                mBackgroundURI = Uri.parse(url)
                mHandler.removeCallbacks(mBackgroundTask!!)
                mHandler.postDelayed(mBackgroundTask!!, 10)
            }
        }
    }

    private fun prepareBackgroundManager() {
        mBackgroundManager = BackgroundManager.getInstance(this)
        mBackgroundManager?.attach(window)
        mDefaultBackground = ColorDrawable(getColorValue(R.color.colorBackground))
        mBackgroundTask = Runnable {
            if (mBackgroundURI != null)
                updateBackground(mBackgroundURI.toString())
        }
        mMetrics = DisplayMetrics()
        windowManager?.defaultDisplay?.getMetrics(mMetrics)
        mBackgroundManager?.drawable = mDefaultBackground
    }

    private fun updateBackground(uri: String) {
        val width = mMetrics?.widthPixels
        val height = mMetrics?.heightPixels
        loadImage(uri, isBlur, true, mDefaultBackground, width, height) {
            mBackgroundManager?.setBitmap(it)
        }
    }

    private fun startBackgroundTimer() {
        mHandler.removeCallbacks(mBackgroundTask!!)
        mHandler.postDelayed(mBackgroundTask!!, 10)
    }

    override fun onResume() {
        startBackgroundTimer()
        super.onResume()
    }

    override fun onDestroy() {
        mHandler.removeCallbacks(mBackgroundTask!!)
        mBackgroundTask = null
        super.onDestroy()
    }

    override fun onStop() {
        mBackgroundManager?.release()
        super.onStop()
    }
}
