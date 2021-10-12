package com.kyawhut.atsycast.gs_mm.ui.home

import android.content.Context
import com.kyawhut.atsycast.gs_mm.R
import com.kyawhut.atsycast.gs_mm.data.network.GSMMAPI
import com.kyawhut.atsycast.gs_mm.data.network.response.DrawerResponse
import com.kyawhut.atsycast.share.db.source.RecentlyWatchSource
import com.kyawhut.atsycast.share.db.source.WatchLaterSource
import com.kyawhut.atsycast.share.network.request.scriptRequest
import com.kyawhut.atsycast.share.network.utils.NetworkError
import com.kyawhut.atsycast.share.network.utils.NetworkResponse
import com.kyawhut.atsycast.share.network.utils.execute
import com.kyawhut.atsycast.share.telegram.utils.TelegramHelper
import com.kyawhut.atsycast.share.utils.Crashlytics
import com.kyawhut.atsycast.share.utils.SourceType
import javax.inject.Inject

/**
 * @author kyawhtut
 * @date 9/9/21
 */
internal class HomeRepositoryImpl @Inject constructor(
    private val api: GSMMAPI,
    private val recentlyWatchSource: RecentlyWatchSource,
    private val watchLaterSource: WatchLaterSource,
    private val crashlytics: Crashlytics,
) : HomeRepository {

    override fun isHasRecently(route: String): Boolean {
        return recentlyWatchSource.isHasRecentlyWatch(SourceType.getSourceType(route))
    }

    override fun isHasWatchLater(route: String): Boolean {
        return watchLaterSource.isHasWatchLater(SourceType.getSourceType(route))
    }

    override suspend fun getHome(
        context: Context,
        route: String,
        callback: (NetworkResponse<List<DrawerResponse.Data>>) -> Unit
    ) {
        NetworkResponse.loading(callback)
        val response = execute(crashlytics) {
            api.getDrawerMenu(scriptRequest {
                this.route = route
                payload = mutableMapOf(
                    "sub_route" to "drawerMenu"
                )
            }).data ?: listOf()
        }
        if (response.isSuccess) {
            if (response.data!!.isNotEmpty()) {
                NetworkResponse.success(response.data ?: listOf(), callback)
            } else {
                NetworkResponse.error(
                    NetworkError(context.getString(R.string.lbl_notify_to_developer)),
                    callback
                )
                TelegramHelper.sendLog(
                    context,
                    "<strong>Category List</strong> is null. Please check in server script."
                )
            }
        } else response.post(callback)
    }
}
