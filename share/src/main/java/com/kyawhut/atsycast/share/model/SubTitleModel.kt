package com.kyawhut.atsycast.share.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * @author kyawhtut
 * @date 10/8/21
 */
@Keep
data class SubTitleModel(
    @SerializedName("language")
    val language: String = "",
    @SerializedName("display_language")
    val displayLanguage: String = "",
    @SerializedName("language_url")
    val languageURL: String = ""
) : Serializable {
    var isSelected: Boolean = false
}
