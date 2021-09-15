package com.kyawhut.atsycast.ets2mm.ui.home

import com.kyawhut.atsycast.ets2mm.data.network.response.GenresResponse
import com.kyawhut.atsycast.share.network.utils.NetworkResponse

/**
 * @author kyawhtut
 * @date 9/9/21
 */
internal interface HomeRepository {

    suspend fun getHome(callback: (NetworkResponse<List<GenresResponse>>) -> Unit)

    val isHasRecently: Boolean

    val isHasWatchLater: Boolean
}
