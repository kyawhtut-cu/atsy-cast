package com.kyawhtut.atsycast.telegram.utils

import java.util.concurrent.TimeUnit
import kotlin.math.log2
import kotlin.math.pow

/**
 * @author kyawhtut
 * @date 21/10/2022
 */
internal object TelegramUtils {

    fun formatAsFileSize(
        size: Long
    ) = log2(if (size != 0L) size.toDouble() else 1.0).toInt().div(10).let {
        val precision = when (it) {
            0 -> 0; 1 -> 1; else -> 2
        }
        val prefix = arrayOf("", "K", "M", "G", "T", "P", "E", "Z", "Y")
        String.format("%.${precision}f ${prefix[it]}B", size.toDouble() / 2.0.pow(it * 10.0))
    }

    fun timeUnitToFullTime(time: Long, timeUnit: TimeUnit): String {
        val day: Long = timeUnit.toDays(time)
        val hour: Long = timeUnit.toHours(time) % 24
        val minute: Long = timeUnit.toMinutes(time) % 60
        val second: Long = timeUnit.toSeconds(time) % 60
        return if (day > 0) {
            String.format("%dday %02d:%02d:%02d", day, hour, minute, second)
        } else if (hour > 0) {
            String.format("%02d:%02d:%02d", hour, minute, second)
        } else if (minute > 0) {
            String.format("%02d:%02d", minute, second)
        } else {
            String.format("0:%02d", second)
        }
    }
}
