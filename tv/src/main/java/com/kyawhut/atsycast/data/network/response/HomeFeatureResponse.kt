package com.kyawhut.atsycast.data.network.response

import androidx.annotation.Keep
import androidx.leanback.widget.DiffCallback
import com.google.gson.annotations.SerializedName

/**
 * @author kyawhtut
 * @date 8/31/21
 */
@Keep
data class HomeFeatureResponse(
    @SerializedName("status")
    val status: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: HashMap<String, List<Data>>,
) {

    @Keep
    data class Data(
        @SerializedName("feature_id")
        val featureID: String,
        @SerializedName("feature_name")
        val featureName: String,
        @SerializedName("feature_order")
        val featureOrder: Int,
        @SerializedName("feature_status")
        val featureStatus: Boolean,
        @SerializedName("feature_key")
        val featureKey: Int,
        @SerializedName("feature_cover")
        val featureCover: String,
        @SerializedName("feature_api_key")
        val featureAPIKey: String,
        @SerializedName("feature_required_password")
        val featureRequiredPassword: Boolean,
    )

    companion object {
        val diff = object : DiffCallback<Data>() {
            override fun areItemsTheSame(
                oldItem: Data,
                newItem: Data
            ): Boolean {
                return oldItem.featureID == newItem.featureID
            }

            override fun areContentsTheSame(
                oldItem: Data,
                newItem: Data
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
