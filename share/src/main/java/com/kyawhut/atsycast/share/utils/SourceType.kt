package com.kyawhut.atsycast.share.utils

/**
 * @author kyawhtut
 * @date 9/8/21
 */
enum class SourceType(val type: String) {
    MSUB_PC("msub_pc"),
    ZCM("z_channel"),
    MSYS("msys"),
    ET2SMM("et2smm"),
    UNKNOWN("unknown");

    companion object {
        fun getSourceType(type: String): SourceType {
            values().forEach {
                if (it.type == type) return it
            }
            throw RuntimeException("Unknown type => $type")
        }
    }
}
