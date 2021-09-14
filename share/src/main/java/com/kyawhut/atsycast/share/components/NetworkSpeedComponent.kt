package com.kyawhut.atsycast.share.components

import android.content.Context
import android.net.TrafficStats
import android.os.CountDownTimer
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.kyawhut.atsycast.share.databinding.NetworkSpeedBinding
import com.kyawhut.atsycast.share.utils.Speed

class NetworkSpeedComponent : FrameLayout {

    companion object {
        private const val TIME_OUT = 1000L
    }

    private var _lastRxBytes: Long = 0
    private var _lastTxBytes: Long = 0
    private var _lastTime: Long = 0

    private val _speed by lazy {
        Speed(context).apply {
            isSpeedUnitBits = true
        }
    }
    private val _speedCountDown by lazy {
        object : CountDownTimer(TIME_OUT, 1L) {

            override fun onTick(millisUntilFinished: Long) {
            }

            override fun onFinish() {
                calculateSpeed()
                start()
            }
        }
    }
    private val vb: NetworkSpeedBinding by lazy {
        NetworkSpeedBinding.inflate(LayoutInflater.from(context))
    }

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        addView(vb.root)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()

        calculateSpeed()
    }

    private fun calculateSpeed() {
        val currentRxBytes = TrafficStats.getTotalRxBytes()
        val currentTxBytes = TrafficStats.getTotalTxBytes()
        val usedRxBytes = currentRxBytes - _lastRxBytes
        val usedTxBytes = currentTxBytes - _lastTxBytes
        val currentTime = System.currentTimeMillis()
        val usedTime = currentTime - _lastTime

        _lastRxBytes = currentRxBytes
        _lastTxBytes = currentTxBytes
        _lastTime = currentTime

        _speed.calcSpeed(usedTime, usedRxBytes, usedTxBytes)

        changeSpeed(
            _speed.getHumanSpeed("total"),
            _speed.getHumanSpeed("down"),
            _speed.getHumanSpeed("up")
        )
    }

    private fun changeSpeed(total: Speed.HumanSpeed, down: Speed.HumanSpeed, up: Speed.HumanSpeed) {
        vb.apply {
            totalSpeed = total.speedValue
            totalSpeedUnit = total.speedUnit
            upSpeed = "%s: %s %s".format("Up", up.speedValue, up.speedUnit)
            downSpeed = "%s: %s %s".format("Down", down.speedValue, down.speedUnit)
            executePendingBindings()
        }
    }

    fun onPause() {
        _speedCountDown.cancel()
    }

    fun onResume() {
        _speedCountDown.start()
    }

    override fun onDetachedFromWindow() {
        _speedCountDown.cancel()
        super.onDetachedFromWindow()
    }
}
