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
) {
    companion object {
        val diff = object : DiffCallback<HomeFeatureResponse>() {
            override fun areItemsTheSame(
                oldItem: HomeFeatureResponse,
                newItem: HomeFeatureResponse
            ): Boolean {
                return oldItem.featureID == newItem.featureID
            }

            override fun areContentsTheSame(
                oldItem: HomeFeatureResponse,
                newItem: HomeFeatureResponse
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
