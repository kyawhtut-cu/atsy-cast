package com.kyawhut.atsycast.share.components.iosloading

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.Paint
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RoundRectShape

/**
 * @author kyawhtut
 * @date 12/23/20
 */
object ToolBox {

    fun roundedCornerRectOutlineWithColor(
        color: Int,
        cornerRadius: Float,
        strokeWidth: Float
    ): ShapeDrawable {
        val radius = floatArrayOf(
            cornerRadius, cornerRadius,
            cornerRadius, cornerRadius,
            cornerRadius, cornerRadius,
            cornerRadius, cornerRadius
        )

        val roundedCornerShape = RoundRectShape(radius, null, null)

        val shape = ShapeDrawable()
        shape.apply {
            paint.color = color
            setShape(roundedCornerShape)
            paint.strokeWidth = strokeWidth
            paint.style = Paint.Style.STROKE
        }

        return shape
    }

    fun roundedCornerRectWithColor(color: Int, cornerRadius: Float): ShapeDrawable {
        val radius = floatArrayOf(
            cornerRadius, cornerRadius,
            cornerRadius, cornerRadius,
            cornerRadius, cornerRadius,
            cornerRadius, cornerRadius
        )

        val roundedCornerShape = RoundRectShape(radius, null, null)
        return ShapeDrawable().apply {
            paint.color = color
            shape = roundedCornerShape
        }
    }

    fun roundedCornerRectWithColor(
        color: Int,
        topLeftRadius: Float,
        topRightRadius: Float,
        bottomRightRadius: Float,
        bottomLeftRadius: Float
    ): ShapeDrawable {
        val radius = floatArrayOf(
            topLeftRadius, topLeftRadius,
            topRightRadius, topRightRadius,
            bottomRightRadius, bottomRightRadius,
            bottomLeftRadius, bottomLeftRadius
        )

        val roundedCornerShape = RoundRectShape(radius, null, null)
        return ShapeDrawable().apply {
            paint.color = color
            shape = roundedCornerShape
        }
    }

    val screenWidth: Int
        get() = Resources.getSystem().displayMetrics.widthPixels

    val screenHeight: Int
        get() = Resources.getSystem().displayMetrics.heightPixels

    fun Context.getScreenOrientation(): Int {
        return this.resources.configuration.orientation
    }

    val Context.isLandscapeOrientation: Boolean
        get() = this.getScreenOrientation() == Configuration.ORIENTATION_LANDSCAPE
}
