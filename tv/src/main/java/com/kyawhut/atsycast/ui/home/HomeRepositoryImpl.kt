package com.kyawhut.atsycast.ui.home

import android.content.Context
import com.kyawhut.atsycast.data.network.SheetAPI
import com.kyawhut.atsycast.data.network.response.HomeFeatureResponse
import com.kyawhut.atsycast.share.network.utils.NetworkResponse
import com.kyawhut.atsycast.share.network.utils.execute
import com.kyawhut.atsycast.share.utils.ShareUtils.deviceID
import com.kyawhut.atsycast.share.utils.extension.Extension.devicePassword
import javax.inject.Inject

/**
 * @author kyawhtut
 * @date 8/31/21
 */
class HomeRepositoryImpl @Inject constructor(
    private val sheetAPI: SheetAPI
) : HomeRepository {

    override suspend fun getHomeFeatures(
        context: Context,
        callback: (NetworkResponse<HashMap<String, List<HomeFeatureResponse.Data>>>) -> Unit
    ) {
        NetworkResponse.loading(callback)
        val features = execute {
            sheetAPI.getHomeFeature(context.deviceID, context.devicePassword).data
        }
        features.post(callback)
    }
}
