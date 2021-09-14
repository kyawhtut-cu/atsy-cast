package com.kyawhut.atsycast.ui.home

import com.kyawhut.atsycast.data.network.SheetAPI
import com.kyawhut.atsycast.data.network.response.HomeFeatureResponse
import com.kyawhut.atsycast.share.network.utils.NetworkResponse
import com.kyawhut.atsycast.share.network.utils.execute
import javax.inject.Inject

/**
 * @author kyawhtut
 * @date 8/31/21
 */
class HomeRepositoryImpl @Inject constructor(
    private val sheetAPI: SheetAPI
) : HomeRepository {

    override suspend fun getHomeFeatures(callback: (NetworkResponse<List<HomeFeatureResponse>>) -> Unit) {
        NetworkResponse.loading(callback)
        val features = execute { sheetAPI.getHomeFeature() }
        features.post(callback)
    }
}
