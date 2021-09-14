package com.kyawhut.atsycast.share.utils.player

/**
 * @author kyawhtut
 * @date 8/31/21
 */
enum class EnumResizeMode(@JvmField val value: String, val type: Int) {

    UNDEFINE("UNDEFINE", -1),
    FIT("Fit", 1),
    FILL("Fill", 2),
    ZOOM("Zoom", 3);

    companion object {
        @JvmStatic
        fun get(value: String?): EnumResizeMode {
            if (value == null) return UNDEFINE
            values().forEach {
                if (it.value == value) return it
            }
            return UNDEFINE
        }

        @JvmStatic
        fun get(type: Int?): EnumResizeMode {
            if (type == null) return UNDEFINE
            values().forEach {
                if (it.type == type) return it
            }
            return UNDEFINE
        }
    }
}
