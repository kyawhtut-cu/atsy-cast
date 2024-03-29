package com.kyawhut.atsycast.zcm.ui.home

import android.content.Context
import com.kyawhut.atsycast.share.network.utils.NetworkResponse
import com.kyawhut.atsycast.zcm.data.network.response.GenresResponse

/**
 * @author kyawhtut
 * @date 9/9/21
 */
internal interface HomeRepository {

    suspend fun getHome(
        context: Context,
        apiKey: String,
        callback: (NetworkResponse<List<GenresResponse>>) -> Unit
    )

    val isHasRecently: Boolean

    val isHasWatchLater: Boolean
}
