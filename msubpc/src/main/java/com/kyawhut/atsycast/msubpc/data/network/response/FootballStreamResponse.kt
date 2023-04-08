package com.kyawhut.atsycast.msubpc.data.network.response

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

/**
 * @author kyawhtut
 * @date 9/14/21
 */
@Keep
internal data class FootballStreamResponse(
    @SerializedName("id")
    val id: String,
    @SerializedName("football_id")
    val footballID: String,
    @SerializedName("quality")
    val quality: String,
    @SerializedName("link")
    val link: String,
    @SerializedName("referer")
    val referer: String?,
    @SerializedName("cusheader")
    val cusheader: String?,
    @SerializedName("cusheadervalue")
    val cusheaderValue: String?,
)
