package com.kyawhut.atsycast.share.components

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat
import com.kyawhut.atsycast.share.R
import com.kyawhut.atsycast.share.components.iosloading.LoadingIndicatorView

/**
 * @author kyawhtut
 * @date 12/23/20
 */
class IOSLoading @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : RelativeLayout(context, attrs) {

    private val color: Int
    private val size: Float

    init {
        val a = context.obtainStyledAttributes(attrs, R.styleable.IOSLoading, 0, 0)
        try {
            color = a.getResourceId(
                R.styleable.IOSLoading_loadingColor,
                R.color.colorBlack
            )
            size = a.getFloat(
                R.styleable.IOSLoading_loadingSize,
                40f
            )
        } finally {
            a.recycle()
        }
    }

    private val loadingView by lazy {
        LoadingIndicatorView(context, size, ContextCompat.getColor(context, color))
    }

    override fun onFinishInflate() {
        super.onFinishInflate()

        descendantFocusability = ViewGroup.FOCUS_BLOCK_DESCENDANTS

        setOnClickListener { }

        addView(loadingView)
        loadingView.layoutParams = LayoutParams(
            (loadingView.radius * 2f).toInt(),
            (loadingView.radius * 2f).toInt()
        ).apply {
            addRule(CENTER_IN_PARENT)
        }
        loadingView.stopAnimating()
    }

    fun toggleAnimation(bool: Boolean) {
        if (!bool) loadingView.stopAnimating()
        else loadingView.startAnimating()
//        isVisible = bool
    }

    fun toggleAnimation() {
        toggleAnimation(!loadingView.isAnimating)
    }
}
