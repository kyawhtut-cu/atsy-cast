package com.kyawhut.atsycast.gs_mm.ui.videogrid

import android.content.Context
import com.kyawhut.atsycast.gs_mm.R
import com.kyawhut.atsycast.gs_mm.data.network.GSMMAPI
import com.kyawhut.atsycast.gs_mm.data.network.response.VideoResponse
import com.kyawhut.atsycast.share.network.request.scriptRequest
import com.kyawhut.atsycast.share.network.utils.NetworkError
import com.kyawhut.atsycast.share.network.utils.NetworkResponse
import com.kyawhut.atsycast.share.network.utils.execute
import com.kyawhut.atsycast.share.telegram.utils.TelegramHelper
import com.kyawhut.atsycast.share.utils.Crashlytics
import javax.inject.Inject

/**
 * @author kyawhtut
 * @date 9/10/21
 */
internal class VideoRepositoryImpl @Inject constructor(
    private val api: GSMMAPI,
    private val crashlytics: Crashlytics,
) : VideoRepository {

    override suspend fun getVideoList(
        context: Context,
        categoryID: String,
        route: String,
        page: Int,
        callback: (NetworkResponse<List<VideoResponse>>) -> Unit
    ) {
        NetworkResponse.loading(callback)
        val response = execute(crashlytics) {
            api.getVideoListByID(
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
                TelegramHelper.sendLog(
                    context,
                    "<strong>Video List</strong> is null. Please check in server script."
                )
            } else {
                NetworkResponse.success(
                    response.data ?: listOf(),
                    callback
                )
            }
        } else NetworkResponse.error(response.error, callback)
    }
}
