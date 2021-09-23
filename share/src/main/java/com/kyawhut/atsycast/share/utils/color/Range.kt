package com.kyawhut.atsycast.share.utils.color

/**
 * @author kyawhtut
 * @date 9/17/21
 */
data class Range(
    @JvmField
    val start: Int,
    @JvmField
    val end: Int,
) {

    fun contain(value: Int): Boolean {
        return value in start..end
    }

    override fun toString(): String {
        return "start : %s, end : %s".format(start, end)
    }
}