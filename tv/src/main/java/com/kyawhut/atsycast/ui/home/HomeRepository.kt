package com.kyawhut.atsycast.ui.home

import com.kyawhut.atsycast.data.network.response.HomeFeatureResponse
import com.kyawhut.atsycast.share.network.utils.NetworkResponse

/**
 * @author kyawhtut
 * @date 8/31/21
 */
interface HomeRepository {

    suspend fun getHomeFeatures(callback: (NetworkResponse<List<HomeFeatureResponse>>) -> Unit)
}
