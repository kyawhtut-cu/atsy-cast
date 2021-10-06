package com.kyawhut.atsycast.msys.data.network.response

import androidx.annotation.Keep
import androidx.leanback.widget.DiffCallback
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * @author kyawhtut
 * @date 9/10/21
 */
@Keep
data class GenresResponse(
    @SerializedName("id")
    val genresID: Int,
    @SerializedName("title")
    val genresTitle: String
) : Serializable {

    companion object {
        val diff = object : DiffCallback<GenresResponse>() {
            override fun areItemsTheSame(
                oldItem: GenresResponse,
                newItem: GenresResponse
            ): Boolean {
                return oldItem.genresID == newItem.genresID
            }

            override fun areContentsTheSame(
                oldItem: GenresResponse,
                newItem: GenresResponse
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
