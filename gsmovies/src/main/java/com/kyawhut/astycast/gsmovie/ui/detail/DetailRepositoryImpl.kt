package com.kyawhut.astycast.gsmovie.ui.detail

import android.content.Context
import com.kyawhut.astycast.gsmovie.R
import com.kyawhut.astycast.gsmovie.data.network.GSAPI
import com.kyawhut.astycast.gsmovie.data.network.response.VideoDetailResponse
import com.kyawhut.atsycast.share.network.utils.NetworkError
import com.kyawhut.atsycast.share.network.utils.NetworkResponse
import com.kyawhut.atsycast.share.network.utils.execute
import com.kyawhut.atsycast.share.telegram.utils.TelegramHelper
import javax.inject.Inject

/**
 * @author kyawhtut
 * @date 9/10/21
 */
internal class DetailRepositoryImpl @Inject constructor(
    private val api: GSAPI,
) : DetailRepository {

    override suspend fun getVideoDetail(
        context: Context,
        route: String,
        videoID: String,
        callback: (NetworkResponse<VideoDetailResponse.Data>) -> Unit
    ) {
        NetworkResponse.loading(callback)
        val response = execute { api.getVideoDetail(route, videoID).data }
        if (response.isSuccess) {
            if (response.data == null) {
                NetworkResponse.error(
                    NetworkError(context.getString(R.string.lbl_notify_to_developer)),
                    callback
                )
                TelegramHelper.sendLog("<strong>Video Detail</strong> is null. Please check in server script.")
            } else {
                NetworkResponse.success(response.data!!, callback)
            }
        } else NetworkResponse.error(
            response.error,
            callback
        )
    }
}
