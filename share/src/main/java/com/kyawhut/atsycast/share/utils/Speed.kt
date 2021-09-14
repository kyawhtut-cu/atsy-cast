package com.kyawhut.atsycast.share.utils

import android.content.Context
import com.kyawhut.atsycast.share.R
import java.util.*

class Speed(private val context: Context) {

    private var _totalSpeed: Long = 0L
    private var _downSpeed: Long = 0L
    private var _upSpeed: Long = 0L

    private val _total by lazy { HumanSpeed() }
    private val _down by lazy { HumanSpeed() }
    private val _up by lazy { HumanSpeed() }

    var isSpeedUnitBits = false

    fun calcSpeed(timeTaken: Long, downBytes: Long, upBytes: Long) {
        var totalSpeed: Long = 0
        var downSpeed: Long = 0
        var upSpeed: Long = 0

        val totalBytes = downBytes + upBytes
        if (timeTaken > 0) {
            totalSpeed = totalBytes * 1000 / timeTaken
            downSpeed = downBytes * 1000 / timeTaken
            upSpeed = upBytes * 1000 / timeTaken
        }

        _totalSpeed = totalSpeed
        _downSpeed = downSpeed
        _upSpeed = upSpeed

        updateHumanSpeed()
    }

    private fun updateHumanSpeed() {
        _total.setSpeed(_totalSpeed)
        _down.setSpeed(_downSpeed)
        _up.setSpeed(_upSpeed)
    }

    fun getHumanSpeed(unit: String): HumanSpeed {
        return when (unit) {
            "up" -> _up
            "down" -> _down
            else -> _total
        }
    }

    inner class HumanSpeed {
        var speedValue: String = ""
        var speedUnit: String = ""

        fun setSpeed(_speed: Long) {
            val speed = if (isSpeedUnitBits) _speed * 8 else _speed
            when {
                speed < 1000000 -> {
                    speedUnit =
                        context.getString(if (isSpeedUnitBits) R.string.kbps else R.string.kBps)
                    speedValue = "${speed / 1000}"
                }
                speed >= 1000000 -> {
                    speedUnit =
                        context.getString(if (isSpeedUnitBits) R.string.Mbps else R.string.MBps)
                    speedValue = when {
                        speed < 10000000 -> {
                            "%.1f".format(Locale.ENGLISH, speed / 1000000.0)
                        }
                        speed < 100000000 -> {
                            "${speed / 1000000}"
                        }
                        else -> {
                            "99+"
                        }
                    }
                }
                else -> {
                    speedValue = "-"
                    speedUnit = "-"
                }
            }
        }
    }
}
