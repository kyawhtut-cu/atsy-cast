package com.kyawhut.atsycast.data.network.response

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.kyawhut.atsycast.BuildConfig
import java.io.Serializable

/**
 * @author kyawhtut
 * @date 9/20/21
 */
@Keep
data class UpdateResponse(
    @SerializedName("status")
    val status: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: List<Data>,
) : Serializable {

    @Keep
    data class Data(
        @SerializedName("version_name")
        val versionName: String,
        @SerializedName("is_maintenance")
        val isMaintenance: Boolean,
        @SerializedName("update_message")
        val updatMessage: String,
        @SerializedName("maintenance_message")
        val maintenanceMessage: String,
    ) {
        val downloadURL: String
            get() = BuildConfig.UPDATE_URL
    }
}
