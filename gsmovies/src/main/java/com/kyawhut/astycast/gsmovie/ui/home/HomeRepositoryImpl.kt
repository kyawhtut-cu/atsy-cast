package com.kyawhut.astycast.gsmovie.ui.home

import android.content.Context
import com.kyawhut.astycast.gsmovie.R
import com.kyawhut.astycast.gsmovie.data.network.GSAPI
import com.kyawhut.astycast.gsmovie.data.network.response.CategoryResponse
import com.kyawhut.atsycast.share.network.utils.NetworkError
import com.kyawhut.atsycast.share.network.utils.NetworkResponse
import com.kyawhut.atsycast.share.network.utils.execute
import com.kyawhut.atsycast.share.telegram.utils.TelegramHelper
import javax.inject.Inject

/**
 * @author kyawhtut
 * @date 9/9/21
 */
internal class HomeRepositoryImpl @Inject constructor(
    private val api: GSAPI,
) : HomeRepository {

    override suspend fun getHome(
        context: Context,
        route: String,
        callback: (NetworkResponse<List<CategoryResponse.Data>>) -> Unit
    ) {
        NetworkResponse.loading(callback)
        val response = execute { api.getCategory(route).data ?: listOf() }
        if (response.isSuccess) {
            if (response.data!!.isNotEmpty()) {
                NetworkResponse.success(response.data ?: listOf(), callback)
            } else {
                NetworkResponse.error(
                    NetworkError(context.getString(R.string.lbl_notify_to_developer)),
                    callback
                )
                TelegramHelper.sendLog("<strong>Category List</strong> is null. Please check in server script.")
            }
        } else response.post(callback)
    }
}
