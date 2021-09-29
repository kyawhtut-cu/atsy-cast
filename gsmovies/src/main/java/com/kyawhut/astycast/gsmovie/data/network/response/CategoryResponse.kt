package com.kyawhut.astycast.gsmovie.data.network.response

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

/**
 * @author kyawhtut
 * @date 9/29/21
 */
@Keep
internal data class CategoryResponse(
    @SerializedName("status")
    val status: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: List<Data>?
) {

    @Keep
    data class Data(
        @SerializedName("category_id")
        val categoryID: Int,
        @SerializedName("category_title")
        val categoryTitle: String
    )
}
