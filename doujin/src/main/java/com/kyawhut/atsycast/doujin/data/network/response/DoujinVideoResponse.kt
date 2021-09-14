package com.kyawhut.atsycast.doujin.data.network.response

import androidx.annotation.Keep
import androidx.leanback.widget.DiffCallback
import com.google.gson.annotations.SerializedName

/**
 * @author kyawhtut
 * @date 9/14/21
 */
@Keep
internal data class DoujinVideoResponse(
    @SerializedName("gid")
    val doujinID: String,
    @SerializedName("title")
    val doujinTitle: String,
    @SerializedName("cover")
    val doujinImage: String
) {
    companion object {
        val diff = object : DiffCallback<DoujinVideoResponse>() {
            override fun areItemsTheSame(
                oldItem: DoujinVideoResponse,
                newItem: DoujinVideoResponse
            ): Boolean {
                return oldItem.doujinID == newItem.doujinID
            }

            override fun areContentsTheSame(
                oldItem: DoujinVideoResponse,
                newItem: DoujinVideoResponse
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
