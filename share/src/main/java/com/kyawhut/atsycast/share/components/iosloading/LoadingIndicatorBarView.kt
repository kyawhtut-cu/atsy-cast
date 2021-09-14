package com.kyawhut.atsycast.share.components.iosloading

import android.content.Context
import android.widget.RelativeLayout

/**
 * Created by Zhang on 11/02/16.
 */
class LoadingIndicatorBarView(
    context: Context,
    private val cornerRadius: Float,
    private val color: Int
) : RelativeLayout(context) {

    private fun initViews() {
        background = ToolBox.roundedCornerRectWithColor(
            color, cornerRadius
        )
        alpha = 0.5f
    }

    fun resetColor() {
        background = ToolBox.roundedCornerRectWithColor(
            color, cornerRadius
        )
        alpha = 0.5f
    }

    init {
        initViews()
    }
}
