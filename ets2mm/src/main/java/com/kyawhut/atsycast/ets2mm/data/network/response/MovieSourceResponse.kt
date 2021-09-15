package com.kyawhut.atsycast.ets2mm.data.network.response

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * @author kyawhtut
 * @date 9/15/21
 */
@Keep
internal data class MovieSourceResponse(
    @SerializedName("video_file_id")
    val videoFileID: Int,
    @SerializedName("label")
    val videoLabel: String,
    @SerializedName("stream_key")
    val videoStreamKey: String,
    @SerializedName("file_type")
    val videoFileType: String,
    @SerializedName("file_url")
    val videoURL: String,
) : Serializable
