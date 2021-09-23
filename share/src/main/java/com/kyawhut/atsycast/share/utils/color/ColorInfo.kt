package com.kyawhut.atsycast.share.utils.color

/**
 * @author kyawhtut
 * @date 9/17/21
 */
data class ColorInfo(
    val hueRange: Range?,
    val saturationRange: Range?,
    val brightnessRange: Range?,
    val lowerBounds: List<Range> = mutableListOf()
)
