package com.kyawhut.atsycast.share.telegram.response

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

/**
 * @author kyawhtut
 * @date 9/10/21
 */
@Keep
data class SendLogResponse(
    @SerializedName("ok")
    val status: Boolean,
    @SerializedName("result")
    val result: Result
) {
    @Keep
    data class Result(
        @SerializedName("message_id")
        val messageID: Int,
        @SerializedName("from")
        val fromUser: FromUser,
        @SerializedName("chat")
        val toUser: ToUser,
        @SerializedName("date")
        val date: Long,
        @SerializedName("text")
        val text: String
    )

    @Keep
    data class FromUser(
        @SerializedName("id")
        val id: Int,
        @SerializedName("is_bot")
        val isBot: Boolean,
        @SerializedName("first_name")
        val firstName: String,
        @SerializedName("username")
        val userName: String
    )

    @Keep
    data class ToUser(
        @SerializedName("id")
        val id: Int,
        @SerializedName("first_name")
        val firstName: String,
        @SerializedName("last_name")
        val lastName: String,
        @SerializedName("username")
        val userName: String,
        @SerializedName("type")
        val type: String
    )
}
