package com.kyawhut.astycast.gsmovie.ui.video

import android.content.Context
import com.kyawhut.astycast.gsmovie.R
import com.kyawhut.astycast.gsmovie.data.network.GSAPI
import com.kyawhut.astycast.gsmovie.data.network.response.VideoResponse
import com.kyawhut.atsycast.share.network.request.scriptRequest
import com.kyawhut.atsycast.share.network.utils.NetworkError
import com.kyawhut.atsycast.share.network.utils.NetworkResponse
import com.kyawhut.atsycast.share.network.utils.execute
import com.kyawhut.atsycast.share.telegram.utils.TelegramHelper
import javax.inject.Inject

/**
 * @author kyawhtut
 * @date 9/10/21
 */
internal class VideoRepositoryImpl @Inject constructor(
    private val api: GSAPI
) : VideoRepository {

    override suspend fun getVideoList(
        context: Context,
        categoryID: Int,
        route: String,
        page: Int,
        callback: (NetworkResponse<List<VideoResponse.Data>>) -> Unit
    ) {
        NetworkResponse.loading(callback)
        val response = execute {
            api.getVideoListByCategoryID(
                scriptRequest {
                    this.route = route
                    payload = mutableMapOf(
                        "category_id" to categoryID,
                        "page" to page,
                        "sub_route" to "videoListByCategoryID"
                    )
                }
            ).data
        }
        if (response.isSuccess) {
            if (response.data == null) {
                NetworkResponse.error(
                    NetworkError(context.getString(R.string.lbl_notify_to_developer)),
                    callback
                )
                TelegramHelper.sendLog("<strong>Video List</strong> is null. Please check in server script.")
            } else {
                NetworkResponse.success(
                    response.data ?: listOf(),
                    callback
                )
            }
        } else NetworkResponse.error(response.error, callback)
    }
}
