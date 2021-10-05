package com.kyawhut.atsycast.ui.setting

import android.content.Context
import com.kyawhut.atsycast.data.network.SheetAPI
import com.kyawhut.atsycast.share.network.request.scriptRequest
import com.kyawhut.atsycast.share.network.utils.execute
import com.kyawhut.atsycast.share.utils.ShareUtils.deviceID
import javax.inject.Inject

/**
 * @author kyawhtut
 * @date 9/16/21
 */
class SettingRepositoryImpl @Inject constructor(
    private val api: SheetAPI
) : SettingRepository {

    override suspend fun changeDisplayName(
        context: Context,
        name: String,
        callback: (Boolean, String) -> Unit
    ) {
        val response = execute {
            api.changeDisplayName(scriptRequest {
                route = "changeDisplayName"
                payload = mutableMapOf(
                    "device_id" to context.deviceID,
                    "display_name" to name
                )
            })
        }
        if (response.isSuccess) {
            if (response.data?.data != null) {
                callback(true, "Success")
            } else callback(false, "Change name error.")
        } else callback(
            false,
            if ((response.error?.resId ?: 0) != 0) context.getString(response.error?.resId ?: 0)
            else response.error?.message ?: ""
        )
    }
}
