package com.kyawhut.atsycast.gs_mm.data.network.response

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

/**
 * @author kyawhtut
 * @date 10/8/21
 */
@Keep
data class DrawerResponse(
    @SerializedName("status")
    val status: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: List<Data>?
) {

    @Keep
    data class Data(
        @SerializedName("drawer_key")
        val drawerKey: String,
        @SerializedName("drawer_title")
        val drawerTitle: String,
        @SerializedName("drawer_type")
        val drawerType: String,
    )
}
