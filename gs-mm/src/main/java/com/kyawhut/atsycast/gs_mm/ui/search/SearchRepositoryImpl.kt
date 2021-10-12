package com.kyawhut.atsycast.gs_mm.ui.search

import android.content.Context
import com.kyawhut.atsycast.gs_mm.R
import com.kyawhut.atsycast.gs_mm.data.network.GSMMAPI
import com.kyawhut.atsycast.gs_mm.data.network.response.SearchResponse
import com.kyawhut.atsycast.share.network.request.scriptRequest
import com.kyawhut.atsycast.share.network.utils.NetworkError
import com.kyawhut.atsycast.share.network.utils.NetworkResponse
import com.kyawhut.atsycast.share.network.utils.execute
import com.kyawhut.atsycast.share.telegram.utils.TelegramHelper
import com.kyawhut.atsycast.share.utils.Crashlytics
import javax.inject.Inject

/**
 * @author kyawhtut
 * @date 9/7/21
 */
internal class SearchRepositoryImpl @Inject constructor(
    private val api: GSMMAPI,
    private val crashlytics: Crashlytics,
) : SearchRepository {

    override suspend fun search(
        context: Context,
        route: String,
        query: String,
        callback: (NetworkResponse<List<SearchResponse.Data>>) -> Unit
    ) {
        NetworkResponse.loading(callback)
        val response = execute(crashlytics) {
            api.search(scriptRequest {
                this.route = route
                payload = mutableMapOf(
                    "sub_route" to "search",
                    "key_word" to query
                )
            }).data
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
                NetworkResponse.success(response.data ?: listOf(), callback)
            }
        } else NetworkResponse.error(response.error, callback)
    }
}
