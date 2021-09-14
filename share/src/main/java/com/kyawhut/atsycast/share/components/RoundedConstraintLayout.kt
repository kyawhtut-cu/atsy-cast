package com.kyawhut.atsycast.share.components

import android.annotation.TargetApi
import android.content.Context
import android.content.res.TypedArray
import android.graphics.*
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.view.ViewOutlineProvider
import androidx.annotation.ColorInt
import androidx.constraintlayout.widget.ConstraintLayout
import com.kyawhut.atsycast.share.R

/**
 * @author kyawhtut
 * @date 9/3/21
 */
class RoundedConstraintLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var path: Path? = null

    /** corner radius */
    var cornerLeftTop: Float = 0F
        set(value) {
            field = value
            postInvalidate()
        }

    var cornerRightTop: Float = 0F
        set(value) {
            field = value
            postInvalidate()
        }

    var cornerLeftBottom: Float = 0F
        set(value) {
            field = value
            postInvalidate()
        }

    var cornerRightBottom: Float = 0F
        set(value) {
            field = value
            postInvalidate()
        }

    /** side option means top and bottom corner */

    /**
     * if left side value existed,
     * leftTop and leftBottom value is equal to leftSide value.
     * this is made in consideration of the custom attribute of motion layout.
     * because Constraint only has maximum two custom attribute. (2.0.0-beta2)
     */
    var cornerLeftSide: Float = 0F
        set(value) {
            field = value

            if (field != 0F) {
                cornerLeftTop = field
                cornerLeftBottom = field
            }

            postInvalidate()
        }

    var cornerRightSide: Float = 0F
        set(value) {
            field = value

            if (field != 0F) {
                cornerRightTop = field
                cornerRightBottom = field
            }

            postInvalidate()
        }


    var cornerAll: Float = 0F
        set(value) {
            field = value

            if (field != 0F) {
                cornerLeftSide = field
                cornerRightSide = field
            }

            postInvalidate()
        }

    /** background color */
    var backgroundColor: Int? = null
        set(@ColorInt value) {
            field = value
            postInvalidate()
        }

    override fun setBackgroundColor(color: Int) {
        backgroundColor = color
    }

    /** stroke & dash options */
    var strokeLineWidth: Float = 0F
        set(value) {
            field = value
            postInvalidate()
        }

    var strokeLineColor = 0XFFFFFFFF.toInt()
        set(@ColorInt value) {
            field = value
            postInvalidate()
        }

    var dashLineGap: Float = 0F
        set(value) {
            field = value
            postInvalidate()
        }

    var dashLineWidth: Float = 0F
        set(value) {
            field = value
            postInvalidate()
        }

    init {
        render(attrs)
    }

    private fun render(attrs: AttributeSet?) {
        attrs?.let {
            /** set corner radii */
            with(context.obtainStyledAttributes(it, R.styleable.RoundedConstraintLayout)) {
                cornerLeftTop = getPixelSize(R.styleable.RoundedConstraintLayout_cornerLeftTop)
                cornerRightTop = getPixelSize(R.styleable.RoundedConstraintLayout_cornerRightTop)
                cornerLeftBottom =
                    getPixelSize(R.styleable.RoundedConstraintLayout_cornerLeftBottom)
                cornerRightBottom =
                    getPixelSize(R.styleable.RoundedConstraintLayout_cornerRightBottom)
                backgroundColor = getColor(
                    R.styleable.RoundedConstraintLayout_backgroundColor, Color.TRANSPARENT
                )
                strokeLineWidth = getPixelSize(R.styleable.RoundedConstraintLayout_strokeLineWidth)
                strokeLineColor = getColor(
                    R.styleable.RoundedConstraintLayout_strokeLineColor, Color.BLACK
                )
                dashLineWidth = getPixelSize(R.styleable.RoundedConstraintLayout_dashLineWidth)
                dashLineGap = getPixelSize(R.styleable.RoundedConstraintLayout_dashLineGap)
                cornerLeftSide = getPixelSize(R.styleable.RoundedConstraintLayout_cornerLeftSide)
                cornerRightSide = getPixelSize(R.styleable.RoundedConstraintLayout_cornerRightSide)
                cornerAll = getPixelSize(R.styleable.RoundedConstraintLayout_cornerAll)
                recycle()
            }
        }
    }

    private fun TypedArray.getPixelSize(style: Int, defValue: Int = 0): Float {
        return getDimensionPixelSize(style, defValue).toFloat()
    }

    override fun dispatchDraw(canvas: Canvas) {
        /** for outline remake whenenver draw */
        path = null

        if (path == null) {
            path = Path()
        }

        floatArrayOf(
            cornerLeftTop, cornerLeftTop, cornerRightTop, cornerRightTop, cornerRightBottom,
            cornerRightBottom, cornerLeftBottom, cornerLeftBottom
        ).let {
            clipPathCanvas(canvas, it)
        }

        /** set drawable resource corner & background & stroke */
        with(GradientDrawable()) {
            cornerRadii = floatArrayOf(
                cornerLeftTop, cornerLeftTop, cornerRightTop, cornerRightTop,
                cornerRightBottom, cornerRightBottom, cornerLeftBottom, cornerLeftBottom
            )

            if (strokeLineWidth != 0F && strokeLineColor != null)
                setStroke(strokeLineWidth.toInt(), strokeLineColor!!, dashLineWidth, dashLineGap)

            background
            setColor(backgroundColor ?: Color.TRANSPARENT)

            background = this
        }

        outlineProvider = getOutlineProvider()

        clipChildren = false

        super.dispatchDraw(canvas)
    }

    private fun clipPathCanvas(canvas: Canvas, floatArray: FloatArray) {
        path?.let {
            it.addRoundRect(
                RectF(0F, 0F, canvas.width.toFloat(), canvas.height.toFloat()),
                floatArray,
                Path.Direction.CW
            )
            canvas.clipPath(it)
        }
    }

    /** For not showing red underline */
    override fun setOutlineProvider(provider: ViewOutlineProvider?) {
        super.setOutlineProvider(provider)
    }

    /** For not showing red underline */
    override fun setElevation(elevation: Float) {
        super.setElevation(elevation)
    }

    /** For not showing red underline */
    override fun setTranslationZ(translationZ: Float) {
        super.setTranslationZ(translationZ)
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    override fun getOutlineProvider(): ViewOutlineProvider {
        return object : ViewOutlineProvider() {
            override fun getOutline(view: View, outline: Outline) {
                path?.let {
                    outline.setConvexPath(it)
                } ?: throw Exception()
            }
        }
    }
}
