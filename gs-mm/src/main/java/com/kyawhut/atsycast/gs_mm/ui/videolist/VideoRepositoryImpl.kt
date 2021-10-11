package com.kyawhut.atsycast.gs_mm.ui.videolist

import android.content.Context
import com.kyawhut.atsycast.gs_mm.R
import com.kyawhut.atsycast.gs_mm.data.network.GSMMAPI
import com.kyawhut.atsycast.gs_mm.data.network.response.DrawerDetailResponse
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
        drawerKey: String,
        route: String,
        callback: (NetworkResponse<List<DrawerDetailResponse.Data>>) -> Unit
    ) {
        NetworkResponse.loading(callback)
        val response = execute(crashlytics) {
            api.getDrawerDetail(
                scriptRequest {
                    this.route = route
                    payload = mutableMapOf(
                        "drawer_key" to drawerKey,
                        "sub_route" to "drawerDetail"
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
