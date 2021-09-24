package com.kyawhut.atsycast.ui.splash

import android.content.Context
import com.kyawhut.atsycast.data.network.response.UpdateResponse
import com.kyawhut.atsycast.data.network.response.UserResponse
import com.kyawhut.atsycast.share.network.utils.NetworkResponse

/**
 * @author kyawhtut
 * @date 9/17/21
 */
interface SplashRepository {

    suspend fun checkDeviceStatus(
        context: Context,
        callback: (NetworkResponse<UserResponse.Data?>) -> Unit
    )

    suspend fun checkUpdate(
        context: Context,
        callback: (NetworkResponse<UpdateResponse.Data?>) -> Unit
    )
}
