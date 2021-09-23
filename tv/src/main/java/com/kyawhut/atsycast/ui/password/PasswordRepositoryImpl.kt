package com.kyawhut.atsycast.ui.password

import android.content.Context
import com.kyawhut.atsycast.R
import com.kyawhut.atsycast.data.network.SheetAPI
import com.kyawhut.atsycast.share.network.utils.execute
import com.kyawhut.atsycast.share.utils.ShareUtils.deviceID
import com.kyawhut.atsycast.share.utils.extension.Extension.devicePassword
import javax.inject.Inject

/**
 * @author kyawhtut
 * @date 9/16/21
 */
class PasswordRepositoryImpl @Inject constructor(
    private val api: SheetAPI
) : PasswordRepository {

    override suspend fun checkAdultPassword(
        context: Context,
        password: String,
        callback: (Boolean, String) -> Unit
    ) {
        val response = execute { api.checkAdultPassword(context.deviceID, password) }
        if (response.isSuccess) {
            if (response.data?.data != null) {
                callback(true, "Success")
            } else callback(false, context.getString(R.string.lbl_wrong_password))
        } else callback(
            false,
            if ((response.error?.resId ?: 0) != 0) context.getString(response.error?.resId ?: 0)
            else response.error?.message ?: ""
        )
    }

    override suspend fun checkDevicePassword(
        context: Context,
        password: String,
        callback: (Boolean, String) -> Unit
    ) {
        val response = execute { api.checkDevicePassword(context.deviceID, password) }
        if (response.isSuccess) {
            context.devicePassword = ""
            if (response.data?.data != null) {
                context.devicePassword = password
                callback(true, "Success")
            } else callback(false, context.getString(R.string.lbl_wrong_password))
        } else callback(
            false,
            if ((response.error?.resId ?: 0) != 0) context.getString(response.error?.resId ?: 0)
            else response.error?.message ?: ""
        )
    }
}
