package com.kyawhut.atsycast.gs_mm.ui.home

import android.content.Context
import com.kyawhut.atsycast.gs_mm.data.network.response.DrawerResponse
import com.kyawhut.atsycast.share.network.utils.NetworkResponse

/**
 * @author kyawhtut
 * @date 9/9/21
 */
internal interface HomeRepository {

    fun isHasRecently(route: String): Boolean

    fun isHasWatchLater(route: String): Boolean

    suspend fun getHome(
        context: Context,
        route: String,
        callback: (NetworkResponse<List<DrawerResponse.Data>>) -> Unit
    )
}
