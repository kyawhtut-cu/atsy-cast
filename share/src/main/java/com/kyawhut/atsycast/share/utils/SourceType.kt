package com.kyawhut.atsycast.share.utils

/**
 * @author kyawhtut
 * @date 9/8/21
 */
sealed class SourceType(val type: String) {
    object MSUB_PC : SourceType("msub_pc")
    object ZCM : SourceType("z_channel")
    object MSYS : SourceType("msys")
    object ET2SMM : SourceType("et2smm")
    data class GS_API_SOURCE(val key: String) : SourceType(key)
    object UNKNOWN : SourceType("unknown")

    companion object {
        fun getSourceType(type: String): SourceType {
            return when (type) {
                MSUB_PC.type -> MSUB_PC
                ZCM.type -> ZCM
                MSYS.type -> MSYS
                ET2SMM.type -> ET2SMM
                UNKNOWN.type -> UNKNOWN
                else -> GS_API_SOURCE(type)
            }
        }
    }
}
