package com.kyawhut.atsycast.share.components.iosloading

import android.content.Context
import android.os.Handler
import android.view.animation.RotateAnimation
import android.widget.RelativeLayout
import java.util.*

/**
 * Created by Zhang on 11/02/16.
 */
class LoadingIndicatorView(
    context: Context,
    val radius: Float,
    private val color: Int
) : RelativeLayout(context) {

    private val numberOfBars = 12
    var arrBars: ArrayList<LoadingIndicatorBarView>? = null
    var isAnimating = false
        private set
    private var currentFrame = 0
    private val animationHandler = Handler()
    private var playFrameRunnable: Runnable? = null

    fun initViews() {
        arrBars = ArrayList()
        for (i in 0 until numberOfBars) {
            val bar = LoadingIndicatorBarView(context, radius / 10.0f, color)
            arrBars!!.add(bar)
        }
    }

    fun initLayouts() {
        for (i in 0 until numberOfBars) {
            val bar = arrBars!![i]
            bar.id = generateViewId()
            val barLayoutParams = LayoutParams(
                (radius / 5.0f).toInt(),
                (radius / 2.0f).toInt()
            )
            barLayoutParams.addRule(ALIGN_PARENT_TOP)
            barLayoutParams.addRule(CENTER_HORIZONTAL)
            bar.layoutParams = barLayoutParams
        }
    }

    fun addViews() {
        for (i in 0 until numberOfBars) {
            val bar = arrBars!![i]
            addView(bar)
        }
    }

    fun spreadBars() {
        var degrees = 0
        for (i in arrBars!!.indices) {
            val bar = arrBars!![i]
            rotateBar(bar, degrees.toFloat())
            degrees += 30
        }
    }

    private fun rotateBar(bar: LoadingIndicatorBarView, degrees: Float) {
        val animation = RotateAnimation(0f, degrees, radius / 10.0f, radius)
        animation.duration = 0
        animation.fillAfter = true
        bar.animation = animation
        animation.start()
    }

    fun startAnimating() {
        alpha = 1.0f
        isAnimating = true
        playFrameRunnable = Runnable { playFrame() }

        // recursive function until isAnimating is false
        playFrame()
    }

    fun stopAnimating() {
        isAnimating = false
        alpha = 0.0f
        invalidate()
        playFrameRunnable = null
    }

    private fun playFrame() {
        if (isAnimating) {
            resetAllBarAlpha()
            updateFrame()
            animationHandler.postDelayed(playFrameRunnable!!, 150)
        }
    }

    private fun updateFrame() {
        if (isAnimating) {
            showFrame(currentFrame)
            currentFrame += 1
            if (currentFrame > 11) {
                currentFrame = 0
            }
        }
    }

    private fun resetAllBarAlpha() {
        var bar: LoadingIndicatorBarView?
        for (i in arrBars!!.indices) {
            bar = arrBars!![i]
            bar.alpha = 0.5f
        }
    }

    private fun showFrame(frameNumber: Int) {
        val indexes = getFrameIndexesForFrameNumber(frameNumber)
        gradientColorBarSets(indexes)
    }

    private fun getFrameIndexesForFrameNumber(frameNumber: Int): IntArray {
        return when (frameNumber) {
            0 -> {
                indexesFromNumbers(0, 11, 10, 9)
            }
            1 -> {
                indexesFromNumbers(1, 0, 11, 10)
            }
            2 -> {
                indexesFromNumbers(2, 1, 0, 11)
            }
            3 -> {
                indexesFromNumbers(3, 2, 1, 0)
            }
            4 -> {
                indexesFromNumbers(4, 3, 2, 1)
            }
            5 -> {
                indexesFromNumbers(5, 4, 3, 2)
            }
            6 -> {
                indexesFromNumbers(6, 5, 4, 3)
            }
            7 -> {
                indexesFromNumbers(7, 6, 5, 4)
            }
            8 -> {
                indexesFromNumbers(8, 7, 6, 5)
            }
            9 -> {
                indexesFromNumbers(9, 8, 7, 6)
            }
            10 -> {
                indexesFromNumbers(10, 9, 8, 7)
            }
            else -> {
                indexesFromNumbers(11, 10, 9, 8)
            }
        }
    }

    private fun indexesFromNumbers(i1: Int, i2: Int, i3: Int, i4: Int): IntArray {
        return intArrayOf(i1, i2, i3, i4)
    }

    private fun gradientColorBarSets(indexes: IntArray) {
        var alpha = 1.0f
        var barView: LoadingIndicatorBarView?
        for (i in indexes.indices) {
            val barIndex = indexes[i]
            barView = arrBars!![barIndex]
            barView.alpha = alpha
            alpha -= 0.125f
        }
        invalidate()
    }

    init {
        initViews()
        initLayouts()
        addViews()
        spreadBars()
    }
}
