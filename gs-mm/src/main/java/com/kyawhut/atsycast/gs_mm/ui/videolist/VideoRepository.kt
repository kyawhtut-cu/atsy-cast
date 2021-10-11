package com.kyawhut.atsycast.gs_mm.ui.videolist

import android.content.Context
import com.kyawhut.atsycast.gs_mm.data.network.response.DrawerDetailResponse
import com.kyawhut.atsycast.share.network.utils.NetworkResponse

/**
 * @author kyawhtut
 * @date 9/10/21
 */
internal interface VideoRepository {

    suspend fun getVideoList(
        context: Context,
        drawerKey: String,
        route: String,
        callback: (NetworkResponse<List<DrawerDetailResponse.Data>>) -> Unit
    )
}
