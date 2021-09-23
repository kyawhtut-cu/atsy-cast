package com.kyawhut.atsycast.twod.data.network.response

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

/**
 * @author kyawhtut
 * @date 9/20/21
 */
@Keep
data class ThreeDHistoryResponse(
    @SerializedName("data")
    val threeDList: List<ThreeDResponse>
)
